package blue.geom;

import java.io.Serializable;

import blue.util.Copyable;
import blue.util.Util;

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
	
	public static abstract class Box2 extends Box<Vector2> {
		private static final long 
			serialVersionUID = 1L;
		protected final Vector2.Mutable
			v0 = new Vector2.Mutable(),
			v1 = new Vector2.Mutable();
		
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
		
		@Override
		public String toString() {
			return toString(this, "%s");
		}
		
		public static String toString(Box2 b2, String format) {
			return "<"
					+ String.format(format, b2.v0.x) + ", "
					+ String.format(format, b2.v0.y) + ", "
					+ String.format(format, b2.v1.x) + ", "
					+ String.format(format, b2.v1.y) + ">";
		}
		
		protected static final <B extends Box2> B fromString(B b2, String str) {
			if(b2 == null)
	            throw new IllegalArgumentException("Null Box");
	        if (str == null)
	            throw new IllegalArgumentException("Null String");
	        if ((str = str.trim()).isEmpty())
	            throw new IllegalArgumentException("Empty String");
	        int
	            a = str.indexOf("<"),
	            b = str.indexOf(">");
	        if (a >= 0 || b >= 0) {
	            if (b > a) {
	                str = str.substring(++a, b);
	            } else {
	                str = str.substring(++a);
	            }
	        }
	        String[] tmp = str.split("\\,");
	        float[] arr = new float[tmp.length];
	        for (int i = 0; i < tmp.length; i++) {
	            arr[i] = Util.stringToFloat(tmp[i]);
	        }
	        switch (arr.length) {
	            default:
	            case 4:
	            	b2.v1.y = arr[3];
	            case 3:
	            	b2.v1.x = arr[2];
	            case 2:
	                b2.v0.y = arr[1];
	            case 1:
	                b2.v0.x = arr[0];
	            case 0:
	        }
	        return b2;
		}
	}
	
	public static abstract class Box3 extends Box<Vector3> {
		private static final long 
			serialVersionUID = 1L;
		protected final Vector3.Mutable
			v0 = new Vector3.Mutable(),
			v1 = new Vector3.Mutable();
		
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
		
		@Override
		public String toString() {
			return toString(this, "%s");
		}
		
		public static String toString(Box3 b3, String format) {
			return "<"
					+ String.format(format, b3.v0.x) + ", "
					+ String.format(format, b3.v0.y) + ", "
					+ String.format(format, b3.v0.z) + ", "
					+ String.format(format, b3.v1.x) + ", "
					+ String.format(format, b3.v1.y) + ", "
					+ String.format(format, b3.v1.z) + ">";
		}
		
		protected static final <B extends Box3> B fromString(B b3, String str) {
			if(b3 == null)
	            throw new IllegalArgumentException("Null Box");
	        if (str == null)
	            throw new IllegalArgumentException("Null String");
	        if ((str = str.trim()).isEmpty())
	            throw new IllegalArgumentException("Empty String");
	        int
	            a = str.indexOf("<"),
	            b = str.indexOf(">");
	        if (a >= 0 || b >= 0) {
	            if (b > a) {
	                str = str.substring(++a, b);
	            } else {
	                str = str.substring(++a);
	            }
	        }
	        String[] tmp = str.split("\\,");
	        float[] arr = new float[tmp.length];
	        for (int i = 0; i < tmp.length; i++) {
	            arr[i] = Util.stringToFloat(tmp[i]);
	        }
	        switch (arr.length) {
	            default:
	            case 6:
	            	b3.v1.z = arr[5];
	            case 5:
	                b3.v1.y = arr[4];
	            case 4:
	                b3.v1.x = arr[3];
	            case 3:
	            	b3.v0.z = arr[2];
	            case 2:
	                b3.v0.y = arr[1];
	            case 1:
	                b3.v0.x = arr[0];
	            case 0:
	        }
	        return b3;
		}
	}	
	
	public static boolean contains(Box2 a, Vector b, boolean include_edge) {
		return contains(a.min(), a.max(), b      , b      , include_edge);			
	}
	
	public static boolean contains(Box2 a, Box<?> b, boolean include_edge) {
		return contains(a.min(), a.max(), b.min(), b.max(), include_edge);
	}
	
	public static boolean contacts(Box2 a, Box<?> b, boolean include_edge) {
		return contacts(a.min(), a.max(), b.min(), b.max(), include_edge);
	}
	
	public static boolean contains(Box3 a, Vector b, boolean include_edge) {
		return contains(a.min(), a.max(), b      , b      , include_edge);			
	}
	
	public static boolean contains(Box3 a, Box<?> b, boolean include_edge) {
		return contains(a.min(), a.max(), b.min(), b.max(), include_edge);
	}
	
	public static boolean contacts(Box3 a, Box<?> b, boolean include_edge) {
		return contacts(a.min(), a.max(), b.min(), b.max(), include_edge);
	}
	
	public static boolean contains(Vector2 a1, Vector2 a2, Vector b1, Vector b2, boolean include_edge) {
		if(include_edge)
			return
					a1.x <= b1.x() && a2.x >= b2.x() &&
					a1.y <= b1.y() && a2.y >= b2.y();
		else
			return 	
					a1.x <  b1.x() && a2.x >  b2.x() &&
					a1.y <  b1.y() && a2.y >  b2.y();
	}
	
	public static boolean contains(Vector3 a1, Vector3 a2, Vector b1, Vector b2, boolean include_edge) {
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
	
	public static boolean contacts(Vector2 a1, Vector2 a2, Vector b1, Vector b2, boolean include_edge) {
		if(include_edge)
			return
					a1.x <= b2.x() && a2.x >= b1.x() &&
					a1.y <= b2.y() && a2.y >= b1.y();
		else
			return 	
					a1.x <  b2.x() && a2.x >  b1.x() &&
					a1.y <  b2.y() && a2.y >  b1.y();
	}
	
	public static boolean contacts(Vector3 a1, Vector3 a2, Vector b1, Vector b2, boolean include_edge) {
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
