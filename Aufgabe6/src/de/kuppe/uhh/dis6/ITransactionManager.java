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

public interface ITransactionManager {

	/**
	 * Beginnt eine neue Transaktion
	 * 
	 * @return Transaktions-ID der neuen Transaktion
	 */
	public long begin();
	
	/**
	 * Leitet das Festschreiben der angegebenen laufenden Transaktion ein.
	 * 
	 * @param aTransactionId
	 * @throws Meldung über Erfolg oder Misserfolg des Abschlusses
	 */
	public void commit(long aTransactionId);
	
	/**
	 * Leitet den Abbruch der angegebenen laufenden Transaktion ein
	 * 
	 * @param aTransactionId
	 * @throws Meldung über Erfolg oder Misserfolg des Abbruchs
	 */
	public void rollback(long aTransactionId);
	
	/**
	 * Registriert den angegebenen Ressourcen-Manager als Teilnehmer der angegebenen Transaktion.
	 * 
	 * @param aResourceManager
	 * @param aTransactionId
	 * @throws Meldung über Erfolg oder Misserfolg der Registrierung
	 */
	public void register(IResourceManager aResourceManager, long aTransactionId);

	/**
	 * @param aTransactionId
	 * @return true if the transaction has been committed successfully
	 */
	boolean getTransactionState(long aTransactionId);

	
	/**
	 * @param aTransactionId
	 * @return true if transaction is still open
	 */
	public boolean isOpen(long aTransactionId);
}
