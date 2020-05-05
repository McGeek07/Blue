package blue;

import blue.core.Audio;
import blue.core.Sound;
import blue.util.Version;

public class Blue {
	public static final Version
		VERSION = new Version("Blue", 0, 1, 7);
	
	public static void main(String[] args) {
		System.out.println(VERSION);
		
		Sound sound = Sound.load("itb", "itb.wav");
		sound.setFrame(sound.getSource().length);
		sound.setSpeed(-1f);
		sound.play();
		
		Audio.init();
	}
}
