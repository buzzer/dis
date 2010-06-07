package de.rockel.uhh.dis5;

import java.util.concurrent.CyclicBarrier;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static final int parties = 5;
	private static final int cycles = 200;
	
	private BundleContext fContext;
	private static Activator plugin;
	
	public Activator() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		fContext = context;
		
		PersistenceManager pm = PersistenceManager.getInstance();
		
		CyclicBarrier barrier = new CyclicBarrier(parties);
		for (int i = 0; i < parties; i++) {
			Thread t = new Thread(new Client(pm, i, cycles, barrier));
			t.start();
		}
 	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		fContext = null;
	}

	public static Activator getDefault() {
		return plugin;
	}
	
	public boolean isActive() {
		return fContext != null;
	}
}
