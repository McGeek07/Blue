package blue.core;

public interface Updateable {
	public void onUpdate(UpdateContext context);
	
	public static class UpdateContext {		
		public float
			t,
			dt,
			fixed_dt;
		public int
			canvas_w,
			canvas_h;
		
		protected UpdateContext() {
			//do nothing
		}
		
		private UpdateContext
			root;
	
		public UpdateContext push() {
			UpdateContext copy = new UpdateContext();
			
			copy.root = this ;
			copy.t  = this.t ;
			copy.dt = this.dt;
			copy.fixed_dt = this.fixed_dt;
			copy.canvas_w = this.canvas_w;
			copy.canvas_h = this.canvas_h;
			
			return copy;
		}
		
		public UpdateContext pop()  {
			if(root != null)
				try {
					return root;
				} finally {
					root = null;
				}
			return this;
		}
	}
}


