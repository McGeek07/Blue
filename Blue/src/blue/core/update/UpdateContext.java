package blue.core.update;

import blue.util.Copyable;

public class UpdateContext implements Copyable<UpdateContext> {
	public long
		frame;
	public double
		t,
		dt,
		fixed_dt;
	public int
		canvas_w,
		canvas_h;
	
	private UpdateContext
		parent;
	
	public UpdateContext push() {
		UpdateContext copy = copy();
		copy.parent = this;
		return copy;
	}
	
	public UpdateContext pop() {
		if(this.parent != null)
			try {
				return this.parent;
			} finally {
				this.parent = null;
			}
		return this;
	}
	
	@Override
	public UpdateContext copy() {
		UpdateContext copy = new UpdateContext();
		copy.canvas_w = this.canvas_w;
		copy.canvas_h = this.canvas_h;
		copy.fixed_dt = this.fixed_dt;
		copy.frame = this.frame;
		copy.dt = this.dt;
		copy.t  = this.t ;
		return copy;
	}
}
