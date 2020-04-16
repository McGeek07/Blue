package blue.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import blue.geom.Vector2;
import blue.util.event.Listener;

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
		NUM_KEYS = 150,
		NUM_BTNS = 6;

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
		//do nothing
	}
	
	public static <T extends Action> void attach(Class<T> type, Listener<T> listener) {
		Engine.getStage().attach(type, listener);
	}
	
	public static <T extends Action> void detach(Class<T> type, Listener<T> listener) {
		Engine.getStage().detach(type, listener);
	}
	
	protected static void poll() {
		INSTANCE.onPollMouse();
		INSTANCE.onPollWheel();
		INSTANCE.onPollKeys();
		INSTANCE.onPollBtns();
	}
	
	public void onPollMouse() {
		if(!mouse_buffer.equals(mouse)) {
			mouse.set(mouse_buffer);
			//event
			Stage.mouseMoved(getMouse());
		}
	}
	
	public void onPollWheel() {
		wheel        = wheel_buffer;
		wheel_buffer = 0           ;
		if(wheel != 0)
			//event
			Stage.wheelMoved(getWheel());
	}
	
	public void onPollKeys() {
		for(int i = 0; i < NUM_KEYS; i ++) {
			if(key_buffer[i])
				switch(keys[i]) {
				case Input.UP: case Input.UP_ACTION:
					keys[i] = Input.DN_ACTION;
					//event
					Engine.getStage().queue(new KeyAction(DN_ACTION, i));
					Stage.keyDn(i);
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
					Engine.getStage().queue(new KeyAction(UP_ACTION, i));
					Stage.keyUp(i);
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
					Engine.getStage().queue(new BtnAction(DN_ACTION, i));
					Stage.btnDn(i);
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
					Engine.getStage().queue(new BtnAction(UP_ACTION, i));
					Stage.btnUp(i);
					break;
			}
		}
	}
	
	public void onGainFocus() {
		//do nothing
	}
	
	public void onLoseFocus() {
		for(int i = 0; i < NUM_KEYS; i ++)
			key_buffer[i] = false;
		for(int i = 0; i < NUM_BTNS; i ++)
			btn_buffer[i] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
			/*ASCII:   0 -  31*/
			case KeyEvent.VK_BACK_SPACE: key_buffer[KEY_BACK_SPACE] = true; break;
			case KeyEvent.VK_TAB       : key_buffer[KEY_TAB       ] = true; break;
			case KeyEvent.VK_ENTER     : key_buffer[KEY_RETURN    ] = true; break;
			case KeyEvent.VK_ESCAPE    : key_buffer[KEY_ESCAPE    ] = true; break;
			
			/*ASCII:  32 -  47*/
			case KeyEvent.VK_SPACE : key_buffer[KEY_SPACE ] = true; break;
			case KeyEvent.VK_QUOTE : key_buffer[KEY_QUOTE ] = true; break;	
			case KeyEvent.VK_COMMA : key_buffer[KEY_COMMA ] = true; break;
			case KeyEvent.VK_PERIOD: key_buffer[KEY_PERIOD] = true; break;
			case KeyEvent.VK_SLASH : key_buffer[KEY_SLASH ] = true; break;
		
			/*ASCII:  48 -  57*/
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
			
			/*ASCII:  58 -  64*/
			case KeyEvent.VK_SEMICOLON: key_buffer[KEY_SEMICOLON] = true; break;
			
			/*ASCII:  65 -  90*/
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
			
			/*ASCII:  91 -  96*/
			case KeyEvent.VK_OPEN_BRACKET : key_buffer[KEY_L_BRACKET ] = true; break;
			case KeyEvent.VK_BACK_SLASH   : key_buffer[KEY_BACK_SLASH] = true; break;
			case KeyEvent.VK_CLOSE_BRACKET: key_buffer[KEY_R_BRACKET ] = true; break;
			case KeyEvent.VK_BACK_QUOTE   : key_buffer[KEY_BACK_QUOTE] = true; break;
			
			/*ASCII: 123 - 127*/
			case KeyEvent.VK_DELETE: key_buffer[KEY_DELETE] = true; break;
			
			/*MISC*/
			
			case KeyEvent.VK_UP   : key_buffer[KEY_UP_ARROW] = true; break;
			case KeyEvent.VK_DOWN : key_buffer[KEY_DN_ARROW] = true; break;
			case KeyEvent.VK_RIGHT: key_buffer[KEY_R_ARROW ] = true; break;
			case KeyEvent.VK_LEFT : key_buffer[KEY_L_ARROW ] = true; break;
			
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
			/*ASCII:   0 -  31*/
			case KeyEvent.VK_BACK_SPACE: key_buffer[KEY_BACK_SPACE] = false; break;
			case KeyEvent.VK_TAB       : key_buffer[KEY_TAB       ] = false; break;
			case KeyEvent.VK_ENTER     : key_buffer[KEY_RETURN    ] = false; break;
			case KeyEvent.VK_ESCAPE    : key_buffer[KEY_ESCAPE    ] = false; break;
			
			/*ASCII:  32 -  47*/
			case KeyEvent.VK_SPACE : key_buffer[KEY_SPACE ] = false; break;
			case KeyEvent.VK_QUOTE : key_buffer[KEY_QUOTE ] = false; break;	
			case KeyEvent.VK_COMMA : key_buffer[KEY_COMMA ] = false; break;
			case KeyEvent.VK_PERIOD: key_buffer[KEY_PERIOD] = false; break;
			case KeyEvent.VK_SLASH : key_buffer[KEY_SLASH ] = false; break;
		
			/*ASCII:  48 -  57*/
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
			
			/*ASCII:  58 -  64*/
			case KeyEvent.VK_SEMICOLON: key_buffer[KEY_SEMICOLON] = false; break;
			
			/*ASCII:  65 -  90*/
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
			
			/*ASCII:  91 -  96*/
			case KeyEvent.VK_OPEN_BRACKET : key_buffer[KEY_L_BRACKET ] = false; break;
			case KeyEvent.VK_BACK_SLASH   : key_buffer[KEY_BACK_SLASH] = false; break;
			case KeyEvent.VK_CLOSE_BRACKET: key_buffer[KEY_R_BRACKET ] = false; break;
			case KeyEvent.VK_BACK_QUOTE   : key_buffer[KEY_BACK_QUOTE] = false; break;
			
			/*ASCII: 123 - 127*/
			case KeyEvent.VK_DELETE: key_buffer[KEY_DELETE] = false; break;
			
			/*MISC*/
			
			case KeyEvent.VK_UP   : key_buffer[KEY_UP_ARROW] = false; break;
			case KeyEvent.VK_DOWN : key_buffer[KEY_DN_ARROW] = false; break;
			case KeyEvent.VK_RIGHT: key_buffer[KEY_R_ARROW ] = false; break;
			case KeyEvent.VK_LEFT : key_buffer[KEY_L_ARROW ] = false; break;
			
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
		mouse_buffer.set(Stage.mouseToPixel(
				e.getX(),
				e.getY()
				));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouse_buffer.set(Stage.mouseToPixel(
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
		return key >= 0 && key < NUM_KEYS ? INSTANCE.keys[key] : 0;
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
		/*ASCII:   0 -  31*/
		KEY_BACK_SPACE       = '\b',     //  8
		KEY_TAB              = '\t',     //  9
		KEY_RETURN           = '\n',     // 10
		KEY_ESCAPE           = '\u001b', // 27
		
		/*ASCII:  32 -  47*/
		KEY_SPACE            = ' ',  // 32
		KEY_QUOTE            = '\'', // 39		
		KEY_L_PARENTHESIS    = '(',  // 40
		KEY_R_PARENTHESIS    = ')',  // 41		
		KEY_COMMA            = ',',  // 44
		KEY_PERIOD           = '.',  // 46
		KEY_SLASH            = '/',  // 47
	
		/*ASCII:  48 -  57*/
		KEY_0                = '0', // 48
		KEY_1                = '1', // 49
		KEY_2                = '2', // 50
		KEY_3                = '3', // 51
		KEY_4                = '4', // 52
		KEY_5                = '5', // 53
		KEY_6                = '6', // 54
		KEY_7                = '7', // 55
		KEY_8                = '8', // 56
		KEY_9                = '9', // 57
		
		/*ASCII:  58 -  64*/
		KEY_SEMICOLON        = ';', // 59
		
		/*ASCII:  65 -  90*/
		KEY_A                = 'A', // 65
		KEY_B                = 'B', // 66
		KEY_C                = 'C', // 67
		KEY_D                = 'D', // 68
		KEY_E                = 'E', // 69
		KEY_F                = 'F', // 70
		KEY_G                = 'G', // 71
		KEY_H                = 'H', // 72
		KEY_I                = 'I', // 73
		KEY_J                = 'J', // 74
		KEY_K                = 'K', // 75
		KEY_L                = 'L', // 76
		KEY_M                = 'M', // 77
		KEY_N                = 'N', // 78
		KEY_O                = 'O', // 79
		KEY_P                = 'P', // 80
		KEY_Q                = 'Q', // 81
		KEY_R                = 'R', // 82
		KEY_S                = 'S', // 83
		KEY_T                = 'T', // 84
		KEY_U                = 'U', // 85
		KEY_V                = 'V', // 86
		KEY_W                = 'W', // 87
		KEY_X                = 'X', // 88
		KEY_Y                = 'Y', // 89
		KEY_Z                = 'Z', // 90
		
		/*ASCII:  91 -  96*/
		KEY_L_BRACKET        = '[',  // 91
		KEY_BACK_SLASH       = '\\', // 92
		KEY_R_BRACKET        = ']',  // 93
		KEY_BACK_QUOTE       = '`',  // 96
		
		/*ASCII: 123 - 127*/
		KEY_DELETE           = '\u00ff', // 127
		
		/*MISC*/
		
		KEY_UP_ARROW         = 128,
		KEY_DN_ARROW         = 129,
		KEY_R_ARROW          = 130,
		KEY_L_ARROW          = 131,
		
		KEY_F1               = 132,
		KEY_F2               = 133,
		KEY_F3               = 134,
		KEY_F4               = 135,
		KEY_F5               = 136,
		KEY_F6               = 137,
		KEY_F7               = 138,
		KEY_F8               = 139,
		KEY_F9               = 140,
		KEY_F10              = 141,
		KEY_F11              = 142,
		KEY_F12              = 143,				
				
		KEY_L_SHIFT          = 144,
		KEY_R_SHIFT          = 145,
		KEY_L_CTRL           = 146,
		KEY_R_CTRL           = 147,
		KEY_L_ALT            = 148,
		KEY_R_ALT            = 149;
	
	public static final int
		BTN_1 = 1,
		BTN_2 = 2,
		BTN_3 = 3,
		BTN_4 = 4,
		BTN_5 = 5;
}
