package blue.core;

import java.awt.Graphics2D;

import blue.geom.Bounds2f;
import blue.util.Copyable;

public interface Renderable {
	public void render(RenderContext context);
	
	public static class RenderContext implements Copyable<RenderContext> {
		public Graphics2D
			g2D;
		public long
			frame;
		public double
			t,
			dt,
			fixed_dt;
		public Bounds2f.Mutable
			bounds = new Bounds2f.Mutable();
		
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
			copy.frame  = this.frame;
			copy.t		= this.t ;
			copy.dt 	= this.dt;
			copy.fixed_dt = this.fixed_dt;			
			copy.bounds.set(this.bounds );		
			return copy;
		}	
	}
}	


