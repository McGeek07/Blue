package blue.core;

import blue.core.Event.Listener;
import blue.core.Event.MonoBroker;
import blue.core.Event.MonoHandle;
import blue.core.Update.Updateable;
import blue.util.Copyable;

public class Update implements Listener<Updateable> {
	protected static final Update
		INSTANCE = new Update();	
	
	protected final MonoHandle<Updateable>
		handle;
	protected final MonoBroker<Updateable>
		broker;
	
	private Update() {
		this.handle = new MonoHandle<Updateable>();
		this.broker = new MonoBroker<Updateable>();
		this.broker.add(this.handle);
		this.handle.add(this       );
	}
	
	public static void queue(Updateable event) {
		if(event != null) INSTANCE.broker.queue(event);
	}
	
	public static void flush(Updateable event) {
		if(event != null) INSTANCE.broker.flush(event);
	}
	
	protected UpdateContext
		_context;
	public void poll(UpdateContext context) {
		_context = context.push();
		broker.flush();
		_context.pop();
	}
	@Override
	public void handle(Updateable event) {
		event.update(_context);
	}
	
	public static interface Updateable {
		public void update(UpdateContext context);
	}
	
	public static class UpdateContext implements Copyable<UpdateContext> {
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
}
