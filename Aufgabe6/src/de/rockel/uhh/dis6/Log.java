package de.rockel.uhh.dis6;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import de.rockel.uhh.dis6.LogRecord.LogType;

public class Log {
	
	private final AtomicLong logSequenceNumber;

	public static final String PREFIX = System.getProperty("basePath") + File.separator + "log"+ File.separator;

	private FileWriter fw;
	
	public Log(long anInitialLogSequenceValue, String aFileName) {
		logSequenceNumber = new AtomicLong(anInitialLogSequenceValue);
		File path = new File(PREFIX);
		path.mkdirs();
		
		File file = new File(PREFIX + aFileName);
		try {
			fw = new FileWriter(file, true);
			// Make it little readable
			//fw.write("| LSN | LogType | TID | PID | Data |\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public long writePage(long aTransactionId, long aPageId, String data) {
		try {
			final long andLogSequenceNumber = logSequenceNumber.getAndIncrement();
			fw.write(new LogRecord(LogType.PAGE, andLogSequenceNumber, aTransactionId, aPageId, data).toString());
			return andLogSequenceNumber;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public void writeBOT(long aTransactionId) {
		try {
			fw.write(new LogRecord(LogType.BOT, logSequenceNumber.getAndIncrement(), aTransactionId).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeEOT(long aTransactionId) {
		try {
			fw.write(new LogRecord(LogType.EOT, logSequenceNumber.getAndIncrement(), aTransactionId).toString());
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writePrepare(long aTransactionId) {
		try {
			fw.write(new LogRecord(LogType.PREPARE, logSequenceNumber.getAndIncrement(), aTransactionId).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeRollBack(long aTransactionId) {
		try {
			fw.write(new LogRecord(LogType.ROLLBACK, logSequenceNumber.getAndIncrement(), aTransactionId).toString());
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
