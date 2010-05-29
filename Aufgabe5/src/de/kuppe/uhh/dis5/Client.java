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

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Client implements Runnable {

	private Random random;
	private int id;
	private int transactionCycles;
	private CyclicBarrier barrier;
	private PersistenceManager persistenceManager;

	public Client(PersistenceManager pm, int anID, int cycles, CyclicBarrier aBarrier) {
		persistenceManager = pm;
		id = anID;
		transactionCycles = cycles;
		barrier = aBarrier;
		random = new Random();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// wait until all others threads are ready
		try {
			barrier.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			return;
		} catch (BrokenBarrierException e1) {
			e1.printStackTrace();
			return;
		}
		
		String data = getClass().getName() + id;
		for (int i = 0; i < transactionCycles; i++) {
			long aTransactionID = persistenceManager.beginTransaction();
			
			int writeCycles = random.nextInt(31);
			for(int j = 0; j < writeCycles; j++) {
				//calculate the page id correctly so each client gets its own page range
				long pageId = (id * 10) + random.nextInt(10);
				persistenceManager.write(aTransactionID, pageId, data + " at " + System.currentTimeMillis());
				sleepRandom();
			}
			persistenceManager.commit(aTransactionID);
			sleepRandom();
		}
		
		System.out.println("Client " + id + " is done!");
	}
	
	// wait for a while before we start the next TA 
	private void sleepRandom() {
		try {
			Thread.sleep(random.nextInt(1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
	}
}
