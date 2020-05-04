package blue.core;

import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import blue.util.event.Broker;
import blue.util.event.Handle;

public abstract class Module implements Runnable {
	protected static final HashSet<Module>
		INDEX = new HashSet<Module>();
	
	protected Thread
		thread ;
	protected volatile boolean
		running;
	
	protected final Map<String, String>
		cfg;
	protected final Broker
		broker;
	protected final Handle
		handle;
	
	public Module() {
		cfg = new TreeMap<>();
		broker = new Broker();
		handle = new Handle();
		broker.attach(handle);
		
		INDEX.add(this);
	}
	
	protected void onInit() { }
	protected void onStop() { }
	protected void onStep() throws Exception { }		
	
	@Override
	public final void run() {
		try {
			System.out.println("[blue.core.Module.run] Init module '" + getClass().getName() + "'.");
			onInit();
			while(running)
				onStep();
		} catch(Exception fatal) {
			System.err.println("[blue.core.Module.run] A fatal exception has occurred in module '" + getClass().getName() + "'.");
			fatal.printStackTrace();
		} finally {
			System.out.println("[blue.core.Module.run] Stop module '" + getClass().getName() + "'.");
			onStop();
		}
	}
}
