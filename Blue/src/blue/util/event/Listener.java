package blue.util.event;

import java.util.HashSet;
import java.util.Iterator;

public interface Listener<T> {
	public void handle(T event);
	
	public static class Group<T> implements Iterable<Listener<T>> {
		protected final HashSet<Listener<T>>
			listeners = new HashSet<>(),
			attach = new HashSet<>(),
			detach = new HashSet<>();
		
		public boolean add(Listener<T> listener) {
			return listeners.add(listener);
		}
		
		public boolean del(Listener<T> listener) {
			return listeners.remove(listener);
		}
		
		public void attach(Listener<T> listener) {
			attach.add(listener);
		}
		
		public void detach(Listener<T> listener) {
			detach.add(listener);
		}
		
		public void attach() {
			if(attach.size() > 0) {
				for(Listener<T> listener: attach)
					add(listener);
				attach.clear();
			}
		}
		
		public void detach() {
			if(detach.size() > 0) {
				for(Listener<T> listener: detach)
					del(listener);
				detach.clear();
			}
		}
		
		public void flush(T event) {
			for(Listener<T> listener : listeners)
				listener.handle(event);		
		}

		@Override
		public Iterator<Listener<T>> iterator() {
			return listeners.iterator();
		}
	}
}