package blue;

import blue.core.Engine;
import blue.core.Input;
import blue.core.Input.KeyInput;
import blue.core.Sound;
import blue.core.Stage;
import blue.util.Version;

public class Blue {
	public static final Version
		VERSION = new Version("Blue", 0, 1, 5);
	
	public static void main(String[] args) {
		System.out.println(VERSION);
		
		Sound.load("itb", "itb.wav");
		
		Sound 
			a = Sound.fromName("itb", null),
			b = Sound.fromName("itb", null),
			c = Sound.fromName("itb", null);
		a.loop();
		b.loop();
		c.loop();
		
		Stage.attach(KeyInput.class, (input) -> {
			if(input.isDn() && input.isKey(Input.KEY_1))
				a.setFrame(0f);
			if(input.isDn() && input.isKey(Input.KEY_2))
				b.setFrame(0f);
			if(input.isDn() && input.isKey(Input.KEY_3))
				c.setFrame(0f);
		});		
		
		Engine.init();
	}
}
