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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentMap;

public class RedoLog {

	private ConcurrentMap<Long, Page> pages;
	private long logSequenceNumber = -1;
	
	public RedoLog(ConcurrentMap<Long, Page> pages2) {
		pages = pages2;
		
		// get file handle
		FileReader fr = null;
		try {
			fr = new FileReader(Log.PREFIX + Log.LOG);
		} catch (FileNotFoundException e1) {
			System.out.println("WARNING: No LOG file found!");
			return;
		}
		
		// read file character-wise
		StringBuffer buf = new StringBuffer();
		int c = -1;
		try {
			while((c = fr.read()) != -1) {
				buf.append((char) c);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		// create the log records and sort them by transaction into buckets
		Map<Long, TreeSet<LogRecord>> multiMap = new HashMap<Long, TreeSet<LogRecord>>();
		String[] str = buf.toString().split("\n");
		for (String string : str) {
			LogRecord record = new LogRecord(string);
			
			// figure out the current LogSequenceNumber
			if(record.getLogSequenceNumber() > logSequenceNumber) {
				logSequenceNumber = record.getLogSequenceNumber();
			}
			
			// _assuming a EOT record comes last_ it executes "redo" for all winning transaction right away
			if(record.isEOT()) {
				TreeSet<LogRecord> treeSet = multiMap.get(record.getTransactionId());
				for (LogRecord logRecord : treeSet) {
					Page page = pages.get(logRecord.getPageId());
					if(page == null) { // recreate missing page
						page = new Page(logRecord.getPageId(), logRecord.getLogSequenceNumber(), logRecord.getData());
						pages.put(page.getId(), page);
						System.out.println("INFO: Recreated page " + logRecord.getPageId() + " with LSN " + logRecord.getLogSequenceNumber());
					} else if(page.getLogSequenceNumber() < logRecord.getLogSequenceNumber()) { // update outdated page
						page.setData(logRecord.getData());
						page.setLogSequenceNumber(logRecord.getLogSequenceNumber());
						System.out.println("INFO: Redo of page " + logRecord.getPageId() + " with LSN " + logRecord.getLogSequenceNumber());
					}
				}
			} else if(record.isPage()) {
				TreeSet<LogRecord> treeSet = multiMap.get(record.getTransactionId());
				treeSet.add(record);
			} else if(record.isBOT()) {
				multiMap.put(record.getTransactionId(), new TreeSet<LogRecord>());
			}
		}
	}

	public ConcurrentMap<Long, Page> getPages() {
		return pages;
	}

	public long getLogSequenceNumber() {
		return logSequenceNumber + 1;
	}
}
