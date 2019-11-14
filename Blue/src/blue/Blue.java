package blue;

import blue.core.Engine;
import blue.core.Scene;
import blue.util.Version;

public class Blue extends Scene {
	private static final long 
		serialVersionUID = 1L;
	
	public static final Version
		VERSION = new Version("Blue", 0, 0, 14);
	
	public static void main(String[] args) {		
		Engine.init();		
	}
}
