package blue.util;

import java.io.Serializable;

public class Tuple2<T0, T1> implements Serializable {
	private static final long 
		serialVersionUID = 1L;
	protected T0
		t0;
	protected T1
		t1;
	
	public Tuple2(T0 t0, T1 t1) {
		this.t0 = t0;
		this.t1 = t1;
	}
	
	public Tuple2(Tuple2<T0, T1> t) {
		this.t0 = t.t0();
		this.t1 = t.t1();
	}
	
	public T0 t0() {
		return t0;
	}
	
	public T1 t1() {
		return t1;
	}
	
	public int n() {
		return 2;
	}
	
	public static class Mutable<T0, T1> extends Tuple2<T0, T1> {
		private static final long 
			serialVersionUID = 1L;
		
		public Mutable(T0 t0, T1 t1) {
			super(t0, t1);
		}
		
		public Mutable(Tuple2<T0, T1> t) {
			super(t);
		}		
		
		public Tuple2.Mutable<T0, T1> t0(T0 t0) {
			this.t0 = t0;
			return this;
		}	
		
		public Tuple2.Mutable<T0, T1> t1(T1 t1) {
			this.t1 = t1;
			return this;
		}	
	}
}