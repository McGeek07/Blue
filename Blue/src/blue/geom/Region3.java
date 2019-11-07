package blue.geom;

import blue.geom.Box.Box3;

public class Region3 extends Box3 {
	private static final long 
		serialVersionUID = 1L;
	protected final Vector3.Mutable
		v0 = new Vector3.Mutable(),
		v1 = new Vector3.Mutable();
	
	public Region3() {
		//do nothing	
	}
	
	public Region3(Box<?> box) {
		this.v0.set(box.loc());
		this.v1.set(box.dim());
	}
	
	public Region3(Vector dim) {
		this.v1.set(dim);
	}
	
	public Region3(Vector loc, Vector dim) {
		this.v0.set(loc);
		this.v1.set(dim);
	}
	
	public Region3(float w, float h, float d) {
		this.v1.set(w, h, d);
	}
	
	public Region3(float x, float y, float z, float w, float h, float d) {
		this.v0.set(x, y, z);
		this.v1.set(w, h, d);
	}
	
	@Override
	public Vector3 loc() {
		return v0;
	}
	
	@Override
	public Vector3 dim() {
		return v1;
	}
	
	@Override
	public Vector3 mid() {
		return new Vector3(
				v0.x + v1.x / 2,
				v0.y + v1.y / 2,
				v0.z + v1.z / 2
				);
	}
	
	@Override
	public Vector3 min() {
		return new Vector3(
				Math.min(v0.x, v0.x + v1.x),
				Math.min(v0.y, v0.y + v1.y),
				Math.min(v0.z, v0.z + v1.z)
				);
	}
	
	@Override
	public Vector3 max() {
		return new Vector3(
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
	public Region3 copy() {
		return new Region3(this);
	}
	
	public static Region3 parseRegion3f(String str) {
		return Box3.parseBox3(new Region3(), str);
	}
	
	public static class Mutable extends Region3 {
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
		
		public Region3.Mutable set(Box<?> box) {
			this.v0.set(box.loc());
			this.v1.set(box.dim());
			return this;
		}
		
		public Region3.Mutable set(Vector dim) {
			this.v1.set(dim);
			return this;
		}
		
		public Region3.Mutable set(Vector loc, Vector dim) {
			this.v0.set(loc);
			this.v1.set(dim);
			return this;
		}
		
		public Region3.Mutable set(float w, float h, float d) {
			this.v1.set(w, h, d);
			return this;
		}
		
		public Region3.Mutable set(float x, float y, float z, float w, float h, float d) {
			this.v0.set(x, y, z);
			this.v1.set(w, h, d);
			return this;
		}
	
		@Override
		public Vector3.Mutable loc() {
			return v0;
		}
	
		@Override
		public Vector3.Mutable dim() {
			return v1;
		}		
		
		@Override
		public Region3.Mutable copy() {
			return new Region3.Mutable(this);
		}
		
		public static Region3.Mutable parseRegion3f(String str) {
			return Box3.parseBox3(new Region3.Mutable(), str);
		}
	}
}
