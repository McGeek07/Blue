package blue;

import blue.core.Engine;
import blue.core.Scene;
import blue.game.Sprite;
import blue.game.Sprite.Effect;
import blue.util.Version;

public class Blue extends Scene {
	private static final long 
		serialVersionUID = 1L;
	
	public static final Version
		VERSION = new Version("Blue", 0, 0, 17);
	
	public static void main(String[] args) {
		Sprite.load("Debug", "Debug.png", 16, 16);
		
		Engine.init();
		
		Engine.setScene(new Blue());
	}
	
	Sprite 
		sprite,
		black,
		white;
	
	@Override
	public void onAttach() {
		sprite = Sprite.fromName("Debug", null);
		
		black = sprite.filter(Effect.BLACKOUT);
		white = sprite.filter(Effect.WHITEOUT);
		
		sprite.loop(3f);		
	}
	
	@Override
	public void onRender(RenderContext context) {
		sprite.bounds.set(
				context.canvas_w,
				context.canvas_h
				);
		sprite.onRender(context);
	}
	
	@Override
	public void onUpdate(UpdateContext context) {
		sprite.onUpdate(context);
	}
}
