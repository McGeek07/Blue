package blue.core;

import blue.util.Config;

public class Shell {
	protected final Config
		config = new Config();
	
	public Config getConfig() {
		return config;
	}
	
	public void init() {
		onInit();
	}
	
	public void exit() {
		onExit();
	}
	
	public void attach() {
		onAttach();
	}
	
	public void detach() {
		onDetach();
	}
	
	public void onInit() { }
	public void onExit() { }
	public void onAttach() { }
	public void onDetach() { }
}
