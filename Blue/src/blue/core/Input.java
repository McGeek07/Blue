package blue.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import blue.core.Event.Broker;
import blue.core.Event.Handle;
import blue.core.Event.Listener;
import blue.geom.Vector2;

public final class Input implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener {
	protected static final Input
		INSTANCE = new Input();
	public static final Class<KeyAction>
		KEY_ACTION = KeyAction.class;
	public static final Class<BtnAction>
		BTN_ACTION = BtnAction.class;
	public static final byte
		UP = 0, UP_ACTION = 1,
		DN = 2, DN_ACTION = 3;	
	public static final int
		NUM_KEYS = 128,
		NUM_BTNS = 25;
	
	protected final Handle
		handle;
	protected final Broker
		broker;

	protected final Vector2.Mutable
		mouse_buffer = new Vector2.Mutable(),
		mouse        = new Vector2.Mutable();
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
		this.handle = new Handle();
		this.broker = new Broker();
		this.broker.add(this.handle);
		
		Event.attach(this.broker);
	}
	
	public static <T extends Action> void attach(Class<T> type, Listener<T> listener) {
		INSTANCE.handle.attach(type, listener);
	}
	
	public static <T extends Action> void detach(Class<T> type, Listener<T> listener) {
		INSTANCE.handle.detach(type, listener);
	}
	
	public static void attach(Handle handle) {
		INSTANCE.broker.attach(handle);
	}
	
	public static void detach(Handle handle) {
		INSTANCE.broker.detach(handle);
	}
	
	public static void attach(Broker broker) {
		INSTANCE.broker.attach(broker);
	}
	
	public static void detach(Broker broker) {
		INSTANCE.broker.detach(broker);
	}
	
	public static <T extends Action> void queue(T action) {
		INSTANCE.broker.queue(action);
	}
	
	public static <T extends Action> void flush(T action) {
		INSTANCE.broker.flush(action);
	}
	
	public static void poll() {
		INSTANCE.onPollMouse();
		INSTANCE.onPollWheel();
		INSTANCE.onPollKeys();
		INSTANCE.onPollBtns();
	}
	
	public void onPollMouse() {
		if(!mouse_buffer.equals(mouse)) {
			mouse.set(mouse_buffer);
			//event
			Engine.mouseMoved(getMouse());
		}
	}
	
	public void onPollWheel() {
		if(
				wheel_buffer != 0f &&
				wheel_buffer != wheel
				) {
			wheel = wheel_buffer;
			wheel_buffer = 0f;
			//event
			Engine.wheelMoved(getWheel());
		}
	}
	
	public void onPollKeys() {
		for(int i = 0; i < NUM_KEYS; i ++) {
			if(key_buffer[i])
				switch(keys[i]) {
				case Input.UP: case Input.UP_ACTION:
					keys[i] = Input.DN_ACTION;
					//event
					queue(new KeyAction(DN_ACTION, i));
					Engine.keyDn(i);
					break;
				case Input.DN: case Input.DN_ACTION:
					keys[i] = Input.DN;
					break;
				}
			else
				switch(keys[i]) {
				case Input.UP: case Input.UP_ACTION:
					keys[i] = Input.UP;			
					break;
				case Input.DN: case Input.DN_ACTION:
					keys[i] = Input.UP_ACTION;
					//event
					queue(new KeyAction(UP_ACTION, i));
					Engine.keyUp(i);
					break;
			}
		}
	}
	
	public void onPollBtns() {
		for(int i = 0; i < NUM_BTNS; i ++) {
			if(btn_buffer[i])
				switch(btns[i]) {
				case Input.UP: case Input.UP_ACTION:
					btns[i] = Input.DN_ACTION;
					//event
					queue(new BtnAction(DN_ACTION, i));
					Engine.btnDn(i);
					break;
				case Input.DN: case Input.DN_ACTION:
					btns[i] = Input.DN;
					break;
				}
			else
				switch(btns[i]) {
				case Input.UP: case Input.UP_ACTION:
					btns[i] = Input.UP;
					break;
				case Input.DN: case Input.DN_ACTION:
					btns[i] = Input.UP_ACTION;
					//event
					queue(new BtnAction(UP_ACTION, i));
					Engine.btnUp(i);
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
		switch(key) {
			case KeyEvent.VK_ESCAPE: key_buffer[KEY_ESCAPE] = true; break;	
			
			case KeyEvent.VK_F1 : key_buffer[KEY_F1 ] = true; break;	
			case KeyEvent.VK_F2 : key_buffer[KEY_F2 ] = true; break;	
			case KeyEvent.VK_F3 : key_buffer[KEY_F3 ] = true; break;	
			case KeyEvent.VK_F4 : key_buffer[KEY_F4 ] = true; break;	
			case KeyEvent.VK_F5 : key_buffer[KEY_F5 ] = true; break;
			case KeyEvent.VK_F6 : key_buffer[KEY_F6 ] = true; break;
			case KeyEvent.VK_F7 : key_buffer[KEY_F7 ] = true; break;
			case KeyEvent.VK_F8 : key_buffer[KEY_F8 ] = true; break;
			case KeyEvent.VK_F9 : key_buffer[KEY_F9 ] = true; break;
			case KeyEvent.VK_F10: key_buffer[KEY_F10] = true; break;
			case KeyEvent.VK_F11: key_buffer[KEY_F11] = true; break;
			case KeyEvent.VK_F12: key_buffer[KEY_F12] = true; break;				
					
			case KeyEvent.VK_INSERT   : key_buffer[KEY_INSERT ] = true; break;
			case KeyEvent.VK_DELETE   : key_buffer[KEY_DELETE ] = true; break;
			case KeyEvent.VK_PAGE_UP  : key_buffer[KEY_PAGE_UP] = true; break;
			case KeyEvent.VK_PAGE_DOWN: key_buffer[KEY_PAGE_DN] = true; break;
			
			case KeyEvent.VK_LEFT : key_buffer[KEY_L_ARROW ] = true; break;
			case KeyEvent.VK_UP   : key_buffer[KEY_UP_ARROW] = true; break;
			case KeyEvent.VK_RIGHT: key_buffer[KEY_R_ARROW ] = true; break;
			case KeyEvent.VK_DOWN : key_buffer[KEY_DN_ARROW] = true; break;
			
			case KeyEvent.VK_TAB       : key_buffer[KEY_TAB   ] = true; break;
			case KeyEvent.VK_BACK_SPACE: key_buffer[KEY_BSPACE] = true; break;				
			case KeyEvent.VK_ENTER     : key_buffer[KEY_ENTER] = true; break;
			
			case KeyEvent.VK_SPACE   : key_buffer[KEY_SPACE ] = true; break;
			case KeyEvent.VK_QUOTE   : key_buffer[KEY_QUOTE ] = true; break;
			case KeyEvent.VK_MULTIPLY: key_buffer[KEY_STAR  ] = true; break;
			case KeyEvent.VK_PLUS    : key_buffer[KEY_PLUS  ] = true; break;
			case KeyEvent.VK_COMMA   : key_buffer[KEY_COMMA ] = true; break;
			case KeyEvent.VK_MINUS   : key_buffer[KEY_MINUS ] = true; break;
			case KeyEvent.VK_PERIOD  : key_buffer[KEY_PERIOD] = true; break;
			case KeyEvent.VK_SLASH   : key_buffer[KEY_SLASH ] = true; break;
		
			case KeyEvent.VK_0: key_buffer[KEY_0] = true; break;
			case KeyEvent.VK_1: key_buffer[KEY_1] = true; break;
			case KeyEvent.VK_2: key_buffer[KEY_2] = true; break;
			case KeyEvent.VK_3: key_buffer[KEY_3] = true; break;
			case KeyEvent.VK_4: key_buffer[KEY_4] = true; break;
			case KeyEvent.VK_5: key_buffer[KEY_5] = true; break;
			case KeyEvent.VK_6: key_buffer[KEY_6] = true; break;
			case KeyEvent.VK_7: key_buffer[KEY_7] = true; break;
			case KeyEvent.VK_8: key_buffer[KEY_8] = true; break;
			case KeyEvent.VK_9: key_buffer[KEY_9] = true; break;
			
			case KeyEvent.VK_SEMICOLON: key_buffer[KEY_SEMICOLON] = true; break;
			case KeyEvent.VK_EQUALS   : key_buffer[KEY_EQUALS   ] = true; break;
			
			case KeyEvent.VK_A: key_buffer[KEY_A] = true; break;
			case KeyEvent.VK_B: key_buffer[KEY_B] = true; break;
			case KeyEvent.VK_C: key_buffer[KEY_C] = true; break;
			case KeyEvent.VK_D: key_buffer[KEY_D] = true; break;
			case KeyEvent.VK_E: key_buffer[KEY_E] = true; break;
			case KeyEvent.VK_F: key_buffer[KEY_F] = true; break;
			case KeyEvent.VK_G: key_buffer[KEY_G] = true; break;
			case KeyEvent.VK_H: key_buffer[KEY_H] = true; break;
			case KeyEvent.VK_I: key_buffer[KEY_I] = true; break;
			case KeyEvent.VK_J: key_buffer[KEY_J] = true; break;
			case KeyEvent.VK_K: key_buffer[KEY_K] = true; break;
			case KeyEvent.VK_L: key_buffer[KEY_L] = true; break;
			case KeyEvent.VK_M: key_buffer[KEY_M] = true; break;
			case KeyEvent.VK_N: key_buffer[KEY_N] = true; break;
			case KeyEvent.VK_O: key_buffer[KEY_O] = true; break;
			case KeyEvent.VK_P: key_buffer[KEY_P] = true; break;
			case KeyEvent.VK_Q: key_buffer[KEY_Q] = true; break;
			case KeyEvent.VK_R: key_buffer[KEY_R] = true; break;
			case KeyEvent.VK_S: key_buffer[KEY_S] = true; break;
			case KeyEvent.VK_T: key_buffer[KEY_T] = true; break;
			case KeyEvent.VK_U: key_buffer[KEY_U] = true; break;
			case KeyEvent.VK_V: key_buffer[KEY_V] = true; break;
			case KeyEvent.VK_W: key_buffer[KEY_W] = true; break;
			case KeyEvent.VK_X: key_buffer[KEY_X] = true; break;
			case KeyEvent.VK_Y: key_buffer[KEY_Y] = true; break;
			case KeyEvent.VK_Z: key_buffer[KEY_Z] = true; break;
			
			case KeyEvent.VK_OPEN_BRACKET : key_buffer[KEY_L_BRACKET] = true; break; 
			case KeyEvent.VK_BACK_SLASH   : key_buffer[KEY_BSLASH   ] = true; break; 
			case KeyEvent.VK_CLOSE_BRACKET: key_buffer[KEY_R_BRACKET] = true; break; 
			case KeyEvent.VK_BACK_QUOTE   : key_buffer[KEY_BQUOTE   ] = true; break;
					
			case KeyEvent.VK_NUMPAD0: key_buffer[KEY_NUMPAD_0] = true; break;
			case KeyEvent.VK_NUMPAD1: key_buffer[KEY_NUMPAD_1] = true; break;
			case KeyEvent.VK_NUMPAD2: key_buffer[KEY_NUMPAD_2] = true; break;
			case KeyEvent.VK_NUMPAD3: key_buffer[KEY_NUMPAD_3] = true; break;
			case KeyEvent.VK_NUMPAD4: key_buffer[KEY_NUMPAD_4] = true; break;
			case KeyEvent.VK_NUMPAD5: key_buffer[KEY_NUMPAD_5] = true; break;
			case KeyEvent.VK_NUMPAD6: key_buffer[KEY_NUMPAD_6] = true; break;
			case KeyEvent.VK_NUMPAD7: key_buffer[KEY_NUMPAD_7] = true; break;
			case KeyEvent.VK_NUMPAD8: key_buffer[KEY_NUMPAD_8] = true; break;
			case KeyEvent.VK_NUMPAD9: key_buffer[KEY_NUMPAD_9] = true; break;
			
			case KeyEvent.VK_SHIFT: 
				if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT ) key_buffer[KEY_L_SHIFT] = true;
				if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT) key_buffer[KEY_R_SHIFT] = true;
				break;
			case KeyEvent.VK_CONTROL:
				if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT ) key_buffer[KEY_L_CTRL] = true;
				if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT) key_buffer[KEY_R_CTRL] = true;
				break;
			case KeyEvent.VK_ALT:
				if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT ) key_buffer[KEY_L_ALT] = true;
				if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT) key_buffer[KEY_R_ALT] = true;
				break;	
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
			case KeyEvent.VK_ESCAPE: key_buffer[KEY_ESCAPE] = false; break;		
			
			case KeyEvent.VK_F1 : key_buffer[KEY_F1 ] = false; break;	
			case KeyEvent.VK_F2 : key_buffer[KEY_F2 ] = false; break;	
			case KeyEvent.VK_F3 : key_buffer[KEY_F3 ] = false; break;	
			case KeyEvent.VK_F4 : key_buffer[KEY_F4 ] = false; break;	
			case KeyEvent.VK_F5 : key_buffer[KEY_F5 ] = false; break;
			case KeyEvent.VK_F6 : key_buffer[KEY_F6 ] = false; break;
			case KeyEvent.VK_F7 : key_buffer[KEY_F7 ] = false; break;
			case KeyEvent.VK_F8 : key_buffer[KEY_F8 ] = false; break;
			case KeyEvent.VK_F9 : key_buffer[KEY_F9 ] = false; break;
			case KeyEvent.VK_F10: key_buffer[KEY_F10] = false; break;
			case KeyEvent.VK_F11: key_buffer[KEY_F11] = false; break;
			case KeyEvent.VK_F12: key_buffer[KEY_F12] = false; break;				
					
			case KeyEvent.VK_INSERT   : key_buffer[KEY_INSERT ] = false; break;
			case KeyEvent.VK_DELETE   : key_buffer[KEY_DELETE ] = false; break;
			case KeyEvent.VK_PAGE_UP  : key_buffer[KEY_PAGE_UP] = false; break;
			case KeyEvent.VK_PAGE_DOWN: key_buffer[KEY_PAGE_DN] = false; break;
			
			case KeyEvent.VK_LEFT : key_buffer[KEY_L_ARROW ] = false; break;
			case KeyEvent.VK_UP   : key_buffer[KEY_UP_ARROW] = false; break;
			case KeyEvent.VK_RIGHT: key_buffer[KEY_R_ARROW ] = false; break;
			case KeyEvent.VK_DOWN : key_buffer[KEY_DN_ARROW] = false; break;
			
			case KeyEvent.VK_TAB       : key_buffer[KEY_TAB   ] = false; break;
			case KeyEvent.VK_BACK_SPACE: key_buffer[KEY_BSPACE] = false; break;				
			case KeyEvent.VK_ENTER     : key_buffer[KEY_ENTER] = false; break;
			
			case KeyEvent.VK_SPACE   : key_buffer[KEY_SPACE ] = false; break;
			case KeyEvent.VK_QUOTE   : key_buffer[KEY_QUOTE ] = false; break;
			case KeyEvent.VK_MULTIPLY: key_buffer[KEY_STAR  ] = false; break;
			case KeyEvent.VK_PLUS    : key_buffer[KEY_PLUS  ] = false; break;
			case KeyEvent.VK_COMMA   : key_buffer[KEY_COMMA ] = false; break;
			case KeyEvent.VK_MINUS   : key_buffer[KEY_MINUS ] = false; break;
			case KeyEvent.VK_PERIOD  : key_buffer[KEY_PERIOD] = false; break;
			case KeyEvent.VK_SLASH   : key_buffer[KEY_SLASH ] = false; break;
		
			case KeyEvent.VK_0: key_buffer[KEY_0] = false; break;
			case KeyEvent.VK_1: key_buffer[KEY_1] = false; break;
			case KeyEvent.VK_2: key_buffer[KEY_2] = false; break;
			case KeyEvent.VK_3: key_buffer[KEY_3] = false; break;
			case KeyEvent.VK_4: key_buffer[KEY_4] = false; break;
			case KeyEvent.VK_5: key_buffer[KEY_5] = false; break;
			case KeyEvent.VK_6: key_buffer[KEY_6] = false; break;
			case KeyEvent.VK_7: key_buffer[KEY_7] = false; break;
			case KeyEvent.VK_8: key_buffer[KEY_8] = false; break;
			case KeyEvent.VK_9: key_buffer[KEY_9] = false; break;
			
			case KeyEvent.VK_SEMICOLON: key_buffer[KEY_SEMICOLON] = false; break;
			case KeyEvent.VK_EQUALS   : key_buffer[KEY_EQUALS   ] = false; break;
			
			case KeyEvent.VK_A: key_buffer[KEY_A] = false; break;
			case KeyEvent.VK_B: key_buffer[KEY_B] = false; break;
			case KeyEvent.VK_C: key_buffer[KEY_C] = false; break;
			case KeyEvent.VK_D: key_buffer[KEY_D] = false; break;
			case KeyEvent.VK_E: key_buffer[KEY_E] = false; break;
			case KeyEvent.VK_F: key_buffer[KEY_F] = false; break;
			case KeyEvent.VK_G: key_buffer[KEY_G] = false; break;
			case KeyEvent.VK_H: key_buffer[KEY_H] = false; break;
			case KeyEvent.VK_I: key_buffer[KEY_I] = false; break;
			case KeyEvent.VK_J: key_buffer[KEY_J] = false; break;
			case KeyEvent.VK_K: key_buffer[KEY_K] = false; break;
			case KeyEvent.VK_L: key_buffer[KEY_L] = false; break;
			case KeyEvent.VK_M: key_buffer[KEY_M] = false; break;
			case KeyEvent.VK_N: key_buffer[KEY_N] = false; break;
			case KeyEvent.VK_O: key_buffer[KEY_O] = false; break;
			case KeyEvent.VK_P: key_buffer[KEY_P] = false; break;
			case KeyEvent.VK_Q: key_buffer[KEY_Q] = false; break;
			case KeyEvent.VK_R: key_buffer[KEY_R] = false; break;
			case KeyEvent.VK_S: key_buffer[KEY_S] = false; break;
			case KeyEvent.VK_T: key_buffer[KEY_T] = false; break;
			case KeyEvent.VK_U: key_buffer[KEY_U] = false; break;
			case KeyEvent.VK_V: key_buffer[KEY_V] = false; break;
			case KeyEvent.VK_W: key_buffer[KEY_W] = false; break;
			case KeyEvent.VK_X: key_buffer[KEY_X] = false; break;
			case KeyEvent.VK_Y: key_buffer[KEY_Y] = false; break;
			case KeyEvent.VK_Z: key_buffer[KEY_Z] = false; break;
			
			case KeyEvent.VK_OPEN_BRACKET : key_buffer[KEY_L_BRACKET] = false; break; 
			case KeyEvent.VK_BACK_SLASH   : key_buffer[KEY_BSLASH   ] = false; break; 
			case KeyEvent.VK_CLOSE_BRACKET: key_buffer[KEY_R_BRACKET] = false; break; 
			case KeyEvent.VK_BACK_QUOTE   : key_buffer[KEY_BQUOTE   ] = false; break;
					
			case KeyEvent.VK_NUMPAD0: key_buffer[KEY_NUMPAD_0] = false; break;
			case KeyEvent.VK_NUMPAD1: key_buffer[KEY_NUMPAD_1] = false; break;
			case KeyEvent.VK_NUMPAD2: key_buffer[KEY_NUMPAD_2] = false; break;
			case KeyEvent.VK_NUMPAD3: key_buffer[KEY_NUMPAD_3] = false; break;
			case KeyEvent.VK_NUMPAD4: key_buffer[KEY_NUMPAD_4] = false; break;
			case KeyEvent.VK_NUMPAD5: key_buffer[KEY_NUMPAD_5] = false; break;
			case KeyEvent.VK_NUMPAD6: key_buffer[KEY_NUMPAD_6] = false; break;
			case KeyEvent.VK_NUMPAD7: key_buffer[KEY_NUMPAD_7] = false; break;
			case KeyEvent.VK_NUMPAD8: key_buffer[KEY_NUMPAD_8] = false; break;
			case KeyEvent.VK_NUMPAD9: key_buffer[KEY_NUMPAD_9] = false; break;
			
			case KeyEvent.VK_SHIFT: 
				if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT ) key_buffer[KEY_L_SHIFT] = false;
				if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT) key_buffer[KEY_R_SHIFT] = false;
				break;
			case KeyEvent.VK_CONTROL:
				if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT ) key_buffer[KEY_L_CTRL] = false;
				if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT) key_buffer[KEY_R_CTRL] = false;
				break;
			case KeyEvent.VK_ALT:
				if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_LEFT ) key_buffer[KEY_L_ALT] = false;
				if(e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT) key_buffer[KEY_R_ALT] = false;
				break;
		}		
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
		wheel_buffer += e.getWheelRotation();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouse_buffer.set(Engine.windowToCanvas(
				e.getX(),
				e.getY()
				));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouse_buffer.set(Engine.windowToCanvas(
				e.getX(),
				e.getY()
				));
	}
	
	public static Vector2 getMouse() {
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
	
	public static class Action {
		public final byte
			type;
		
		public Action(byte type) {
			this.type = type;
		}
		
		public boolean isDn() {
			return type == DN_ACTION;
		}
		
		public boolean isUp() {
			return type == UP_ACTION;
		}
	}
	
	public static class KeyAction extends Action {		
		public final int
			key;

		public KeyAction(byte type, int key) {
			super(type);
			this.key = key;
		}
		
		public boolean isKey(int key) {
			return this.key == key;
		}
	}
	
	public static class BtnAction extends Action {
		public final int
			btn;
	
		public BtnAction(byte type, int btn) {
			super(type);
			this.btn = btn;
		}
		
		public boolean isBtn(int btn) {
			return this.btn == btn;
		}
	}
	
	public static final int	
		KEY_ESCAPE = 0,
		
		KEY_F1  = 1,
		KEY_F2  = 2,
		KEY_F3  = 3,
		KEY_F4  = 4,
		KEY_F5  = 5,
		KEY_F6  = 6,
		KEY_F7  = 7,
		KEY_F8  = 8,
		KEY_F9  = 9,
		KEY_F10 = 10,
		KEY_F11 = 11,
		KEY_F12 = 12,				
				
		KEY_INSERT = 13,
		KEY_DELETE = 14,
		KEY_PAGE_UP = 15,
		KEY_PAGE_DN = 16,		
		
		KEY_L_ARROW  = 25,
		KEY_UP_ARROW = 26,
		KEY_R_ARROW  = 27,
		KEY_DN_ARROW = 28,
		
		KEY_TAB    = 29,
		KEY_BSPACE = 30,				
		KEY_ENTER = 31,
		
		KEY_SPACE  = ' ',     // #32
		KEY_QUOTE  = '\'',    // #39
		KEY_STAR   = '*',     // #42
		KEY_PLUS   = '+',     // #43
		KEY_COMMA  = ',',     // #44
		KEY_MINUS  = '-',     // #45
		KEY_PERIOD = '.',     // #46
		KEY_SLASH  = '/',     // #47
	
		KEY_0 = '0',          // #48
		KEY_1 = '1',
		KEY_2 = '2',
		KEY_3 = '3',
		KEY_4 = '4',
		KEY_5 = '5',
		KEY_6 = '6',
		KEY_7 = '7',
		KEY_8 = '8',
		KEY_9 = '9',          // #57
		
		KEY_SEMICOLON = ';',  // #59
		KEY_EQUALS = '=',     // #61
		
		KEY_A = 'A',          // #65
		KEY_B = 'B',
		KEY_C = 'C',
		KEY_D = 'D',
		KEY_E = 'E',
		KEY_F = 'F',
		KEY_G = 'G',
		KEY_H = 'H',
		KEY_I = 'I',
		KEY_J = 'J',
		KEY_K = 'K',
		KEY_L = 'L',
		KEY_M = 'M',
		KEY_N = 'N',
		KEY_O = 'O',
		KEY_P = 'P',
		KEY_Q = 'Q',
		KEY_R = 'R',
		KEY_S = 'S',
		KEY_T = 'T',
		KEY_U = 'U',
		KEY_V = 'V',
		KEY_W = 'W',
		KEY_X = 'X',
		KEY_Y = 'Y',
		KEY_Z = 'Z',          // #90
		
		KEY_L_BRACKET = '[' , // #91
		KEY_BSLASH    = '\\', // #92
		KEY_R_BRACKET = ']' , // #93
		KEY_BQUOTE    = '`' , // #96
				
		KEY_NUMPAD_0 = 112,
		KEY_NUMPAD_1 = 113,
		KEY_NUMPAD_2 = 114,
		KEY_NUMPAD_3 = 115,
		KEY_NUMPAD_4 = 116,
		KEY_NUMPAD_5 = 117,
		KEY_NUMPAD_6 = 118,
		KEY_NUMPAD_7 = 119,
		KEY_NUMPAD_8 = 120,
		KEY_NUMPAD_9 = 121,
		
		KEY_L_SHIFT = 122,
		KEY_R_SHIFT = 123,
		KEY_L_CTRL  = 124,
		KEY_R_CTRL  = 125,
		KEY_L_ALT   = 126,
		KEY_R_ALT   = 127;	
	
	public static final int
		BTN_1 = 1,
		BTN_2 = 2,
		BTN_3 = 3,
		BTN_4 = 4,
		BTN_5 = 5,
		BTN_6 = 6,
		BTN_7 = 7,
		BTN_8 = 8,
		BTN_9 = 9,
		BTN_10 = 10,
		BTN_11 = 11,
		BTN_12 = 12,
		BTN_13 = 13,
		BTN_14 = 14,
		BTN_15 = 15,
		BTN_16 = 16,
		BTN_17 = 17,
		BTN_18 = 18,
		BTN_19 = 19,
		BTN_20 = 20,
		BTN_21 = 21,
		BTN_22 = 22,
		BTN_23 = 23,
		BTN_24 = 24;
}
