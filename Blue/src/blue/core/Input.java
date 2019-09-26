package blue.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import blue.geom.Vector2f;

public final class Input implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener {
	protected static final Input
		INSTANCE = new Input();
	public static final byte
		UP = 0, UP_ACTION = 1,
		DN = 2, DN_ACTION = 3;	
	public static final int
		NUM_KEYS = 65536,
		NUM_BTNS = 16;

	protected final Vector2f.Mutable
		mouse_buffer = new Vector2f.Mutable(),
		mouse        = new Vector2f.Mutable();
	protected float
		wheel_buffer = 0f,
		wheel        = 0f;
	protected final boolean[]
		key_buffer = new boolean[NUM_KEYS],
		btn_buffer = new boolean[NUM_BTNS];
	protected final byte[]
		keys = new byte[NUM_KEYS],
		btns = new byte[NUM_BTNS];	
	
	private Input() {
		//do nothing
	}
	
	public static void poll() {
		INSTANCE.pollMouse();
		INSTANCE.pollWheel();
		INSTANCE.pollKeys();
		INSTANCE.pollBtns();
	}
	
	public void pollMouse() {
		if(!mouse_buffer.equals(mouse)) {
			mouse.set(mouse_buffer);
			//event
			Engine.INSTANCE.onMouseMoved(mouse);
		}
	}
	
	public void pollWheel() {
		if(
				wheel_buffer != 0f &&
				wheel_buffer != wheel
				) {
			wheel = wheel_buffer;
			wheel_buffer = 0f;
			//event
			Engine.INSTANCE.onWheelMoved(wheel);
		}
	}
	
	public void pollKeys() {
		for(int i = 0; i < NUM_KEYS; i ++) {
			if(key_buffer[i])
				switch(keys[i]) {
				case Input.UP: case Input.UP_ACTION:
					keys[i] = Input.DN_ACTION;
					//event
					Engine.INSTANCE.onKeyDnAction(i);
					break;
				case Input.DN: case Input.DN_ACTION:
					keys[i] = Input.DN;
					break;
				}
			else
				switch(keys[i]) {
				case Input.UP: case Input.UP_ACTION:
					keys[i] = Input.UP_ACTION;
					//event
					Engine.INSTANCE.onKeyUpAction(i);
					break;
				case Input.DN: case Input.DN_ACTION:
					keys[i] = Input.DN;
					break;
			}
		}
	}
	
	public void pollBtns() {
		for(int i = 0; i < NUM_BTNS; i ++) {
			if(btn_buffer[i])
				switch(btns[i]) {
				case Input.UP: case Input.UP_ACTION:
					btns[i] = Input.DN_ACTION;
					//event
					Engine.INSTANCE.onBtnDnAction(i);
					break;
				case Input.DN: case Input.DN_ACTION:
					btns[i] = Input.DN;
					break;
				}
			else
				switch(btns[i]) {
				case Input.UP: case Input.UP_ACTION:
					btns[i] = Input.UP_ACTION;
					//event
					Engine.INSTANCE.onBtnUpAction(i);
					break;
				case Input.DN: case Input.DN_ACTION:
					btns[i] = Input.DN;
					break;
			}
		}
	}


	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key > 0 && key < NUM_KEYS)
			key_buffer[key] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key > 0 && key < NUM_KEYS)
			key_buffer[key] = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//do nothing
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int btn = e.getButton();
		if(btn > 0 && btn < NUM_BTNS)
			btn_buffer[btn] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int btn = e.getButton();
		if(btn > 0 && btn < NUM_BTNS)
			btn_buffer[btn] = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//do nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//do nothing
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		wheel_buffer = e.getWheelRotation();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouse_buffer.set(
				e.getX(),
				e.getY()
				);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouse_buffer.set(
				e.getX(),
				e.getY()
				);
	}
	
	public static Vector2f getMouse() {
		return INSTANCE.mouse;
	}
	
	public static float getWheel() {
		return INSTANCE.wheel;
	}
	
	public static boolean isWheelUp() {
		return getWheel() < 0;
	}
	
	public static boolean isWheelDn() {
		return getWheel() > 0;
	}	
	
	public static byte getKey(int key) {
		return key > 0 && key < NUM_KEYS ? INSTANCE.keys[key] : 0;
	}
	
	public static boolean isKeyDn(int key) {
		byte k = getKey(key);
		return
				k == Input.DN ||
				k == Input.DN_ACTION;
	}
	
	public static boolean isKeyDnAction(int key) {
		byte k = getKey(key);
		return 
				k == Input.DN_ACTION;
	}
	
	public static boolean isKeyUp(int key) {
		byte b = getKey(key);
		return
				b == Input.UP ||
				b == Input.UP_ACTION;
	}
	
	public static boolean isKeyUpAction(int key) {
		byte b = getKey(key);
		return 
				b == Input.UP_ACTION;
	}
	
	public static byte getBtn(int btn) {
		return btn > 0 && btn < NUM_BTNS ? INSTANCE.btns[btn] : 0;
	}
	
	public static boolean isBtnDn(int btn) {
		byte b = getBtn(btn);
		return
				b == Input.DN ||
				b == Input.DN_ACTION;
	}
	
	public static boolean isBtnDnAction(int btn) {
		byte b = getBtn(btn);
		return 
				b == Input.DN_ACTION;
	}
	
	public static boolean isBtnUp(int btn) {
		byte b = getBtn(btn);
		return
				b == Input.UP ||
				b == Input.UP_ACTION;
	}
	
	public static boolean isBtnUpAction(int btn) {
		byte b = getBtn(btn);
		return 
				b == Input.UP_ACTION;
	}	
}
