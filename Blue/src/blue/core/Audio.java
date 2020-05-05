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
	
	public static final int
		PLAYBACK_CHANNELS    =     2,
		PLAYBACK_SAMPLE_RATE = 44100,
		PLAYBACK_SAMPLE_SIZE =    16;
	public static final AudioFormat
		PLAYBACK_FORMAT = new AudioFormat(
				PLAYBACK_SAMPLE_RATE,
				PLAYBACK_SAMPLE_SIZE,
				PLAYBACK_CHANNELS,
				true ,
				false
				);
	
	private Audio() {
		//do nothing
	}
	
	private SourceDataLine
		sdl;
	
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
		try {			
			sdl = AudioSystem.getSourceDataLine(PLAYBACK_FORMAT);
			sdl.open(PLAYBACK_FORMAT, 2048);
			sdl.start();			
		} catch (Exception e) {
			System.err.println("[blue.core.Audio.onInit] Failed to init module '" + getClass().getName() + "'.");
			e.printStackTrace();
			Engine.stop(MODULE);
		}
	}
	
	@Override
	public void onStop() {
		
	}
	
	@Override
	public void onStep() throws InterruptedException {
		             poll();	
		Mixer.MASTER.poll();
		
		short[] frame = Mixer.MASTER.step(1f);		
		byte[] buffer = {
			(byte)( frame[0]       & 0xff),
			(byte)((frame[0] >> 8) & 0xff),
			(byte)( frame[1]       & 0xff),
			(byte)((frame[1] >> 8) & 0xff)
		};
		
		sdl.write(buffer, 0, buffer.length);
	}
	
	public static byte[] read(String path) throws IOException, UnsupportedAudioFileException {
		return read(new File(path));
	}
	
	public static byte[] read(File   file) throws IOException, UnsupportedAudioFileException {
		AudioInputStream 
			ais0 = AudioSystem.getAudioInputStream(                 file),
			ais1 = AudioSystem.getAudioInputStream(PLAYBACK_FORMAT, ais0);
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
}
