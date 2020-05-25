package blue;

import blue.core.Engine;
import blue.core.Stage;
import blue.util.Version;

public class Blue {
	public static final Version
		VERSION = new Version("Blue", 0, 1, 18);
	
	public static void main(String[] args) {
		System.out.println(VERSION);
		
		Stage.setProperty(Stage.WINDOW_DEVICE,                 1);
		Stage.setProperty(Stage.DEBUG        , "blue.core.Stage");
		
		Engine.init();
	}
}
