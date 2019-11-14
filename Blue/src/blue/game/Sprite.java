package blue.game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import blue.core.Renderable;
import blue.core.Updateable;
import blue.geom.Boundable;
import blue.geom.Region2;

public class Sprite implements Boundable<Region2.Mutable>, Renderable, Updateable {
	public static final int
		STOP = 0,
		PLAY = 1,
		LOOP = 2;	
	
	protected final Sprite.Atlas
		atlas;
	protected Effect
		effect;
	protected BufferedImage[]
		frames;
	
	protected final Region2.Mutable
		bounds = new Region2.Mutable();
	
	protected float
		frame,
		speed,	
		alpha;
	protected boolean
		flip,
		flop;
	protected int
		mode;
	
	public Sprite(Sprite.Atlas atlas, Effect effect) {
		this.atlas  = atlas ;
		this.effect = effect;
		this.frames = atlas.frames(effect);
		this.bounds.dim().set(
				this.atlas.frame_w,
				this.atlas.frame_h
				);
	}

	@Override
	public Region2.Mutable bounds() {
		return bounds;
	}

	@Override
	public void onRender(RenderContext context) {
		context.push();
		int
			x1, y1,
			x2, y2;
		if(flip) {
			x1 = (int)bounds.x2();
			x2 = (int)bounds.x1();
		} else {
			x1 = (int)bounds.x1();
			x2 = (int)bounds.x2();
		}
		if(flop) {
			y1 = (int)bounds.y2();
			y2 = (int)bounds.y1();
		} else {
			y1 = (int)bounds.y1();
			y2 = (int)bounds.y2();
		}
		context.g.drawImage(
				atlas.frames[(int)frame],
				x1, y1,
				x2, y2,
				0 , 0 ,
				atlas.frame_w,
				atlas.frame_h,
				null
				);
		context.pop();
	}

	@Override
	public void onUpdate(UpdateContext context) {
		if(mode > 0) {
			frame += speed * context.dt;
			switch(mode) {
				case PLAY: 
					if(frame <  0f           ) stop(0f               );
					if(frame >= frames.length) stop(frames.length - 1);
					break;
				case LOOP:
					if(frame <  0f           ) frame += frames.length;
					if(frame >= frames.length) frame -= frames.length;
			}
		}
	}
	
	public int w() {
		return atlas.frame_w;
	}
	
	public int h() {
		return atlas.frame_h;
	}
	
	public void setEffect(Effect effect) {
		this.effect = effect;
		this.frames = atlas.frames(effect);
	}
	
	public void setFrame(float frame) {
		this.frame = frame;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	public float getFrame() {
		return this.frame;
	}
	
	public float getSpeed() {
		return this.speed;
	}
	
	public float getAlpha() {
		return this.alpha;
	}
	
	public void play(float speed) {
		this.setSpeed(speed);
		this.play();
	}
	
	public void play() {
		this.mode = PLAY;
	}
	
	public void loop(float speed) {
		this.setSpeed(speed);
		this.loop();
	}
	
	public void loop() {
		this.mode = LOOP;
	}
	
	public void stop(float frame) {
		this.setFrame(frame);
		this.stop();
	}
	
	public void stop() {
		this.mode = STOP;
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
	
	public void flip(boolean flip) {
		this.flip =  flip;
	}
	
	public void flip() {
		this.flip = !flip;
	}
	
	public void flop(boolean flop) {
		this.flop =  flop;
	}
	
	public void flop() {
		this.flop = !flop;
	}
	
	public boolean isFlipped() {
		return this.flip;
	}
	
	public boolean isFlopped() {
		return this.flop;
	}
	
	public static Sprite getByName(String name) {
		return Sprite.getByName(name, null);
	}
	
	public static Sprite getByPath(String path) {
		return Sprite.getByPath(path, null);
	}
	
	public static Sprite getByName(String name, Effect effect) {
		return new Sprite(Sprite.Atlas.getByName(name), effect);
	}
	
	public static Sprite getByPath(String path, Effect effect) {
		return new Sprite(Sprite.Atlas.getByPath(path), effect);
	}
	
	public static class Atlas {
		private static final TreeMap<String, Atlas>
			NAME_INDEX = new TreeMap<String, Atlas>(),
			PATH_INDEX = new TreeMap<String, Atlas>();
		public final String
			name,
			path;
		public final int
			frame_w,
			frame_h;
		public final BufferedImage[]
			frames;
		public final HashMap<Integer, BufferedImage[]>
			cached = new HashMap<>();
		
		public Atlas(
				String name,
				String path,
				int frame_w,
				int frame_h
				) {
			if(NAME_INDEX.get(name) != null)
				throw new IllegalArgumentException("Duplicate Name '" + name + "' for Sprite.Atlas");
			if(PATH_INDEX.get(path) != null)
				throw new IllegalArgumentException("Duplicate Path '" + path + "' for Sprite.Atlas");
			this.name = name;
			this.path = path;
			this.frame_w = frame_w;
			this.frame_h = frame_h;
			this.frames = Sprite.Atlas.create(
					this.path,
					this.frame_w,
					this.frame_h
					);
			NAME_INDEX.put(this.name, this);
			PATH_INDEX.put(this.path, this);
		}
		
		public BufferedImage[] frames(Effect effect) {
			if(effect != null) {
				BufferedImage[] frames = cached.get(effect.uid);
				if(frames == null) {
					frames = Effect.filter(frames, effect);
					cached.put(effect.uid, frames);
				}
				return frames;
			} else
				return frames;
		}		
		
		public static Sprite.Atlas getByName(String name) {
			Sprite.Atlas atlas = NAME_INDEX.get(name);
			if(atlas == null) 
				throw new IllegalArgumentException("No Sprite Atlas exists for name '" + name + "'");
			else
				return atlas;
		}
		
		public static Sprite.Atlas getByPath(String path) {
			Sprite.Atlas atlas = NAME_INDEX.get(path);
			if(atlas == null) 
				throw new IllegalArgumentException("No Sprite Atlas exists for path '" + path + "'");
			else
				return atlas;
		}
		
		public static final BufferedImage[] create(String path, int frame_w, int frame_h) {
			try {
				BufferedImage image = ImageIO.read(new File(path));		
				int
					w = image.getWidth()  / frame_w,
					h = image.getHeight() / frame_h;
				BufferedImage[] frames = new BufferedImage[w * h];
				for(int x = 0; x < w; x ++)
					for(int y = 0; y < h; y ++)
						frames[h * y + x] = image.getSubimage(
								x * frame_w,
								y * frame_h,
								frame_w,
								frame_h
								);			
				return frames;
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
			return null;
		}	
	}
	
	public static interface Filter {
		public int[] filter(int[] pixels, int w, int h);
	}
	
	public static class Effect {
		private static final HashMap<Integer, Effect>
			INDEX = new HashMap<>();
		private static int
			UID;

		protected final int
			uid;
		protected final Filter[]
			filters;
		
		public Effect(Filter... filters) {
			this.uid = UID ++;
			this.filters = filters;
			INDEX.put(this.uid, this);
		}
		
		public int[] filter(int[] pixels, int w, int h) {
			for(Filter filter: filters)
				pixels = filter.filter(pixels, w, h);
			return pixels;
		}
		
		public static final BufferedImage[] filter(BufferedImage[] frames, Effect effect) {
			BufferedImage[] filter = new BufferedImage[frames.length];
			for(int i = 0; i < frames.length; i ++) {
				int
					frame_w = frames[i].getWidth() ,
					frame_h = frames[i].getHeight();
				BufferedImage
					frame0 = frames[i],
					frame1 = new BufferedImage(
							frame_w,
							frame_h,
							BufferedImage.TYPE_INT_ARGB
							);
				int[] pixels = new int[frame_w * frame_h];
				frame0.getRGB(0, 0, frame_w, frame_h, pixels, 0, frame_w);
				
				pixels = effect.filter(pixels, frame_w, frame_h);
				frame1.setRGB(0, 0, frame_w, frame_h, pixels, 0, frame_w);
				
				filter[i] = frame1;
			}
			return filter;
		}
	}
}
