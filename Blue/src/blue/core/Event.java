package blue.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import blue.util.Util;

public class Event {
	protected static final Event
		INSTANCE = new Event();
	
	protected final Handle
		handle;
	protected final Broker
		broker;
	
	private Event() {
		this.handle = new Handle();
		this.broker = new Broker();
		this.broker.add(this.handle);
	}
	
	public static <T> void attach(Class<T> type, Listener<T> listener) {
		INSTANCE.handle.attach(type, listener);
	}
	
	public static <T> void detach(Class<T> type, Listener<T> listener) {
		INSTANCE.handle.detach(type, listener);
	}
	
	public static void attach(Handle handle) {
		INSTANCE.broker.attach(handle);
	}
	
	public static void detach(Handle handle) {
		INSTANCE.broker.detach(handle);
	}
	
	public static void attach(Broker broker) {
		INSTANCE.broker.attach(broker);
	}
	
	public static void detach(Broker broker) {
		INSTANCE.broker.detach(broker);
	}
	
	public static <T> void queue(T event) {
		INSTANCE.broker.queue(event);
	}
	
	public static <T> void flush(T event) {
		INSTANCE.broker.flush(event);
	}
	
	protected static void poll() {
		INSTANCE.broker.poll();
	}	
	
	public static interface Listener<T> {
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
				for(Listener<T> listener: attach)
					add(listener);
				attach.clear();
			}
			
			public void detach() {
				for(Listener<T> listener: detach)
					del(listener);
				detach.clear();
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
	
	public static class Handle {
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
				for(Handle handle: attach)
					add(handle);
				for(Handle handle: handles)
					handle.attach();
				attach.clear();
			}
			
			public void detach() {
				for(Handle handle: handles)
					handle.detach();
				for(Handle handle: detach)
					del(handle);
				detach.clear();
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
	
	public static class Broker {
		protected final Handle.Group
			handles = new Handle.Group();
		protected final Broker.Group
			brokers = new Broker.Group();
		
		protected LinkedList<Object>
			events1 = new LinkedList<>(),
			events2 = new LinkedList<>();
		
		public boolean add(Handle handle) {
			return handles.add(handle);
		}
		
		public boolean del(Handle handle) {
			return handles.del(handle);
		}
		
		public void attach(Handle handle) {
			handles.attach(handle);
		}
		
		public void detach(Handle handle) {
			handles.detach(handle);
		}
		
		public boolean add(Broker broker) {
			return brokers.add(broker);
		}
		
		public boolean del(Broker broker) {
			return brokers.del(broker);
		}
		
		public void attach(Broker broker) {
			brokers.attach(broker);
		}
		
		public void detach(Broker broker) {
			brokers.detach(broker);
		}
		
		public void attach() {
			handles.attach();
			brokers.attach();
		}
		
		public void detach() {
			handles.detach();
			brokers.detach();
		}				
		
		public <T> void queue(T event) {
			events1.add(event);
		}
		
		public <T> void flush(T event) {
			handles.flush(event);
			brokers.flush(event);
		}	
		
		public void flush() {
			if(events1.size() > 0) {
				LinkedList<Object> events3 = events1;
				events1 = events2;
				events2 = events3;
				
				for(Object event: events2)
					flush(event);
				events2.clear();
			}
			for(Broker broker: brokers)
				broker.flush();
		}
		
		public void poll() {
			attach();
			detach();
			flush();
		}
		
		public static class Group implements Iterable<Broker> {
			protected final HashSet<Broker>
				brokers = new HashSet<>(),
				attach = new HashSet<>(),
				detach = new HashSet<>();
			
			public boolean add(Broker broker) {
				return brokers.add(broker);
			}
			
			public boolean del(Broker broker) {
				return brokers.remove(broker);
			}
			
			public void attach(Broker broker) {
				attach.add(broker);
			}
			
			public void detach(Broker broker) {
				detach.add(broker);
			}
			
			public void attach() {
				for(Broker broker: attach)
					add(broker);
				for(Broker broker: brokers)
					broker.attach();
				attach.clear();
			}
			
			public void detach() {
				for(Broker broker: brokers)
					broker.detach();
				for(Broker broker: detach)
					del(broker);
				detach.clear();
			}
			
			public <T> void flush(T event) {
				for(Broker broker: brokers)
					broker.flush(event);
			}

			@Override
			public Iterator<Broker> iterator() {
				return brokers.iterator();
			}
		}
	}
	
	public static class MonoHandle<T> extends Listener.Group<T> {
		
		public static class Group<T> implements Iterable<MonoHandle<T>> {
			protected final HashSet<MonoHandle<T>>
				handles = new HashSet<>(),
				attach = new HashSet<>(),
				detach = new HashSet<>();
			
			public boolean add(MonoHandle<T> handle) {
				return handles.add(handle);
			}
			
			public boolean del(MonoHandle<T> handle) {
				return handles.remove(handle);
			}
			
			public void attach(MonoHandle<T> handle) {
				attach.add(handle);
			}
			
			public void detach(MonoHandle<T> handle) {
				detach.add(handle);
			}
			
			public void attach() {
				for(MonoHandle<T> handle: attach)
					add(handle);
				for(MonoHandle<T> handle: handles)
					handle.attach();
				attach.clear();
			}
			
			public void detach() {
				for(MonoHandle<T> handle: handles)
					handle.detach();
				for(MonoHandle<T> handle: detach)
					del(handle);
				detach.clear();
			}
			
			public void flush(T event) {
				for(MonoHandle<T> handle: handles)
					handle.flush(event);
			}

			@Override
			public Iterator<MonoHandle<T>> iterator() {
				return handles.iterator();
			}
		}
	}
	
	public static class MonoBroker<T> {
		protected final MonoHandle.Group<T>
			handles = new MonoHandle.Group<>();
		protected final MonoBroker.Group<T>
			brokers = new MonoBroker.Group<>();
		
		protected LinkedList<T>
			events1 = new LinkedList<>(),
			events2 = new LinkedList<>();
		
		public boolean add(MonoHandle<T> handle) {
			return handles.add(handle);
		}
		
		public boolean del(MonoHandle<T> handle) {
			return handles.del(handle);
		}
		
		public void attach(MonoHandle<T> handle) {
			handles.attach(handle);
		}
		
		public void detach(MonoHandle<T> handle) {
			handles.detach(handle);
		}
		
		public boolean add(MonoBroker<T> queue) {
			return brokers.add(queue);
		}
		
		public boolean del(MonoBroker<T> queue) {
			return brokers.del(queue);
		}
		
		public void attach(MonoBroker<T> queue) {
			brokers.attach(queue);
		}
		
		public void detach(MonoBroker<T> queue) {
			brokers.detach(queue);
		}
		
		public void attach() {
			handles.attach();
			brokers.attach();
		}
		
		public void detach() {
			handles.detach();
			brokers.detach();
		}	
		
		public void queue(T event) {
			events1.add(event);
		}
		
		public void flush(T event) {
			handles.flush(event);
			brokers.flush(event);
		}
		
		public void flush() {
			if(events1.size() > 0) {
				LinkedList<T> events3 = events1;
				events1 = events2;
				events2 = events3;
				
				for(T event: events2)
					flush(event);			
				events2.clear();
			}
			for(MonoBroker<T> broker: brokers)
				broker.flush();
		}
		
		public void poll() {
			attach();
			detach();
			flush();
		}
		
		public static class Group<T> implements Iterable<MonoBroker<T>> {
			protected final HashSet<MonoBroker<T>>
				brokers = new HashSet<>(),
				attach = new HashSet<>(),
				detach = new HashSet<>();
			
			public boolean add(MonoBroker<T> broker) {
				return brokers.add(broker);
			}
			
			public boolean del(MonoBroker<T> broker) {
				return brokers.remove(broker);
			}
			
			public void attach(MonoBroker<T> broker) {
				attach.add(broker);
			}
			
			public void detach(MonoBroker<T> broker) {
				detach.add(broker);
			}
			
			public void attach() {
				for(MonoBroker<T> broker: attach)
					add(broker);
				for(MonoBroker<T> broker: brokers)
					broker.attach();
				attach.clear();
			}
			
			public void detach() {
				for(MonoBroker<T> broker: brokers)
					broker.detach();
				for(MonoBroker<T> broker: detach)
					del(broker);
				detach.clear();
			}
			
			public void flush(T event) {
				for(MonoBroker<T> broker: brokers)
					broker.flush(event);
			}

			@Override
			public Iterator<MonoBroker<T>> iterator() {
				return brokers.iterator();
			}
		}
	}
}
