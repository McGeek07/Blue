package blue.game;

import java.util.HashMap;

public class Sound {	
	
	public static Sprite load(String name, String path) {
		return null;
	}
	
	public static class Sample {
		private static final HashMap<String, Sample>
			NAME_INDEX = new HashMap<String, Sample>(),
			PATH_INDEX = new HashMap<String, Sample>();	
		
		public static Sample getByName(String name) {
			Sample sample = NAME_INDEX.get(name);
			if(sample == null)
				System.err.println("[blue.game.Sound.Sample.getByName] A Sample with name '" + name + "' does not exist.");
			return sample;
		}
		
		public static Sample getByPath(String path) {
			Sample sample = PATH_INDEX.get(path);
			if(sample == null)
				System.err.println("[blue.game.Sound.Sample.getByPath] A Sample with path '" + path + "' does not exist.");
			return sample;
		}
		
		public static Sample load(String name, String path) {
			if(NAME_INDEX.containsKey(name))
				System.err.println("[blue.game.Sound.Sample.load] A Sample with name '" + name + "' already exists.");
			if(PATH_INDEX.containsKey(path))
				System.err.println("[blue.game.Sound.Sample.load] A Sample with path '" + path + "' already exists.");
			return null;
		}
	}
}
