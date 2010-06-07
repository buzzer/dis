package de.rockel.uhh.dis5;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import de.rockel.uhh.dis5.LogRecord.LogType;

public class Log {

	public static final String LOG = "LOG";
	public static final String PREFIX = System.getProperty("basePath") + File.separator + "log" + File.separator;

	private FileWriter fw;
	
	public Log() {
		File path = new File(PREFIX);
		path.mkdirs();
		
		File file = new File(PREFIX + LOG);
		try {
			fw = new FileWriter(file, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writePage(long aLSN, long aTransactionId, long aPageId, String data) {
		try {
			fw.write(new LogRecord(LogType.PAGE, aLSN, aTransactionId, aPageId, data).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeBOT(long aLSN, long aTransactionId) {
		try {
			fw.write(new LogRecord(LogType.BOT, aLSN, aTransactionId).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeEOT(long aLSN, long aTransactionId) {
		try {
			fw.write(new LogRecord(LogType.EOT, aLSN, aTransactionId).toString());
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
