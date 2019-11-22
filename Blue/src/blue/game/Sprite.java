package blue.game;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import blue.core.Renderable;
import blue.core.Updateable;
import blue.geom.Region2;

public class Sprite implements Renderable, Updateable {
	public static final int
		STOP = 0,
		PLAY = 1,
		LOOP = 2;
	
	protected Frames
		frames;
	protected Effect
		effect;	
	protected BufferedImage[]
		images;
	
	protected float
		frame = 0f,
		speed = 0f,
		alpha = 1f;
	protected boolean
		flip,
		flop;
	protected int
		mode;
	
	protected Composite
		alpha_composite;
	
	public final Region2.Mutable
		bounds = new Region2.Mutable();
	
	public Sprite(
			Frames frames,
			Effect effect
			) {
		this.frames = frames;
		this.effect = effect;
		
		this.images = frames.filter(effect);
		
		this.bounds.set(
				0, 0,
				frames.frame_w,
				frames.frame_h
				);
	}
	
	public void setFrames(Frames frames) {
		this.frames = frames;
		
		this.images = this.frames != null ? this.frames.filter(effect) : null;
	}
	
	public void setEffect(Effect effect) {
		this.effect = effect;
		
		this.images = this.frames != null ? this.frames.filter(effect) : null;
	}
	
	public Frames getFrames() {
		return this.frames;
	}
	public Effect getEffect() {
		return this.effect;
	}
	
	public void setFrame(float frame) {
		this.frame = frame;
	}	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public void setAlpha(float alpha) {
		if(this.alpha != alpha) {
			this.alpha           = alpha;
			this.alpha_composite = null ;
		}		
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
	
	public void play() {
		this.mode = PLAY;
	}	
	public void loop() {
		this.mode = LOOP;
	}	
	public void stop() {
		this.mode = STOP;
	}	
	public void flip() {
		this.flip = !flip;
	}	
	public void flop() {
		this.flop = !flop;
	}
	
	public void play(float speed) {
		this.setSpeed(speed);
		this.play();
	}	
	public void loop(float speed) {
		this.setSpeed(speed);
		this.loop();
	}	
	public void stop(float frame) {
		this.setFrame(frame);
		this.stop();
	}	
	public void flip(boolean flip) {
		this.flip = flip;
	}	
	public void flop(boolean flop) {
		this.flop = flop;
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
	public boolean isFlipped() {
		return this.flip;
	}
	public boolean isFlopped() {
		return this.flop;
	}

	@Override
	public void onRender(RenderContext context) {
		if(alpha > 0f) {
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
			if(alpha < 1f) {				
				context.push();
				
				if(alpha_composite == null)
					alpha_composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
				
				context.g.setComposite(alpha_composite);
				context.g.drawImage(
						images[(int)frame],
						x1, y1,
						x2, y2,
						0 , 0 ,
						frames.frame_w,
						frames.frame_h,
						null
						);	
				
				context.pop();
			} else
				context.g.drawImage(
						images[(int)frame],
						x1, y1,
						x2, y2,
						0 , 0 ,
						frames.frame_w,
						frames.frame_h,
						null
						);
		}
	}

	@Override
	public void onUpdate(UpdateContext context) {
		if(mode > 0) {
			frame += speed * context.dt;
			switch(mode) {
				case PLAY: 
					if(frame <  0f           ) stop(0f               );
					if(frame >= images.length) stop(images.length - 1);
					break;
				case LOOP:
					if(frame <  0f           ) frame += images.length;
					if(frame >= images.length) frame -= images.length;
			}
		}
	}
	
	public Sprite filter(Effect effect) {
		return new Sprite(
				frames ,
				effect
				);
	}
	
	public static void load(String name, String path, int frame_w, int frame_h) {
		try {
			new Frames(name, path, frame_w, frame_h);
		} catch (Exception ex) {
			System.err.println("Failed to load Sprite");
			ex.printStackTrace();
		}
	}
	
	public static Sprite fromName(String name, Effect effect) {
		return new Sprite(Frames.fromName(name), effect);
	}
	
	public static Sprite fromPath(String path, Effect effect) {
		return new Sprite(Frames.fromName(path), effect);
	}
	
	public static class Frames {		
		private static final HashMap<String, Frames>
			NAME_INDEX = new HashMap<String, Frames>(),
			PATH_INDEX = new HashMap<String, Frames>();
		public final String
			name,
			path;
		public final BufferedImage
			atlas;
		public final int
			atlas_w,
			atlas_h,
			frame_w,
			frame_h;
		
		private final HashMap<Effect, BufferedImage[]>
			cached = new HashMap<Effect, BufferedImage[]>();
		
		public Frames(
				String name,
				String path,
				int frame_w,
				int frame_h
				) throws IOException {
			if(NAME_INDEX.containsKey(name))
				throw new IllegalArgumentException("Duplicate name '" + name + "'");
			if(PATH_INDEX.containsKey(name))
				throw new IllegalArgumentException("Duplicate path '" + path + "'");
			
			this.atlas = ImageIO.read(new File(path));
			
			this.name = name;
			this.path = path;
			this.frame_w = frame_w;
			this.frame_h = frame_h;
			this.atlas_w = atlas.getWidth() ;
			this.atlas_h = atlas.getHeight();
			
			int
				w = atlas_w / frame_w,
				h = atlas_h / frame_h;
			BufferedImage[] frames = new BufferedImage[w * h];
			for(int x = 0; x < w; x ++)
				for(int y = 0; y < h; y ++)
					frames[h * y + x] = atlas.getSubimage(
							x * frame_w,
							y * frame_h,
							frame_w,
							frame_h
							);
			 
			cached.put(null, frames);
			
			NAME_INDEX.put(name, this);
			PATH_INDEX.put(path, this);
		}
		
		public BufferedImage[] filter(Effect effect) {
			BufferedImage[]
					sprite_frames = cached.get(null  ),
					effect_frames = cached.get(effect);	
			if(effect_frames == null) {
				effect_frames = new BufferedImage[sprite_frames.length];
				for(int i = 0; i < sprite_frames.length; i ++) {
					int
						frame_w = sprite_frames[i].getWidth() ,
						frame_h = sprite_frames[i].getHeight();
					BufferedImage
						sprite_frame = sprite_frames[i],
						effect_frame = new BufferedImage(
								frame_w,
								frame_h,
								BufferedImage.TYPE_INT_ARGB
								);
					int[] frame_data = new int[frame_w * frame_h];
					sprite_frame.getRGB(0, 0, frame_w, frame_h, frame_data, 0, frame_w);
					
					effect.filter(frame_data, frame_w, frame_h);
					effect_frame.setRGB(0, 0, frame_w, frame_h, frame_data, 0, frame_w);
					
					effect_frames[i] = effect_frame;
				}
				cached.put(effect, effect_frames);
			}			
			return effect_frames;
		}
		
		public static Frames fromName(String name) {
			Frames frames = NAME_INDEX.get(name);
			if(frames == null)
				throw new IllegalArgumentException("No Atlas exists for name '" + name + "'");
			return frames;
		}
		
		public static Frames fromPath(String path) {
			Frames frames = PATH_INDEX.get(path);
			if(frames == null)
				throw new IllegalArgumentException("No Atlas exists for path '" + path + "'");
			return frames;
		}
	}
	
	private static final int
		BLACK = 0xFF000000,
		WHITE = 0xFFFFFFFF;
	
	public static interface Effect {
		public void filter(int[] frame_data, int frame_w, int frame_h);
		
		public static final Effect
			BLACKOUT = (frame_data, frame_w, frame_h) -> {
				for(int i = 0; i < frame_data.length; i ++) {
					int alpha = (frame_data[i] >> 24) & 0xFF;
					if(alpha > 0) frame_data[i] = BLACK;
				}					
			},
			WHITEOUT = (frame_data, frame_w, frame_h) -> {
				for(int i = 0; i < frame_data.length; i ++) {
					int alpha = (frame_data[i] >> 24) & 0xFF;
					if(alpha > 0) frame_data[i] = WHITE;
				}
			};
	}
}
