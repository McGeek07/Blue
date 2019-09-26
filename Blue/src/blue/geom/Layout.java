package blue.geom;

import blue.util.Util;

public class Layout {	
	protected final Attribute
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
	
	public Region2f region(Box<?> box) {
		return region(box, preferred_w, preferred_h);
	}
	
	public Region2f region(Box<?> box, Object preferred_w, Object preferred_h) {
		return region(box, new Attribute(preferred_w), new Attribute(preferred_h));
	}
	
	public Region2f region(Box<?> box, Attribute preferred_w, Attribute preferred_h) {
		float[] layout = compute(
				box.x(), box.y(),
				box.w(), box.h(),
				preferred_w,
				preferred_h
				);
		return new Region2f(
				layout[0],
				layout[1],
				layout[2],
				layout[3]
				);
	}
	
	public Bounds2f bounds(Box<?> box) {
		return bounds(box, preferred_w, preferred_h);
	}
	
	public Bounds2f bounds(Box<?> box, Object preferred_w, Object preferred_h) {
		return bounds(box, new Attribute(preferred_w), new Attribute(preferred_h));
	}
	
	public Bounds2f bounds(Box<?> box, Attribute preferred_w, Attribute preferred_h) {
		float[] layout = compute(
				box.x(), box.y(),
				box.w(), box.h(),
				preferred_w,
				preferred_h
				);
		return new Bounds2f(
				layout[0],
				layout[1],
				layout[4],
				layout[5]
				);
	}
	
	public Region2f.Mutable apply(Region2f.Mutable box) {
		return apply(box, preferred_w, preferred_h);
	}
	
	public Region2f.Mutable apply(Region2f.Mutable box, Object preferred_w, Object preferred_h) {
		return apply(box, new Attribute(preferred_w), new Attribute(preferred_h));
	}

	public Region2f.Mutable apply(Region2f.Mutable box, Attribute preferred_w, Attribute preferred_h) {
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
	
	public Bounds2f.Mutable apply(Bounds2f.Mutable box) {
		return apply(box, preferred_w, preferred_h);
	}
	
	public Bounds2f.Mutable apply(Bounds2f.Mutable box, Object preferred_w, Object preferred_h) {
		return apply(box, new Attribute(preferred_w), new Attribute(preferred_h));
	}

	public Bounds2f.Mutable apply(Bounds2f.Mutable box, Attribute preferred_w, Attribute preferred_h) {
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
			dst_w = preferred_w.fixed ? preferred_w.value : preferred_w.value * src_w,
			dst_h = preferred_h.fixed ? preferred_h.value : preferred_h.value * src_h,
			min_w = minimum_w.fixed   ? minimum_w.value   : minimum_w.value   * src_w,
			min_h = minimum_h.fixed   ? minimum_h.value   : minimum_h.value   * src_h,
			max_w = maximum_w.fixed   ? maximum_w.value   : maximum_w.value   * src_w,
			max_h = maximum_h.fixed   ? maximum_h.value   : maximum_h.value   * src_h;
		dst_w = minimum_w.fixed ? clamp1(dst_w, min_w, max_w) : clamp2(dst_w, min_w, max_w);
		dst_h = minimum_h.fixed ? clamp1(dst_h, min_h, max_h) : clamp2(dst_h, min_h, max_h);
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
	
	protected static float clamp1(float x, float a, float b) {
		if(x < a) return a;
		if(x > b) return b;
		return x;
	}
	
	protected static float clamp2(float x, float a, float b) {
		if(x > b) return b;
		if(x < a) return a;
		return x;
	}
	
	@Override
	public String toString() {
		return "{"
				+ anchor_x + ", "
				+ anchor_y + ", "
				+ offset_x + ", "
				+ offset_y + ", "
				+ preferred_w + ", "
				+ preferred_h + ", "
				+ minimum_w + ", "
				+ minimum_h + ", "
				+ maximum_w + ", "
				+ maximum_h + "}";
	}
	
	public static class Attribute {
		public float
			value;
		public boolean
			fixed;
		
		public Attribute(Object obj) {
			if(obj instanceof Number) {
				Number n = (Number)obj;
				
				value = n.floatValue();
				fixed = false;
			}
			if(obj instanceof String) {
				String s = (String)obj;
				int i = s.indexOf('#');
				s = s.substring(i + 1);
				
				value = Util.stringToFloat(s);
				fixed = i >= 0;
			}
			if(obj instanceof Attribute) {
				Attribute a = (Attribute)obj;
				
				value = a.value;
				fixed = a.fixed;
			}
		}
		
		public String toString() {
			return (fixed ? "#" : "") + value;
		}
	}
	
	public static final Layout
		DEFAULT = new Layout(.5f, .5f, .5f, .5f, 1f, 1f),
		//HALF
		LV_HALF = new Layout( 0f, .5f,  0f, .5f, .5f,  1f),
		MV_HALF = new Layout(.5f, .5f, .5f, .5f, .5f,  1f),
		RV_HALF = new Layout( 1f, .5f,  1f, .5f, .5f,  1f),
		TH_HALF = new Layout(.5f,  0f, .5f,  0f,  1f, .5f),
		MH_HALF = new Layout(.5f, .5f, .5f, .5f,  1f, .5f),
		BH_HALF = new Layout(.5f,  1f, .5f,  1f,  1f, .5f);
}
