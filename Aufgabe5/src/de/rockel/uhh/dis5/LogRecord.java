/*******************************************************************************
 * based on template by
 *     Markus Alexander Kuppe (ecf-dev_eclipse.org <at> lemmster <dot> de) - initial API and implementation
 ******************************************************************************/
package de.rockel.uhh.dis5;

import java.util.StringTokenizer;

public class LogRecord implements Comparable<LogRecord> {
	
	public enum LogType {
		BOT, EOT, PAGE;
	}
	
	private final long logSequenceNumber;
	private final long transactionId;
	private final LogType logType;
	private final long pageId;
	private final String data;
	
	public LogRecord(String aString) {
		StringTokenizer tokenizer = new StringTokenizer(aString, ",");
		logSequenceNumber = Long.parseLong(tokenizer.nextToken());
		
		String token = tokenizer.nextToken();
		logType = Enum.valueOf(LogType.BOT.getDeclaringClass(), token);

		transactionId = Long.parseLong(tokenizer.nextToken());
		if(logType == LogType.PAGE) {
			pageId = Long.parseLong(tokenizer.nextToken());
			data = tokenizer.nextToken();
		} else {
			pageId = -1;
			data = "";
		}
	}
	
	public LogRecord(LogType aLogType, long aLSN, long aTransactionId) {
		this(aLogType, aLSN, aTransactionId, -1, "");
	}
	
	public LogRecord(LogType aLogType, long aLSN, long aTransactionId, long aPageId, String data2) {
		logType = aLogType;
		logSequenceNumber = aLSN;
		transactionId = aTransactionId;
		pageId = aPageId;
		data = data2;
	}
	
	/**
	 * @return the logSequenceNumber
	 */
	public long getLogSequenceNumber() {
		return logSequenceNumber;
	}

	/**
	 * @return the transactionId
	 */
	public long getTransactionId() {
		return transactionId;
	}

	/**
	 * @return the pageId
	 */
	public long getPageId() {
		return pageId;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	
	public boolean isEOT() {
		return logType == LogType.EOT;
	}

	public boolean isPage() {
		return logType == LogType.PAGE;
	}
	
	public boolean isBOT() {
		return logType == LogType.BOT;
	}
	
	/**
	 * @param o
	 * @return
	 */
	public int compareTo(LogRecord o) {
		Long l1 = new Long(getLogSequenceNumber());
		return l1.compareTo(o.getLogSequenceNumber());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(logSequenceNumber);
		builder.append(",");
		builder.append(logType);
		builder.append(",");
		builder.append(transactionId);
		builder.append(",");
		builder.append(pageId);
		builder.append(",");
		builder.append(data);
		builder.append("\n");
		return builder.toString();
	}
}
