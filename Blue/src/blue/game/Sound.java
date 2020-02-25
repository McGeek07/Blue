package blue.game;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {	
	private Clip
		clip;
	
	public Sound(Clip clip) {
		this.clip = clip;
	}
	
	
	public static void main(String[] args) {
		
	}
	
	public void play() {
	}
	
	public void loop() {
		
	}
	
	public void stop() {
		
	}
	
	public static final Sound load(String path) {
		return load(new File(path));
	}
	
	public static final Sound load(File   file) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(file);			
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			
			return new Sound(clip);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
