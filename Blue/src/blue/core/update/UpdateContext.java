package blue.core.update;

import blue.util.Copyable;

public class UpdateContext implements Copyable<UpdateContext> {
	public double
		t,
		dt,
		fixed_dt;
	
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
		copy.fixed_dt = this.fixed_dt;
		copy.dt = this.dt;
		copy.t  = this.t ;
		return copy;
	}
}
