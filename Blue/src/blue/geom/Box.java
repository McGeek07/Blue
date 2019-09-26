package blue.geom;

import java.io.Serializable;

import blue.util.Copyable;

public abstract class Box<T extends Vector> implements Serializable, Copyable<Box<T>> {
	private static final long 
		serialVersionUID = 1L;
	
	public abstract T loc();
	public abstract T dim();	
	public abstract T mid();
	public abstract T min();
	public abstract T max();
	
	public float x() { return loc().x(); }
	public float y() { return loc().y(); }
	public float z() { return loc().z(); }
	public float w() { return dim().x(); }
	public float h() { return dim().y(); }
	public float d() { return dim().z(); }
	
	public float x1() { return min().x(); }
	public float y1() { return min().y(); }
	public float z1() { return min().z(); }
	public float x2() { return max().x(); }
	public float y2() { return max().y(); }
	public float z2() { return max().z(); }
	
	public abstract boolean contains(Vector b, boolean include_edge);
	public abstract boolean contains(Box<?> b, boolean include_edge);
	public abstract boolean contacts(Box<?> b, boolean include_edge);
	
	public static abstract class Box2f extends Box<Vector2f> {
		private static final long 
			serialVersionUID = 1L;
		protected final Vector2f.Mutable
			v0 = new Vector2f.Mutable(),
			v1 = new Vector2f.Mutable();
		
		@Override
		public boolean contains(Vector b, boolean include_edge) {
			return Box.contains(this, b, include_edge);
		}

		@Override
		public boolean contains(Box<?> b, boolean include_edge) {
			return Box.contains(this, b, include_edge);
		}

		@Override
		public boolean contacts(Box<?> b, boolean include_edge) {
			return Box.contacts(this, b, include_edge);
		}		
	}
	
	public static abstract class Box3f extends Box<Vector3f> {
		private static final long 
			serialVersionUID = 1L;
		protected final Vector3f.Mutable
			v0 = new Vector3f.Mutable(),
			v1 = new Vector3f.Mutable();
		
		@Override
		public boolean contains(Vector b, boolean include_edge) {
			return Box.contains(this, b, include_edge);
		}

		@Override
		public boolean contains(Box<?> b, boolean include_edge) {
			return Box.contains(this, b, include_edge);
		}

		@Override
		public boolean contacts(Box<?> b, boolean include_edge) {
			return Box.contacts(this, b, include_edge);
		}		
	}	
	
	public static boolean contains(Box2f a, Vector b, boolean include_edge) {
		return contains(a.min(), a.max(), b      , b      , include_edge);			
	}
	
	public static boolean contains(Box2f a, Box<?> b, boolean include_edge) {
		return contains(a.min(), a.max(), b.min(), b.max(), include_edge);
	}
	
	public static boolean contacts(Box2f a, Box<?> b, boolean include_edge) {
		return contacts(a.min(), a.max(), b.min(), b.max(), include_edge);
	}
	
	public static boolean contains(Box3f a, Vector b, boolean include_edge) {
		return contains(a.min(), a.max(), b      , b      , include_edge);			
	}
	
	public static boolean contains(Box3f a, Box<?> b, boolean include_edge) {
		return contains(a.min(), a.max(), b.min(), b.max(), include_edge);
	}
	
	public static boolean contacts(Box3f a, Box<?> b, boolean include_edge) {
		return contacts(a.min(), a.max(), b.min(), b.max(), include_edge);
	}
	
	public static boolean contains(Vector2f a1, Vector2f a2, Vector b1, Vector b2, boolean include_edge) {
		if(include_edge)
			return
					a1.x <= b1.x() && a2.x >= b2.x() &&
					a1.y <= b1.y() && a2.y >= b2.y();
		else
			return 	
					a1.x <  b1.x() && a2.x >  b2.x() &&
					a1.y <  b1.y() && a2.y >  b2.y();
	}
	
	public static boolean contains(Vector3f a1, Vector3f a2, Vector b1, Vector b2, boolean include_edge) {
		if(include_edge)
			return
					a1.x <= b1.x() && a2.x >= b2.x() &&
					a1.y <= b1.y() && a2.y >= b2.y() &&
					a1.z <= b1.z() && a2.z >= b2.z();
		else
			return 	
					a1.x <  b1.x() && a2.x >  b2.x() &&
					a1.y <  b1.y() && a2.y >  b2.y() &&
					a1.z <  b1.z() && a2.z >  b2.z();
	}
	
	public static boolean contacts(Vector2f a1, Vector2f a2, Vector b1, Vector b2, boolean include_edge) {
		if(include_edge)
			return
					a1.x <= b2.x() && a2.x >= b1.x() &&
					a1.y <= b2.y() && a2.y >= b1.y();
		else
			return 	
					a1.x <  b2.x() && a2.x >  b1.x() &&
					a1.y <  b2.y() && a2.y >  b1.y();
	}
	
	public static boolean contacts(Vector3f a1, Vector3f a2, Vector b1, Vector b2, boolean include_edge) {
		if(include_edge)
			return
					a1.x <= b2.x() && a2.x >= b1.x() &&
					a1.y <= b2.y() && a2.y >= b1.y() &&
					a1.z <= b2.z() && a2.z >= b1.z();
		else
			return 	
					a1.x <  b2.x() && a2.x >  b1.x() &&
					a1.y <  b2.y() && a2.y >  b1.y() &&
					a1.z <  b2.z() && a2.z >  b1.z();
	}
}
