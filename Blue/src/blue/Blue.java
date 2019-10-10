package blue;

import blue.core.Debug;
import blue.core.Engine;
import blue.geom.Vector3f;
import blue.util.Version;

public class Blue {
	public static final Version
		VERSION = new Version("Blue", 0, 0, 8);
	
	public static void main(String[] args) {
		System.out.println(VERSION);
		
		Debug.getConfig().set(Debug.CONSOLE_BACKGROUND, new Vector3f(0f, 0f, 0f));
		Debug.getConfig().set(Debug.CONSOLE_FOREGROUND, new Vector3f(0f, 1f, 0f));
		
		Engine.init();
	}
}
