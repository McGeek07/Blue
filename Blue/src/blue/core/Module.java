package blue.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import blue.util.Debug;
import blue.util.Util;
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
	protected final Metrics
		metrics;
	
	public Module() {
		cfg = new TreeMap<>();
		broker = new Broker();
		handle = new Handle();
		broker.attach(handle);
		metrics = new Metrics(this);
		
		INDEX.add(this);
	}
	
	protected void onInit() { }
	protected void onStop() { }
	protected void onStep() throws Exception { }
	
	@Override
	public final void run() {
		try {
			Debug.info(new Object() {/* trace */}, "Init module '" + getClass().getName() + "'.");
			onInit();
			while(running)
				onStep();
		} catch(Exception fatal) {
			Debug.warn(new Object() {/* trace */}, "A fatal exception has occurred in module '" + getClass().getName() + "'.");
			fatal.printStackTrace();
		} finally {
			Debug.info(new Object() {/* trace */}, "Stop module '" + getClass().getName() + "'.");
			onStop();
		}
	}
	
	public static class Metrics {
		protected static final HashMap<String, Metrics>
			NAME_INDEX = new HashMap<String, Metrics>();
		protected final TreeMap<String, String>
			map = new TreeMap<String, String>();
		
		protected Metrics(Module module) {
			NAME_INDEX.put(module.getClass().getName(), this);
		}
		
		public String setMetric(Object key, Object val) {
			return Util.setEntry(map, key, val);
		}
		
		public String getMetric(Object key, Object alt) {
			return Util.getEntry(map, key, alt);
		}
		
		public static <T extends Module> Metrics getByType(Class<T> type) {
			return NAME_INDEX.get(type.getName());
		}
		
		public static <T extends Module> Metrics getByName(String   name) {
			return NAME_INDEX.get(name);
		}
	}
}
