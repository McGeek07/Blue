package blue.core;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import blue.geom.Vector2;
import blue.util.Util;

public class Scene implements Serializable, Renderable, Updateable {
	private static final long 
		serialVersionUID = 1L;
	
	@Override
	public void onRender(RenderContext context) { }	
	@Override
	public void onUpdate(UpdateContext context) { }
	
	public void onInit() { }
	public void onExit() { }
	public void onAttach() { }
	public void onDetach() { }
	public void onMouseMoved(Vector2 mouse) { }
	public void onWheelMoved(float   wheel) { }
	public void onKeyDn(int key) { }	
	public void onKeyUp(int key) { }
	public void onBtnDn(int btn) { }
	public void onBtnUp(int btn) { }
	
	public void onSave(ObjectOutputStream out) { 
		try {
			out.defaultWriteObject();
		} catch (Exception ex) {
			System.err.println("[ERROR] Scene.onSave()");
			ex.printStackTrace();
		} 
	}
	
	public void onLoad(ObjectInputStream  in ) { 
		try {
			in .defaultReadObject();
		} catch (Exception ex) {
			System.err.println("[ERROR] Scene.onLoad()");
			ex.printStackTrace();
		}
	}
	
	public void writeObject(ObjectOutputStream out) throws IOException {
		onSave(out);
	}
	
	public void readObject (ObjectInputStream  in ) throws IOException, ClassNotFoundException {
		onLoad(in );
	}
	
	public static void save(Scene scene, String path) {		
		Util.writeToFile(path, false, scene);
	}
	
	public static void save(Scene scene, File file  ) {
		Util.writeToFile(file, false, scene);
	}
	
	public static Scene load(String path) {
		Scene scene = Util.readFromFile(path);
		return scene;
	}
	
	public static Scene load(File file  ) {
		Scene scene = Util.readFromFile(file);
		return scene;
	}
}
