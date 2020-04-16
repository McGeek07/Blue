package blue;

import blue.core.Engine;
import blue.util.Version;

public class Blue {
	public static final Version
		VERSION = new Version("Blue", 0, 1, 1);
	
	public static void main(String[] args) {
		System.out.println(VERSION);
		
		Engine.init();
	}
}
