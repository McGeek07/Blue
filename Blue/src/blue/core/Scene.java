package blue.core;

import java.io.Serializable;

import blue.geom.Vector2;

public class Scene implements Serializable, Renderable, Updateable {
	private static final long 
		serialVersionUID = 1L;
	
	@Override
	public void onRender(RenderContext context) { }
	@Override
	public void onUpdate(UpdateContext context) { }
	
	public void onAttach() { }
	public void onDetach() { }
	public void onResize() { }
	public void onMouseMoved(Vector2 mouse) { }
	public void onWheelMoved(float   wheel) { }
	public void onKeyDn(int key) { }	
	public void onKeyUp(int key) { }
	public void onBtnDn(int btn) { }
	public void onBtnUp(int btn) { }
}
