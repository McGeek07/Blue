package blue.core;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import blue.util.Debug;
import blue.util.Util;

public class Sound {
	public static final int
		STOP = 0,
		PLAY = 1,
		LOOP = 2;
	
	protected Mixer
		mixer;
	protected Source
		source;
	protected Filter
		filter;
	
	protected short[]
		frames;
	protected float
		frame = 0f,
		speed = 1f,
		level = 1f,
		balance = 0f;
	protected float
		stereo_l = 1f,
		stereo_r = 1f;
	protected int
		mode;
	
	public Sound(
		Source source,
		Filter filter
	) {
		this(
			Mixer.MASTER,
			source,
			filter
		);
	}
	
	public Sound(
		Mixer mixer,
		Source source,
		Filter filter
	) {
		this.mixer = mixer;
		this.source = source;
		this.filter = filter;
		
		this.frames = this.source != null ? this.source.filter(this.filter) : null;
	}
	
	public void setSource(Source source) {
		this.source = source;
		
		this.frames = this.source != null ? this.source.filter(this.filter) : null;
	}
	
	public void setFilter(Filter filter) {
		this.filter = filter;
		
		this.frames = this.source != null ? this.source.filter(this.filter) : null;
	}
	
	public Source getSource() {
		return this.source;
	}
	
	public Filter getFilter() {
		return this.filter;
	}
	
	public void setFrame(float frame) {
		this.frame = frame;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void setLevel(float level) {
		this.level = Util.clamp(level, 0f, Float.POSITIVE_INFINITY);		
		this.stereo_l = Math.min(1f - this.balance, 1f) * this.level;
		this.stereo_r = Math.min(1f + this.balance, 1f) * this.level;
	}
	
	public void setBalance(float balance) {
		this.balance = Util.clamp(balance, -1f, 1f);
		this.stereo_l = Math.min(1f - this.balance, 1f) * this.level;
		this.stereo_r = Math.min(1f + this.balance, 1f) * this.level;
	}
	
	public float getFrame() {
		return this.frame;
	}
	
	public float getSpeed() {
		return this.speed;
	}
	
	public float getLevel() {
		return this.level;
	}
	
	public float getBalance() {
		return this.balance;
	}
	
	public void play() {
		this.mode = PLAY;
		this.mixer.attach(this);
	}
	
	public void loop() {
		this.mode = LOOP;
		this.mixer.attach(this);
	}
	
	public void stop() {
		this.mode = STOP;
		this.mixer.detach(this);
	}
	
	public void play(float level, float balance) {
		this.setBalance(balance);
		this.setLevel(level);
		this.play();
	}
	
	public void loop(float level, float balance) {
		this.setBalance(balance);
		this.setLevel(level);
		this.loop();
	}
	
	public void stop(float frame) {
		this.setFrame(frame);
		this.stop();
	}	
	
	public boolean isPlaying() {
		return this.mode == PLAY;
	}
	
	public boolean isLooping() {
		return this.mode == LOOP;
	}
	
	public boolean isStopped() {
		return this.mode == STOP;
	}	
	
	public Sound step(float dt) {
		if(mode > 0) {
			frame += speed * dt;
			switch(mode) {
				case PLAY: 
					if(frame <  0f           ) stop(0f               );
					if(frame >= source.length) stop(source.length - 1);
					break;
				case LOOP:
					while(frame <  0f           ) frame += source.length;
					while(frame >= source.length) frame -= source.length;
			}
		}
		return this;
	}
	
	public static Sound load(String name, String path) {
		return new Sound(Source.load(name, path), null);
	}
	
	public static Sound fromName(Mixer mixer, String name, Filter filter) {
		return new Sound(mixer, Source.getByName(name), filter);
	}
	
	public static Sound fromPath(Mixer mixer, String path, Filter filter) {
		return new Sound(mixer, Source.getByPath(path), filter);
	}
	
	public static Sound fromName(String name, Filter filter) {
		return new Sound(Source.getByName(name), filter);
	}
	
	public static Sound fromPath(String path, Filter filter) {
		return new Sound(Source.getByPath(path), filter);
	}
	
	public static class Source {
		private static final HashMap<String, Source>
			NAME_INDEX = new HashMap<String, Source>(),
			PATH_INDEX = new HashMap<String, Source>();
		
		private final HashMap<Sound.Filter, short[]>
			cache = new HashMap<Sound.Filter, short[]>();
		
		public final String
			name,
			path;
		public final int
			length;
		
		public Source(
			String name,
			String path,
			short[] pcm
		) {
			this.name = name;
			this.path = path;
			this.length = pcm.length / 2;
			
			cache.put(null, pcm);
		}
		
		public static Source getByName(String name) {
			Source source = NAME_INDEX.get(name);
			if(source == null)
				Debug.warn(new Object() {/* trace */}, "A Sound.Source with name '" + name + "' does not exist.");
			return source;
		}
		
		public static Source getByPath(String path) {
			Source source = PATH_INDEX.get(path);
			if(source == null)
				Debug.warn(new Object() {/* trace */}, "A Sound.Source with path '" + path + "' does not exist.");
			return source;
		}
		
		public short[] filter(Filter filter) {
			short[]
				source_frames = cache.get(null  ),
				filter_frames = cache.get(filter),
				buffer_frames;
			if(filter_frames == null) {
				buffer_frames = new short[source_frames.length]; 
				filter_frames = new short[source_frames.length];
				
				for(int i = 0; i < source_frames.length; i ++)
					buffer_frames[i] = source_frames[i];
				filter.filter(buffer_frames);
				for(int i = 0; i < source_frames.length; i ++)
					filter_frames[i] = buffer_frames[i];
				
				cache.put(filter, filter_frames);
			}
			return filter_frames;
		}
		
		public static Source load(String name, String path) {
			if(NAME_INDEX.containsKey(name))
				Debug.warn(new Object() {/* trace */}, "A Sound.Source with name '" + name + "' already exists.");
			if(PATH_INDEX.containsKey(path))
				Debug.warn(new Object() {/* trace */}, "A Sound.Source with path '" + path + "' already exists.");
			
			try {
				byte [] bytes = Audio.read(path);
				short[] frames = new short[bytes.length / 2]; 
				ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(frames);
				
				Source source = new Source(
					name,
					path,
					frames
					);
				NAME_INDEX.put(name, source);
				PATH_INDEX.put(path, source);				
				return source;
				
			} catch (Exception e) {
				Debug.warn(new Object() {/* trace */}, "Failed to load Sound.Source (" + name + ", " + path + ").");
				e.printStackTrace();
				return null;
			}
		}		
	}
	
	public static interface Filter {
		public void filter(short[] source);
	}
	
	public static class Group implements Iterable<Sound> {
		protected final HashSet<Sound>
			sounds = new HashSet<>(),
			attach = new HashSet<>(),
			detach = new HashSet<>();
		
		public void attach(Sound sound) {
			attach.add(sound);
		}
		
		public void detach(Sound sound) {
			detach.add(sound);
		}
		
		public boolean onAttach(Sound sound) {
			return sounds.add(sound);
		}
		
		public boolean onDetach(Sound sound) {
			return sounds.remove(sound);
		}
		
		public void attachPending() {
			if(attach.size() > 0) {
				for(Sound sound: attach)
					onAttach(sound);
				attach.clear();
			}
		}
		
		public void detachPending() {
			if(detach.size() > 0) {
				for(Sound sound: detach)
					onDetach(sound);
				detach.clear();
			}
		}

		@Override
		public Iterator<Sound> iterator() {
			return sounds.iterator();
		}		
	}
}
