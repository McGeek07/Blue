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
	
	public <T> boolean add(Class<T> type, Listener<T> listener) {
		return getListeners(type).add(listener);
	}
	
	public <T> boolean del(Class<T> type, Listener<T> listener) {
		return getListeners(type).del(listener);
	}
	
	public <T> void attach(Class<T> type, Listener<T> listener) {
		getListeners(type).attach(listener);
	}
	
	public <T> void detach(Class<T> type, Listener<T> listener) {
		getListeners(type).detach(listener);
	}
	
	public void attach() {
		this.listeners.forEach((type, listeners) -> {
			listeners.attach();
		});
	}
	
	public void detach() {
		this.listeners.forEach((type, listeners) -> {
			listeners.detach();
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
		
		public boolean add(Handle handle) {
			return handles.add(handle);
		}
		
		public boolean del(Handle handle) {
			return handles.remove(handle);
		}
		
		public void attach(Handle handle) {
			attach.add(handle);
		}
		
		public void detach(Handle handle) {
			detach.add(handle);
		}
		
		public void attach() {
			if(attach.size() > 0) {
				for(Handle handle: attach)
					add(handle);
				attach.clear();
			}
			if(handles.size() > 0) {
				for(Handle handle: handles)
					handle.attach();
			}
		}
		
		public void detach() {
			if(handles.size() > 0) {
				for(Handle handle: handles)
					handle.detach();
			}
			if(detach.size() > 0) {
				for(Handle handle: detach)
					del(handle);
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