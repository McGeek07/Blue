package blue;

import blue.core.Engine;
import blue.core.Input;
import blue.core.Scene;
import blue.game.Sprite;
import blue.geom.Layout;
import blue.util.Version;

public class Blue extends Scene {
	public static final Version
		VERSION = new Version("Blue", 0, 0, 11);
	
	public static void main(String[] args) {
		System.out.println(VERSION);
		
		System.setProperty("sun.awt.noerasebackground", "true");
		
		Engine.getConfig().set(Engine.CANVAS_LAYOUT, Layout.DEFAULT);
		Engine.getConfig().set(Engine.WINDOW_BORDER, false);
		
		new Sprite.Atlas("Debug", "Debug.png", 16, 16);
		
		Engine.setScene(new Blue());
		
		Engine.init();
	}
	
	protected Sprite
		sprite;
	
	public Blue() {
		sprite = Sprite.getByName("Debug");
		sprite.loop(1f);
	}
	
	@Override
	public void onRender(RenderContext context) {
		sprite.render(context);
	}
	
	@Override
	public void onUpdate(UpdateContext context) {
		sprite.update(context);
	}	
	
	@Override
	public void onKeyDn(int key) {
		switch(key) {
			case Input.KEY_1:
				Engine.getConfig().set(Engine.WINDOW_DEVICE, 0);
				Engine.init();
				break;
			case Input.KEY_2:
				Engine.getConfig().set(Engine.WINDOW_DEVICE, 1);
				Engine.init();
				break;
			case Input.KEY_ESCAPE:
				Engine.exit();
				break;
		}
	}
}
