package blue.core;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio extends Module {
	protected static final Audio
		INSTANCE = new Audio();	
	
	protected int
		channels = 2,
		sample_rate = 44100,
		sample_size =    16;
	protected AudioFormat
		format = new AudioFormat(
				sample_rate,
				sample_size,
				channels,
				false,
				false
				);
	
	private Audio() {
		config.set(
				CHANNELS, channels,
				SAMPLE_RATE, sample_rate,
				SAMPLE_SIZE, sample_size
				);
	}
	
	SourceDataLine
		sdl;
	byte[]
		audio;
	
	@Override
	public void onInit() {		
//		channels = config.getInt(CHANNELS, channels);
//		sample_rate = config.getInt(SAMPLE_RATE, sample_rate);
//		sample_size = config.getInt(SAMPLE_SIZE, sample_size);
//		format = new AudioFormat(
//				sample_rate,
//				sample_size,
//				channels,
//				true ,
//				false
//				);
//		
//		try {
//			audio = read("itb.wav");
//			System.out.printf("%1$.2f mB%n", audio.length/1000000f);
//			sdl = AudioSystem.getSourceDataLine(format);
//			sdl.open(format);
//			sdl.start();
//			
//			sdl.write(audio, 0, audio.length);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			stop();
//		}
	}
	
	@Override
	public void onStop() {
		
	}
	
	@Override
	public void onStep() throws Exception {
		poll();
	}
	
	public static byte[] read(String path) throws IOException, UnsupportedAudioFileException {
		return read(new File(path));
	}
	
	public static byte[] read(File   file) throws IOException, UnsupportedAudioFileException {
		AudioInputStream 
			ais0 = AudioSystem.getAudioInputStream(                 file),
			ais1 = AudioSystem.getAudioInputStream(INSTANCE.format, ais0);
		ByteArrayOutputStream 
			baos = new ByteArrayOutputStream();
		
		int    
			read;
		byte[] 
			bits = new byte[1024];
		
		while((read = ais1.read(bits)) > 0)
			baos.write(bits, 0, read);
		
		baos.flush();
		ais0.close();
		ais1.close();
		
		return baos.toByteArray();
	}
	
	public static final String
		CHANNELS    = "channels",
		SAMPLE_RATE = "sample-rate",
		SAMPLE_SIZE = "sample-size";
}
