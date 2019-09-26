package blue.geom;

import blue.geom.Box.Box3f;

public class Bounds3f extends Box3f {
	private static final long 
		serialVersionUID = 1L;
	
	public Bounds3f() {
		//do nothing
	}
	
	public Bounds3f(Box<?> box) {
		this.v0.set(box.min());
		this.v1.set(box.max());
	}
	
	public Bounds3f(Vector min, Vector max) {
		this.v0.set(min);
		this.v1.set(max);
	}
	
	public Bounds3f(float x1, float y1, float z1, float x2, float y2, float z2) {
		this.v0.set(x1, y1, z1);
		this.v1.set(x2, y2, z2);
	}

	@Override
	public Vector3f loc() {
		return new Vector3f(
				v0.x,
				v0.y,
				v0.z
				);
	}

	@Override
	public Vector3f dim() {
		return new Vector3f(
				v1.x - v0.x,
				v1.y - v0.y,
				v1.z - v0.z
				);
	}

	@Override
	public Vector3f mid() {
		return new Vector3f(
				(v0.x + v1.x) / 2, 
				(v0.y + v1.y) / 2,
				(v0.z + v1.z) / 2 
				);
	}

	@Override
	public Vector3f min() {
		return v0;
	}

	@Override
	public Vector3f max() {
		return v1;
	}
	
	@Override
	public float x() { return v0.x; }
	@Override
	public float y() { return v0.y; }
	@Override
	public float z() { return v0.z; }
	@Override
	public float w() { return v1.x - v0.x; }
	@Override
	public float h() { return v1.y - v0.y; }
	@Override
	public float d() { return v1.z - v0.z; }

	@Override
	public Bounds3f copy() {
		return new Bounds3f(this);
	}
	
	public static Bounds3f parseBounds3f(String str) {
		return Box3f.parseBox3f(new Bounds3f(), str);
	}
	
	public static class Mutable extends Bounds3f {
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
		
		public Mutable(float x1, float y1, float z1, float x2, float y2, float z2) {
			super(x1, y1, z1, x2, y2, z2);
		}
		
		public Bounds3f.Mutable set(Box<?> box) {
			this.v0.set(box.min());
			this.v1.set(box.max());
			return this;
		}
		
		public Bounds3f.Mutable set(Vector min, Vector max) {
			this.v0.set(min);
			this.v1.set(max);
			return this;
		}
		
		public Bounds3f.Mutable set(float x1, float y1, float z1, float x2, float y2, float z2) {
			this.v0.set(x1, y1, z1);
			this.v1.set(x2, y2, z2);
			return this;
		}
		
		@Override
		public Vector3f.Mutable min() {
			return v0;
		}
		
		@Override
		public Vector3f.Mutable max() {
			return v1;
		}
		
		@Override
		public Bounds3f.Mutable copy() {
			return new Bounds3f.Mutable(this);
		}
		
		public static Bounds3f.Mutable parseBounds3f(String str) {
			return Box3f.parseBox3f(new Bounds3f.Mutable(), str);
		}
	}
}
