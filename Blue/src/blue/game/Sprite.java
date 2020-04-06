package blue.game;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import javax.imageio.ImageIO;

import blue.core.Renderable;
import blue.core.Updateable;
import blue.geom.Region2;
import blue.geom.Vector;

public class Sprite implements Renderable, Updateable {
	public static final int
		STOP = 0,
		PLAY = 1,
		LOOP = 2;
	
	protected Atlas
		atlas;
	protected Paint
		paint;
	protected BufferedImage[]
		frames;
	
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
			Atlas atlas,
			Paint paint
			) {
		this.atlas = atlas;
		this.paint = paint;
		
		this.bounds.set(
				0, 0,
				atlas.frame_w,
				atlas.frame_h
				);
		
		this.frames = this.atlas != null ? this.atlas.paint(this.paint) : null;
	}
	
	public void setAtlas(Atlas frames) {
		this.atlas = frames;
		
		this.frames = this.atlas != null ? this.atlas.paint(this.paint) : null;
	}
	
	public void setPaint(Paint paint) {		
		this.paint = paint;
		
		this.frames = this.atlas != null ? this.atlas.paint(this.paint) : null;
	}
	
	public Atlas getAtlas() {
		return this.atlas;
	}
	
	public Paint getPaint() {
		return this.paint;
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
	
	public void center(float x, float y) {
		float
			w2 = bounds.w() / 2,
			h2 = bounds.h() / 2;
		bounds.loc().set(
				x - w2,
				y - h2
				);
	}
	public void center(Vector v) {
		this.center(v.x(), v.y());
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
						frames[(int)frame],
						x1, y1,
						x2, y2,
						0 , 0 ,
						atlas.frame_w,
						atlas.frame_h,
						null
						);	
				
				context.pop();
			} else
				context.g.drawImage(
						frames[(int)frame],
						x1, y1,
						x2, y2,
						0 , 0 ,
						atlas.frame_w,
						atlas.frame_h,
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
					if(frame >= frames.length) stop(frames.length - 1);
					break;
				case LOOP:
					while(frame <  0f           ) frame += frames.length;
					while(frame >= frames.length) frame -= frames.length;
			}
		}
	}
	
	public Sprite paint(Paint paint) {
		return new Sprite(
				atlas,
				paint
				);
	}
	
	public static Sprite load(String name, String path, int frame_w, int frame_h) {
		return new Sprite(Atlas.load(name, path, frame_w, frame_h), null);
	}
	
	public static Sprite fromName(String name, Paint paint) {
		return new Sprite(Atlas.getByName(name), paint);
	}
	
	public static Sprite fromPath(String path, Paint paint) {
		return new Sprite(Atlas.getByPath(path), paint);
	}
	
	public static class Atlas implements Serializable {
		private static final long 
			serialVersionUID = 1L;
		private static final HashMap<String, Atlas>
			NAME_INDEX = new HashMap<String, Atlas>(),
			PATH_INDEX = new HashMap<String, Atlas>();
		
		public final String
			name,
			path;
		public final BufferedImage
			image;
		public final int
			image_w,
			image_h,
			frame_w,
			frame_h;
		
		private final HashMap<Paint, BufferedImage[]>
			cached = new HashMap<Paint, BufferedImage[]>();
		
		public Atlas(
				String name,
				String path,
				BufferedImage image,
				int frame_w,
				int frame_h
				) {			
			this.name = name;
			this.path = path;
			this.image = image;
			this.frame_w = frame_w;
			this.frame_h = frame_h;
			this.image_w = image.getWidth() ;
			this.image_h = image.getHeight();
			
			int
				w = image_w / frame_w,
				h = image_h / frame_h;
			BufferedImage[] frames = new BufferedImage[w * h];
			for(int x = 0; x < w; x ++)
				for(int y = 0; y < h; y ++)
					frames[h * y + x] = image.getSubimage(
							x * frame_w,
							y * frame_h,
							frame_w,
							frame_h
							);
			 
			cached.put(null, frames);
		}
		
		public BufferedImage[] paint(Paint paint) {
			BufferedImage[]
					sprite_frames = cached.get(null ),
					filter_frames = cached.get(paint);
			if(filter_frames == null) {
				filter_frames = new BufferedImage[sprite_frames.length];
				for(int i = 0; i < sprite_frames.length; i ++) {
					int
						frame_w = sprite_frames[i].getWidth() ,
						frame_h = sprite_frames[i].getHeight();
					BufferedImage
						sprite_frame = sprite_frames[i],
						filter_frame = new BufferedImage(
								frame_w,
								frame_h,
								BufferedImage.TYPE_INT_ARGB
								);					
					int[] frame_data = new int[frame_w * frame_h];
					sprite_frame.getRGB(0, 0, frame_w, frame_h, frame_data, 0, frame_w);
					
					paint.filter(frame_data, frame_w, frame_h);
					filter_frame.setRGB(0, 0, frame_w, frame_h, frame_data, 0, frame_w);
					
					filter_frames[i] = filter_frame;
				}
				cached.put(paint, filter_frames);
			}			
			return filter_frames;
		}
		
		public static Atlas getByName(String name) {
			Atlas atlas = NAME_INDEX.get(name);
			if(atlas == null)
				System.err.println("[blue.game.Sprite.Atlas.getByName] An Atlas with name '" + name + "' does not exist.");
			return atlas;
		}
		
		public static Atlas getByPath(String path) {
			Atlas atlas = PATH_INDEX.get(path);
			if(atlas == null)
				System.err.println("[blue.game.Sprite.Atlas.getByPath] An Atlas with path '" + path + "' does not exist.");
			return atlas;
		}
		
		public static Atlas load(String name, String path, int frame_w, int frame_h) {
			if(NAME_INDEX.containsKey(name))
				System.err.println("[blue.game.Sprite.Atlas.load] An Atlas with name '" + name + "' already exists.");
			if(PATH_INDEX.containsKey(path))
				System.err.println("[blue.game.Sprite.Atlas.load] An Atlas with path '" + path + "' already exists.");
			
			try {
				BufferedImage image = ImageIO.read(new File(path));
				Atlas atlas = new Atlas(
					name,
					path,
					image,
					frame_w,
					frame_h
					);				
				NAME_INDEX.put(name, atlas);
				PATH_INDEX.put(path, atlas);				
				return atlas;
				
			} catch(IOException ioe) {
				System.err.println("[blue.game.Sprite.Atlas.load] Failed to load atlas (" + name + ", " + path + ", " + frame_w + ", " + frame_h + ").");
				ioe.printStackTrace();				
				return null;
			}
		}
	}
	
	private static final int
		BLACK = 0xFF000000,
		WHITE = 0xFFFFFFFF;
	
	public static interface Paint {
		public void filter(int[] frame_data, int frame_w, int frame_h);
		
		public static final Paint
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
