/*******************************************************************************
 * Copyright (c) 2009 Markus Alexander Kuppe.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Markus Kuppe (ecf-dev_eclipse.org <at> lemmster <dot> de) - initial API and implementation
 ******************************************************************************/
package de.kuppe.uhh.dis6;

import java.util.concurrent.ConcurrentMap;

public class AbortingResourceManagerImpl extends ResourceManagerImpl implements
		IResourceManager {

	public AbortingResourceManagerImpl(long aResourceManagerId, long l, ConcurrentMap<Long, Page> concurrentMap) {
		super(aResourceManagerId, l, concurrentMap);
	}

	/* (non-Javadoc)
	 * @see de.kuppe.uhh.dis6.ResourceManagerImpl#prepare(long)
	 */
	@Override
	public void prepare(long aTransactionId) {
		System.out.println("INFO: AbortingResourceManagerImpl throwing XAException during prepare(long)!");
		throw new XAException("Failed to prepare(long)");
	}
}
