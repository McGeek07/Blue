package blue.core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import blue.geom.Box;
import blue.geom.Vector;

public interface Renderable {
	public void onRender(RenderContext context);
	
	public static class RenderContext {
		public Graphics2D
			g;
		public float
			t,
			dt,
			fixed_dt;
		public int
			canvas_w,
			canvas_h;
		
		protected RenderContext() {
			//do nothing
		}
		
		public void stroke(float w) {
			g.setStroke(new BasicStroke(w));
		}		
		
		public void color(Color color) {
			g.setColor(color);
		}
		public void color3i(Vector color) {
			g.setColor(Vector.toColor3i(color));
		}		
		public void color3f(Vector color) {
			g.setColor(Vector.toColor3f(color));
		}
		public void color4i(Vector color) {
			g.setColor(Vector.toColor4i(color));
		}		
		public void color4f(Vector color) {
			g.setColor(Vector.toColor4f(color));
		}
		
		public void rect(float x, float y, float w, float h, boolean fill) {
			if(fill)
				g.fillRect(
						(int)x, (int)y,
						(int)w, (int)h
						);
			else
				g.drawRect(
						(int)x, (int)y,
						(int)w, (int)h
						);
		}		
		public void oval(float x, float y, float w, float h, boolean fill) {
			if(fill)
				g.fillOval(
						(int)x, (int)y,
						(int)w, (int)h
						);
			else
				g.drawOval(
						(int)x, (int)y,
						(int)w, (int)h
						);
		}		
		public void square(float x, float y, float s, boolean fill) {
			float r = s / 2f;
			if(fill)
				g.fillRect(
						(int)(x - r),
						(int)(y - r),
						(int)s,
						(int)s
						);
			else
				g.drawRect(
						(int)(x - r),
						(int)(y - r),
						(int)s,
						(int)s
						);					
		}		
		public void circle(float x, float y, float r, boolean fill) {
			float s = r * 2f;
			if(fill)
				g.fillOval(
						(int)(x - r),
						(int)(y - r),
						(int)s,
						(int)s
						);
			else
				g.drawOval(
						(int)(x - r),
						(int)(y - r),
						(int)s,
						(int)s
						);					
		}
		public void pixel(float x, float y) {
			g.fillRect(
					(int)x,
					(int)y,
					1, 
					1
					);
		}
		
		public void rect(Box<?> box, boolean fill) {
			rect(box.x(), box.y(), box.w(), box.h(), fill);
		}		
		public void oval(Box<?> box, boolean fill) {
			oval(box.x(), box.y(), box.w(), box.h(), fill);
		}		
		public void square(Vector p, float s, boolean fill) {
			square(p.x(), p.y(), s, fill);					
		}		
		public void circle(Vector p, float r, boolean fill) {
			circle(p.x(), p.y(), r, fill);
		}
		public void pixel(Vector p) {
			pixel(p.x(), p.y());
		}
		
		public void translate(float tx, float ty) {
			g.translate(tx, ty);
		}
		public void scale(float sx, float sy) {
			g.scale(sx, sy);
		}		
		
		public void translate(Vector t) {
			translate(t.x(), t.y());
		}
		public void scale(Vector s) {
			scale(s.x(), s.y());
		}
		
		public void rotate(float angle, boolean degrees) {
			if(degrees)
				g.rotate(Math.toRadians(angle));
			else
				g.rotate(               angle );
		}
		
		private final LinkedList<RenderContext>
			stack = new LinkedList<>();
		
		public void push() {
			RenderContext copy = new RenderContext();
			
			stack.push(copy);			
			copy.g  = this.g ;
			copy.t  = this.t ;
			copy.dt = this.dt;
			copy.fixed_dt = this.fixed_dt;
			copy.canvas_w = this.canvas_w;
			copy.canvas_h = this.canvas_h;
			
			this.g = (Graphics2D)copy.g.create();
		}
		
		public void pop()  {
			RenderContext copy = stack.pop();
			
			this.g.dispose();
			this.g  = copy.g ;
			this.t  = copy.t ;
			this.dt = copy.dt;
			this.fixed_dt = copy.fixed_dt;
			this.canvas_w = copy.canvas_w;
			this.canvas_h = copy.canvas_h;
		}
		
		public static enum Angle {
			DEGREES,
			RADIANS
		}
	}
}	