package blue.math;

import java.io.Serializable;

import blue.util.Copyable;
import blue.util.Util;

public class Layout implements Serializable, Copyable<Layout> {
	private static final long 
		serialVersionUID = 1L;
	
	protected final Attribute.Mutable
		//origin
		x0 = new Attribute.Mutable(0f, false),
		y0 = new Attribute.Mutable(0f, false),
		//offset
		x1 = new Attribute.Mutable(0f, false),
		y1 = new Attribute.Mutable(0f, false),
		//size
		w0 = new Attribute.Mutable(1f, false),
		h0 = new Attribute.Mutable(1f, false),
		//min size
		w1 = new Attribute.Mutable(0f, false),
		h1 = new Attribute.Mutable(0f, false),
		//max size
		w2 = new Attribute.Mutable(1f, false),
		h2 = new Attribute.Mutable(1f, false);
	
	public Layout() {
		//do nothing
	}
	
	public Layout(Layout l) {
		x0.set(l.x0); y0.set(l.y0);
		x1.set(l.x1); y1.set(l.y1);
		w0.set(l.w0); h0.set(l.h0);
		w1.set(l.w1); h1.set(l.h1);
		w2.set(l.w2); h2.set(l.h2);
	}
	
	public Layout(
		Object x0, Object y0,
		Object x1, Object y1,
		Object w0, Object h0
	) {
		this.x0.set(x0); this.y0.set(y0);
		this.x1.set(x1); this.y1.set(y1);
		this.w0.set(w0); this.h0.set(h0);
	}
	
	public Layout(
		Object x0, Object y0,
		Object x1, Object y1,
		Object w0, Object h0,
		Object w1, Object h1,
		Object w2, Object h2
	) {
		this.x0.set(x0); this.y0.set(y0);
		this.x1.set(x1); this.y1.set(y1);
		this.w0.set(w0); this.h0.set(h0);
		this.w1.set(w1); this.h1.set(h1);
		this.w2.set(w2); this.h2.set(h2);
	}
	
	public Attribute x0() { return x0; }
	public Attribute y0() { return y0; }
	public Attribute x1() { return x1; }
	public Attribute y1() { return y1; }
	public Attribute w0() { return w0; }
	public Attribute h0() { return h0; }
	public Attribute w1() { return w1; }
	public Attribute h1() { return h1; }
	public Attribute w2() { return w2; }
	public Attribute h2() { return h2; }
	
	@Override
	public Layout copy() {
		return new Layout(this);
	}
	
	@Override
	public String toString() {
		return Layout.toString(this, "%s");
	}
	
	public static String toString(Layout l, String f) {
		return "{" +
			Attribute.toString(l.x0, f) + ", " + Attribute.toString(l.y0, f) + ", " +
			Attribute.toString(l.x1, f) + ", " + Attribute.toString(l.y1, f) + ", " +
			Attribute.toString(l.w0, f) + ", " + Attribute.toString(l.h0, f) + ", " +
			Attribute.toString(l.w1, f) + ", " + Attribute.toString(l.h1, f) + ", " +
			Attribute.toString(l.w2, f) + ", " + Attribute.toString(l.h2, f) + "}";
	}
	
	protected static <L extends Layout> L parseLayout(L l, String s) {
		if(l != null && s != null) {
			int
	            i = s.indexOf("{"),
	            j = s.indexOf("}");
	        if (i >= 0 || j >= 0) {
	            if (j > i)
	                s = s.substring(++i, j);
	            else
	                s = s.substring(++i);
	        }
	        java.util.Map<String, String> t = Util.parse(
        		s, 
        		"x0", "y0", 
        		"x1", "y1",
        		"w0", "h0",
        		"w1", "h1",
        		"w2", "h2"
    		);
	        l.x0.set(Util.getEntry(t, "x0", 0f));
	        l.y0.set(Util.getEntry(t, "y0", 0f));
	        l.x1.set(Util.getEntry(t, "x1", 0f));
	        l.y1.set(Util.getEntry(t, "y1", 0f));
	        l.w0.set(Util.getEntry(t, "w0", 1f));
	        l.h0.set(Util.getEntry(t, "h0", 1f));
	        l.w1.set(Util.getEntry(t, "w1", 0f));
	        l.h1.set(Util.getEntry(t, "h1", 0f));
	        l.w2.set(Util.getEntry(t, "w2", 1f));
	        l.h2.set(Util.getEntry(t, "h2", 1f));
		}
		return l;
	}
	
	public static Layout parseLayout(String s) {
		return Layout.parseLayout(new Layout(), s);
	}

	public static class Mutable extends Layout {
		private static final long 
			serialVersionUID = 1L;
		
		public Mutable() {
			super();
		}
		
		public Mutable(Layout l) {
			super(l);
		}
		
		public Mutable(
			Object x0, Object y0,
			Object x1, Object y1,
			Object w0, Object h0
		) {
			super(x0, y0, x1, y1, w0, h0);
		}
		
		public Mutable(
			Object x0, Object y0,
			Object x1, Object y1,
			Object w0, Object h0,
			Object w1, Object h1,
			Object w2, Object h2
		) {
			super(x0, y0, x1, y1, w0, h0, w1, h1, w2, h2);
		}
		
		public Attribute.Mutable x0() { return x0; }
		public Attribute.Mutable y0() { return y0; }
		public Attribute.Mutable x1() { return x1; }
		public Attribute.Mutable y1() { return y1; }
		public Attribute.Mutable w0() { return w0; }
		public Attribute.Mutable h0() { return h0; }
		public Attribute.Mutable w1() { return w1; }
		public Attribute.Mutable h1() { return h1; }
		public Attribute.Mutable w2() { return w2; }
		public Attribute.Mutable h2() { return h2; }
		
		@Override
		public Layout.Mutable copy() {
			return new Layout.Mutable(this);
		}
		
		public static Layout.Mutable parseLayout(String s) {
			return Layout.parseLayout(new Layout.Mutable(), s);
		}
	}	
	
	public static class Attribute implements Serializable, Copyable<Attribute> {
		private static final long 
			serialVersionUID = 1L;
		
		protected float
			value;
		protected boolean
			fixed;
		
		public Attribute(
			float   value,
			boolean fixed
		) {
			this.value = value;
			this.fixed = fixed;
		}
		
		public Attribute(Object o) {
			if(o instanceof Attribute)
				mSet((Attribute)o);
			else if(o instanceof String)
				mSet((String)o);
			else if(o instanceof Number)
				mSet((Number)o);
		}
		
		protected void mSet(
			float   value,
			boolean fixed
		) {
			this.value = value;
			this.fixed = fixed;
		}
		
		protected void mSet(Attribute a) {
			value = a.value();
			fixed = a.fixed();
		}
		
		protected void mSet(String s) {
			int i = s.indexOf('#');
			s = s.substring(i + 1);
			
			value = Util.stringToFloat(s);
			fixed = i >= 0;
		}
		
		protected void mSet(Number n) {
			value = n.floatValue();
			fixed = false;
		}
		
		public float   value() {
			return value;
		}
		
		public boolean fixed() {
			return fixed;
		}
		
		@Override
		public Attribute copy() {
			return new Attribute(this);
		}
		
		@Override
		public String toString() {
			return toString(this, "%s");
		}
		
		public static String toString(Attribute a, String f) {
			return (a.fixed ? "#" : "") + String.format(f, a.value);
		}

		public static class Mutable extends Attribute {
			private static final long 
				serialVersionUID = 1L;
			
			public Mutable(
				float   value, 
				boolean fixed
			) {
				super(value, fixed);
			}
			
			public Mutable(Object o) {
				super(o);
			}			
			
			public Attribute.Mutable set(
				float   value,
				boolean fixed
			) {
				this.value = value;
				this.fixed = fixed;
				return this;
			}
			
			public Attribute.Mutable set(Object o) {
				if(o instanceof Attribute)
					mSet((Attribute)o);
				else if(o instanceof String)
					mSet((String)o);
				else if(o instanceof Number)
					mSet((Number)o);
				return this;
			}
			
			public Attribute.Mutable set(Attribute a) {
				mSet(a);
				return this;
			}
			
			public Attribute.Mutable set(String s) {
				mSet(s);
				return this;
			}
			
			public Attribute.Mutable set(Number n) {
				mSet(n);
				return this;
			}
			
			public float   value(float   value) {
				return this.value = value;
			}
			
			public boolean fixed(boolean fixed) {
				return this.fixed = fixed;
			}
			
			@Override
			public Attribute.Mutable copy() {
				return new Attribute.Mutable(this);
			}
		}
	}
	
	public Region2 region(Box<?> src) {
		float[] layout = compute(
			src.x(), src.y(),
			src.w(), src.h()
		);
		return new Region2(
			layout[0],
			layout[1],
			layout[2],
			layout[3]
		);
	}
	
	public Region2.Mutable m_region(Box<?> src, Region2.Mutable dst) {
		float[] layout = compute(
			src.x(), src.y(),
			src.w(), src.h()
		);
		return dst.set(
			layout[0],
			layout[1],
			layout[2],
			layout[3]
		);
	}
	
	public Bounds2 bounds(Box<?> src) {
		float[] layout = compute(
			src.x(), src.y(),
			src.w(), src.h()
		);
		return new Bounds2(
			layout[0],
			layout[1],
			layout[4],
			layout[5]
		);
	}
	
	public Bounds2.Mutable m_bounds(Box<?> src, Bounds2.Mutable dst) {
		float[] layout = compute(
			src.x(), src.y(),
			src.w(), src.h()
		);
		return dst.set(
			layout[0],
			layout[1],
			layout[4],
			layout[5]
		);
	}
	
	public float[] compute(
		float src_x, float src_y,
		float src_w, float src_h
	) {
		float
			dst_w = w0.value >= 0 ?
				w0.fixed ?         w0.value :         w0.value * src_w :
				w0.fixed ? src_w + w0.value : src_w + w0.value * src_w ,
			dst_h = h0.value >= 0 ? 
				h0.fixed ?         h0.value :         h0.value * src_h :
				h0.fixed ? src_h + h0.value : src_h + h0.value * src_h ,
			min_w = w1.value >= 0 ? 
				w1.fixed ?         w1.value :         w1.value * src_w :
				w1.fixed ? src_w + w1.value : src_w + w1.value * src_w ,
			min_h = h1.value >= 0 ? 
				h1.fixed ?         h1.value :         h1.value * src_h :
				h1.fixed ? src_h + h1.value : src_h + h1.value * src_h ,
			max_w = w2.value >= 0 ? 
				w2.fixed ?         w2.value :         w2.value * src_w :
				w2.fixed ? src_w + w2.value : src_w + w2.value * src_w ,
			max_h = h2.value >= 0 ? 
				h2.fixed ?         h2.value :         h2.value * src_h :
				h2.fixed ? src_h + h2.value : src_h + h2.value * src_h ;
		dst_w = w1.fixed ? 
			clamp_force_min(dst_w, min_w, max_w) : 
			clamp_force_max(dst_w, min_w, max_w) ;
		dst_h = h1.fixed ? 
			clamp_force_min(dst_h, min_h, max_h) :
			clamp_force_max(dst_h, min_h, max_h) ;
		float
			_x0 = x0.value >= 0 ? 
				x0.fixed ?         x0.value :         src_w * x0.value :
				x0.fixed ? src_w + x0.value : src_w + src_w * x0.value ,
			_y0 = y0.value >= 0 ? 
				y0.fixed ?         y0.value :         src_h * y0.value :
				y0.fixed ? src_h + y0.value : src_h + src_h * y0.value ,
			_x1 = x1.value >= 0 ? 
				x1.fixed ?         x1.value :         dst_w * x1.value :
				x1.fixed ? dst_w + x1.value : dst_w + dst_w * x1.value ,
			_y1 = y1.value >= 0 ? 
				y1.fixed ?         y1.value :         dst_h * y1.value :
				y1.fixed ? dst_h + y1.value : dst_h + dst_h * y1.value ,
			dst_x = src_x + _x0 - _x1,
			dst_y = src_y + _y0 - _y1;
		return new float[] {
			dst_x , dst_y,
			dst_w , dst_h,
			dst_x + dst_w,
			dst_y + dst_h
		};
	}
	
	protected static float clamp_force_min(float x, float a, float b) {
		if(x < a) return a;
		if(x > b) return b;
		return x;
	}
	
	protected static float clamp_force_max(float x, float a, float b) {
		if(x > b) return b;
		if(x < a) return a;
		return x;
	}
}
