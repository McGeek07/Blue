package blue.util;

import java.io.Serializable;

public class Tuple<T0> implements Serializable {
	private static final long 
		serialVersionUID = 1L;
	protected T0
		t0;
	
	public Tuple(T0 t0) {
		this.t0 = t0;
	}
	
	public Tuple(Tuple<T0> t) {
		this.t0 = t.t0();
	}
	
	public T0 t0() {
		return t0;
	}
	
	public static class Mutable<T0> extends Tuple<T0> {
		private static final long 
			serialVersionUID = 1L;
		
		public Mutable(T0 t0) {
			super(t0);
		}
		
		public Mutable(Tuple<T0> t) {
			super(t);
		}
		
		public Tuple.Mutable<T0> t0(T0 t0) {
			this.t0 = t0;
			return this;
		}	
	}
}
