package blue;

import blue.core.Engine;
import blue.core.Sound;
import blue.core.Stage;
import blue.util.Version;

public class Blue {
	public static final Version
		VERSION = new Version("Blue", 0, 1, 9);
	
	public static void main(String[] args) {
		System.out.println(VERSION);
		
		Stage.setProperty(Stage.DEBUG, "blue.core.Stage");
		
		Sound sound = Sound.load("itb", "itb.wav");
		sound.play();
		
		Engine.init();
	}
}
