/*******************************************************************************
 * Copyright (c) 2009 Markus Alexander Kuppe.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Markus Alexander Kuppe (ecf-dev_eclipse.org <at> lemmster <dot> de) - initial API and implementation
 ******************************************************************************/
package de.kuppe.uhh.dis5;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class PersistenceManager {
	private static final int UPPER_PAGE_LIMIT = 5;
	private static final int LATCH_COUNT = 5;

	private static final PersistenceManager instance;
	
	static  {
		ConcurrentMap<Long, Page> pages = Page.getPages();
		RedoLog redolog = new RedoLog(pages);
		instance = new PersistenceManager(redolog.getLogSequenceNumber(), redolog.getPages());
	}
	
	public static PersistenceManager getInstance() {
		return instance;
	}
	
	/* Instance */
	
	private ConcurrentMap<Long, Page> pageBuffer;
	
	private AtomicLong logSequenceNumber;
	private AtomicLong transactionId;
	
	private Log logWriter;

	private CountDownLatch latch;
	private ConcurrentMap<Long, Set<Long>> transactionIds2PageIds;
	
	private PersistenceManager(long initialValue, ConcurrentMap<Long, Page> pages) {
		pageBuffer = pages;
		
		logSequenceNumber = new AtomicLong(initialValue);
		transactionId = new AtomicLong();
		
		logWriter = new Log();
		
		transactionIds2PageIds = new ConcurrentHashMap<Long, Set<Long>>();
		
		latch = new CountDownLatch(LATCH_COUNT);
		Thread t = new Thread(new AsyncFlusher());
		t.start();
	}
	
	public long beginTransaction() {
		long currentTransactionId = transactionId.getAndIncrement();
		logWriter.writeBOT(logSequenceNumber.getAndIncrement(), currentTransactionId);
		
		transactionIds2PageIds.put(currentTransactionId, new HashSet<Long>());
		
		return currentTransactionId;
	}
	
	public void write(long aTransactionId, long aPageId, String data) {
		long aLogSequenceNumber = logSequenceNumber.getAndIncrement();
		logWriter.writePage(aLogSequenceNumber, aTransactionId, aPageId, data);

		Set<Long> set = transactionIds2PageIds.get(aTransactionId);
		set.add(aPageId);
		
		// this doesn't have to be thread safe (put if absent...) 
		// cause of discrete page ranges per client
		// however put/remove
		Page aPage;
		if(pageBuffer.containsKey(aPageId)) {
			aPage = pageBuffer.get(aPageId);
			aPage.markInUseBy(aTransactionId);
			aPage.setLogSequenceNumber(aLogSequenceNumber);
			aPage.setData(data);
		} else {
			aPage = new Page(aLogSequenceNumber, data);
			aPage.markInUseBy(aTransactionId);
			pageBuffer.put(aPageId, aPage);
		}
		
		// TODO this makes more sense in commit() but the exercise says write() %-)
		latch.countDown();
	}
	
	public void commit(long aTransactionId) {
		logWriter.writeEOT(logSequenceNumber.getAndIncrement(), aTransactionId);
		Set<Long> pageIds = (Set<Long>) transactionIds2PageIds.remove(aTransactionId);
		for (Long pageId : pageIds) {
			Page page = pageBuffer.get(pageId);
			page.unUsedBy(aTransactionId);
		}
	}
	
	private class AsyncFlusher implements Runnable {
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			while(Activator.getDefault().isActive()) {
				try {
					latch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
				
				// flush if page limit is reached
				if(pageBuffer.size() > UPPER_PAGE_LIMIT) {
					for (Page page : pageBuffer.values()) {
						if(!page.isInUse() && page.isDirty()) {
							page.flush();
							System.out.println("INFO: Flushed page " + page.getId() + " to disk!");
						} else if(page.isInUse() && page.isDirty()) {
							System.out.println("INFO: Not flushed page " + page.getId() + " to disk because still in use!");
						} else {
							System.out.println("INFO: Not flushed page " + page.getId() + " since it isn't dirty");
						}
					}
				}
				
				latch = new CountDownLatch(LATCH_COUNT);
			}
		}
	}
}
