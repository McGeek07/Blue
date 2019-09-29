package blue.core;

import blue.core.render.RenderContext;
import blue.core.render.Renderable;
import blue.core.update.UpdateContext;
import blue.core.update.Updateable;
import blue.geom.Vector2f;

public class Scene implements Renderable, Updateable {
	
	public void attach() {
		onAttach();
	}
	
	public void detach() {
		onDetach();
	}
	
	@Override
	public void render(RenderContext context) {
		context = context.push();
		onRender(context);
		context = context.pop();
	}	

	@Override
	public void update(UpdateContext context) {
		context = context.push();
		onUpdate(context);
		context = context.pop();
	}
	
	public void onRender(RenderContext context) { }
	public void onUpdate(UpdateContext context) { }
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
