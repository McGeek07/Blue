package blue.core;

import java.util.HashSet;

import blue.Blue;
import blue.util.Configuration;
import blue.util.Util.ObjectToString;
import blue.util.Util.StringToObject;
import blue.util.event.Broker;
import blue.util.event.Handle;
import blue.util.event.Listener;

public abstract class Module implements Runnable {
	protected static final HashSet<Module>
		INDEX = new HashSet<Module>();
	
	protected Thread
		thread ;
	protected volatile boolean
		running;
	
	protected final Configuration
		config;
	protected final Broker
		broker;
	protected final Handle
		handle;
	
	public Module() {
		config = new Configuration();
		broker = new Broker();
		handle = new Handle();
		broker.attach(handle);
		
		INDEX.add(this);
	}
	
	public void configure(Object... args) {
		config.set(args);
	}
	
	public Configuration getConfiguration() {
		return config;
	}
	
	public void setProperty(Object key, Object val) {
		config.set(key, val);
	}
	
	public <T> void setProperty(ObjectToString<T> o2s, Object key, Object val) {
		config.set(o2s, key, val);
	}
	
	public String getProperty(Object key, Object alt) {
		return config.get(key, alt);
	}
	
	public <T> T getProperty(StringToObject<T> s2o, Object key, T alt) {
		return config.get(s2o, key, alt);
	}
	
	public synchronized void init() {
		if( running)
			onInit();
		else {			
			thread = new Thread(this, Blue.VERSION.toString());
			running = true;
			thread.start();
		}
	}
	
	public synchronized void stop() {
		if(!running)
			onStop();
		else
			running = false;
	}
	
	protected void onInit() { }
	protected void onStop() { }
	protected void onStep() throws Exception { }
	
	public <T> void attach(Class<T> type, Listener<T> listener) {
		this.handle.attach(type, listener);
	}
	
	public <T> void detach(Class<T> type, Listener<T> listener) {
		this.handle.detach(type, listener);
	}
	
	public void attach(Broker broker) {
		this.broker.attach(broker);
	}
	
	public void detach(Broker broker) {
		this.broker.detach(broker);
	}
	
	public void attach(Handle handle) {
		this.broker.attach(handle);
	}
	
	public void detach(Handle handle) {
		this.broker.detach(handle);
	}
	
	public <T> void queue(T event) {
		this.broker.queue(event);
	}
	
	public <T> void flush(T event) {
		this.broker.flush(event);
	}
	
	public void poll() {
		broker.poll();
	}	
	
	@Override
	public void run() {
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
