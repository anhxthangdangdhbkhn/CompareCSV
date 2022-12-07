package component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKey implements KeyListener {
    public void keyTyped(KeyEvent e) {
        // Invoked when a key has been typed.
    }

    public void keyPressed(KeyEvent e) {
        // Invoked when a key has been pressed.
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("ENTER");
        }
    }

    public void keyReleased(KeyEvent e) {
        // Invoked when a key has been released.
    }
}
