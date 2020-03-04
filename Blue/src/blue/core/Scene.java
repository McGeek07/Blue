package blue.core;

import blue.geom.Vector2;

public class Scene implements Renderable, Updateable {
	
	@Override
	public void onRender(RenderContext context) { }
	@Override
	public void onUpdate(UpdateContext context) { }
	
	public void onAttach() { }
	public void onDetach() { }
	public void onResize() { }
	public void onGainFocus() { }
	public void onLoseFocus() { }
	public void onMouseMoved(Vector2 mouse) { }
	public void onWheelMoved(float   wheel) { }
	public void onKeyDn(int key) { }
	public void onKeyUp(int key) { }
	public void onBtnDn(int btn) { }
	public void onBtnUp(int btn) { }
}
