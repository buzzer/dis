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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;


public class Page {
	
	private static final String PREFIX = System.getProperty("basePath") + File.separator + "pages" + File.separator;

	public static ConcurrentMap<Long, Page> getPages() {
		File path = new File(PREFIX);
		File[] listFiles = path.listFiles();
		if(listFiles == null) {
			listFiles = new File[0];
		}
		ConcurrentMap<Long, Page> pages = new ConcurrentHashMap<Long, Page>(listFiles.length);
		for (int i = 0; i < listFiles.length; i++) {
			File file = listFiles[i];
			FileReader fr;
			try {
				fr = new FileReader(file);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				return new ConcurrentHashMap<Long, Page>();
			}
			StringBuffer buf = new StringBuffer();
			int c = -1;
			try {
				while((c = fr.read()) != -1) {
					buf.append((char) c);
				}
			} catch (IOException e) {
				e.printStackTrace();
				return new ConcurrentHashMap<Long, Page>();
			}
			Page aPage = new Page(buf.toString());
			pages.put(aPage.getId(), aPage);
		}
		return pages;
	}
	
	private static final AtomicLong pageId = new AtomicLong();
	
	/* Instance */
	private long id;
	private long lsn;
	private String data;
	
	private boolean isDirty = true;

	/**
	 * Set of transaction ids that have touched this page
	 */
	private final Set<Long> transactionIds = new CopyOnWriteArraySet<Long>();

	
	public Page(long aLogSequenceNumber, String aString) {
		this(pageId.getAndIncrement(), aLogSequenceNumber, aString);
	}
	
	public Page(long pageId2, long aLogSequenceNumber, String aString) {
		id = pageId2;
		lsn = aLogSequenceNumber;
		data = aString;
	}
	
	private Page(String fromFile) {
		StringTokenizer tokenizer = new StringTokenizer(fromFile, ",");
		id = Long.parseLong(tokenizer.nextToken());
		lsn = Long.parseLong(tokenizer.nextToken());
		data = tokenizer.nextToken();
		isDirty = false; // when read from file, it ain't dirty
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	
	public long getLogSequenceNumber() {
		return lsn;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(id);
		builder.append(",");
		builder.append(lsn);
		builder.append(",");
		builder.append(data);
		return builder.toString();
	}

	/**
	 * @param lsn the lsn to set
	 */
	public void setLogSequenceNumber(long lsn) {
		this.lsn = lsn;
		isDirty = true;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
		isDirty = true;
	}
	
	public void markInUseBy(Long aTransactionId) {
		transactionIds.add(aTransactionId);
	}
	
	public boolean isInUse() {
		return transactionIds.size() > 0;
	}
	
	public void unUsedBy(Long aTransactionId) {
		transactionIds.remove(aTransactionId);
	}
	
	public void flush() {
		File path = new File(PREFIX);
		path.mkdirs();
		
		File file = new File(PREFIX + getId() + ".page");
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(toString());
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		isDirty = false;
	}

	public boolean isDirty() {
		return isDirty;
	}
}
