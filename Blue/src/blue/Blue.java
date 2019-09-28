package blue;

import blue.core.Engine;
import blue.core.Stage;
import blue.util.Version;

public class Blue {
	public static final Version
		VERSION = new Version("Blue", 0, 0, 3);
	
	public static void main(String[] args) {
		System.out.println(VERSION);
		
		Engine.getStage().getConfig().set(Stage.WINDOW_DEVICE, 1);
		
		Engine.getConfig().set(Engine.THREAD_ASYNC, false);
		
		Engine.init();
	}
}
