package blue.core;

import blue.geom.Bounds2;
import blue.util.Copyable;

public interface Updateable {
	public void update(UpdateContext context);
	
	public static class UpdateContext implements Copyable<UpdateContext> {
		public long
			frame;
		public double
			t,
			dt,
			fixed_dt;
		public final Bounds2.Mutable
			bounds = new Bounds2.Mutable();
		
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
			copy.frame  = this.frame;
			copy.t		= this.t ;
			copy.dt 	= this.dt;
			copy.fixed_dt = this.fixed_dt;			
			copy.bounds.set(this.bounds );
			return copy;
		}
	}
}


