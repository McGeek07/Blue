package blue.core;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import blue.util.Util;
import blue.util.event.Broker;
import blue.util.event.Handle;
import blue.util.event.Listener;

public class Audio extends Module {
	protected static final Audio
		MODULE = new Audio();
	
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
		Util.map(cfg,
			CHANNELS, channels,
			SAMPLE_RATE, sample_rate,
			SAMPLE_SIZE, sample_size
			);
	}
	
	SourceDataLine
		sdl;
	byte[]
		audio;
	
	public static void init() {
		Engine.init(MODULE);
	}
	
	public static void stop() {
		Engine.stop(MODULE);
	}
	
	public static String setProperty(Object key, Object val) {
		return Util.setEntry(MODULE.cfg, key, val);
	}
	
	public static String getProperty(Object key, Object alt) {
		return Util.getEntry(MODULE.cfg, key, alt);
	}
	
	public static <T> void attach(Class<T> type, Listener<T> listener) {
		MODULE.handle.attach(type, listener);
	}
	
	public static <T> void detach(Class<T> type, Listener<T> listener) {
		MODULE.handle.detach(type, listener);
	}
	
	public static void attach(Broker broker) {
		MODULE.broker.attach(broker);
	}
	
	public static void detach(Broker broker) {
		MODULE.broker.detach(broker);
	}
	
	public static void attach(Handle handle) {
		MODULE.broker.attach(handle);
	}
	
	public static void detach(Handle handle) {
		MODULE.broker.detach(handle);
	}
	
	public static <T> void queue(T event) {
		MODULE.broker.queue(event);
	}
	
	public static <T> void flush(T event) {
		MODULE.broker.flush(event);
	}
	
	public static void poll() {
		MODULE.broker.poll();
	}
	
	@Override
	public void onInit() {		
		channels    = Util.getEntryAsInt(cfg, CHANNELS   , channels   );
		sample_rate = Util.getEntryAsInt(cfg, SAMPLE_RATE, sample_rate);
		sample_size = Util.getEntryAsInt(cfg, SAMPLE_SIZE, sample_size);
		format = new AudioFormat(
				sample_rate,
				sample_size,
				channels,
				true ,
				false
				);
		
		try {
			audio = read("itb.wav");
			System.out.printf("%1$.2f mB%n", audio.length/1000000f);
			sdl = AudioSystem.getSourceDataLine(format);
			sdl.open(format);
			sdl.start();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Engine.stop(MODULE);
		}
	}
	
	@Override
	public void onStop() {
		
	}
	
	int 
		offset = 0,
		length = 0;
	
	@Override
	public void onStep() throws InterruptedException {
		poll();
		
		length = Math.min(audio.length - offset, sdl.available());
		offset += sdl.write(audio, offset, length);
	}
	
	public static byte[] read(String path) throws IOException, UnsupportedAudioFileException {
		return read(new File(path));
	}
	
	public static byte[] read(File   file) throws IOException, UnsupportedAudioFileException {
		AudioInputStream 
			ais0 = AudioSystem.getAudioInputStream(               file),
			ais1 = AudioSystem.getAudioInputStream(MODULE.format, ais0);
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
