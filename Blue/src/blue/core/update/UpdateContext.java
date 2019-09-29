package blue.core.update;

import blue.geom.Region2f;
import blue.util.Copyable;

public class UpdateContext implements Copyable<UpdateContext> {
	public double
		t,
		dt,
		fixed_dt;
	public final Region2f.Mutable
		region = new Region2f.Mutable();
	
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
		copy.region.set(this.region );
		copy.fixed_dt = this.fixed_dt;
		copy.dt = this.dt;
		copy.t  = this.t ;
		return copy;
	}
}
