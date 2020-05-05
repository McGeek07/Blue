package blue.core;

import java.util.HashSet;
import java.util.Iterator;

import blue.util.Util;

public class Mixer {
	public static final Mixer
		MASTER = new Mixer(null);	
	
	protected final Mixer.Group
		mixers = new Mixer.Group();
	protected final Sound.Group
		sounds = new Sound.Group();
	
	protected float
		level   = 1f,
		balance = 0f;
	protected float
		stereo_l = 1f,
		stereo_r = 1f;
	
	public Mixer() {
		this(MASTER);
	}
	
	public Mixer(Mixer mixer) {
		if(mixer != null)
			mixer.attach(this);
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
	
	public boolean add(Mixer mixer) {
		return mixers.add(mixer);
	}
	
	public boolean del(Mixer mixer) {
		return mixers.del(mixer);
	}
	
	public void attach(Mixer mixer) {
		mixers.attach(mixer);
	}
	
	public void detach(Mixer mixer) {
		mixers.detach(mixer);
	}
	
	public boolean add(Sound sound) {
		return sounds.add(sound);
	}
	
	public boolean del(Sound sound) {
		return sounds.del(sound);
	}
	
	public void attach(Sound sound) {
		sounds.attach(sound);
	}
	
	public void detach(Sound sound) {
		sounds.detach(sound);
	}
	
	public void attach() {
		sounds.attach();
		mixers.attach();
	}
	
	public void detach() {
		sounds.detach();
		mixers.detach();
	}
	
	public void poll() {
		attach();
		detach();	
	}
	
	public short[] step(float dt) {
		short[] frame = {0, 0};
		
		for(Sound sound: sounds) {
			int f = (int)sound.step(dt).frame * 2;
			frame[0] += (short)(sound.frames[f + 0] * sound.stereo_l);
			frame[1] += (short)(sound.frames[f + 1] * sound.stereo_r);
		}		
		for(Mixer mixer: mixers) {
			short[] _frame = mixer.step(dt);
			frame[0] += _frame[0];
			frame[1] += _frame[1];
		}		
		frame[0] *= stereo_l;
		frame[1] *= stereo_r;
		
		return frame;
	}
	
	
	public static class Group implements Iterable<Mixer> {
		protected final HashSet<Mixer>
			mixers = new HashSet<>(),
			attach = new HashSet<>(),
			detach = new HashSet<>();
		
		public boolean add(Mixer mixer) {
			return mixers.add   (mixer);
		}
		
		public boolean del(Mixer mixer) {
			return mixers.remove(mixer);
		}
		
		public void attach(Mixer mixer) {
			attach.add(mixer);
		}
		
		public void detach(Mixer mixer) {
			detach.add(mixer);
		}
		
		public void attach() {
			if(attach.size() > 0) {
				for(Mixer mixer: attach)
					add(mixer);
				attach.clear();
			}
		}
		
		public void detach() {
			if(detach.size() > 0) {
				for(Mixer mixer: detach)
					del(mixer);
				detach.clear();
			}
		}

		@Override
		public Iterator<Mixer> iterator() {
			return mixers.iterator();
		}		
	}
}
