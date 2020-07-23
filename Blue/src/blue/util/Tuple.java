package blue.util;

import java.io.Serializable;

public class Tuple implements Serializable {
	private static final long 
		serialVersionUID = 1L;	
	protected Object[]
		t;
	
	public Tuple(Object... t) {
		this.t = t;
	}
	
	public Tuple(Tuple2<?, ?> t) {
		this(t.t0, t.t1);
	}
	
	public Tuple(Tuple3<?, ?, ?> t) {
		this(t.t0, t.t1, t.t2);
	}
	
	public Tuple(Tuple4<?, ?, ?, ?> t) {
		this(t.t0, t.t1, t.t2, t.t3);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T t(int i) {		
		return i < n() ? (T)(this.t[i]) : null;
	}
	
	public int n() {
		return t.length;
	}
	
	public static Tuple of(Object... t) {
		return new Tuple(t);
	}
	
	public static <T0, T1> Tuple2<T0, T1> of(T0 t0, T1 t1) {
		return new Tuple2<>(t0, t1);
	}
	
	public static <T0, T1, T2> Tuple3<T0, T1, T2> of(T0 t0, T1 t1, T2 t2) {
		return new Tuple3<>(t0, t1, t2);
	}
	
	public static <T0, T1, T2, T3> Tuple4<T0, T1, T2, T3> of(T0 t0, T1 t1, T2 t2, T3 t3) {
		return new Tuple4<>(t0, t1, t2, t3);
	}
	
	public static class Mutable extends Tuple {
		private static final long 
			serialVersionUID = 1L;
		
		public Mutable(Object... t) {
			super(t);
		}
		
		public Mutable(Tuple2<?, ?> t) {
			super(t);
		}
		
		public Mutable(Tuple3<?, ?, ?> t) {
			super(t);
		}
		
		public Mutable(Tuple4<?, ?, ?, ?> t) {
			super(t);
		}
		
		@SuppressWarnings("unchecked")
		public <T> T t(int i, T t) {
			return i < n() ? (T)(this.t[i] = t) : null;
		}
	}
}
