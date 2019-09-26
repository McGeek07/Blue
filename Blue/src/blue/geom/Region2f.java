package blue.geom;

import blue.geom.Box.Box2f;

public class Region2f extends Box2f {
	private static final long 
		serialVersionUID = 1L;
	
	public Region2f() {
		//do nothing	
	}
	
	public Region2f(Box<?> box) {
		this.v0.set(box.loc());
		this.v1.set(box.dim());
	}
	
	public Region2f(Vector dim) {
		this.v1.set(dim);
	}
	
	public Region2f(Vector loc, Vector dim) {
		this.v0.set(loc);
		this.v1.set(dim);
	}
	
	public Region2f(float w, float h) {
		this.v1.set(w, h);
	}
	
	public Region2f(float x, float y, float w, float h) {
		this.v0.set(x, y);
		this.v1.set(w, h);
	}

	@Override
	public Vector2f loc() {
		return v0;
	}

	@Override
	public Vector2f dim() {
		return v1;
	}

	@Override
	public Vector2f mid() {
		return new Vector2f(
				v0.x + v1.x / 2,
				v0.y + v1.y / 2
				);
	}

	@Override
	public Vector2f min() {
		return new Vector2f(
				Math.min(v0.x, v0.x + v1.x),
				Math.min(v0.y, v0.y + v1.y)
				);
	}

	@Override
	public Vector2f max() {
		return new Vector2f(
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
	public Region2f copy() {
		return new Region2f(this);
	}
	
	public static class Mutable extends Region2f {
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
		
		public Region2f.Mutable set(Box<?> box) {
			this.v0.set(box.loc());
			this.v1.set(box.dim());
			return this;
		}
		
		public Region2f.Mutable set(Vector dim) {
			this.v1.set(dim);
			return this;
		}
		
		public Region2f.Mutable set(Vector loc, Vector dim) {
			this.v0.set(loc);
			this.v1.set(dim);
			return this;
		}
		
		public Region2f.Mutable set(float w, float h) {
			this.v1.set(w, h);
			return this;
		}
		
		public Region2f.Mutable set(float x, float y, float w, float h) {
			this.v0.set(x, y);
			this.v1.set(w, h);
			return this;
		}
	
		@Override
		public Vector2f.Mutable loc() {
			return v0;
		}
	
		@Override
		public Vector2f.Mutable dim() {
			return v1;
		}		
		
		@Override
		public Region2f.Mutable copy() {
			return new Region2f.Mutable(this);
		}
	}
}
