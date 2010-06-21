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
package de.kuppe.uhh.dis6;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ResourceManagerImpl implements IResourceManager {

	/* Resource Manager management */
	
	
	private static final String RESOURCE_MANAGER_LOG = "ResourceManagerLog";

	private static final boolean withAborts = Boolean.parseBoolean(System.getProperty("withAborts", "false"));

	private static final Map<Long, IResourceManager> managers = new HashMap<Long, IResourceManager>();
	
	public static IResourceManager getInstance(long aResourceManagerId) {
		synchronized (managers) {
			IResourceManager aResourceManager = managers.get(aResourceManagerId);
			if(aResourceManager == null) {
				ConcurrentMap<Long, Page> pages = Page.getPages(aResourceManagerId);
				RedoLog redolog = new RedoLog(RESOURCE_MANAGER_LOG + aResourceManagerId, pages);
				if(withAborts && (aResourceManagerId % 5) == 0) {
					System.out.println("INFO: ResourceManagerImpl.getInstance(long) returning AbortingResourceManagerImpl!");
					aResourceManager = new AbortingResourceManagerImpl(aResourceManagerId, redolog.getLogSequenceNumber(), redolog.getPages());
				} else {
					aResourceManager = new ResourceManagerImpl(aResourceManagerId, redolog.getLogSequenceNumber(), redolog.getPages());
				}
				managers.put(aResourceManagerId, aResourceManager);
			}
			return aResourceManager;
		}
	}
	
	/* Instance */
	
	private ConcurrentMap<Long, Page> pageBuffer;
	
	private Log logWriter;

	private ConcurrentMap<Long, Set<Long>> transactionIds2PageIds;

	private long resourceManagerId;

	protected ResourceManagerImpl(long aResourceManagerId, long initialValue, ConcurrentMap<Long, Page> pages) {
		resourceManagerId = aResourceManagerId;
		pageBuffer = pages;
		
		logWriter = new Log(initialValue, RESOURCE_MANAGER_LOG + aResourceManagerId);
		
		transactionIds2PageIds = new ConcurrentHashMap<Long, Set<Long>>();
	}
	
	/* (non-Javadoc)
	 * @see de.kuppe.uhh.dis6.IResourceManager#write(long, long, java.lang.String)
	 */
	public void write(long aTransactionId, long aPageId, String data) {
		Set<Long> set = transactionIds2PageIds.get(aTransactionId);
		if(set == null) {
			set = new HashSet<Long>();
			transactionIds2PageIds.put(aTransactionId, set);
			
			// a null Set also shows us that we haven't registered with the TM, lets do so and fail if unsuccessful
			ITransactionManager transactionManager = TransactionManagerImpl.getInstance();
			transactionManager.register(this, aTransactionId);
			logWriter.writeBOT(aTransactionId);
		}
		set.add(aPageId);
		
		final long aLogSequenceNumber = logWriter.writePage(aTransactionId, aPageId, data);
		// this doesn't have to be thread safe (put if absent...) 
		// cause of discrete page ranges per client
		// however put/remove
		Page aPage;
		if(pageBuffer.containsKey(aPageId)) {
			aPage = pageBuffer.get(aPageId);
			aPage.setLogSequenceNumber(aLogSequenceNumber);
			aPage.setData(data);
		} else {
			aPage = new Page(aPageId, aLogSequenceNumber, data);
			pageBuffer.put(aPageId, aPage);
		}
	}
	
	/* (non-Javadoc)
	 * @see de.kuppe.uhh.dis6.IResourceManager#commit(long)
	 */
	public void commit(long aTransactionId) {
		logWriter.writeEOT(aTransactionId);
		Set<Long> pageIds = (Set<Long>) transactionIds2PageIds.remove(aTransactionId);
		for (Long pageId : pageIds) {
			Page page = pageBuffer.get(pageId);
			page.flush(resourceManagerId);
		}
	}

	/* (non-Javadoc)
	 * @see de.kuppe.uhh.dis6.IResourceManager#prepare(long)
	 */
	public void prepare(long aTransactionId) {
		logWriter.writePrepare(aTransactionId);
	}

	/* (non-Javadoc)
	 * @see de.kuppe.uhh.dis6.IResourceManager#rollback(long)
	 */
	public void rollback(long aTransactionId) {
		logWriter.writeRollBack(aTransactionId);
		Set<Long> pageIds = (Set<Long>) transactionIds2PageIds.remove(aTransactionId);
		for (Long pageId : pageIds) {
			pageBuffer.remove(pageId);
		}
	}
}
