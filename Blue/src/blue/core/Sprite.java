package blue.core;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

import blue.math.Box;
import blue.math.Region2;
import blue.math.Vector;
import blue.util.Util;

public class Sprite implements Renderable, Updateable {
	public static final int
		STOP = 0,
		PLAY = 1,
		LOOP = 2;
	
	protected Source
		source;
	protected Filter
		filter;
	
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
			Source source,
			Filter filter
			) {
		this.source = source;
		this.filter = filter;
		
		this.bounds.set(
				0, 0,
				source.frame_w,
				source.frame_h
				);
		
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
	
	public void align(Box<?> b) {
		Box.m_align(bounds, b);
	}
	
	public void align(Vector v) {
		Box.m_align(bounds, v);
	}
	
	public void align(float x, float y) {
		Box.m_align(bounds, x, y);
	}
	
	public Sprite step(float dt) {
		if(mode > 0) {
			frame += speed * dt;
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
		return this;
	}

	@Override
	public void render(RenderContext context) {		
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
				context = context.push();
				
				if(alpha_composite == null)
					alpha_composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
				
				context.g.setComposite(alpha_composite);
				context.g.drawImage(
						frames[(int)frame],
						x1, y1,
						x2, y2,
						0 , 0 ,
						source.frame_w,
						source.frame_h,
						null
						);
				
				context = context.pop();
			} else
				context.g.drawImage(
						frames[(int)frame],
						x1, y1,
						x2, y2,
						0 , 0 ,
						source.frame_w,
						source.frame_h,
						null
						);
		}
	}

	@Override
	public void update(UpdateContext context) {
		step(context.fixed_dt);
	}
	
	public static Sprite load(String name, String path, int frame_w, int frame_h) {
		return new Sprite(Source.load(name, path, frame_w, frame_h), null);
	}
	
	public static Sprite fromName(String name, Filter filter) {
		return new Sprite(Source.getByName(name), filter);
	}
	
	public static Sprite fromPath(String path, Filter filter) {
		return new Sprite(Source.getByPath(path), filter);
	}
	
	public static class Source {
		private static final HashMap<String, Source>
			NAME_INDEX = new HashMap<String, Source>(),
			PATH_INDEX = new HashMap<String, Source>();
		
		private final HashMap<Sprite.Filter, BufferedImage[]>
			cache = new HashMap<Sprite.Filter, BufferedImage[]>();
		
		public final String
			name,
			path;
		public final BufferedImage
			atlas;
		public final int
			atlas_w,
			atlas_h,
			frame_w,
			frame_h,
			length;
		
		public Source(
				String name,
				String path,
				BufferedImage atlas,
				int frame_w,
				int frame_h
				) {			
			this.name = name;
			this.path = path;
			this.atlas = atlas;
			this.frame_w = frame_w;
			this.frame_h = frame_h;
			this.atlas_w = atlas.getWidth() ;
			this.atlas_h = atlas.getHeight();
			this.length = (atlas_w / frame_w) * (atlas_h / frame_h);
			
			int
				w = atlas_w / frame_w,
				h = atlas_h / frame_h;
			BufferedImage[] frames = new BufferedImage[length];
			for(int x = 0; x < w; x ++)
				for(int y = 0; y < h; y ++)
					frames[h * y + x] = atlas.getSubimage(
							x * frame_w,
							y * frame_h,
							frame_w,
							frame_h
							);
			cache.put(null, frames);
		}
		
		public BufferedImage[] filter(Filter filter) {
			BufferedImage[]
					source_frames = cache.get(null  ),
					filter_frames = cache.get(filter);
			if(filter_frames == null) {
				filter_frames = new BufferedImage[source_frames.length];
				for(int i = 0; i < source_frames.length; i ++) {
					int
						frame_w = source_frames[i].getWidth() ,
						frame_h = source_frames[i].getHeight();
					BufferedImage
						source_frame = source_frames[i],
						buffer_frame = Util.createBufferedImage(0, frame_w, frame_h),
						filter_frame = Util.createBufferedImage(0, frame_w, frame_h);			
					
					Graphics2D g0 = buffer_frame.createGraphics();
					g0.drawImage(source_frame, 0, 0, null);
					g0.dispose();
					
					filter.filter(
							((DataBufferInt)buffer_frame.getRaster().getDataBuffer()).getData(),
							frame_w,
							frame_h
							);
					
					Graphics2D g1 = filter_frame.createGraphics();
					g1.drawImage(buffer_frame, 0, 0, null);
					g1.dispose();
					
					filter_frames[i] = filter_frame;					
				}
				cache.put(filter, filter_frames);
			}			
			return filter_frames;
		}
		
		public static Source getByName(String name) {
			Source source = NAME_INDEX.get(name);
			if(source == null)
				System.err.println("[blue.core.Sprite.Source.getByName] A Sprite.Source with name '" + name + "' does not exist.");
			return source;
		}
		
		public static Source getByPath(String path) {
			Source source = PATH_INDEX.get(path);
			if(source == null)
				System.err.println("[blue.core.Sprite.Source.getByPath] A Sprite.Source with path '" + path + "' does not exist.");
			return source;
		}
		
		public static Source load(String name, String path, int frame_w, int frame_h) {
			if(NAME_INDEX.containsKey(name))
				System.err.println("[blue.core.Sprite.Source.load] A Sprite.Source with name '" + name + "' already exists.");
			if(PATH_INDEX.containsKey(path))
				System.err.println("[blue.core.Sprite.Source.load] A Sprite.Source with path '" + path + "' already exists.");
			
			try {
				BufferedImage 
					image = ImageIO.read(new File(path));
				
				Source source = new Source(
					name,
					path,
					image,
					frame_w,
					frame_h
					);				
				NAME_INDEX.put(name, source);
				PATH_INDEX.put(path, source);
				return source;
				
			} catch(Exception e) {
				System.err.println("[blue.core.Sprite.Source.load] Failed to load Sprite.Source (" + name + ", " + path + ", " + frame_w + ", " + frame_h + ").");
				e.printStackTrace();				
				return null;
			}
		}		
	}
	
	public static interface Filter {
		public void filter(int[] source, int source_w, int source_h);	
	}
	
	public static final Filter
		BLACK = (source, source_w, source_h) -> {
			for(int i = 0; i < source.length; i ++)
				source[i] &= 0xFF000000;
		},
		WHITE = (source, source_w, source_h) -> {
			for(int i = 0; i < source.length; i ++)
				source[i] |= 0x00FFFFFF;
		},
		RED   = (source, source_w, source_h) -> {
			for(int i = 0; i < source.length; i ++)
				source[i] |= 0x00FF0000;
		},
		GREEN = (source, source_w, source_h) -> {
			for(int i = 0; i < source.length; i ++)
				source[i] |= 0x0000FF00;
		},
		BLUE  = (source, source_w, source_h) -> {
			for(int i = 0; i < source.length; i ++)
				source[i] |= 0x000000FF;
		},
		MAGENTA = (source, source_w, source_h) -> {
			for(int i = 0; i < source.length; i ++)
				source[i] |= 0x00FF00FF;
		},
		CYAN    = (source, source_w, source_h) -> {
			for(int i = 0; i < source.length; i ++)
				source[i] |= 0x0000FFFF;
		},
		YELLOW  = (source, source_w, source_h) -> {
			for(int i = 0; i < source.length; i ++)
				source[i] |= 0x00FFFF00;
		};
}
