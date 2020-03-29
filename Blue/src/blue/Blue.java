package blue;

import blue.core.Debug;
import blue.core.Engine;
import blue.util.Version;

public class Blue {
	public static final Version
		VERSION = new Version("Blue", 0, 0, 40);
	
	public static void main(String[] args) {
		Debug.log(Debug.INFO, Blue.class, VERSION);		
		Engine.setProperty(Engine.DEBUG, true);
		Engine.init();
	}
}
