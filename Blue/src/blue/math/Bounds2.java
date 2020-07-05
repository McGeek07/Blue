package blue.math;

import blue.math.Box.Box2;

public class Bounds2 extends Box2 {
	private static final long 
		serialVersionUID = 1L;
	
	public Bounds2() {
		//do nothing
	}
	
	public Bounds2(Box<?> box) {
		v0.set(box.min());
		v1.set(box.max());
	}
	
	public Bounds2(Vector min, Vector max) {
		v0.set(min);
		v1.set(max);
	}
	
	public Bounds2(float x1, float y1, float x2, float y2) {
		v0.set(x1, y1);
		v1.set(x2, y2);
	}

	@Override
	public Vector2 loc() {
		return new Vector2(
			v0.x,
			v0.y
		);
	}

	@Override
	public Vector2 dim() {
		return new Vector2(
			v1.x - v0.x,
			v1.y - v0.y
		);
	}

	@Override
	public Vector2 mid() {
		return new Vector2(
			(v0.x + v1.x) / 2, 
			(v0.y + v1.y) / 2
		);
	}

	@Override
	public Vector2 min() {
		return v0;
	}

	@Override
	public Vector2 max() {
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
	public Bounds2 copy() {
		return new Bounds2(this);
	}
	
	public static Bounds2 parseBounds2(String str) {
		return Box2.parseBox2(new Bounds2(), str);
	}
	
	public static class Mutable extends Bounds2 {
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
		
		public Bounds2.Mutable set(Box<?> box) {
			v0.set(box.min());
			v1.set(box.max());
			return this;
		}
		
		public Bounds2.Mutable set(Vector min, Vector max) {
			v0.set(min);
			v1.set(max);
			return this;
		}
		
		public Bounds2.Mutable set(float x1, float y1, float x2, float y2) {
			v0.set(x1, y1);
			v1.set(x2, y2);
			return this;
		}
		
		@Override
		public Vector2.Mutable min() {
			return v0;
		}
		
		@Override
		public Vector2.Mutable max() {
			return v1;
		}
		
		@Override
		public Bounds2.Mutable copy() {
			return new Bounds2.Mutable(this);
		}
		
		public static Bounds2.Mutable parseBounds2(String str) {
			return Box2.parseBox2(new Bounds2.Mutable(), str);
		}
	}
}
