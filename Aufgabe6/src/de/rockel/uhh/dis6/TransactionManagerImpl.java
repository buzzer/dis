package de.rockel.uhh.dis6;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.rockel.uhh.dis6.TransactionLog.TransactionState;

public class TransactionManagerImpl implements ITransactionManager {

	/* Singleton */

	private static final TransactionManagerImpl instance;
	
	static  {
		instance = new TransactionManagerImpl();
	}
	
	public static TransactionManagerImpl getInstance() {
		return instance;
	}
	
	/* Instance */
	
	private static final String TRANSACTION_MANAGER_LOG = "TransactionManagerLog";
	private ConcurrentHashMap<Long, Set<IResourceManager>> resourceManagers;
	private TransactionLog txLog;

	private TransactionManagerImpl() {
		super();
		txLog = new TransactionLog(TRANSACTION_MANAGER_LOG);
		resourceManagers = new ConcurrentHashMap<Long, Set<IResourceManager>>();
	}

	/* (non-Javadoc)
	 * @see de.rockel.uhh.dis6.ITransactionManager#begin()
	 */
	public synchronized long begin() {
		long currentTransactionId = txLog.getAndIncrementTransactionId();
		txLog.writeBOT(currentTransactionId);
		
		Set<IResourceManager> aSynchronizedSet = Collections.synchronizedSet(new HashSet<IResourceManager>());
		resourceManagers.put(currentTransactionId, aSynchronizedSet);
		
		return currentTransactionId;
	}

	/* (non-Javadoc)
	 * @see de.rockel.uhh.dis6.ITransactionManager#commit(long)
	 */
	public synchronized void commit(long aTransactionId) {
		// has already been committed -> ignore
		TransactionState transactionState = txLog.getTransactionState(aTransactionId);
		if(TransactionState.COMMITTED == transactionState || TransactionState.ROLLED_BACK == transactionState) {
			System.out.println("INFO: TransactionManagerImpl.commit(long) trying to commit/rollback a closed transaction!");
			return;
		}
		
		// Set all nested resource managers into prepare state
		Set<IResourceManager> set = resourceManagers.get(aTransactionId);
		for (IResourceManager resourceManager : set) {
			try {
				resourceManager.prepare(aTransactionId);
			} catch (XAException e) {
				// if one of them fails, rollback whole TX
				txLog.writeRollBack(aTransactionId);
				for (IResourceManager resourceManager2 : set) {
					resourceManager2.rollback(aTransactionId);
				}
				set.clear(); // clean-up
				throw e;
			}
		}
		
		// only after prepare went through it's allowed to write the log record
		txLog.writeEOT(aTransactionId);
		// prepare went through successfully -> commit
		for (IResourceManager resourceManager : set) {
			resourceManager.commit(aTransactionId);
		}

		set.clear(); // clean-up
	}

	/* (non-Javadoc)
	 * @see de.rockel.uhh.dis6.ITransactionManager#register(de.kuppe.uhh.dis6.IResourceManager, long)
	 */
	public synchronized void register(IResourceManager aResourceManager,
			long aTransactionId) {
		Set<IResourceManager> set = resourceManagers.get(aTransactionId);
		set.add(aResourceManager);
	}

	/* (non-Javadoc)
	 * @see de.rockel.uhh.dis6.ITransactionManager#rollback(long)
	 */
	public synchronized void rollback(long aTransactionId) {
		// has already been committed -> ignore
		TransactionState transactionState = txLog.getTransactionState(aTransactionId);
		if(TransactionState.COMMITTED == transactionState || TransactionState.ROLLED_BACK == transactionState) {
			System.out.println("INFO: TransactionManagerImpl.rollback(long) trying to commit/rollback a closed transaction!");
			return;
		}
		
		// client side rollback
		txLog.writeRollBack(aTransactionId);
		Set<IResourceManager> set = resourceManagers.get(aTransactionId);
		for (IResourceManager resourceManager : set) {
			resourceManager.rollback(aTransactionId);
		}
		set.clear(); // clean-up
	}

	/* (non-Javadoc)
	 * @see de.rockel.uhh.dis6.ITransactionManager#getTransactionState(long)
	 */
	public boolean getTransactionState(long aTransactionId) {
		TransactionState transactionState = txLog.getTransactionState(aTransactionId);
		if(transactionState == null || transactionState == TransactionState.ROLLED_BACK) {
			return false;
		} else {
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see de.rockel.uhh.dis6.ITransactionManager#isOpen()
	 */
	public boolean isOpen(long aTransactionId) {
		TransactionState transactionState = txLog.getTransactionState(aTransactionId);
		return transactionState == null;
	}
}
