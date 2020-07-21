package blue.core;

import blue.Blue;

/**
 * The Engine is the singleton responsible for managing module threads. New 
 * modules are implicitly registered with the Engine. Registered modules can be 
 * initialized via Engine.init() or via Engine.init(module). Registered modules 
 * can be stopped via Engine.exit() or via Engine.stop(module).
 */
public final class Engine {
	//singleton instance
	protected static final Engine
		INSTANCE = new Engine();
	
	
	private Engine() {
		//hide constructor
		getStage();//force class loader to initialize Stage singleton
		getAudio();//force class loader to initialize Audio singleton
	}
	
	protected static Module getStage() {
		return Stage.MODULE;
	}
	
	protected static Module getAudio() {
		return Audio.MODULE;
	}
	
	/**
	 * Init a module. This method is thread-safe and can be called at any time. 
	 * If the module is already running, this method will do nothing.
	 */
	public static synchronized void init(Module module) {
		if(!module.running) {			
			module.thread = new Thread(module, Blue.VERSION.toString());
			module.running = true;
			module.thread.start();
		}
	}
	
	/**
	 * Stop a module. This method is thread-safe and can be called at any time. 
	 * If the module is not already running, this method will do nothing.
	 */
	public static synchronized void stop(Module module) {
		if( module.running)
			module.running = false;
	}
	
	/**
	 * Init all registered modules. This method is thread-safe and can be called
	 * at any time. This method calls Engine.init(module) on all registered 
	 * modules.
	 */
	public static synchronized void init() {
		for(Module module: Module.INDEX)
			init(module);
	}
	
	/**
	 * Stop all registered modules. This method is thread-safe and can be called
	 * at any time. This method calls Engine.stop(module) on all registered 
	 * modules.
	 */
	public static synchronized void exit() {
		for(Module module: Module.INDEX)
			stop(module);
	}	
}
