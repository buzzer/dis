package de.rockel.uhh.dis6;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Client implements Runnable {

	final private Random random;
	final private int id;
	final private int transactionCycles;
	final private CyclicBarrier barrier;

	public Client(int anID, int cycles, CyclicBarrier aBarrier) {
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

		final ITransactionManager transactionManager = TransactionManagerImpl.getInstance();
		final String data = getClass().getName() + id;
		for (int i = 0; i < transactionCycles; i++) {
			long aTransactionId = transactionManager.begin();
			try {
				int resourceManagers = random.nextInt(10);
				for(int j = 0; j < resourceManagers; j++) {
					long resourceManagerId = (id * 10) + random.nextInt(10);
					IResourceManager resourceManager = ResourceManagerImpl.getInstance(resourceManagerId);
					int writeCycles = random.nextInt(31);
					for (int k = 0; k < writeCycles; k++) {
						long pageId = (id * 10) + random.nextInt(10);
						resourceManager.write(aTransactionId, pageId, data + " at " + System.currentTimeMillis());
					}
					sleepRandom();
				}
				transactionManager.commit(aTransactionId);
				sleepRandom();
			} catch(XAException e) {
				System.out.println("WARNING: Transaction failed with " + e.getMessage());
				if(transactionManager.isOpen(aTransactionId) == true) {
					transactionManager.rollback(aTransactionId);
				}
			}
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
