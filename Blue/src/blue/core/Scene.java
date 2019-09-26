package blue.core;

import blue.core.render.RenderContext;
import blue.core.render.Renderable;
import blue.core.update.UpdateContext;
import blue.core.update.Updateable;
import blue.geom.Vector2f;

public class Scene implements Renderable, Updateable {
	

	
	@Override
	public void render(RenderContext context) {
		
	}

	@Override
	public void update(UpdateContext context) {
		
	}
	
	public void onInit() { }
	public void onExit() { }
	public void onAttach() { }
	public void onDetach() { }
	public void onMouseMoved(Vector2f mouse) { }	
	public void onWheelMoved(float    wheel) { }	
	public void onKeyDnAction(int key) { }	
	public void onKeyUpAction(int key) { }
	public void onBtnDnAction(int btn) { }
	public void onBtnUpAction(int btn) { }
}
