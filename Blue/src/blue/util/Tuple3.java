package blue.util;

import java.io.Serializable;

public class Tuple3<T0, T1, T2> implements Serializable {
	private static final long 
		serialVersionUID = 1L;
	protected T0
		t0;
	protected T1
		t1;
	protected T2
		t2;
	
	public Tuple3(T0 t0, T1 t1, T2 t2) {
		this.t0 = t0;
		this.t1 = t1;
		this.t2 = t2;
	}
	
	public Tuple3(Tuple3<T0, T1, T2> t) {
		this.t0 = t.t0();
		this.t1 = t.t1();
		this.t2 = t.t2();
	}
	
	public T0 t0() {
		return t0;
	}
	
	public T1 t1() {
		return t1;
	}
	
	public T2 t2() {
		return t2;
	}
	
	public int n() {
		return 3;
	}
	
	public static class Mutable<T0, T1, T2> extends Tuple3<T0, T1, T2> {
		private static final long 
			serialVersionUID = 1L;
		
		public Mutable(T0 t0, T1 t1, T2 t2) {
			super(t0, t1, t2);
		}
		
		public Mutable(Tuple3<T0, T1, T2> t) {
			super(t);
		}		
		
		public Tuple3.Mutable<T0, T1, T2> t0(T0 t0) {
			this.t0 = t0;
			return this;
		}	
		
		public Tuple3.Mutable<T0, T1, T2> t1(T1 t1) {
			this.t1 = t1;
			return this;
		}	
		
		public Tuple3.Mutable<T0, T1, T2> t2(T2 t2) {
			this.t2 = t2;
			return this;
		}
	}
}