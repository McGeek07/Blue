package blue.core;

public final class Engine {
	protected static final Engine
		INSTANCE = new Engine();
	
	private Engine() {
		getStage();
		getAudio();
	}	
	
	public static Stage getStage() {
		return Stage.INSTANCE;
	}
	
	public static Audio getAudio() {
		return Audio.INSTANCE;
	}
	
	public static synchronized void init() {
		for(Module module: Module.INDEX)
			if(!module.running)
				module.init();
	}
	
	public static synchronized void exit() {
		for(Module module: Module.INDEX)
			if( module.running)
				module.stop();
	}
}
