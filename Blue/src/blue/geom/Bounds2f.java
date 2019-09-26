package blue.geom;

import blue.geom.Box.Box2f;

public class Bounds2f extends Box2f {
	private static final long 
		serialVersionUID = 1L;
	
	public Bounds2f() {
		//do nothing
	}
	
	public Bounds2f(Box<?> box) {
		this.v0.set(box.min());
		this.v1.set(box.max());
	}
	
	public Bounds2f(Vector min, Vector max) {
		this.v0.set(min);
		this.v1.set(max);
	}
	
	public Bounds2f(float x1, float y1, float x2, float y2) {
		this.v0.set(x1, y1);
		this.v1.set(x2, y2);
	}

	@Override
	public Vector2f loc() {
		return new Vector2f(
				v0.x,
				v0.y
				);
	}

	@Override
	public Vector2f dim() {
		return new Vector2f(
				v1.x - v0.x,
				v1.y - v0.y
				);
	}

	@Override
	public Vector2f mid() {
		return new Vector2f(
				(v0.x + v1.x) / 2, 
				(v0.y + v1.y) / 2
				);
	}

	@Override
	public Vector2f min() {
		return v0;
	}

	@Override
	public Vector2f max() {
		return v1;
	}
	
	@Override
	public float x() { return v0.x; }
	@Override
	public float y() { return v0.y; }
	@Override
	public float z() { return 0f; }
	@Override
	public float w() { return v1.x - v0.x; }
	@Override
	public float h() { return v1.y - v0.y; }
	@Override
	public float d() { return 0f; }

	@Override
	public Bounds2f copy() {
		return new Bounds2f(this);
	}
	
	public static Bounds2f parseBounds2f(String str) {
		return Box2f.parseBox2f(new Bounds2f(), str);
	}
	
	public static class Mutable extends Bounds2f {
		private static final long 
			serialVersionUID = 1L;
		
		public Mutable() {
			super();
		}
		
		public Mutable(Box<?> box) {
			super(box);
		}
		
		public Mutable(Vector min, Vector max) {
			super(min, max);
		}
		
		public Mutable(float x1, float y1, float x2, float y2) {
			super(x1, y1, x2, y2);
		}
		
		public Bounds2f.Mutable set(Box<?> box) {
			this.v0.set(box.min());
			this.v1.set(box.max());
			return this;
		}
		
		public Bounds2f.Mutable set(Vector min, Vector max) {
			this.v0.set(min);
			this.v1.set(max);
			return this;
		}
		
		public Bounds2f.Mutable set(float x1, float y1, float x2, float y2) {
			this.v0.set(x1, y1);
			this.v1.set(x2, y2);
			return this;
		}
		
		@Override
		public Vector2f.Mutable min() {
			return v0;
		}
		
		@Override
		public Vector2f.Mutable max() {
			return v1;
		}
		
		@Override
		public Bounds2f.Mutable copy() {
			return new Bounds2f.Mutable(this);
		}
		
		public static Bounds2f.Mutable parseBounds2f(String str) {
			return Box2f.parseBox2f(new Bounds2f.Mutable(), str);
		}
	}
}
