package blue.util;

import java.io.Serializable;

public class Tuple4<T0, T1, T2, T3> implements Serializable {
	private static final long 
		serialVersionUID = 1L;
	protected T0
		t0;
	protected T1
		t1;
	protected T2
		t2;
	protected T3
		t3;
	
	public Tuple4(T0 t0, T1 t1, T2 t2, T3 t3) {
		this.t0 = t0;
		this.t1 = t1;
		this.t2 = t2;
		this.t3 = t3;
	}
	
	public Tuple4(Tuple4<T0, T1, T2, T3> t) {
		this.t0 = t.t0();
		this.t1 = t.t1();
		this.t2 = t.t2();
		this.t3 = t.t3();
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
	
	public T3 t3() {
		return t3;
	}
	
	public static class Mutable<T0, T1, T2, T3> extends Tuple4<T0, T1, T2, T3> {
		private static final long 
			serialVersionUID = 1L;
		
		public Mutable(T0 t0, T1 t1, T2 t2, T3 t3) {
			super(t0, t1, t2, t3);
		}
		
		public Mutable(Tuple4<T0, T1, T2, T3> t) {
			super(t);
		}		
		
		public Tuple4.Mutable<T0, T1, T2, T3> t0(T0 t0) {
			this.t0 = t0;
			return this;
		}	
		
		public Tuple4.Mutable<T0, T1, T2, T3> t1(T1 t1) {
			this.t1 = t1;
			return this;
		}	
		
		public Tuple4.Mutable<T0, T1, T2, T3> t2(T2 t2) {
			this.t2 = t2;
			return this;
		}
		
		public Tuple4.Mutable<T0, T1, T2, T3> t3(T3 t3) {
			this.t3 = t3;
			return this;
		}
	}
}