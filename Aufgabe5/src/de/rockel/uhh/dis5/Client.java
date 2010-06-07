package de.rockel.uhh.dis5;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Client implements Runnable {

	private Random random;
	private int id;
	private int transactionCycles;
	private CyclicBarrier barrier;
	private PersistenceManager persistenceManager;

	/**
	 * @param pm Persistence Manager
	 * @param anID Client ID
	 * @param cycles Transaction cycles
	 * @param aBarrier Clients' synchronization barrier
	 */
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
				// Calculate the page id correctly so each client gets its own page range.
				// Otherwise lock managment of DBMS has to be implemented as well.
				long pageId = (id * 10) + random.nextInt(10);
				persistenceManager.write(aTransactionID, pageId, data + " at " + System.currentTimeMillis());
				sleepRandom();
			}
			persistenceManager.commit(aTransactionID);
			sleepRandom();
		}
		
		System.out.println("Client " + id + " is done!");
	}
	
	/**
	 * Zwischen den einzelnen Operationen sollen die Clients kurze
	 * Pausen einlegen, um ein realis- tisches Verhalten anzunähern. 
	 */
	private void sleepRandom() {
		try {
			Thread.sleep(random.nextInt(1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
	}
}
