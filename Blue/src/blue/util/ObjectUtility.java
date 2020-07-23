package blue.util;

public class ObjectUtility {
	
	private ObjectUtility() {
		//hide constructor
	}

	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> typeOf(T t) {
		return (Class<T>)t.getClass();
	}
}
