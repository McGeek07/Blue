package blue.geom;

import java.io.Serializable;

import blue.util.Util;

public class Layout implements Serializable {
	private static final long 
		serialVersionUID = 1L;
	public final Attribute
		anchor_x,
		anchor_y,
		offset_x,
		offset_y,
		preferred_w,
		preferred_h,
		minimum_w,
		minimum_h,
		maximum_w,
		maximum_h;
	
	public Layout(
			Object preferred_w, Object preferred_h
			) {
		this(
				0, 0,
				0, 0,
				preferred_w,
				preferred_h,
				0, 0,
				1, 1
				);
	}
	
	public Layout(
			Object anchor_x, Object anchor_y,
			Object offset_x, Object offset_y,
			Object preferred_w, Object preferred_h
			) {
		this(
				anchor_x, anchor_y,
				offset_x, offset_y,
				preferred_w, 
				preferred_h,
				0, 0,
				1, 1
				);
	}
	
	public Layout(
			Object anchor_x, Object anchor_y,
			Object offset_x, Object offset_y,
			Object preferred_w, Object preferred_h,
			Object minimum_w, Object minimum_h,
			Object maximum_w, Object maximum_h
			) {
		this.anchor_x = new Attribute(anchor_x);
		this.anchor_y = new Attribute(anchor_y);
		this.offset_x = new Attribute(offset_x);
		this.offset_y = new Attribute(offset_y);
		this.preferred_w = new Attribute(preferred_w);
		this.preferred_h = new Attribute(preferred_h);
		this.minimum_w = new Attribute(minimum_w);
		this.minimum_h = new Attribute(minimum_h);
		this.maximum_w = new Attribute(maximum_w);
		this.maximum_h = new Attribute(maximum_h);
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
	
	public Region2.Mutable apply(Region2.Mutable box) {
		return apply(box, preferred_w, preferred_h);
	}
	
	public Region2.Mutable apply(Region2.Mutable box, Object preferred_w, Object preferred_h) {
		return apply(box, new Attribute(preferred_w), new Attribute(preferred_h));
	}

	public Region2.Mutable apply(Region2.Mutable box, Attribute preferred_w, Attribute preferred_h) {
		float[] layout = compute(
				box.x(), box.y(),
				box.w(), box.h(),
				preferred_w,
				preferred_h
				);
		box.set(
				layout[0],
				layout[1],
				layout[2],
				layout[3]
				);
		return box;
	}
	
	public Bounds2.Mutable apply(Bounds2.Mutable box) {
		return apply(box, preferred_w, preferred_h);
	}
	
	public Bounds2.Mutable apply(Bounds2.Mutable box, Object preferred_w, Object preferred_h) {
		return apply(box, new Attribute(preferred_w), new Attribute(preferred_h));
	}

	public Bounds2.Mutable apply(Bounds2.Mutable box, Attribute preferred_w, Attribute preferred_h) {
		float[] layout = compute(
				box.x(), box.y(),
				box.w(), box.h(),
				preferred_w,
				preferred_h
				);
		box.set(
				layout[0],
				layout[1],
				layout[4],
				layout[5]
				);
		return box;
	}
	
	protected float[] compute(
			float src_x, float src_y,
			float src_w, float src_h,
			Attribute preferred_w,
			Attribute preferred_h
			) {
		float
			dst_w = preferred_w.value >= 0 ? 
					preferred_w.fixed ?         preferred_w.value :         preferred_w.value * src_w :
					preferred_w.fixed ? src_w - preferred_w.value : src_w - preferred_w.value * src_w ,
			dst_h = preferred_h.value >= 0 ? 
					preferred_h.fixed ?         preferred_h.value :         preferred_h.value * src_h :
					preferred_h.fixed ? src_h - preferred_h.value : src_h - preferred_h.value * src_h ,
			min_w = minimum_w.value >= 0 ? 
					minimum_w.fixed ?         minimum_w.value :         minimum_w.value * src_w :
					minimum_w.fixed ? src_w - minimum_w.value : src_w - minimum_w.value * src_w ,
			min_h = minimum_h.value >= 0 ? 
					minimum_h.fixed ?         minimum_h.value :         minimum_h.value * src_h :
					minimum_h.fixed ? src_h - minimum_h.value : src_h - minimum_h.value * src_h ,
			max_w = maximum_w.value >= 0 ? 
					maximum_w.fixed ?         maximum_w.value :         maximum_w.value * src_w :
					maximum_w.fixed ? src_w - maximum_w.value : src_w - maximum_w.value * src_w ,
			max_h = maximum_h.value >= 0 ? 
					maximum_h.fixed ?         maximum_h.value :         maximum_h.value * src_h :
					maximum_h.fixed ? src_h - maximum_h.value : src_h - maximum_h.value * src_h ;
		dst_w = minimum_w.fixed ? clamp_force_min(dst_w, min_w, max_w) : clamp_force_max(dst_w, min_w, max_w);
		dst_h = minimum_h.fixed ? clamp_force_min(dst_h, min_h, max_h) : clamp_force_max(dst_h, min_h, max_h);
		float
			x1 = anchor_x.value >= 0 ? 
					anchor_x.fixed ?         anchor_x.value :         src_w * anchor_x.value :
					anchor_x.fixed ? src_w - anchor_x.value : src_w - src_w * anchor_x.value ,
			y1 = anchor_y.value >= 0 ? 
					anchor_y.fixed ?         anchor_y.value :         src_h * anchor_y.value :
					anchor_y.fixed ? src_h - anchor_y.value : src_h - src_h * anchor_y.value ,
			x2 = offset_x.value >= 0 ? 
					offset_x.fixed ?         offset_x.value :         dst_w * offset_x.value :
					offset_x.fixed ? dst_w - offset_x.value : dst_w - dst_w * offset_x.value ,
			y2 = offset_y.value >= 0 ? 
					offset_y.fixed ?         offset_y.value :         dst_h * offset_y.value :
					offset_y.fixed ? dst_h - offset_y.value : dst_h - dst_h * offset_y.value ,
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
					this.anchor_x.equals(l.anchor_x) &&
					this.anchor_y.equals(l.anchor_y) &&
					this.offset_x.equals(l.offset_x) &&
					this.offset_y.equals(l.offset_y) &&
					this.preferred_w.equals(l.preferred_w) &&
					this.preferred_h.equals(l.preferred_h) &&
					this.minimum_w.equals(l.minimum_w) &&
					this.minimum_h.equals(l.minimum_h) &&
					this.maximum_w.equals(l.maximum_w) &&
					this.maximum_h.equals(l.maximum_h);
	}
	
	public static String toString(Layout l, String format) {
		//DEFAULT
        if(l.equals(DEFAULT)) return "DEFAULT";
        //VH_HALF
        if(l.equals(LV_HALF)) return "LV_HALF";
        if(l.equals(MV_HALF)) return "MV_HALF";
        if(l.equals(RV_HALF)) return "RV_HALF";
        if(l.equals(TH_HALF)) return "TH_HALF";
        if(l.equals(MH_HALF)) return "MH_HALF";
        if(l.equals(BH_HALF)) return "BH_HALF";
        //VH THIRD
        if(l.equals(LV_THIRD)) return "LV_THIRD";
        if(l.equals(MV_THIRD)) return "MV_THIRD";
        if(l.equals(RV_THIRD)) return "RV_THIRD";
        if(l.equals(TH_THIRD)) return "TH_THIRD";
        if(l.equals(MH_THIRD)) return "MH_THIRD";
        if(l.equals(BH_THIRD)) return "BH_THIRD";
        //FOURTH
        if(l.equals(TL_FOURTH)) return "TL_FOURTH";
        if(l.equals(TM_FOURTH)) return "TM_FOURTH";
        if(l.equals(TR_FOURTH)) return "TR_FOURTH";
        if(l.equals(ML_FOURTH)) return "ML_FOURTH";
        if(l.equals(MM_FOURTH)) return "MM_FOURTH";
        if(l.equals(MR_FOURTH)) return "MR_FOURTH";
        if(l.equals(BL_FOURTH)) return "BL_FOURTH";
        if(l.equals(BM_FOURTH)) return "BM_FOURTH";
        if(l.equals(BR_FOURTH)) return "BR_FOURTH";
        //NINTH
        if(l.equals(TL_NINTH)) return "TL_NINTH";
        if(l.equals(TM_NINTH)) return "TM_NINTH";
        if(l.equals(TR_NINTH)) return "TR_NINTH";
        if(l.equals(ML_NINTH)) return "ML_NINTH";
        if(l.equals(MM_NINTH)) return "MM_NINTH";
        if(l.equals(MR_NINTH)) return "MR_NINTH";
        if(l.equals(BL_NINTH)) return "BL_NINTH";
        if(l.equals(BM_NINTH)) return "BM_NINTH";
        if(l.equals(BR_NINTH)) return "BR_NINTH";
		
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
	
	public static final Layout parseLayout(String str) {
		if(str == null)
            throw new IllegalArgumentException("Null String");
        if((str = str.trim()).isEmpty())
            throw new IllegalArgumentException("Empty String"); 
        
        //DEFAULT
        if(str.equalsIgnoreCase("DEFAULT")) return DEFAULT;
        //VH_HALF
        if(str.equalsIgnoreCase("LV_HALF")) return LV_HALF;
        if(str.equalsIgnoreCase("MV_HALF")) return MV_HALF;
        if(str.equalsIgnoreCase("RV_HALF")) return RV_HALF;
        if(str.equalsIgnoreCase("TH_HALF")) return TH_HALF;
        if(str.equalsIgnoreCase("MH_HALF")) return MH_HALF;
        if(str.equalsIgnoreCase("BH_HALF")) return BH_HALF;
        //VH THIRD
        if(str.equalsIgnoreCase("LV_THIRD")) return LV_THIRD;
        if(str.equalsIgnoreCase("MV_THIRD")) return MV_THIRD;
        if(str.equalsIgnoreCase("RV_THIRD")) return RV_THIRD;
        if(str.equalsIgnoreCase("TH_THIRD")) return TH_THIRD;
        if(str.equalsIgnoreCase("MH_THIRD")) return MH_THIRD;
        if(str.equalsIgnoreCase("BH_THIRD")) return BH_THIRD;
        //FOURTH
        if(str.equalsIgnoreCase("TL_FOURTH")) return TL_FOURTH;
        if(str.equalsIgnoreCase("TM_FOURTH")) return TM_FOURTH;
        if(str.equalsIgnoreCase("TR_FOURTH")) return TR_FOURTH;
        if(str.equalsIgnoreCase("ML_FOURTH")) return ML_FOURTH;
        if(str.equalsIgnoreCase("MM_FOURTH")) return MM_FOURTH;
        if(str.equalsIgnoreCase("MR_FOURTH")) return MR_FOURTH;
        if(str.equalsIgnoreCase("BL_FOURTH")) return BL_FOURTH;
        if(str.equalsIgnoreCase("BM_FOURTH")) return BM_FOURTH;
        if(str.equalsIgnoreCase("BR_FOURTH")) return BR_FOURTH;
        //NINTH
        if(str.equalsIgnoreCase("TL_NINTH")) return TL_NINTH;
        if(str.equalsIgnoreCase("TM_NINTH")) return TM_NINTH;
        if(str.equalsIgnoreCase("TR_NINTH")) return TR_NINTH;
        if(str.equalsIgnoreCase("ML_NINTH")) return ML_NINTH;
        if(str.equalsIgnoreCase("MM_NINTH")) return MM_NINTH;
        if(str.equalsIgnoreCase("MR_NINTH")) return MR_NINTH;
        if(str.equalsIgnoreCase("BL_NINTH")) return BL_NINTH;
        if(str.equalsIgnoreCase("BM_NINTH")) return BM_NINTH;
        if(str.equalsIgnoreCase("BR_NINTH")) return BR_NINTH;
        
        int
	        a = str.indexOf("{"),
	        b = str.indexOf("}");
	    if (a >= 0 || b >= 0) {
	        if (b > a) {
	            str = str.substring(++a, b);
	        } else {
	            str = str.substring(++a);
	        }
	    }
	    String[] temp = str.split("\\,");
	    Attribute[] attr = new Attribute[temp.length];
	    for (int i = 0; i < temp.length; i++) {
	        attr[i] = new Attribute(temp[i]);
	    }
	    return new Layout(
	    		temp.length > 0 ? attr[0] : 0f,
				temp.length > 1 ? attr[1] : 0f,
				temp.length > 2 ? attr[2] : 0f,
				temp.length > 3 ? attr[3] : 0f,
				temp.length > 4 ? attr[4] : 0f,
				temp.length > 5 ? attr[5] : 0f,
				temp.length > 6 ? attr[6] : 0f,
				temp.length > 7 ? attr[7] : 0f,
				temp.length > 8 ? attr[8] : 0f,
				temp.length > 9 ? attr[9] : 0f
				);    
	}
	
	public static class Attribute implements Serializable {
		private static final long 
			serialVersionUID = 1L;
		public final float
			value;
		public final boolean
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
				return;
			}
			if(obj instanceof String) {
				String s = (String)obj;
				int i = s.indexOf('#');
				s = s.substring(i + 1);
				
				value = Util.stringToFloat(s);
				fixed = i >= 0;
				return;
			}
			if(obj instanceof Attribute) {
				Attribute a = (Attribute)obj;
				
				value = a.value;
				fixed = a.fixed;
				return;
			}
			value = 0f;
			fixed = false;
		}
		
		@Override
		public String toString() {
			return toString(this, "%s");
		}
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof Attribute) return equals((Attribute)o);
			if(o instanceof String) return equals(new Attribute(o));
			if(o instanceof Number) return equals(new Attribute(o));
			return false;
		}
		
		public boolean equals(Attribute a) {
			return
					this.fixed == a.fixed &&
					this.value == a.value;
		}
		
		public static String toString(Attribute a, String format) {
			return (a.fixed ? "#" : "") + String.format(format, a.value);
		}
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
		_1920x1080 = new Layout(.5f, .5f, .5f, .5f, "#1920", "#1080", "#1920", "#1080", "#1920", "#1080"),
		_1600x900  = new Layout(.5f, .5f, .5f, .5f, "#1600", "#900" , "#1600", "#900" , "#1600", "#900" ),
		_1366x768  = new Layout(.5f, .5f, .5f, .5f, "#1366", "#768" , "#1366", "#768" , "#1366", "#768" ),
		_1280x720  = new Layout(.5f, .5f, .5f, .5f, "#1280", "#720" , "#1280", "#720" , "#1280", "#720" ),
		_1024x576  = new Layout(.5f, .5f, .5f, .5f, "#1024", "#576" , "#1024", "#576" , "#1024", "#576" ),
		//4:3
		_1600x1200 = new Layout(.5f, .5f, .5f, .5f, "#1600", "#1200", "#1600", "#1200", "#1600", "#1200"),
		_1440x1080 = new Layout(.5f, .5f, .5f, .5f, "#1440", "#1080", "#1440", "#1080", "#1440", "#1080"),
		_1400x1050 = new Layout(.5f, .5f, .5f, .5f, "#1400", "#1050", "#1400", "#1050", "#1400", "#1050"),
		_1152x864  = new Layout(.5f, .5f, .5f, .5f, "#1152", "#864" , "#1152", "#864" , "#1152", "#864" ),
		_1024x768  = new Layout(.5f, .5f, .5f, .5f, "#1024", "#768" , "#1024", "#768" , "#1024", "#768" ),
		_800x600   = new Layout(.5f, .5f, .5f, .5f, "#800" , "#600" , "#800" , "#600" , "#800" , "#600" ),
		_768x576   = new Layout(.5f, .5f, .5f, .5f, "#768" , "#576" , "#768" , "#576" , "#768" , "#576" ),
		_640x480   = new Layout(.5f, .5f, .5f, .5f, "#640" , "#480" , "#640" , "#480" , "#640" , "#480" );
}
