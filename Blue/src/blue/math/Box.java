package blue.math;

import java.io.Serializable;

import blue.util.Configuration;
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
	
	public static abstract class Box2 extends Box<Vector2> {
		private static final long 
			serialVersionUID = 1L;
		protected final Vector2.Mutable
			v0 = new Vector2.Mutable(),
			v1 = new Vector2.Mutable();
		
		public boolean contains(float x, float y, boolean include_edge) {
			return Box.contains(this, x, y, include_edge);
		}
		
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
		
		protected static final <B extends Box2> B parseBox2(B b, String s) {
			if(b != null && s != null) {
				int
		            i = s.indexOf("<"),
		            j = s.indexOf(">");
		        if (i >= 0 || j >= 0) {
		            if (i > j)
		                s = s.substring(++i, j);
		            else
		                s = s.substring(++i);
		        }
		        
		        String[] t = s.split("\\,");
		        switch (t.length) {
		            default:
		            case 4:
		            	b.v1.y = Configuration.stringToFloat(t[3]);
		            case 3:
		            	b.v1.x = Configuration.stringToFloat(t[2]);
		            case 2:
		                b.v0.y = Configuration.stringToFloat(t[1]);
		            case 1:
		                b.v0.x = Configuration.stringToFloat(t[0]);
		            case 0:
		        }
			}	        
	        return b;
		}
	}
	
	public static abstract class Box3 extends Box<Vector3> {
		private static final long 
			serialVersionUID = 1L;
		protected final Vector3.Mutable
			v0 = new Vector3.Mutable(),
			v1 = new Vector3.Mutable();
		
		public boolean contains(float x, float y, float z, boolean include_edge) {
			return Box.contains(this, x, y, z, include_edge);
		}
		
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
		
		protected static final <B extends Box3> B parseBox3(B b, String s) {
			if(b != null && s != null) {
				int
		            i = s.indexOf("<"),
		            j = s.indexOf(">");
		        if (i >= 0 || j >= 0) {
		            if (i > j)
		                s = s.substring(++i, j);
		            else
		                s = s.substring(++i);
		        }
			        
		        String[] t = s.split("\\,");
		        switch (t.length) {
		            default:
		            case 6:
		            	b.v1.z = Configuration.stringToFloat(t[5]);
		            case 5:
		            	b.v1.y = Configuration.stringToFloat(t[4]);
		            case 4:
		            	b.v1.x = Configuration.stringToFloat(t[3]);
		            case 3:
		            	b.v0.z = Configuration.stringToFloat(t[2]);
		            case 2:
		                b.v0.y = Configuration.stringToFloat(t[1]);
		            case 1:
		                b.v0.x = Configuration.stringToFloat(t[0]);
		            case 0:
		        }
			}	        
	        return b;
		}
	}		
	
	public static boolean contains(Box2 a, Vector b, boolean include_edge) {
		return contains(a, b.x(), b.y(), b.x(), b.y(), include_edge);			
	}
	
	public static boolean contains(Box2 a, Box<?> b, boolean include_edge) {
		return contains(a, b.x1(), b.y1(), b.x2(), b.y2(), include_edge);
	}
	
	public static boolean contacts(Box2 a, Box<?> b, boolean include_edge) {
		return contacts(a, b.x1(), b.y1(), b.x2(), b.y2(), include_edge);
	}
	
	public static boolean contains(Box2 a, float x, float y, boolean include_edge) {
		return contains(a, x, y, x, y, include_edge);
	}
	
	public static boolean contains(Box2 a, float x1, float y1, float x2, float y2, boolean include_edge) {
		if(include_edge)
			return
				a.x1() <= x1 && a.x2() >= x2 &&
				a.y1() <= y1 && a.y2() >= y2;
		else
			return
				a.x1() <  x1 && a.x2() >  x2 &&
				a.y1() <  y1 && a.y2() >  y2;
	}
	
	public static boolean contacts(Box2 a, float x1, float y1, float x2, float y2, boolean include_edge) {
		if(include_edge)
			return
				a.x1() <= x2 && a.x2() >= x1 &&
				a.y1() <= y2 && a.y2() >= y1;
		else
			return
				a.x1() <  x2 && a.x2() >  x1 &&
				a.y1() <  y2 && a.y2() >  y1;
	}
	
	public static boolean contains(Box3 a, Vector b, boolean include_edge) {
		return contains(a, b.x(), b.y(), b.z(), b.x(), b.y(), b.z(), include_edge);			
	}
	
	public static boolean contains(Box3 a, Box<?> b, boolean include_edge) {
		return contains(a, b.x1(), b.y1(), b.z1(), b.x2(), b.y2(), b.z2(), include_edge);
	}
	
	public static boolean contacts(Box3 a, Box<?> b, boolean include_edge) {
		return contacts(a, b.x1(), b.y1(), b.z1(), b.x2(), b.y2(), b.z2(), include_edge);
	}
	
	public static boolean contains(Box3 a, float x, float y, float z, boolean include_edge) {
		return contains(a, x, y, z, x, y, z, include_edge);
	}
	
	public static boolean contains(Box3 a, float x1, float y1, float z1, float x2, float y2, float z2, boolean include_edge) {
		if(include_edge)
			return
				a.x1() <= x1 && a.x2() >= x2 &&
				a.y1() <= y1 && a.y2() >= y2 &&
				a.z1() <= z1 && a.z2() >= z2;
		else
			return
				a.x1() <  x1 && a.x2() >  x2 &&
				a.y1() <  y1 && a.y2() >  y2 &&
				a.z1() <  z1 && a.z2() >  z2;
	}
	
	public static boolean contacts(Box3 a, float x1, float y1, float z1, float x2, float y2, float z2, boolean include_edge) {
		if(include_edge)
			return
				a.x1() <= x2 && a.x2() >= x1 &&
				a.y1() <= y2 && a.y2() >= y1 &&
				a.z1() <= z2 && a.z2() >= z1;
		else
			return
				a.x1() <  x2 && a.x2() >  x1 &&
				a.y1() <  y2 && a.y2() >  y1 &&
				a.z1() <  z2 && a.z2() >  z1;
	}
	
	/* IMMUTABLE ALIGN */
	public static Bounds2 align(Bounds2 a, Box<?> b) {
		float
			w2 = a.w() / 2,
			h2 = a.h() / 2;
		Vector
			c = b.mid();
		return new Bounds2(
			c.x() - w2,
			c.y() - h2,
			c.x() + w2,
			c.y() + h2
		);
	}
	
	public static Bounds2 align(Bounds2 a, Vector b) {
		float
			w2 = a.w() / 2,
			h2 = a.h() / 2;
		return new Bounds2(
			b.x() - w2,
			b.y() - h2,
			b.x() + w2,
			b.y() + h2
		);
	}
	
	public static Bounds2 align(Bounds2 a, float x, float y) {
		float
			w2 = a.w() / 2,
			h2 = a.h() / 2;
		return new Bounds2(
			x - w2,
			y - h2,
			x + w2,
			y + h2
		);
	}
	
	public static Region2 align(Region2 a, Box<?> b) {
		Vector 
			c = b.mid();
		return new Region2(
			c.x() - a.w() / 2,
			c.y() - a.h() / 2,
			a.w(),
			a.h()
		);
	}
	
	public static Region2 align(Region2 a, Vector b) {
		return new Region2(
			b.x() - a.w() / 2,
			b.y() - a.h() / 2,
			a.w(),
			a.h()
		);
	}
	
	public static Region2 align(Region2 a, float x, float y) {
		return new Region2(
			x - a.w() / 2,
			y - a.h() / 2,
			a.w(),
			a.h()
		);
	}
	
	public static Bounds3 align(Bounds3 a, Box<?> b) {
		float
			w2 = a.w() / 2,
			h2 = a.h() / 2,
			d2 = a.d() / 2;
		Vector
			c = b.mid();
		return new Bounds3(
			c.x() - w2,
			c.y() - h2,
			c.z() - d2,
			c.x() + w2,
			c.y() + h2,
			c.z() + d2
		);
	}
	
	public static Bounds3 align(Bounds3 a, Vector b) {
		float
			w2 = a.w() / 2,
			h2 = a.h() / 2,
			d2 = a.d() / 2;
		return new Bounds3(
			b.x() - w2,
			b.y() - h2,
			b.z() - d2,
			b.x() + w2,
			b.y() + h2,
			b.z() + d2
		);
	}
	
	public static Bounds3 align(Bounds3 a, float x, float y, float z) {
		float
			w2 = a.w() / 2,
			h2 = a.h() / 2,
			d2 = a.d() / 2;
		return new Bounds3(
			x - w2,
			y - h2,
			z - d2,
			x + w2,
			y + h2,
			z + d2
		);
	}
	
	public static Region3 align(Region3 a, Box<?> b) {
		Vector 
			c = b.mid();
		return new Region3(
			c.x() - a.w() / 2,
			c.y() - a.h() / 2,
			c.z() - a.d() / 2,
			a.w(),
			a.h(),
			a.d()
		);
	}	
	
	public static Region3 align(Region3 a, Vector b) {
		return new Region3(
			b.x() - a.w() / 2,
			b.y() - a.h() / 2,
			b.z() - a.d() / 2,
			a.w(),
			a.h(),
			a.d()
		);
	}	
	
	public static Region3 align(Region3 a, float x, float y, float z) {
		return new Region3(
			x - a.w() / 2,
			y - a.h() / 2,
			z - a.d() / 2,
			a.w(),
			a.h(),
			a.d()
		);
	}
	
	/* MUTABLE ALIGN */
	public static Bounds2.Mutable m_align(Bounds2.Mutable a, Box<?> b) {
		float
			w2 = a.w() / 2,
			h2 = a.h() / 2;
		Vector
			c = b.mid();
		return a.set(
			c.x() - w2,
			c.y() - h2,
			c.x() + w2,
			c.y() + h2
		);
	}
	
	public static Bounds2.Mutable m_align(Bounds2.Mutable a, Vector b) {
		float
			w2 = a.w() / 2,
			h2 = a.h() / 2;
		return a.set(
			b.x() - w2,
			b.y() - h2,
			b.x() + w2,
			b.y() + h2
		);
	}
	
	public static Bounds2.Mutable m_align(Bounds2.Mutable a, float x, float y) {
		float
			w2 = a.w() / 2,
			h2 = a.h() / 2;
		return a.set(
			x - w2,
			y - h2,
			x + w2,
			y + h2
		);
	}
	
	public static Region2.Mutable m_align(Region2.Mutable a, Box<?> b) {
		Vector 
			c = b.mid();
		return a.set(
			c.x() - a.w() / 2,
			c.y() - a.h() / 2,
			a.w(),
			a.h()
		);
	}	
	
	public static Region2.Mutable m_align(Region2.Mutable a, Vector b) {
		return a.set(
			b.x() - a.w() / 2,
			b.y() - a.h() / 2,
			a.w(),
			a.h()
		);
	}	
	
	public static Region2.Mutable m_align(Region2.Mutable a, float x, float y) {
		return a.set(
			x - a.w() / 2,
			y - a.h() / 2,
			a.w(),
			a.h()
		);
	}
	
	public static Bounds3.Mutable m_align(Bounds3.Mutable a, Box<?> b) {
		float
			w2 = a.w() / 2,
			h2 = a.h() / 2,
			d2 = a.d() / 2;
		Vector
			c = b.mid();
		return a.set(
			c.x() - w2,
			c.y() - h2,
			c.z() - d2,
			c.x() + w2,
			c.y() + h2,
			c.z() + d2
		);
	}
	
	public static Bounds3.Mutable m_align(Bounds3.Mutable a, Vector b) {
		float
			w2 = a.w() / 2,
			h2 = a.h() / 2,
			d2 = a.d() / 2;
		return a.set(
			b.x() - w2,
			b.y() - h2,
			b.z() - d2,
			b.x() + w2,
			b.y() + h2,
			b.z() + d2
		);
	}
	
	public static Bounds3.Mutable m_align(Bounds3.Mutable a, float x, float y, float z) {
		float
			w2 = a.w() / 2,
			h2 = a.h() / 2,
			d2 = a.d() / 2;
		return a.set(
			x - w2,
			y - h2,
			z - d2,
			x + w2,
			y + h2,
			z + d2
		);
	}
	
	public static Region3.Mutable m_align(Region3.Mutable a, Box<?> b) {
		Vector 
			c = b.mid();
		return a.set(
			c.x() - a.w() / 2,
			c.y() - a.h() / 2,
			c.z() - a.d() / 2,
			a.w(),
			a.h(),
			a.d()
		);
	}	
	
	public static Region3.Mutable m_align(Region3.Mutable a, Vector b) {
		return a.set(
			b.x() - a.w() / 2,
			b.y() - a.h() / 2,
			b.z() - a.d() / 2,
			a.w(),
			a.h(),
			a.d()
		);
	}	
	
	public static Region3.Mutable m_align(Region3.Mutable a, float x, float y, float z) {
		return a.set(
			x - a.w() / 2,
			y - a.h() / 2,
			z - a.d() / 2,
			a.w(),
			a.h(),
			a.d()
		);
	}
}
