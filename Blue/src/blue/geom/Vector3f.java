package blue.geom;

public class Vector3f extends Vector {
	private static final long 
		serialVersionUID = 1L;
	protected float
		x,
		y,
		z;
	
	public Vector3f() {
		//do nothing
	}
	
	public Vector3f(Vector v) {
		this.x = v.x();
		this.y = v.y();
		this.z = v.z();
	}
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public float x() {
		return this.x;
	}
	
	@Override
	public float y() {
		return this.y;
	}
	
	@Override
	public float z() {
		return this.z;
	}
	
	@Override
	public Vector3f copy() {
		return new Vector3f(this);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Vector3f) return this.equals((Vector3f)o);
		if(o instanceof Vector2f) return this.equals((Vector2f)o);
		return false;
	}
	
	public boolean equals(Vector3f v) {
		return Vector.compare(this, v) == 0;
	}
	
	public boolean equals(Vector2f v) {
		return Vector.compare(this, v) == 0;
	}
	
	public static class Mutable extends Vector3f {
		private static final long 
			serialVersionUID = 1L;
	
		public Mutable() {
			super();
		}
		
		public Mutable(Vector v) {
			super(v);
		}
		
		public Mutable(float x, float y, float z) {
			super(x, y, z);
		}
		
		public Vector3f.Mutable set(Vector v) {
			this.x = v.x();
			this.y = v.y();
			this.z = v.z();
			return this;
		}
		
		public Vector3f.Mutable set(float x, float y, float z) {
			this.x = x;
			this.y = y;
			this.z = z;
			return this;
		}
		
		public Vector3f.Mutable setX(float x) {
			this.x = x;
			return this;
		}
		
		public Vector3f.Mutable setY(float y) {
			this.y = y;
			return this;
		}
		
		public Vector3f.Mutable setZ(float z) {
			this.z = z;
			return this;
		}
		
		@Override
		public Vector3f.Mutable copy() {
			return new Vector3f.Mutable(this);
		}
	}
}
