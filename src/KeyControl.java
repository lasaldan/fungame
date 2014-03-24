/**
 * Created by lasaldan on 3/19/14.
 */

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KeyControl extends KeyAdapter {

    public KeyManager keys;

    public KeyControl() {
        keys = new KeyManager();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys.setKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys.setKeyReleased(e.getKeyCode());
    }

    public boolean isPressed(int key) {
        return keys.isPressed(key);
    }

    // Subclass used by Keyboard to keep track of which keys are pressed
    private class KeyManager {

        // Keeps track of which keys are currently being held down
        private Map<Integer, Boolean> keys = new HashMap<Integer,Boolean>();

        // list of keys we want to watch - everything else will be ignored
        // the list of keys is set in the KeyManager Constructor
        private ArrayList<Integer> eventKeys = new ArrayList<Integer>();

        protected KeyManager() {

            eventKeys.add(KeyEvent.VK_UP);
            eventKeys.add(KeyEvent.VK_DOWN);
            eventKeys.add(KeyEvent.VK_LEFT);
            eventKeys.add(KeyEvent.VK_RIGHT);
            eventKeys.add(KeyEvent.VK_ESCAPE);

            for (int i : eventKeys) {
                keys.put(i,false);
            }
        }

        public boolean isPressed(int key) {
            if(eventKeys.contains(key))
                return keys.get(key);
            return false;
        }

        public void setKeyPressed(int key) {
            if(eventKeys.contains(key))
                keys.put(key,true);
        }

        public void setKeyReleased(int key) {
            if(eventKeys.contains(key))
                keys.put(key,false);
        }

        public String getKeyMap() {
            String out = "KEYS: ";
            for (int i : eventKeys) {
                if( keys.get(i) )
                    out += "Pressed,";
                else
                    out += "Not Pressed,";
            }
            return out;
        }

    }
}
