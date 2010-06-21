package de.rockel.uhh.dis6;

public interface IResourceManager {

	/**
	 * Schreibt die angegebenen Daten (Seite, Nutzdaten) im Rahmen der angegebenen Transaktion.
	 * 
	 * @param aTransactionId
	 * @param aPageID
	 * @param data
	 * @throws Meldung über Erfolg oder Misserfolg des Schreibzugriffs.
	 */
	public void write(long aTransactionId, long aPageID, String data);
	
	/**
	 * Weist zur Vorbereitung des endgültigen Festschreibens der angegebenen Transaktion an.
	 * 
	 * @param aTransactionId
	 * @throws Meldung über Erfolg oder Misserfolg der Vorbereitung, entsprechend 
	 * false für aborted und true für prepared
	 */
	public void prepare(long aTransactionId);
	
	/**
	 * Weist zum Festschreiben der angegebenen Transaktion an.
	 * 
	 * @param aTransactionId
	 * @throws Meldung über Erfolg oder Misserfolg des Festschreibens (true für ack).
	 */
	public void commit(long aTransactionId);
	
	/**
	 * Weist zum Abbruch der angegebenen Transaktion an.
	 * 
	 * @param aTransactionId
	 * @throws Meldung über Erfolg oder Misserfolg des Abbruchs (true für ack).
	 */
	public void rollback(long aTransactionId);
}
