package blue;

import blue.core.Engine;
import blue.util.Debug;
import blue.util.Version;

public class Blue {
	public static final Version
		VERSION = new Version("Blue", 0, 2, 4);
	
	public static void main(String[] args) {
		Debug.info(new Object() {}, VERSION);
		
		Engine.init();
	}
}