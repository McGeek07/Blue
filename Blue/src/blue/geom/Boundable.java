package blue.geom;

public interface Boundable<BOX extends Box<?>> {
	public BOX bounds();
}
