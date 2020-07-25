package blue.core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import blue.math.Box;
import blue.math.Vector;

public interface Renderable {
	public void render(RenderContext context);
	
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
		
		public void line(float x1, float y1, float x2, float y2) {
			g.drawLine(
				(int)x1,
				(int)y1,
				(int)x2,
				(int)y2
			);
		}		
		
		public void rect(Box<?> box, boolean fill) {
			rect(box.x(), box.y(), box.w(), box.h(), fill);
		}	
		
		public void oval(Box<?> box, boolean fill) {
			oval(box.x(), box.y(), box.w(), box.h(), fill);
		}	
		
		public void square(Vector v, float s, boolean fill) {
			square(v.x(), v.y(), s, fill);					
		}	
		
		public void circle(Vector v, float r, boolean fill) {
			circle(v.x(), v.y(), r, fill);
		}
		
		public void pixel(Vector v) {
			pixel(v.x(), v.y());
		}
		
		public void line(Vector v0, Vector v1) {
			line(v0.x(), v0.y(), v1.x(), v1.y());
		}
		
		public void translate(float tx, float ty) {
			g.translate(tx, ty);
		}
		
		public void scale(float sx, float sy) {
			g.scale(sx, sy);
		}	
		
		public void rotate(float angle, boolean degrees) {
			if(degrees)
				g.rotate(Math.toRadians(angle));
			else
				g.rotate(               angle );
		}
		
		public void translate(Vector t) {
			translate(t.x(), t.y());
		}
		
		public void scale(Vector s) {
			scale(s.x(), s.y());
		}
		
		public void clip(float w, float h) {
			g.clipRect(
				0,
				0,
				(int)w,
				(int)h
			);
		}
		
		public void clip(float x, float y, float w, float h) {
			g.clipRect(
				(int)x,
				(int)y,
				(int)w,
				(int)h
			);
		}

		public void clip(Box<?> b) {
			clip(b.x(), b.y(), b.w(), b.h());
		}
		
		public void clip(Vector v) {
			clip(v.x(), v.y());
		}
		
		public void string(String s, float x, float y) {
			g.drawString(s, x, y);
		}	
		
		public void string(String s, Vector p) {
			string(s, p.x(), p.y());
		}
		
		public void string(String s) {
			string(s, 0f, 0f);
		}
		
		public void font(Font font) {
			g.setFont(font);
		}
		
		public void font(
			String font_name, 
			int    font_type,
			int    font_size
		) {
			font(new Font(font_name, font_type, font_size));
		}		
		
		private RenderContext 
			root;
		
		public RenderContext push() {
			RenderContext copy = new RenderContext();			

			copy.root = this ;
			copy.g  = this.g ;
			copy.t  = this.t ;
			copy.dt = this.dt;
			copy.fixed_dt = this.fixed_dt;
			copy.canvas_w = this.canvas_w;
			copy.canvas_h = this.canvas_h;
			
			copy.g = (Graphics2D)this.g.create();
			
			return copy;
		}
		
		public RenderContext pop()  {
			if(root != null)
				try {
					g.dispose();
					return root;
				} finally {
					root = null;
				}
			return this;
		}
	}
}	