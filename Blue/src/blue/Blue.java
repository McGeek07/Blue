package blue;

import java.awt.Color;

import blue.core.Engine;
import blue.core.Input;
import blue.core.Input.KeyAction;
import blue.core.Node;
import blue.core.Scene;
import blue.geom.Layout;
import blue.util.Version;

public class Blue {
	public static final Version
		VERSION = new Version("Blue", 0, 0, 46);
	
	public static void main(String[] args) {
		System.out.println(VERSION);
		
		Engine.configure(
				Engine.DEBUG        , true,
				Engine.WINDOW_BORDER, true
				);
		Engine.init();
		
		Input.attach(KeyAction.class, (event) -> {
			if(event.isDn() && event.isKey(Input.KEY_ESCAPE))
				Engine.exit();
		});
		
		Scene scene = new Scene() {
			@Override
			public void onAttach() {				
				layout(Layout.DEFAULT);
			}
			
			@Override
			public void onRender(RenderContext context) {
				context.color(Color.RED);
				context.rect(bounds, true);
			}
			
			@Override
			public void onUpdate(UpdateContext context) {
				bounds.dim().x(bounds.w() - 128 * context.dt);
				resize();
			}
		};
		
		scene.add(new Node() {
			@Override
			public void onAttach() {
				layout(Layout.MM_NINTH);
			}
			
			@Override
			public void onRender(RenderContext context) {
				context.color(Color.GREEN);
				context.rect(bounds, true);
			}
		});
		scene.add(new Node() {
			@Override
			public void onAttach() {
				layout(Layout.TM_NINTH);
			}
			
			@Override
			public void onRender(RenderContext context) {
				context.color(Color.BLUE);
				context.rect(bounds, true);
			}
		});
		scene.add(new Node() {
			@Override
			public void onAttach() {
				layout(Layout.BM_NINTH);
			}
			
			@Override
			public void onRender(RenderContext context) {
				context.color(Color.ORANGE);
				context.rect(bounds, true);
			}
		});
		
		Engine.setScene(scene);
	}
}
