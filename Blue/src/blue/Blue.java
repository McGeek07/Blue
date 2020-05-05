package blue;

import blue.core.Engine;
import blue.core.Sound;
import blue.core.Stage;
import blue.util.Version;

public class Blue {
	public static final Version
		VERSION = new Version("Blue", 0, 1, 8);
	
	public static void main(String[] args) {
		System.out.println(VERSION);
		
		Sound sound = Sound.load("itb", "itb.wav");
		sound.setFrame(sound.getSource().length);
		sound.setSpeed(-1f);
		sound.play();
		
		Stage.setProperty(Stage.WINDOW_DEVICE, 1);
		
		Engine.init();
	}
}
