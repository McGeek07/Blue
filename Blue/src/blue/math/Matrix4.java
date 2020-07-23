package blue.math;

import java.util.HashMap;

import blue.util.Configuration;

public class Matrix4 extends Matrix {
	private static final long 
		serialVersionUID = 1L;
	protected float
		xx, xy, xz, xw,
		yx, yy, yz, yw,
		zx, zy, zz, zw,
		wx, wy, wz, ww;
	
	public Matrix4() {
		//do nothing
	}
	
	public Matrix4(Matrix m) {		
		mSet(m);
	}
	
	public Matrix4(
		int mode,
		Vector v0,
		Vector v1,
		Vector v2,
		Vector v3
	) {
		switch(mode) {
			case ROW_MAJOR: mSetRowMajor(v0, v1, v2, v3); break;
			case COL_MAJOR: mSetColMajor(v0, v1, v2, v3); break;
		}
	}
	
	public Matrix4(
		int mode,
		float a, float b, float c, float d, 
		float e, float f, float g, float h, 
		float i, float j, float k, float l,
		float m, float n, float o, float p
	) {
		switch(mode) {
			case ROW_MAJOR: mSetRowMajor(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p); break;
			case COL_MAJOR: mSetColMajor(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p); break;
		}
	}
	
	protected void mSet(Matrix m) {
		xx = m.xx(); xy = m.xy(); xz = m.xz(); xw = m.xw();
		yx = m.yx(); yy = m.yy(); yz = m.yz(); yw = m.yx();
		zx = m.zx(); zy = m.zy(); zz = m.zz(); zw = m.zw();
		wx = m.wx(); wy = m.wy(); wz = m.wz(); ww = m.ww();
	}
	
	protected void mSetRowMajor(
		Vector r0,
		Vector r1,
		Vector r2,
		Vector r3
	) {
		xx = r0.x(); xy = r0.y(); xz = r0.z(); xw = r0.w();
		yx = r1.x(); yy = r1.y(); yz = r1.z(); yw = r1.w();
		zx = r2.x(); zy = r2.y(); zz = r2.z(); zw = r2.w();
		wx = r3.x(); wy = r3.y(); wz = r3.z(); ww = r3.w();
	}
	
	protected void mSetColMajor(
		Vector c0,
		Vector c1,
		Vector c2,
		Vector c3
	) {
		xx = c0.x(); xy = c1.x(); xz = c2.x(); xw = c3.x();
		yx = c0.y(); yy = c1.y(); yz = c2.y(); yw = c3.y();
		zx = c0.z(); zy = c1.z(); zz = c2.z(); zw = c3.z();
		wx = c0.w(); wy = c1.w(); wz = c2.w(); ww = c3.w();
	}
	
	protected void mSetRowMajor(
		float a, float b, float c, float d,
		float e, float f, float g, float h, 
		float i, float j, float k, float l,
		float m, float n, float o, float p
	) {
		xx = a; xy = b; xz = c; xw = d;
		yx = e; yy = f; yz = g; yw = h;
		zx = i; zy = j; zz = k; zw = l;
		wx = m; wy = n; wz = o; ww = p;
	}
	
	protected void mSetColMajor(
		float a, float b, float c, float d,
		float e, float f, float g, float h, 
		float i, float j, float k, float l,
		float m, float n, float o, float p
	) {
		xx = a; xy = e; xz = i; xw = m;
		yx = b; yy = f; yz = j; yw = n;
		zx = c; zy = g; zz = k; zw = o;
		wx = d; wy = h; wz = l; ww = p;
	}
	
	@Override
	public float xx() { return xx; }
	@Override
	public float xy() { return xy; }
	@Override
	public float xz() { return xz; }
	@Override
	public float xw() { return xw; }
	@Override
	public float yx() { return yx; }
	@Override
	public float yy() { return yy; }
	@Override
	public float yz() { return yz; }
	@Override
	public float yw() { return yw; }
	@Override
	public float zx() { return zx; }
	@Override
	public float zy() { return zy; }
	@Override
	public float zz() { return zz; }
	@Override
	public float zw() { return zw; }
	@Override
	public float wx() { return wx; }
	@Override
	public float wy() { return wy; }
	@Override
	public float wz() { return wz; }
	@Override
	public float ww() { return ww; }
	
	@Override
	public Vector4 row(int i) {
		switch(i) {
			case 0: return new Vector4(xx, xy, xz, xw);
			case 1: return new Vector4(yx, yy, yz, yw);
			case 2: return new Vector4(zx, zy, zz, zw);
			case 3: return new Vector4(wx, wy, wz, ww);
		}
		return null;
	}
	
	@Override
	public Vector4 col(int j) {
		switch(j) {
			case 0: return new Vector4(xx, yx, zx, wx);
			case 1: return new Vector4(xy, yy, zy, wy);
			case 2: return new Vector4(xz, yz, zz, wz);
			case 3: return new Vector4(xw, yw, zw, ww);
		}
		return null;
	}
	
	@Override
	public int m() {
		return 4;
	}
	
	@Override
	public int n() {
		return 4;
	}
	
	@Override
	public Matrix4 copy() {
		return new Matrix4(this);
	}
	
	@Override
	public String toString() {
		return Matrix4.toString(this, "%s");
	}
	
	public static String toString(Matrix4 m, String f) {
		return
			"[" + String.format(f, m.xx) + ", " + String.format(f, m.xy) + ", " + String.format(f, m.xz) + ", " + String.format(f, m.xw) + "]\n" +
			"[" + String.format(f, m.yx) + ", " + String.format(f, m.yy) + ", " + String.format(f, m.yz) + ", " + String.format(f, m.yw) + "]\n" +
			"[" + String.format(f, m.zx) + ", " + String.format(f, m.zy) + ", " + String.format(f, m.zz) + ", " + String.format(f, m.zw) + "]\n" +
			"[" + String.format(f, m.wx) + ", " + String.format(f, m.wy) + ", " + String.format(f, m.wz) + ", " + String.format(f, m.ww) + "]";
	}	
	
	protected static final <M extends Matrix4> M parseMatrix4(M m, String s) {
		if(m != null && s != null) {
			s = s.replace("[", "");
		    s = s.replace("]", "");
		    
		    HashMap<String, String> map = new HashMap<>();	        
	        for(String t : s.split("\n"))	        
		        Configuration.parse(
	        		map, t,
	        		"xx", "xy", "xz", "xw",
	        		"yx", "yy", "yz", "yw",
	        		"zx", "zy", "zz", "zw",
	        		"wx", "wy", "wz", "ww"
	    		);
	        m.xx = Configuration.getPropertyAsFloat(map, "xx");
	        m.xy = Configuration.getPropertyAsFloat(map, "xy");
	        m.xz = Configuration.getPropertyAsFloat(map, "xz");
	        m.xw = Configuration.getPropertyAsFloat(map, "xw");
	        m.yx = Configuration.getPropertyAsFloat(map, "yx");
	        m.yy = Configuration.getPropertyAsFloat(map, "yy");
	        m.yz = Configuration.getPropertyAsFloat(map, "yz");
	        m.yw = Configuration.getPropertyAsFloat(map, "yw");
	        m.zx = Configuration.getPropertyAsFloat(map, "zx");
	        m.zy = Configuration.getPropertyAsFloat(map, "zy");
	        m.zz = Configuration.getPropertyAsFloat(map, "zz");
	        m.zw = Configuration.getPropertyAsFloat(map, "zw");
	        m.wx = Configuration.getPropertyAsFloat(map, "wx");
	        m.wy = Configuration.getPropertyAsFloat(map, "wy");
	        m.wz = Configuration.getPropertyAsFloat(map, "wz");
	        m.ww = Configuration.getPropertyAsFloat(map, "ww");
		}	    
	    return m;
	}
	
	public static Matrix4 parseMatrix4(String str) {
		return Matrix4.parseMatrix4(new Matrix4(), str);
	}
	
	public static Matrix4 identity() {
		return new Matrix4(
			Matrix.ROW_MAJOR, 
			1, 0, 0, 0,
			0, 1, 0, 0, 
			0, 0, 1, 0, 
			0, 0, 0, 1
		);
	}
	
	public static class Mutable extends Matrix4 {
		private static final long 
			serialVersionUID = 1L;
		
		public Mutable() {
			super();
		}
		
		public Mutable(Matrix m) {
			super(m);
		}
		
		public Mutable(
			int mode,
			Vector v0,
			Vector v1,
			Vector v2,
			Vector v3
		) {
			super(mode, v0, v1, v2, v3);
		}
		
		public Mutable(
			int mode,
			float a, float b, float c, float d,
			float e, float f, float g, float h, 
			float i, float j, float k, float l, 
			float m, float n, float o, float p
		) {
			super(mode, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p);
		}
		
		public Matrix4.Mutable xx(float xx) {
			this.xx = xx;
			return this;
		}
		
		public Matrix4.Mutable xy(float xy) {
			this.xy = xy;
			return this;
		}
		
		public Matrix4.Mutable xz(float xz) {
			this.xz = xz;
			return this;
		}
		
		public Matrix4.Mutable xw(float xw) {
			this.xw = xw;
			return this;
		}
		
		public Matrix4.Mutable yx(float yx) {
			this.yx = yx;
			return this;
		}
		
		public Matrix4.Mutable yy(float yy) {
			this.yy = yy;
			return this;
		}
		
		public Matrix4.Mutable yz(float yz) {
			this.yz = yz;
			return this;
		}
		
		public Matrix4.Mutable yw(float yw) {
			this.yw = yw;
			return this;
		}
		
		public Matrix4.Mutable zx(float zx) {
			this.zx = zx;
			return this;
		}
		
		public Matrix4.Mutable zy(float zy) {
			this.zy = zy;
			return this;
		}
		
		public Matrix4.Mutable zz(float zz) {
			this.zz = zz;
			return this;
		}
		
		public Matrix4.Mutable zw(float zw) {
			this.zw = zw;
			return this;
		}
		
		public Matrix4.Mutable wx(float wx) {
			this.wx = wx;
			return this;
		}
		
		public Matrix4.Mutable wy(float wy) {
			this.wy = wy;
			return this;
		}
		
		public Matrix4.Mutable wz(float wz) {
			this.wz = wz;
			return this;
		}
		
		public Matrix4.Mutable ww(float ww) {
			this.ww = ww;
			return this;
		}
		
		public Matrix4.Mutable row(int i, float x, float y, float z, float w) {
			switch(i) {
				case 0: xx = x; xy = y; xz = z; xw = w; break;
				case 1: yx = x; yy = y; yz = z; yw = w; break;
				case 2: zx = x; zy = y; zz = z; zw = w; break;
				case 3: wx = x; wy = y; wz = z; ww = w; break;
			}
			return this;
		}
		
		public Matrix4.Mutable row(int i, Vector r0) {
			switch(i) {
				case 0: xx = r0.x(); xy = r0.y(); xz = r0.z(); xw = r0.w(); break;
				case 1: yx = r0.x(); yy = r0.y(); yz = r0.z(); yw = r0.w(); break;
				case 2: zx = r0.x(); zy = r0.y(); zz = r0.z(); zw = r0.w(); break;
				case 3: wx = r0.x(); wy = r0.y(); wz = r0.z(); ww = r0.w(); break;
			}
			return this;
		}
		
		public Matrix4.Mutable col(int j, float x, float y, float z, float w) {
			switch(j) {
				case 0: xx = x; yx = y; zx = z; wx = w; break;
				case 1: xy = x; yy = y; zy = z; wy = w; break;
				case 2: xz = x; yz = y; zz = z; wz = w; break;
				case 3: xw = x; yw = y; zw = z; ww = w; break;
			}
			return this;
		}
		
		public Matrix4.Mutable col(int j, Vector c0) {
			switch(j) {
				case 0: xx = c0.x(); yx = c0.y(); zx = c0.z(); wx = c0.w(); break;
				case 1: xy = c0.x(); yy = c0.y(); zy = c0.z(); wy = c0.w(); break;
				case 2: xz = c0.x(); yz = c0.y(); zz = c0.z(); wz = c0.w(); break;
				case 3: xw = c0.x(); yw = c0.y(); zw = c0.z(); ww = c0.w(); break;
			}
			return this;
		}
		
		public Matrix4.Mutable set(Matrix m) {
			mSet(m);
			return this;
		}
		
		public Matrix4.Mutable setRowMajor(
			Vector r0,
			Vector r1,
			Vector r2,
			Vector r3
		) {
			mSetRowMajor(r0, r1, r2, r3);
			return this;
		}
		
		public Matrix4.Mutable setRowMajor(
			float a, float b, float c, float d,
			float e, float f, float g, float h, 
			float i, float j, float k, float l, 
			float m, float n, float o, float p
		) {
			mSetRowMajor(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p);
			return this;
		}
		
		public Matrix4.Mutable setColMajor(
			Vector c0,
			Vector c1,
			Vector c2,
			Vector c3
		) {
			mSetColMajor(c0, c1, c2, c3);
			return this;
		}
		
		public Matrix4.Mutable setColMajor(
			float a, float b, float c, float d,
			float e, float f, float g, float h, 
			float i, float j, float k, float l, 
			float m, float n, float o, float p
		) {
			mSetColMajor(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p);
			return this;
		}
		
		public static Matrix4.Mutable parseMatrix4(String str) {
			return Matrix4.parseMatrix4(new Matrix4.Mutable(), str);
		}
		
		public static Matrix4.Mutable identity() {
			return new Matrix4.Mutable(
				Matrix.ROW_MAJOR, 
				1, 0, 0, 0, 
				0, 1, 0, 0, 
				0, 0, 1, 0, 
				0, 0, 0, 1
			);
		}
	}
}
