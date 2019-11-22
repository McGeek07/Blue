package blue;

import blue.core.Engine;
import blue.core.Input;
import blue.core.Scene;
import blue.game.Sprite;
import blue.game.Sprite.Effect;
import blue.util.Version;

public class Blue extends Scene {
	private static final long 
		serialVersionUID = 1L;
	
	public static final Version
		VERSION = new Version("Blue", 0, 0, 18);
	
	public static void main(String[] args) {
		
		Engine.getConfiguration().print(System.out);
		Sprite.load("Debug", "Debug.png", 16, 16);
		
		Engine.init();
		
		Engine.setScene(new Blue());
	}
	
	Sprite 
		sprite,
		effect;
	
	@Override
	public void onAttach() {
		sprite = Sprite.fromName("Debug", null);		
		effect = sprite.filter(Effect.BLACKOUT);
		
		effect.setAlpha(0f);
		
		sprite.loop(3f);
		effect.loop(3f);
	}
	
	@Override
	public void onRender(RenderContext context) {
		sprite.bounds.set(
				context.canvas_w,
				context.canvas_h
				);
		effect.bounds.set(
				context.canvas_w,
				context.canvas_h
				);
		sprite.onRender(context);
		effect.onRender(context);
	}
	
	@Override
	public void onUpdate(UpdateContext context) {
		sprite.onUpdate(context);
		effect.onUpdate(context);
	}
	
	@Override
	public void onKeyDn(int key) {
		switch(key) {
			case Input.KEY_UP_ARROW:
				effect.setAlpha(effect.getAlpha() + .1f);
				break;
			case Input.KEY_DN_ARROW:
				effect.setAlpha(effect.getAlpha() - .1f);
				break;
		}
	}
}
