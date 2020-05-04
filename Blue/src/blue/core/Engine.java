package blue.core;

import blue.Blue;

public final class Engine {
	protected static final Engine
		INSTANCE = new Engine();
	
	private Engine() {
		getStage();
		getAudio();
	}
	
	protected static Module getStage() {
		return Stage.MODULE;
	}
	
	protected static Module getAudio() {
		return Audio.MODULE;
	}
	
	public static synchronized void init(Module module) {
		if( module.running)
			module.onInit();
		else {			
			module.thread = new Thread(module, Blue.VERSION.toString());
			module.running = true;
			module.thread.start();
		}
	}
	
	public static synchronized void stop(Module module) {
		if(!module.running)
			module.onStop();
		else
			module.running = false;
	}
	
	public static synchronized void init() {
		for(Module module: Module.INDEX)
			if(!module.running)
				init(module);
	}
	
	public static synchronized void exit() {
		for(Module module: Module.INDEX)
			if( module.running)
				stop(module);
	}	
}
