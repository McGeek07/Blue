package blue.math;

import java.io.Serializable;

import blue.util.Copyable;
import blue.util.Util;

public class Layout implements Serializable, Copyable<Layout> {
	private static final long 
		serialVersionUID = 1L;
	protected final Attribute.Mutable
		anchor_x    = new Attribute.Mutable(0f, false),
		anchor_y    = new Attribute.Mutable(0f, false),
		offset_x    = new Attribute.Mutable(0f, false),
		offset_y    = new Attribute.Mutable(0f, false),
		preferred_w = new Attribute.Mutable(1f, false),
		preferred_h = new Attribute.Mutable(1f, false),
		minimum_w   = new Attribute.Mutable(0f, false),
		minimum_h   = new Attribute.Mutable(0f, false),
		maximum_w   = new Attribute.Mutable(1f, false),
		maximum_h   = new Attribute.Mutable(1f, false);
	
	public Layout() {
		//do nothing
	}
	
	public Layout(Layout layout) {
		this.mSet(layout);
	}
	
	public Layout(
		Object preferred_w, 
		Object preferred_h
	) {
		mSet(
			preferred_w, 
			preferred_h
		);
	}
	
	public Layout(
		Object anchor_x, Object anchor_y,
		Object offset_x, Object offset_y,
		Object preferred_w, Object preferred_h
	) {
		mSet(
			anchor_x, anchor_y,
			offset_x, offset_y,
			preferred_w, preferred_h
		);
	}
	
	public Layout(
		Object anchor_x, Object anchor_y,
		Object offset_x, Object offset_y,
		Object preferred_w, Object preferred_h,
		Object minimum_w, Object minimum_h,
		Object maximum_w, Object maximum_h
	) {
		mSet(
			anchor_x, anchor_y,
			offset_x, offset_y,
			preferred_w, preferred_h,
			minimum_w, minimum_h,
			maximum_w, maximum_h
		);
	}
	
	protected void mSet(Layout layout) {
		anchor_x.set(layout.anchor_x); anchor_y.set(layout.anchor_y);
		offset_x.set(layout.offset_x); offset_y.set(layout.offset_y);
		preferred_w.set(layout.preferred_w); preferred_h.set(layout.preferred_h);
		minimum_w.set(layout.minimum_w); minimum_h.set(layout.minimum_h);
		maximum_w.set(layout.maximum_w); maximum_h.set(layout.maximum_h);
	}
	
	protected void mSet(
		Object preferred_w, 
		Object preferred_h
	) {
		this.preferred_w.set(preferred_w);
		this.preferred_h.set(preferred_h);
	}
	
	protected void mSet(
		Object anchor_x, Object anchor_y,
		Object offset_x, Object offset_y,
		Object preferred_w, Object preferred_h
	) {
		this.anchor_x.set(anchor_x); this.anchor_y.set(anchor_y);
		this.offset_x.set(offset_x); this.offset_y.set(offset_y);
		this.preferred_w.set(preferred_w); this.preferred_h.set(preferred_h);
	}
	
	protected void mSet(
		Object anchor_x, Object anchor_y,
		Object offset_x, Object offset_y,
		Object preferred_w, Object preferred_h,
		Object minimum_w, Object minimum_h,
		Object maximum_w, Object maximum_h
	) {
		this.anchor_x.set(anchor_x);
		this.anchor_y.set(anchor_y);
		this.offset_x.set(offset_x);
		this.offset_y.set(offset_y);
		this.preferred_w.set(preferred_w);
		this.preferred_h.set(preferred_h);
		this.minimum_w.set(minimum_w);
		this.minimum_h.set(minimum_h);
		this.maximum_w.set(maximum_w);
		this.maximum_h.set(maximum_h);
	}
	
	public Attribute getAnchorX() { 
		return this.anchor_x; 
	}
	
	public Attribute getAnchorY() { 
		return this.anchor_y; 
	}
	
	public Attribute getOffsetX() { 
		return this.offset_x; 
	}
	
	public Attribute getOffsetY() { 
		return this.offset_y; 
	}
	
	public Attribute getPreferredW() { 
		return this.preferred_w; 
	}
	
	public Attribute getPreferredH() { 
		return this.preferred_h; 
	}
	
	public Attribute getMinimumW() { 
		return this.minimum_w; 
	}
	
	public Attribute getMinimumH() { 
		return this.minimum_h; 
	}
	
	public Attribute getMaximumW() { 
		return this.maximum_w; 
	}
	
	public Attribute getMaximumH() { 
		return this.maximum_h; 
	}
	
	@Override
	public Layout copy() {
		return new Layout(this);
	}
	
	@Override
	public String toString() {
		return toString(this, "%s");
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Layout) return equals((Layout)o);
		return false;
	}
	
	public boolean equals(Layout l) {
		return
			anchor_x.equals(l.anchor_x) &&
			anchor_y.equals(l.anchor_y) &&
			offset_x.equals(l.offset_x) &&
			offset_y.equals(l.offset_y) &&
			preferred_w.equals(l.preferred_w) &&
			preferred_h.equals(l.preferred_h) &&
			minimum_w.equals(l.minimum_w) &&
			minimum_h.equals(l.minimum_h) &&
			maximum_w.equals(l.maximum_w) &&
			maximum_h.equals(l.maximum_h);
	}
	
	public static String toString(Layout l, String format) {
		//DEFAULT
        if(l.equals(DEFAULT)) return "default";
        //VH_HALF
        if(l.equals(LV_HALF)) return "lv_half";
        if(l.equals(MV_HALF)) return "mv_half";
        if(l.equals(RV_HALF)) return "rv_half";
        if(l.equals(TH_HALF)) return "th_half";
        if(l.equals(MH_HALF)) return "mh_half";
        if(l.equals(BH_HALF)) return "bh_half";
        //VH THIRD
        if(l.equals(LV_THIRD)) return "lv_third";
        if(l.equals(MV_THIRD)) return "mv_third";
        if(l.equals(RV_THIRD)) return "rv_third";
        if(l.equals(TH_THIRD)) return "th_third";
        if(l.equals(MH_THIRD)) return "mh_third";
        if(l.equals(BH_THIRD)) return "bh_third";
        //FOURTH
        if(l.equals(TL_FOURTH)) return "tl_fourth";
        if(l.equals(TM_FOURTH)) return "tm_fourth";
        if(l.equals(TR_FOURTH)) return "tr_fourth";
        if(l.equals(ML_FOURTH)) return "ml_fourth";
        if(l.equals(MM_FOURTH)) return "mm_fourth";
        if(l.equals(MR_FOURTH)) return "mr_fourth";
        if(l.equals(BL_FOURTH)) return "bl_fourth";
        if(l.equals(BM_FOURTH)) return "bm_fourth";
        if(l.equals(BR_FOURTH)) return "br_fourth";
        //NINTH
        if(l.equals(TL_NINTH)) return "tl_ninth";
        if(l.equals(TM_NINTH)) return "tm_ninth";
        if(l.equals(TR_NINTH)) return "tr_ninth";
        if(l.equals(ML_NINTH)) return "ml_ninth";
        if(l.equals(MM_NINTH)) return "mm_ninth";
        if(l.equals(MR_NINTH)) return "mr_ninth";
        if(l.equals(BL_NINTH)) return "bl_ninth";
        if(l.equals(BM_NINTH)) return "bm_ninth";
        if(l.equals(BR_NINTH)) return "br_ninth";
		
		return "{"
				+ Attribute.toString(l.anchor_x, format) + ", "
				+ Attribute.toString(l.anchor_y, format) + ", "
				+ Attribute.toString(l.offset_x, format) + ", "
				+ Attribute.toString(l.offset_y, format) + ", "
				+ Attribute.toString(l.preferred_w, format) + ", "
				+ Attribute.toString(l.preferred_h, format) + ", "
				+ Attribute.toString(l.minimum_w, format) + ", "
				+ Attribute.toString(l.minimum_h, format) + ", "
				+ Attribute.toString(l.maximum_w, format) + ", "
				+ Attribute.toString(l.maximum_h, format) + "}";
	}
	
	protected static final <LAYOUT extends Layout> LAYOUT parseLayout(LAYOUT layout, String string) {
		if(layout == null)
			throw new IllegalArgumentException("Null Layout");
		if(string == null)
            throw new IllegalArgumentException("Null String");
		
		string = string.trim().toLowerCase();
		switch(string) {
			//DEFAULT
	        case "default": layout.mSet(DEFAULT); return layout;
	        //VH_HALF
	        case "lv_half": layout.mSet(LV_HALF); return layout;
	        case "mv_half": layout.mSet(MV_HALF); return layout;
	        case "rv_half": layout.mSet(RV_HALF); return layout;
	        case "th_half": layout.mSet(TH_HALF); return layout;
	        case "mh_half": layout.mSet(MH_HALF); return layout;
	        case "bh_half": layout.mSet(BH_HALF); return layout;
	        //VH THIRD
	        case "lv_third": layout.mSet(LV_THIRD); return layout;
	        case "mv_third": layout.mSet(MV_THIRD); return layout;
	        case "rv_third": layout.mSet(RV_THIRD); return layout;
	        case "th_third": layout.mSet(TH_THIRD); return layout;
	        case "mh_third": layout.mSet(MH_THIRD); return layout;
	        case "bh_third": layout.mSet(BH_THIRD); return layout;
	        //FOURTH
	        case "tl_fourth": layout.mSet(TL_FOURTH); return layout;
	        case "tm_fourth": layout.mSet(TM_FOURTH); return layout;
	        case "tr_fourth": layout.mSet(TR_FOURTH); return layout;
	        case "ml_fourth": layout.mSet(ML_FOURTH); return layout;
	        case "mm_fourth": layout.mSet(MM_FOURTH); return layout;
	        case "mr_fourth": layout.mSet(MR_FOURTH); return layout;
	        case "bl_fourth": layout.mSet(BL_FOURTH); return layout;
	        case "bm_fourth": layout.mSet(BM_FOURTH); return layout;
	        case "br_fourth": layout.mSet(BR_FOURTH); return layout;
	        //NINTH
	        case "tl_ninth": layout.mSet(TL_NINTH); return layout;
	        case "tm_ninth": layout.mSet(TM_NINTH); return layout;
	        case "tr_ninth": layout.mSet(TR_NINTH); return layout;
	        case "ml_ninth": layout.mSet(ML_NINTH); return layout;
	        case "mm_ninth": layout.mSet(MM_NINTH); return layout;
	        case "mr_ninth": layout.mSet(MR_NINTH); return layout;
	        case "bl_ninth": layout.mSet(BL_NINTH); return layout;
	        case "bm_ninth": layout.mSet(BM_NINTH); return layout;
	        case "br_ninth": layout.mSet(BR_NINTH); return layout;
		}      
        
        int
	        a = string.indexOf("{"),
	        b = string.indexOf("}");
	    if (a >= 0 || b >= 0) {
	        if (b > a) {
	            string = string.substring(++a, b);
	        } else {
	            string = string.substring(++a);
	        }
	    }
	    
	    String[] temp = string.split("\\,");
	    
	    layout.anchor_x.set(temp.length > 0 ? temp[0] : 0f);
    	layout.anchor_y.set(temp.length > 1 ? temp[1] : 0f);
    	layout.offset_x.set(temp.length > 2 ? temp[2] : 0f);
	    layout.offset_y.set(temp.length > 3 ? temp[3] : 0f);
	    layout.preferred_w.set(temp.length > 4 ? temp[4] : 1f);
	    layout.preferred_h.set(temp.length > 5 ? temp[5] : 1f);
	    layout.minimum_w.set(temp.length > 6 ? temp[6] : 0f);
	    layout.minimum_h.set(temp.length > 7 ? temp[7] : 0f);
	    layout.maximum_w.set(temp.length > 8 ? temp[8] : 1f);
	    layout.maximum_h.set(temp.length > 9 ? temp[9] : 1f);
	    
	    return layout;
	}
	
	public static Layout parseLayout(String str) {
		return parseLayout(new Layout(), str); 
	}
	
	public static class Mutable extends Layout {
		private static final long 
			serialVersionUID = 1L;
		
		public Mutable() {
			super();
		}
		
		public Mutable(Layout layout) {
			super(layout);
		}
		
		public Mutable(
			Object preferred_w,
			Object preferred_h
		) {
			super(preferred_w, preferred_h);
		}
		
		public Mutable(
			Object anchor_x, Object anchor_y,
			Object offset_x, Object offset_y,
			Object preferred_w, Object preferred_h
		) {
			super(
				anchor_x, anchor_y,
				offset_x, offset_y,
				preferred_w, preferred_h
			);
		}
		
		public Mutable(
			Object anchor_x, Object anchor_y,
			Object offset_x, Object offset_y,
			Object preferred_w, Object preferred_h,
			Object minimum_w, Object minimum_h,
			Object maximum_w, Object maximum_h
		) {
			super(
				anchor_x, anchor_y,
				offset_x, offset_y,
				preferred_w, preferred_h,
				minimum_w, minimum_h,
				maximum_w, maximum_h
			);
		}
		
		public Layout.Mutable set(Layout layout) {
			this.mSet(layout);
			return  this;
		}
		
		public Layout.Mutable set(
			Object preferred_w, 
			Object preferred_h
		) {
			this.mSet(preferred_w, preferred_h);
			return this;
		}
		
		public Layout.Mutable set(
			Object anchor_x, Object anchor_y,
			Object offset_x, Object offset_y,
			Object preferred_w, Object preferred_h
		) {
			this.mSet(
				anchor_x, anchor_y,
				offset_x, offset_y,
				preferred_w, preferred_h
			);
			return this;
		}
		
		public Layout.Mutable set(
			Object anchor_x, Object anchor_y,
			Object offset_x, Object offset_y,
			Object preferred_w, Object preferred_h,
			Object minimum_w, Object minimum_h,
			Object maximum_w, Object maximum_h
		) {
			this.mSet(
				anchor_x, anchor_y,
				offset_x, offset_y,
				preferred_w, preferred_h,
				minimum_w, minimum_h,
				maximum_w, maximum_h
			);
			return this;
		}
		
		@Override
		public Attribute.Mutable getAnchorX() { 
			return this.anchor_x; 
		}		
		
		@Override
		public Attribute.Mutable getAnchorY() { 
			return this.anchor_y; 
		}
		
		@Override
		public Attribute.Mutable getOffsetX() {
			return this.offset_x; 
		}
		
		@Override
		public Attribute.Mutable getOffsetY() { 
			return this.offset_y; 
		}
		
		@Override
		public Attribute.Mutable getPreferredW() { 
			return this.preferred_w; 
		}
		
		@Override
		public Attribute.Mutable getPreferredH() { 
			return this.preferred_h; 
		}
		
		@Override
		public Attribute.Mutable getMinimumW() { 
			return this.minimum_w; 
		}
		
		@Override
		public Attribute.Mutable getMinimumH() { 
			return this.minimum_h; 
		}
		
		@Override
		public Attribute.Mutable getMaximumW() { 
			return this.maximum_w; 
		}
		
		@Override
		public Attribute.Mutable getMaximumH() { 
			return this.maximum_h; 
		}
		
		@Override
		public Layout.Mutable copy() {
			return new Layout.Mutable(this);
		}
		
		public static Layout.Mutable parseLayout(String string) {
			return Layout.parseLayout(new Layout.Mutable(), string);
		}
	}
	
	public static class Attribute implements Serializable {
		private static final long 
			serialVersionUID = 1L;
		protected float
			value;
		protected boolean
			fixed;
		
		public Attribute(float value, boolean fixed) {
			this.value = value;
			this.fixed = fixed;
		}
		
		public Attribute(Object obj) {
			if(obj instanceof Number) {
				Number n = (Number)obj;
				
				value = n.floatValue();
				fixed = false;
			} else if(obj instanceof String) {
				String s = (String)obj;
				int i = s.indexOf('#');
				s = s.substring(i + 1);
				
				value = Util.stringToFloat(s);
				fixed = i >= 0;
			} else if(obj instanceof Attribute) {
				Attribute a = (Attribute)obj;
				
				value = a.value;
				fixed = a.fixed;
			}
		}
		
		@Override
		public String toString() {
			return toString(this, "%s");
		}
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof Attribute) return equals((Attribute)o);
			else if(o instanceof String) return equals(new Attribute(o));
			else if(o instanceof Number) return equals(new Attribute(o));
			else return false;
		}
		
		public boolean equals(Attribute a) {
			return
					this.fixed == a.fixed &&
					this.value == a.value;
		}
		
		public static String toString(Attribute a, String format) {
			return (a.fixed ? "#" : "") + String.format(format, a.value);
		}
		
		public static class Mutable extends Attribute {
			private static final long 
				serialVersionUID = 1L;

			public Mutable(float value, boolean fixed) {
				super(value, fixed);
			}	
			
			public Mutable(Object obj) {
				super(obj);
			}
			
			public Attribute.Mutable set(float value, boolean fixed) {
				this.value = value;
				this.fixed = fixed;
				return this;
			}
			
			public Attribute.Mutable set(Object obj) {
				if(obj instanceof Number) {
					Number n = (Number)obj;
					
					value = n.floatValue();
					fixed = false;
				} else if(obj instanceof String) {
					String s = (String)obj;
					int i = s.indexOf('#');
					s = s.substring(i + 1);
					
					value = Util.stringToFloat(s);
					fixed = i >= 0;
				} else if(obj instanceof Attribute) {
					Attribute a = (Attribute)obj;
					
					value = a.value;
					fixed = a.fixed;
				}
				return this;
			}
			
			public Attribute.Mutable setValue(float   value) {
				this.value = value;
				return this;
			}
			
			public Attribute.Mutable setFixed(boolean fixed) {
				this.fixed = fixed;
				return this;
			}
		}
	}
	
	public Region2 region(Box<?> box) {
		return region(box, preferred_w, preferred_h);
	}
	
	public Region2 region(Box<?> box, Object preferred_w, Object preferred_h) {
		return region(box, new Attribute(preferred_w), new Attribute(preferred_h));
	}
	
	public Region2 region(Box<?> box, Attribute preferred_w, Attribute preferred_h) {
		float[] layout = compute(
				box.x(), box.y(),
				box.w(), box.h(),
				preferred_w,
				preferred_h
				);
		return new Region2(
				layout[0],
				layout[1],
				layout[2],
				layout[3]
				);
	}
	
	public Bounds2 bounds(Box<?> box) {
		return bounds(box, preferred_w, preferred_h);
	}
	
	public Bounds2 bounds(Box<?> box, Object preferred_w, Object preferred_h) {
		return bounds(box, new Attribute(preferred_w), new Attribute(preferred_h));
	}
	
	public Bounds2 bounds(Box<?> box, Attribute preferred_w, Attribute preferred_h) {
		float[] layout = compute(
				box.x(), box.y(),
				box.w(), box.h(),
				preferred_w,
				preferred_h
				);
		return new Bounds2(
				layout[0],
				layout[1],
				layout[4],
				layout[5]
				);
	}
	
	public Region2.Mutable m_region(Region2.Mutable dst) {
		return m_region(dst, dst, preferred_w, preferred_h);
	}
	
	public Region2.Mutable m_region(Region2.Mutable dst, Object preferred_w, Object preferred_h) {
		return m_region(dst, dst, new Attribute(preferred_w), new Attribute(preferred_h));
	}

	public Region2.Mutable m_region(Region2.Mutable dst, Attribute preferred_w, Attribute preferred_h) {
		return m_region(dst, dst, preferred_w, preferred_h);
	}
	
	public Region2.Mutable m_region(Region2.Mutable dst, Box<?> src) {
		return m_region(dst, src, preferred_w, preferred_h);
	}
	
	public Region2.Mutable m_region(Region2.Mutable dst, Box<?> src, Object preferred_w, Object preferred_h) {
		return m_region(dst, src, new Attribute(preferred_w), new Attribute(preferred_h));
	}

	public Region2.Mutable m_region(Region2.Mutable dst, Box<?> src, Attribute preferred_w, Attribute preferred_h) {
		float[] layout = compute(
				src.x(), src.y(),
				src.w(), src.h(),
				preferred_w,
				preferred_h
				);
		return dst.set(
				layout[0],
				layout[1],
				layout[2],
				layout[3]
				);
	}
	
	public Bounds2.Mutable m_bounds(Bounds2.Mutable dst) {
		return m_bounds(dst, dst, preferred_w, preferred_h);
	}
	
	public Bounds2.Mutable m_bounds(Bounds2.Mutable dst, Object preferred_w, Object preferred_h) {
		return m_bounds(dst, dst, new Attribute(preferred_w), new Attribute(preferred_h));
	}

	public Bounds2.Mutable m_bounds(Bounds2.Mutable dst, Attribute preferred_w, Attribute preferred_h) {
		return m_bounds(dst, dst, preferred_w, preferred_h);
	}
	public Bounds2.Mutable m_bounds(Bounds2.Mutable dst, Box<?> src) {
		return m_bounds(dst, src, preferred_w, preferred_h);
	}
	
	public Bounds2.Mutable m_bounds(Bounds2.Mutable dst, Box<?> src, Object preferred_w, Object preferred_h) {
		return m_bounds(dst, src, new Attribute(preferred_w), new Attribute(preferred_h));
	}

	public Bounds2.Mutable m_bounds(Bounds2.Mutable dst, Box<?> src, Attribute preferred_w, Attribute preferred_h) {
		float[] layout = compute(
				src.x(), src.y(),
				src.w(), src.h(),
				preferred_w,
				preferred_h
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
			float src_w, float src_h,
			Attribute preferred_w,
			Attribute preferred_h
			) {
		float
			dst_w = preferred_w.value >= 0 ? 
					preferred_w.fixed ?         preferred_w.value :         preferred_w.value * src_w :
					preferred_w.fixed ? src_w + preferred_w.value : src_w + preferred_w.value * src_w ,
			dst_h = preferred_h.value >= 0 ? 
					preferred_h.fixed ?         preferred_h.value :         preferred_h.value * src_h :
					preferred_h.fixed ? src_h + preferred_h.value : src_h + preferred_h.value * src_h ,
			min_w = minimum_w.value >= 0 ? 
					minimum_w.fixed ?         minimum_w.value :         minimum_w.value * src_w :
					minimum_w.fixed ? src_w + minimum_w.value : src_w + minimum_w.value * src_w ,
			min_h = minimum_h.value >= 0 ? 
					minimum_h.fixed ?         minimum_h.value :         minimum_h.value * src_h :
					minimum_h.fixed ? src_h + minimum_h.value : src_h + minimum_h.value * src_h ,
			max_w = maximum_w.value >= 0 ? 
					maximum_w.fixed ?         maximum_w.value :         maximum_w.value * src_w :
					maximum_w.fixed ? src_w + maximum_w.value : src_w + maximum_w.value * src_w ,
			max_h = maximum_h.value >= 0 ? 
					maximum_h.fixed ?         maximum_h.value :         maximum_h.value * src_h :
					maximum_h.fixed ? src_h + maximum_h.value : src_h + maximum_h.value * src_h ;
		dst_w = minimum_w.fixed ? clamp_force_min(dst_w, min_w, max_w) : clamp_force_max(dst_w, min_w, max_w);
		dst_h = minimum_h.fixed ? clamp_force_min(dst_h, min_h, max_h) : clamp_force_max(dst_h, min_h, max_h);
		float
			x1 = anchor_x.value >= 0 ? 
					anchor_x.fixed ?         anchor_x.value :         src_w * anchor_x.value :
					anchor_x.fixed ? src_w + anchor_x.value : src_w + src_w * anchor_x.value ,
			y1 = anchor_y.value >= 0 ? 
					anchor_y.fixed ?         anchor_y.value :         src_h * anchor_y.value :
					anchor_y.fixed ? src_h + anchor_y.value : src_h + src_h * anchor_y.value ,
			x2 = offset_x.value >= 0 ? 
					offset_x.fixed ?         offset_x.value :         dst_w * offset_x.value :
					offset_x.fixed ? dst_w + offset_x.value : dst_w + dst_w * offset_x.value ,
			y2 = offset_y.value >= 0 ? 
					offset_y.fixed ?         offset_y.value :         dst_h * offset_y.value :
					offset_y.fixed ? dst_h + offset_y.value : dst_h + dst_h * offset_y.value ,
			dst_x = src_x + x1 - x2,
			dst_y = src_y + y1 - y2;
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
	
	public static final Layout fixed(float preferred_w, float preferred_h) {
		Attribute
			w = new Attribute(preferred_w, true),
			h = new Attribute(preferred_h, true);
		return new Layout(.5f, .5f, .5f, .5f, w, h, w, h, w, h);
	}	
	
	public static final Layout
		//DEFAULT
		DEFAULT = new Layout(.5f, .5f, .5f, .5f,  1f,  1f),
		//VH_HALF
		LV_HALF = new Layout( 0f, .5f,  0f, .5f, .5f,  1f),
		MV_HALF = new Layout(.5f, .5f, .5f, .5f, .5f,  1f),
		RV_HALF = new Layout( 1f, .5f,  1f, .5f, .5f,  1f),
		TH_HALF = new Layout(.5f,  0f, .5f,  0f,  1f, .5f),
		MH_HALF = new Layout(.5f, .5f, .5f, .5f,  1f, .5f),
		BH_HALF = new Layout(.5f,  1f, .5f,  1f,  1f, .5f),
		//VH_THIRD
		LV_THIRD = new Layout( 0f, .5f,  0f, .5f, 1f/3f,    1f),
		MV_THIRD = new Layout(.5f, .5f, .5f, .5f, 1f/3f,    1f),
		RV_THIRD = new Layout( 1f, .5f,  1f, .5f, 1f/3f,    1f),
		TH_THIRD = new Layout(.5f,  0f, .5f,  0f,    1f, 1f/3f),
		MH_THIRD = new Layout(.5f, .5f, .5f, .5f,    1f, 1f/3f),
		BH_THIRD = new Layout(.5f,  1f, .5f,  1f,    1f, 1f/3f),
		//FOURTH
		TL_FOURTH = new Layout( 0f,  0f,  0f,  0f, .5f, .5f),
		TM_FOURTH = new Layout(.5f,  0f, .5f,  0f, .5f, .5f),
		TR_FOURTH = new Layout( 1f,  0f,  1f,  0f, .5f, .5f),
		ML_FOURTH = new Layout( 0f, .5f,  0f, .5f, .5f, .5f),
		MM_FOURTH = new Layout(.5f, .5f, .5f, .5f, .5f, .5f),
		MR_FOURTH = new Layout( 1f, .5f,  1f, .5f, .5f, .5f),
		BL_FOURTH = new Layout( 0f,  1f,  0f,  1f, .5f, .5f),
		BM_FOURTH = new Layout(.5f,  1f, .5f,  1f, .5f, .5f),
		BR_FOURTH = new Layout( 1f,  1f,  1f,  1f, .5f, .5f),
		//NINTH
		TL_NINTH = new Layout( 0f,  0f,  0f,  0f, 1f/3f, 1f/3f),
		TM_NINTH = new Layout(.5f,  0f, .5f,  0f, 1f/3f, 1f/3f),
		TR_NINTH = new Layout( 1f,  0f,  1f,  0f, 1f/3f, 1f/3f),
		ML_NINTH = new Layout( 0f, .5f,  0f, .5f, 1f/3f, 1f/3f),
		MM_NINTH = new Layout(.5f, .5f, .5f, .5f, 1f/3f, 1f/3f),
		MR_NINTH = new Layout( 1f, .5f,  1f, .5f, 1f/3f, 1f/3f),
		BL_NINTH = new Layout( 0f,  1f,  0f,  1f, 1f/3f, 1f/3f),
		BM_NINTH = new Layout(.5f,  1f, .5f,  1f, 1f/3f, 1f/3f),
		BR_NINTH = new Layout( 1f,  1f,  1f,  1f, 1f/3f, 1f/3f),
		//16:9
		_1920x1080 = Layout.fixed(1920, 1080),
		_1600x900  = Layout.fixed(1600,  900),
		_1366x768  = Layout.fixed(1366,  768),
		_1280x720  = Layout.fixed(1280,  720),
		_1024x576  = Layout.fixed(1024,  576),
		//4:3
		_1600x1200 = Layout.fixed(1600, 1200),
		_1440x1080 = Layout.fixed(1440, 1080),
		_1400x1050 = Layout.fixed(1400, 1050),
		_1152x864  = Layout.fixed(1152,  864),
		_1024x768  = Layout.fixed(1024,  768),
		_800x600   = Layout.fixed( 800,  600),
		_768x576   = Layout.fixed( 768,  576),
		_640x480   = Layout.fixed( 640,  480);
}
