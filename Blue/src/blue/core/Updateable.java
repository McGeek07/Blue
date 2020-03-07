package blue.core;

import java.util.Deque;
import java.util.LinkedList;

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
		
		public void update(Updateable updateable) {
			updateable.onUpdate(this);
		}
		
		private final Deque<UpdateContext>
			stack = new LinkedList<>();
	
		public void push() {
			UpdateContext copy = new UpdateContext();
			
			stack.push(copy);
			copy.t  = this.t ;
			copy.dt = this.dt;
			copy.fixed_dt = this.fixed_dt;
			copy.canvas_w = this.canvas_w;
			copy.canvas_h = this.canvas_h;
		}
		
		public void pop()  {
			UpdateContext copy = stack.pop();
			
			this.t  = copy.t ;
			this.dt = copy.dt;
			this.fixed_dt = copy.fixed_dt;
			this.canvas_w = copy.canvas_w;
			this.canvas_h = copy.canvas_h;
		}
	}
}


