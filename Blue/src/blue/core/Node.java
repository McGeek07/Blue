package blue.core;

import java.util.LinkedList;
import java.util.List;

import blue.geom.Box;
import blue.geom.Layout;
import blue.geom.Region2;
import blue.geom.Vector2;
import blue.util.Forward;
import blue.util.Reverse;

public class Node implements Renderable, Updateable, Forward<Node>, Reverse<Node> {
	protected final Layout.Mutable
		layout = new Layout.Mutable();
	protected final Region2.Mutable
		bounds = new Region2.Mutable();
	
	protected Node
		root;
	protected List<Node>
		tree = new LinkedList<>();
	
	protected ForwardIterable<Node>
		forward;
	protected ReverseIterable<Node>
		reverse;
	
	public void add(Node node) {
		if(node.root == null && tree.add   (node)) {
			node.root    = this;
			this.forward = null;
			this.reverse = null;
			node.attach();
		}
	}
	
	public void del(Node node) {
		if(node.root == this && tree.remove(node)) {
			node.detach();
			node.root    = null;
			this.forward = null;
			this.reverse = null;
		}
	}
	
	@Override
	public void render(RenderContext context) {
		onRender(context);
		for(Node node: forward())
			node.render(context);
	}
	
	@Override
	public void update(UpdateContext context) {
		onUpdate(context);
		for(Node node: reverse())
			node.update(context);
	}
	
	public void attach() {
		onAttach();
		if(tree.size() > 0) {
			for(Node node: tree)
				node.attach();
		}
	}
	public void detach() { 
		if(tree.size() > 0) {
			for(Node node: tree)
				node.detach();
		}
		onDetach();
	}
	
	public Layout layout() { return layout; }
	public Box<?> bounds() { return bounds; }
	
	public void layout(Layout layout) {
		Box<?> bounds = root != null ? root.bounds: Engine.canvas();
		this.layout.set(layout).m_region(this.bounds, bounds);
		resize();
	}
	
	public void bounds(float x, float y, float w, float h) {
		this.bounds.set(x, y, w, h);
		resize();
	}
	
	public void bounds(float w, float h) {
		this.bounds.set(w, h);
		resize();
	}
	
	public void bounds(Box<?> bounds) {
		this.bounds.set(bounds);
		resize();
	}
	
	public void resize(float x, float y, float w, float h) {
		this.layout.m_region(this.bounds, new Region2(x, y, w, h));
		resize();
	}
	
	public void resize(float w, float h) {
		this.layout.m_region(this.bounds, new Region2(w, h));
		resize();
	}
	
	public void resize(Box<?> bounds) {
		this.layout.m_region(this.bounds, bounds);
		resize();
	}
	
	public void resize() {
		onResize();
		if(tree.size() > 0)
			for(Node node: reverse())
				node.resize(this.bounds);
	}
	
	public boolean keyDn(int key) {
		if(onKeyDn(key))
			return true;
		else if(tree.size() > 0)
			for(Node node: reverse())
				if(node.keyDn(key))
					return true;
		return false;
	}
	
	public boolean keyUp(int key) { 
		if(onKeyUp(key))
			return true;
		else if(tree.size() > 0)
			for(Node node: reverse())
				if(node.keyUp(key))
					return true;
		return false;
	}
	
	public boolean btnDn(int btn) { 
		if(onBtnDn(btn))
			return true;
		else if(tree.size() > 0)
			for(Node node: reverse())
				if(node.btnDn(btn))
					return true;
		return false;
	}
	
	public boolean btnUp(int btn) { 
		if(onBtnUp(btn))
			return true;
		else if(tree.size() > 0)
			for(Node node: reverse())
				if(node.btnUp(btn))
					return true;
		return false;
	}
	
	public boolean mouseMoved(Vector2 mouse) { 
		if(onMouseMoved(mouse))
			return true;
		else if(tree.size() > 0)
			for(Node node: reverse())
				if(node.mouseMoved(mouse))
					return true;
		return false;
	}
	
	public boolean wheelMoved(float   wheel) { 
		if(onWheelMoved(wheel))
			return true;
		else if(tree.size() > 0)
			for(Node node: reverse())
				if(node.wheelMoved(wheel))
					return true;
		return false;
	}
	
	public void onRender(RenderContext context) { }
	public void onUpdate(UpdateContext context) { }	
	public void onAttach() { }
	public void onDetach() { }
	public void onResize() { }
	public boolean onKeyDn(int key) { return false; }
	public boolean onKeyUp(int key) { return false; }
	public boolean onBtnDn(int btn) { return false; }
	public boolean onBtnUp(int btn) { return false; }
	public boolean onMouseMoved(Vector2 mouse) { return false; }
	public boolean onWheelMoved(float   wheel) { return false; }
	
	@Override
	public ForwardIterable<Node> forward() {
		return forward != null ? forward : (forward = new ForwardIterable<>(tree));
	}
	
	@Override
	public ReverseIterable<Node> reverse() {
		return reverse != null ? reverse : (reverse = new ReverseIterable<>(tree));
	}
}
