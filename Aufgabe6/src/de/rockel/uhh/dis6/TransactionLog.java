package de.rockel.uhh.dis6;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class TransactionLog {
	
	public enum TransactionState {
		COMMITTED,
		ROLLED_BACK
	}
	private AtomicLong transactionId;
	private Map<Long, TransactionState> transactionState;
	private Log logWriter;

	public TransactionLog(String transactionManagerLog) {
		transactionState = new HashMap<Long, TransactionState>();
		
		// get file handle
		FileReader fr = null;
		try {
			fr = new FileReader(Log.PREFIX + transactionManagerLog);
		} catch (FileNotFoundException e1) {
			System.out.println("WARNING: No LOG file found for "
					+ transactionManagerLog + "!");
			transactionId = new AtomicLong();
			logWriter = new Log(0, transactionManagerLog);
			return;
		}

		System.out.println("INFO: Log file found for " + transactionManagerLog + "!");
		// read file character-wise
		StringBuffer buf = new StringBuffer();
		int c = -1;
		try {
			while ((c = fr.read()) != -1) {
				buf.append((char) c);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		// create the log records and sort them by transaction into buckets
		String[] str = buf.toString().split("\n");
		long transactionNumber = 0;
		long logSequenceNumber = 0;
		for (String string : str) {
			LogRecord record = new LogRecord(string);

			// figure out the highest/new transactionNumber
			if (record.getTransactionId() > transactionNumber) {
				transactionNumber = record.getLogSequenceNumber();
			}
			
			// figure out the current LogSequenceNumber
			if(record.getLogSequenceNumber() > logSequenceNumber) {
				logSequenceNumber = record.getLogSequenceNumber();
			}

			// we only need to add winners to the state list -> rest is false automatically
			if (record.isEOT()) {
				transactionState.put(record.getTransactionId(), TransactionState.COMMITTED);
			} else if(record.isRollback()) {
				transactionState.put(record.getTransactionId(), TransactionState.ROLLED_BACK);
			}
		}
		transactionId = new AtomicLong(transactionNumber + 1);
		logWriter = new Log(logSequenceNumber + 1, transactionManagerLog);
	}

	public long getAndIncrementTransactionId() {
		return transactionId.getAndIncrement();
	}

	public TransactionState getTransactionState(long aTransactionId) {
		return transactionState.get(aTransactionId);
	}

	/**
	 * @param aTransactionId
	 */
	public void writeBOT(long aTransactionId) {
		logWriter.writeBOT(aTransactionId);
	}

	/**
	 * @param aTransactionId
	 */
	public void writeRollBack(long aTransactionId) {
		logWriter.writeRollBack(aTransactionId);
		transactionState.put(aTransactionId, TransactionState.ROLLED_BACK);
	}

	/**
	 * @param aTransactionId
	 */
	public void writeEOT(long aTransactionId) {
		logWriter.writeEOT(aTransactionId);
		transactionState.put(aTransactionId, TransactionState.COMMITTED);
	}
}
