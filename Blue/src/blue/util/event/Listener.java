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
		
		public void attach(Listener<T> listener) {
			attach.add(listener);
		}
		
		public void detach(Listener<T> listener) {
			detach.add(listener);
		}		
		
		public boolean onAttach(Listener<T> listener) {
			return listeners.add(listener);
		}
		
		public boolean onDetach(Listener<T> listener) {
			return listeners.remove(listener);
		}
		
		public void attachPending() {
			if(attach.size() > 0) {
				for(Listener<T> listener: attach)
					onAttach(listener);
				attach.clear();
			}
		}
		
		public void detachPending() {
			if(detach.size() > 0) {
				for(Listener<T> listener: detach)
					onDetach(listener);
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