package blue.core.render;

import java.awt.Graphics2D;

import blue.util.Copyable;

public class RenderContext implements Copyable<RenderContext> {
	public Graphics2D
		g2D;
	public long
		frame;
	public double
		t,
		dt,
		fixed_dt;
	public int
		canvas_w,
		canvas_h;
	
	private RenderContext
		parent;
	
	public RenderContext push() {
		RenderContext copy = copy();
		copy.parent = this;
		return copy;
	}
	
	public RenderContext pop() {
		if(this.parent != null)
			try {
				this.g2D.dispose();
				return this.parent;
			} finally {
				this.parent = null;
			}
		return this;
	}

	@Override
	public RenderContext copy() {
		RenderContext copy = new RenderContext();
		copy.g2D = (Graphics2D)this.g2D.create();
		copy.canvas_w = this.canvas_w;
		copy.canvas_h = this.canvas_h;
		copy.fixed_dt = this.fixed_dt;
		copy.frame = this.frame;
		copy.dt = this.dt;
		copy.t  = this.t ;
		return copy;
	}	
}
