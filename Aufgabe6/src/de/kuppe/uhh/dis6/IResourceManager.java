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
