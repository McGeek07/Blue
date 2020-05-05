package blue.core;

import blue.geom.Vector2;

public abstract class Scene implements Renderable, Updateable {
	
	public void render(RenderContext context) { onRender(context); }
	public void update(UpdateContext context) { onUpdate(context); }
	
	public void onRender(RenderContext context) { }
	public void onUpdate(UpdateContext context) { }
	public void onAttach() { }
	public void onDetach() { }
	public void onResize() { }
	
	public void onMouseMoved(Vector2 mouse) { }
	public void onWheelMoved(float   wheel) { }
	public void onBtnDn(int btn) { }
	public void onBtnUp(int btn) { }
	public void onKeyDn(int key) { }
	public void onKeyUp(int key) { }	

	public void onGainFocus() { }
	public void onLoseFocus() { }
}
