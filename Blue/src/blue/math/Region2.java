package blue.math;

import blue.math.Box.Box2;

public class Region2 extends Box2 {
	private static final long 
		serialVersionUID = 1L;
	
	public Region2() {
		//do nothing	
	}
	
	public Region2(Box<?> box) {
		v0.set(box.loc());
		v1.set(box.dim());
	}
	
	public Region2(Vector dim) {
		v1.set(dim);
	}
	
	public Region2(Vector loc, Vector dim) {
		v0.set(loc);
		v1.set(dim);
	}
	
	public Region2(float w, float h) {
		v1.set(w, h);
	}
	
	public Region2(float x, float y, float w, float h) {
		v0.set(x, y);
		v1.set(w, h);
	}

	@Override
	public Vector2 loc() {
		return v0;
	}

	@Override
	public Vector2 dim() {
		return v1;
	}

	@Override
	public Vector2 mid() {
		return new Vector2(
			v0.x + v1.x / 2,
			v0.y + v1.y / 2
		);
	}

	@Override
	public Vector2 min() {
		return new Vector2(
			Math.min(v0.x, v0.x + v1.x),
			Math.min(v0.y, v0.y + v1.y)
		);
	}

	@Override
	public Vector2 max() {
		return new Vector2(
			Math.max(v0.x, v0.x + v1.x),
			Math.max(v0.y, v0.y + v1.y)
		);
	}	
	
	@Override
	public float x1() { return Math.min(v0.x, v0.x + v1.x); }
	@Override
	public float y1() { return Math.min(v0.y, v0.y + v1.y); }
	@Override
	public float z1() { return 0f; }
	@Override
	public float x2() { return Math.max(v0.x, v0.x + v1.x); }
	@Override
	public float y2() { return Math.max(v0.y, v0.y + v1.y); }
	@Override
	public float z2() { return 0f; }

	@Override
	public Region2 copy() {
		return new Region2(this);
	}
	
	public static Region2 fromString(String s) {
		return Box2.fromString(new Region2(), s);
	}
	
	public static class Mutable extends Region2 {
		private static final long 
			serialVersionUID = 1L;
	
		public Mutable() {
			super();
		}
		
		public Mutable(Box<?> box) {
			super(box);
		}
		
		public Mutable(Vector dim) {
			super(dim);
		}
		
		public Mutable(Vector loc, Vector dim) {
			super(loc, dim);
		}
		
		public Mutable(float w, float h) {
			super(w, h);
		}
		
		public Mutable(float x, float y, float w, float h) {
			super(x, y, w, h);
		}
		
		public Region2.Mutable set(Box<?> box) {
			v0.set(box.loc());
			v1.set(box.dim());
			return this;
		}
		
		public Region2.Mutable set(Vector dim) {
			v1.set(dim);
			return this;
		}
		
		public Region2.Mutable set(Vector loc, Vector dim) {
			v0.set(loc);
			v1.set(dim);
			return this;
		}
		
		public Region2.Mutable set(float w, float h) {
			v1.set(w, h);
			return this;
		}
		
		public Region2.Mutable set(float x, float y, float w, float h) {
			v0.set(x, y);
			v1.set(w, h);
			return this;
		}
	
		@Override
		public Vector2.Mutable loc() {
			return v0;
		}
	
		@Override
		public Vector2.Mutable dim() {
			return v1;
		}		
		
		@Override
		public Region2.Mutable copy() {
			return new Region2.Mutable(this);
		}
		
		public static Region2.Mutable fromString(String s) {
			return Box2.fromString(new Region2.Mutable(), s);
		}
	}
}
