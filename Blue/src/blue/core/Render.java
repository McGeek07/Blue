package blue.core;

import java.awt.Graphics2D;

import blue.core.Event.Listener;
import blue.core.Event.MonoBroker;
import blue.core.Event.MonoHandle;
import blue.core.Render.Renderable;
import blue.util.Copyable;

public class Render implements Listener<Renderable> {
	protected static final Render
		INSTANCE = new Render();
	protected final MonoHandle<Renderable>
		handle;
	protected final MonoBroker<Renderable>
		broker;
	
	private Render() {
		this.handle = new MonoHandle<Renderable>();
		this.broker = new MonoBroker<Renderable>();
		this.broker.add(this.handle);
		this.handle.add(this       );
	}
	
	public static void queue(Renderable event) {
		if(event != null) INSTANCE.broker.queue(event);
	}
	
	public static void flush(Renderable event) {
		if(event != null) INSTANCE.broker.flush(event);
	}
	
	protected RenderContext
		_context;
	public void poll(RenderContext context) {
		_context = context.push();
		broker.flush();
		_context.pop();
	}
	
	@Override
	public void handle(Renderable event) {	
		event.render(_context);
	}
	
	public static interface Renderable {
		public void render(RenderContext context);
	}	
	
	public static class RenderContext implements Copyable<RenderContext> {
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
}
