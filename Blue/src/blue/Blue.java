package blue;

import blue.core.Engine;
import blue.util.Log;
import blue.util.Version;

public class Blue {
	public static final Version
		VERSION = new Version("Blue", 0, 2, 5);
	
	public static void main(String[] args) {
		Log.info(new Object() {/* trace */}, VERSION);
		
		Engine.init();
	}
}