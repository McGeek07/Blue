package blue;

import blue.core.Engine;
import blue.core.Event;
import blue.core.Input;
import blue.core.Scene;
import blue.core.Engine.EngineEvent;
import blue.game.Sprite;
import blue.geom.Layout;
import blue.util.Version;

public class Blue extends Scene {
	public static final Version
		VERSION = new Version("Blue", 0, 0, 12);
	
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
				Event.queue(EngineEvent.ON_INIT);
				break;
			case Input.KEY_2:
				Engine.getConfig().set(Engine.WINDOW_DEVICE, 1);
				Event.queue(EngineEvent.ON_INIT);
				break;
			case Input.KEY_ESCAPE:
				Event.queue(EngineEvent.ON_EXIT);
				break;
		}
	}
}
