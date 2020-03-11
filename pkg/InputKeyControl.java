package pkg;

import java.awt.event.KeyEvent;

public interface InputKeyControl{
    public void keyPress(String es, KeyEvent e);
    public void keyRelease(String es, KeyEvent e);
}
