package blue;

import blue.core.Engine;
import blue.core.Input;
import blue.core.Input.KeyAction;
import blue.util.Version;

public class Blue {
	public static final Version
		VERSION = new Version("Blue", 0, 0, 34);
	
	public static void main(String[] args) {
		System.out.println(VERSION);
		
		Engine.getConfiguration().set(Engine.WINDOW_BORDER, true);
		Engine.getConfiguration().set(Engine.WINDOW_DEVICE,    0);
		Engine.getConfiguration().set(Engine.ENGINE_FPS, 60);
		Engine.getConfiguration().set(Engine.DEBUG, true);
		Engine.init();
		
		Input.attach(KeyAction.class, (event) -> {
			if(event.isDn() && event.isKey(Input.KEY_ESCAPE))
				Engine.exit();
		});
	}
}
