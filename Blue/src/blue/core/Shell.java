package blue.core;

import blue.util.Config;

public class Shell {
	protected final Config
		config = new Config();
	
	public Config getConfig() {
		return config;
	}
	
	public void onInit() { }
	public void onExit() { }
	public void onAttach() { }
	public void onDetach() { }
}
