package blue.geom;

import blue.geom.Box.Box3f;

public class Region3f extends Box3f {
	private static final long 
		serialVersionUID = 1L;
	protected final Vector3f.Mutable
		v0 = new Vector3f.Mutable(),
		v1 = new Vector3f.Mutable();
	
	public Region3f() {
		//do nothing	
	}
	
	public Region3f(Box<?> box) {
		this.v0.set(box.loc());
		this.v1.set(box.dim());
	}
	
	public Region3f(Vector dim) {
		this.v1.set(dim);
	}
	
	public Region3f(Vector loc, Vector dim) {
		this.v0.set(loc);
		this.v1.set(dim);
	}
	
	public Region3f(float w, float h, float d) {
		this.v1.set(w, h, d);
	}
	
	public Region3f(float x, float y, float z, float w, float h, float d) {
		this.v0.set(x, y, z);
		this.v1.set(w, h, d);
	}
	
	@Override
	public Vector3f loc() {
		return v0;
	}
	
	@Override
	public Vector3f dim() {
		return v1;
	}
	
	@Override
	public Vector3f mid() {
		return new Vector3f(
				v0.x + v1.x / 2,
				v0.y + v1.y / 2,
				v0.z + v1.z / 2
				);
	}
	
	@Override
	public Vector3f min() {
		return new Vector3f(
				Math.min(v0.x, v0.x + v1.x),
				Math.min(v0.y, v0.y + v1.y),
				Math.min(v0.z, v0.z + v1.z)
				);
	}
	
	@Override
	public Vector3f max() {
		return new Vector3f(
				Math.max(v0.x, v0.x + v1.x),
				Math.max(v0.y, v0.y + v1.y),
				Math.max(v0.z, v0.z + v1.z)
				);
	}	
	
	@Override
	public float x1() { return Math.min(v0.x, v0.x + v1.x); }
	@Override
	public float y1() { return Math.min(v0.y, v0.y + v1.y); }
	@Override
	public float z1() { return Math.min(v0.z, v0.z + v1.z); }
	@Override
	public float x2() { return Math.max(v0.x, v0.x + v1.x); }
	@Override
	public float y2() { return Math.max(v0.y, v0.y + v1.y); }
	@Override
	public float z2() { return Math.max(v0.z, v0.z + v1.z); }
	
	@Override
	public Region3f copy() {
		return new Region3f(this);
	}
	
	public static Region3f parseRegion3f(String str) {
		return Box3f.parseBox3f(new Region3f(), str);
	}
	
	public static class Mutable extends Region3f {
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
		
		public Mutable(float w, float h, float d) {
			super(w, h, d);
		}
		
		public Mutable(float x, float y, float z, float w, float h, float d) {
			super(x, y, z, w, h, d);
		}
		
		public Region3f.Mutable set(Box<?> box) {
			this.v0.set(box.loc());
			this.v1.set(box.dim());
			return this;
		}
		
		public Region3f.Mutable set(Vector dim) {
			this.v1.set(dim);
			return this;
		}
		
		public Region3f.Mutable set(Vector loc, Vector dim) {
			this.v0.set(loc);
			this.v1.set(dim);
			return this;
		}
		
		public Region3f.Mutable set(float w, float h, float d) {
			this.v1.set(w, h, d);
			return this;
		}
		
		public Region3f.Mutable set(float x, float y, float z, float w, float h, float d) {
			this.v0.set(x, y, z);
			this.v1.set(w, h, d);
			return this;
		}
	
		@Override
		public Vector3f.Mutable loc() {
			return v0;
		}
	
		@Override
		public Vector3f.Mutable dim() {
			return v1;
		}		
		
		@Override
		public Region3f.Mutable copy() {
			return new Region3f.Mutable(this);
		}
		
		public static Region3f.Mutable parseRegion3f(String str) {
			return Box3f.parseBox3f(new Region3f.Mutable(), str);
		}
	}
}
