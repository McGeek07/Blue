package blue.core;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import blue.core.Event.Broker;
import blue.core.Event.Handle;
import blue.core.Event.Listener;
import blue.util.Configuration;
import blue.util.Debug;

/**
 * The Audio module is the singleton responsible for the audio thread. It is a
 * basic wrapper for a java.sound.sampled.SourceDataLine which handles audio
 * playback by writing raw audio bytes to the output device. The Audio module
 * can be initialized implicitly via Engine.init() or explicitly via 
 * Audio.init()
 * <br>
 * <br>
 * The Audio module also contains static helper methods for reading raw audio 
 * bytes and converting them to the proper playback format. Because sounds can 
 * be loaded at any time, even before the Audio module has been initialized, the
 * playback format is readonly and cannot be changed. Any change to the playback
 * format could make previously loaded sounds incompatible.
 */
public class Audio extends Module {
	//singleton instance
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
		metrics.setMetric(Audio.PLAYBACK_CHANNELS_METRIC   , String.format("%1$s", PLAYBACK_CHANNELS));
		metrics.setMetric(Audio.PLAYBACK_SAMPLE_RATE_METRIC, String.format("%1$s hz", PLAYBACK_SAMPLE_RATE));
		metrics.setMetric(Audio.PLAYBACK_SAMPLE_SIZE_METRIC, String.format("%1$s bits", PLAYBACK_SAMPLE_SIZE));
	}
	
	private SourceDataLine
		sdl;
	
	/**
	 * Explicitly init the module. This method is thread-safe and can be called 
	 * at any time. If the Audio module is already running this method will do 
	 * nothing.
	 */
	public static void init() {
		Engine.init(MODULE);
	}
	
	/**
	 * Explicitly stop the module. This method is thread-safe and can be called
	 * at any time. If the Audio module is not already running this method will 
	 * do nothing.
	 */
	public static void stop() {
		Engine.stop(MODULE);
	}
	
	public static String setProperty(Object key, Object val) {
		return Configuration.setProperty(MODULE.cfg, key, val);
	}
	
	public static String getProperty(Object key, Object alt) {
		return Configuration.setProperty(MODULE.cfg, key, alt);
	}
	
	/**
	 * Queue an event Listener to be attached to the Audio module. This method 
	 * is thread-safe and can be called at any time. Any queued items will 
	 * remain until the queue is flushed either implicitly while the module is 
	 * running or explicitly via Audio.poll().
	 * <br>
	 * <br>
	 * Each module is run in a separate thread and is backed by it own private 
	 * event queue; therefore, attached Listeners will not receive events from 
	 * other modules.
	 */
	public static <T> void attach(Class<T> type, Listener<T> listener) {
		MODULE.handle.attach(type, listener);
	}
	
	/**
	 * Queue an event Listener to be detached from the Audio module. This method 
	 * is thread-safe and can be called at any time. Any queued items will 
	 * remain until the queue is flushed either implicitly while the module is 
	 * running or explicitly via Audio.poll().
	 * <br>
	 * <br>
	 * Each module is run in a separate thread and is backed by it own private 
	 * event queue; therefore, attached Listeners will not receive events from 
	 * other modules.
	 */
	public static <T> void detach(Class<T> type, Listener<T> listener) {
		MODULE.handle.detach(type, listener);
	}
	
	/**
	 * Queue an event Broker to be attached to the Audio module. This method is 
	 * thread-safe and can be called at any time. Any queued items will remain 
	 * until the queue is flushed either implicitly while the module is running 
	 * or explicitly via Audio.poll().
	 * <br>
	 * <br>
	 * Each module is run in a separate thread and is backed by it own private 
	 * event queue; therefore, attached Brokers will not receive events from 
	 * other modules.
	 */
	public static void attach(Broker broker) {
		MODULE.broker.attach(broker);
	}
	
	/**
	 * Queue an event Broker to be detached from the Audio module. This method 
	 * is thread-safe and can be called at any time. Any queued items will 
	 * remain until the queue is flushed either implicitly while the module is 
	 * running or explicitly via Audio.poll().
	 * <br>
	 * <br>
	 * Each module is run in a separate thread and is backed by it own private 
	 * event queue; therefore, attached Brokers will not receive events from 
	 * other modules.
	 */
	public static void detach(Broker broker) {
		MODULE.broker.detach(broker);
	}
	
	/**
	 * Queue an event Handle to be attached to the Audio module. This method is 
	 * thread-safe and can be called at any time. Any queued items will remain 
	 * until the queue is flushed either implicitly while the module is running 
	 * or explicitly via Audio.poll().
	 * <br>
	 * <br>
	 * Each module is run in a separate thread and is backed by it own private 
	 * event queue; therefore, attached Handles will not receive events from 
	 * other modules.
	 */
	public static void attach(Handle handle) {
		MODULE.broker.attach(handle);
	}
	
	/**
	 * Queue an event Handle to be detached from the Audio module. This method 
	 * is thread-safe and can be called at any time. Any queued items will 
	 * remain until the queue is flushed either implicitly while the module is 
	 * running or explicitly via Audio.poll().
	 * <br>
	 * <br>
	 * Each module is run in a separate thread and is backed by it own private 
	 * event queue; therefore, attached Handles will not receive events from 
	 * other modules.
	 */
	public static void detach(Handle handle) {
		MODULE.broker.detach(handle);
	}
	
	/**
	 * Queue an event. This method is thread-safe and can be called at any time.
	 * Any queued items will remain until the queue is flushed implicitly by the 
	 * module or explicitly via Audio.poll().
	 */
	public static <T> void queue(T event) {
		MODULE.broker.queue(event);
	}
	
	/**
	 * Flush an event. This method is executed immediately and therefore is not
	 * thread-safe. To avoid race conditions or "stutter" this method should 
	 * only be called explicitly via the Audio thread.
	 */
	public static <T> void flush(T event) {
		MODULE.broker.flush(event);
	}
	
	/**
	 * Flush queued items. This method is executed immediately and therefore is 
	 * not thread-safe. To avoid race conditions or "stutter" this method should 
	 * only be called explicitly via the Audio thread.
	 */
	public static void poll() {
		MODULE.broker.poll();
	}
	
	public static Metrics getMetrics() {
		return MODULE.metrics;
	}
	
	@Override
	public void onInit() {		
		try {			
			sdl = AudioSystem.getSourceDataLine(PLAYBACK_FORMAT);
			sdl.open(PLAYBACK_FORMAT, 4096);
			sdl.start();
		} catch (Exception e) {
			Debug.warn(new Object() {/* trace */}, "Failed to init module '" + getClass().getName() + "'.");
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
	
	/**
	 * Read raw audio bytes from a file and convert them to the proper playback
	 * format.
	 */
	public static byte[] read(String path) throws IOException, UnsupportedAudioFileException {
		return read(new File(path));
	}
	
	/**
	 * Read raw audio bytes from a file and convert them to the proper playback 
	 * format.
	 */
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
	
	/**
	 * Read raw audio bytes from a resource and convert them to the proper 
	 * playback format.
	 */
	public static byte[] read(Class<?> from, String resource) throws IOException, UnsupportedAudioFileException {
		AudioInputStream 
			ais0 = AudioSystem.getAudioInputStream(from.getResource(resource)),
			ais1 = AudioSystem.getAudioInputStream(PLAYBACK_FORMAT ,    ais0 );
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
		METRICS = Audio.class.getName(),
		PLAYBACK_CHANNELS_METRIC = "Playback Channels",
		PLAYBACK_SAMPLE_RATE_METRIC = "Playback Sample Rate",
		PLAYBACK_SAMPLE_SIZE_METRIC = "Playback Sample Size";
}
