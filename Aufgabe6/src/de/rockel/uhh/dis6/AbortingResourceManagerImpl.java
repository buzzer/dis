package de.rockel.uhh.dis6;

import java.util.concurrent.ConcurrentMap;

public class AbortingResourceManagerImpl extends ResourceManagerImpl implements
		IResourceManager {

	public AbortingResourceManagerImpl(long aResourceManagerId, long l, ConcurrentMap<Long, Page> concurrentMap) {
		super(aResourceManagerId, l, concurrentMap);
	}

	/* (non-Javadoc)
	 * @see de.rockel.uhh.dis6.ResourceManagerImpl#prepare(long)
	 */
	@Override
	public void prepare(long aTransactionId) {
		System.out.println("INFO: AbortingResourceManagerImpl throwing XAException during prepare!");
		throw new XAException("Failed to prepare");
	}
}