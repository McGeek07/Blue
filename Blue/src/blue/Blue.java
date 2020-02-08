package blue;

import blue.core.Engine;
import blue.geom.Layout;
import blue.util.Version;

public class Blue {
	public static final Version
		VERSION = new Version("Blue", 0, 0, 25);
	
	public static void main(String[] args) {
		System.out.println(VERSION);
		
		Engine.getConfiguration().set(Engine.CANVAS_LAYOUT, Layout._640x480);
		Engine.getConfiguration().set(Engine.WINDOW_BORDER, true);
		Engine.getConfiguration().set(Engine.WINDOW_DEVICE,    1);
		Engine.getConfiguration().set(Engine.ENGINE_FPS, 0);
		Engine.getConfiguration().set(Engine.DEBUG, true);
		Engine.init();
	}
}
