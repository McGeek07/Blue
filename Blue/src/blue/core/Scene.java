package blue.core;

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
		onRender(context);
	}	

	@Override
	public void update(UpdateContext context) {
		onUpdate(context);
	}
	
	public void onRender(RenderContext context) { }
	public void onUpdate(UpdateContext context) { }
	public void onAttach() { }
	public void onDetach() { }
	public void onMouseMoved(Vector2f mouse) { }	
	public void onWheelMoved(float    wheel) { }	
	public void onKeyDn(int key) { }	
	public void onKeyUp(int key) { }
	public void onBtnDn(int btn) { }
	public void onBtnUp(int btn) { }
}
