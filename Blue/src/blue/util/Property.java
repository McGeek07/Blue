package blue.util;

import java.io.Serializable;

public interface Property<T> extends Serializable {
	public T get();	
	
	public static interface Mutable<T> extends Property<T> {
		public Property.Mutable<T> set(T t);
	}
	
	public static class IntegerProperty implements Property<Integer> {
		private static final long 
			serialVersionUID = 1L;
		protected int
			t;
		
		public IntegerProperty() {
			//do nothing
		}
		
		public IntegerProperty(Integer t) {
			this.t = t;
		}
		
		public IntegerProperty(Property<Integer> p) {
			this.t = p.get();
		}
		
		
		@Override
		public Integer get() {
			return t;
		}
		
		public static class Mutable extends IntegerProperty implements Property.Mutable<Integer> {
			private static final long 
				serialVersionUID = 1L;

			public Mutable() {
				super();
			}
			
			public Mutable(int t) {
				super(t);
			}
			
			public Mutable(Property<Integer> p) {
				super(p);
			}

			@Override
			public IntegerProperty.Mutable set(Integer t) {
				this.t = t;
				return this;
			}			
		}		
	}	
	
	public static class FloatProperty implements Property<Float> {
		private static final long 
			serialVersionUID = 1L;
		protected float
			t;
		
		public FloatProperty() {
			//do nothing
		}
		
		public FloatProperty(Float t) {
			this.t = t;
		}
		
		public FloatProperty(Property<Float> p) {
			this.t = p.get();
		}

		@Override
		public Float get() {
			return t;
		}
		
		public static class Mutable extends FloatProperty implements Property.Mutable<Float> {
			private static final long 
				serialVersionUID = 1L;

			public Mutable() {
				super();
			}
			
			public Mutable(float t) {
				super(t);
			}
			
			public Mutable(Property<Float> p) {
				super(p);
			}

			@Override
			public FloatProperty.Mutable set(Float t) {
				this.t = t;
				return this;
			}			
		}
	}
}
