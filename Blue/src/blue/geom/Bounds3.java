package blue.geom;

import blue.geom.Box.Box3;

public class Bounds3 extends Box3 {
	private static final long 
		serialVersionUID = 1L;
	
	public Bounds3() {
		//do nothing
	}
	
	public Bounds3(Box<?> box) {
		this.v0.set(box.min());
		this.v1.set(box.max());
	}
	
	public Bounds3(Vector min, Vector max) {
		this.v0.set(min);
		this.v1.set(max);
	}
	
	public Bounds3(float x1, float y1, float z1, float x2, float y2, float z2) {
		this.v0.set(x1, y1, z1);
		this.v1.set(x2, y2, z2);
	}

	@Override
	public Vector3 loc() {
		return new Vector3(
				v0.x,
				v0.y,
				v0.z
				);
	}

	@Override
	public Vector3 dim() {
		return new Vector3(
				v1.x - v0.x,
				v1.y - v0.y,
				v1.z - v0.z
				);
	}

	@Override
	public Vector3 mid() {
		return new Vector3(
				(v0.x + v1.x) / 2, 
				(v0.y + v1.y) / 2,
				(v0.z + v1.z) / 2 
				);
	}

	@Override
	public Vector3 min() {
		return v0;
	}

	@Override
	public Vector3 max() {
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
	public Bounds3 copy() {
		return new Bounds3(this);
	}
	
	public static Bounds3 fromString(String str) {
		return Box3.fromString(new Bounds3(), str);
	}
	
	public static class Mutable extends Bounds3 {
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
		
		public Bounds3.Mutable set(Box<?> box) {
			this.v0.set(box.min());
			this.v1.set(box.max());
			return this;
		}
		
		public Bounds3.Mutable set(Vector min, Vector max) {
			this.v0.set(min);
			this.v1.set(max);
			return this;
		}
		
		public Bounds3.Mutable set(float x1, float y1, float z1, float x2, float y2, float z2) {
			this.v0.set(x1, y1, z1);
			this.v1.set(x2, y2, z2);
			return this;
		}
		
		@Override
		public Vector3.Mutable min() {
			return v0;
		}
		
		@Override
		public Vector3.Mutable max() {
			return v1;
		}
		
		@Override
		public Bounds3.Mutable copy() {
			return new Bounds3.Mutable(this);
		}
		
		public static Bounds3.Mutable fromString(String str) {
			return Box3.fromString(new Bounds3.Mutable(), str);
		}
	}
}
