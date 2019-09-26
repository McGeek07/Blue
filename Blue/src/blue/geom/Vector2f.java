package blue.geom;

public class Vector2f extends Vector {
	private static final long 
		serialVersionUID = 1L;
	protected float
		x,
		y;
	
	public Vector2f() {
		//do nothing
	}
	
	public Vector2f(Vector v) {
		this.x = v.x();
		this.y = v.y();
	}
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
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
	public Vector2f copy() {
		return new Vector2f(this);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Vector2f) return this.equals((Vector2f)o);
		if(o instanceof Vector3f) return this.equals((Vector3f)o);
		return false;
	}
	
	public boolean equals(Vector2f v) {
		return Vector.compare(this, v) == 0;
	}
	
	public boolean equals(Vector3f v) {
		return Vector.compare(this, v) == 0;
	}
	
	public static class Mutable extends Vector2f {
		private static final long 
			serialVersionUID = 1L;

		public Mutable() {
			super();
		}
		
		public Mutable(Vector v) {
			super(v);
		}
		
		public Mutable(float x, float y) {
			super(x, y);
		}
		
		public Vector2f.Mutable set(Vector v) {
			this.x = v.x();
			this.y = v.y();
			return this;
		}
		
		public Vector2f.Mutable set(float x, float y) {
			this.x = x;
			this.y = y;
			return this;
		}
		
		public Vector2f.Mutable setX(float x) {
			this.x = x;
			return this;
		}
		
		public Vector2f.Mutable setY(float y) {
			this.y = y;
			return this;
		}
		
		@Override
		public Vector2f.Mutable copy() {
			return new Vector2f.Mutable(this);
		}
	}
}
