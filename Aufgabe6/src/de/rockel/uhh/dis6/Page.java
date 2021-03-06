package de.rockel.uhh.dis6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;


public class Page {
	
	private static final String PREFIX = System.getProperty("basePath") + File.separator + "pages";

	public static ConcurrentMap<Long, Page> getPages(long aPageCacheID) {
		File path = new File(PREFIX + File.separator + aPageCacheID);
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
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	
	public void flush(long aPageCacheID) {
		File path = new File(PREFIX + File.separator + aPageCacheID);
		path.mkdirs();
		
		File file = new File(PREFIX + File.separator + aPageCacheID + File.separator + getId() + ".page");
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(toString());
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
