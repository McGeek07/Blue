package blue.util.event;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class MonoBroker<T> {
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
			if(attach.size() > 0) {
				for(MonoBroker<T> broker: attach)
					add(broker);
				attach.clear();
			}
			if(brokers.size() > 0) {
				for(MonoBroker<T> broker: brokers)
					broker.attach();
			}
		}
		
		public void detach() {
			if(brokers.size() > 0) {
				for(MonoBroker<T> broker: brokers)
					broker.detach();
			}
			if(detach.size() > 0) {
				for(MonoBroker<T> broker: detach)
					del(broker);
				detach.clear();
			}
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