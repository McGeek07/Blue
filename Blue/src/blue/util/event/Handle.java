package blue.util.event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import blue.util.Util;

public class Handle {
	protected final HashMap<Class<?>, Listener.Group<?>>
		listeners = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public <T> Listener.Group<T> getListeners(Class<T> type) {
		Listener.Group<T> listeners = (Listener.Group<T>)this.listeners.get(type);
		if(listeners == null) {
			listeners = new Listener.Group<T>();
			this.listeners.put(type, listeners);
		}
		return listeners;
	}
	
	public <T> void attach(Class<T> type, Listener<T> listener) {
		getListeners(type).attach(listener);
	}
	
	public <T> void detach(Class<T> type, Listener<T> listener) {
		getListeners(type).detach(listener);
	}
	
	public <T> boolean onAttach(Class<T> type, Listener<T> listener) {
		return getListeners(type).onAttach(listener);
	}
	
	public <T> boolean onDetach(Class<T> type, Listener<T> listener) {
		return getListeners(type).onDetach(listener);
	}
	
	public void attachPending() {
		this.listeners.forEach((type, listeners) -> {
			listeners.attachPending();
		});
	}
	
	public void detachPending() {
		this.listeners.forEach((type, listeners) -> {
			listeners.detachPending();
		});
	}
	
	public <T> void flush(T event) {
		getListeners(Util.typeOf(event)).flush(event);
	}
	
	public static class Group implements Iterable<Handle> {
		protected final HashSet<Handle>
			handles = new HashSet<>(),
			attach = new HashSet<>(),
			detach = new HashSet<>();
		
		public void attach(Handle handle) {
			attach.add(handle);
		}
		
		public void detach(Handle handle) {
			detach.add(handle);
		}
		
		public boolean onAttach(Handle handle) {
			return handles.add(handle);
		}
		
		public boolean onDetach(Handle handle) {
			return handles.remove(handle);
		}
		
		public void attachPending() {
			if(attach.size() > 0) {
				for(Handle handle: attach)
					onAttach(handle);
				attach.clear();
			}
			if(handles.size() > 0) {
				for(Handle handle: handles)
					handle.attachPending();
			}
		}
		
		public void detachPending() {
			if(handles.size() > 0) {
				for(Handle handle: handles)
					handle.detachPending();
			}
			if(detach.size() > 0) {
				for(Handle handle: detach)
					onDetach(handle);
				detach.clear();
			}
		}
		
		public <T> void flush(T event) {
			for(Handle handle: handles)
				handle.flush(event);
		}

		@Override
		public Iterator<Handle> iterator() {
			return handles.iterator();
		}
	}
}