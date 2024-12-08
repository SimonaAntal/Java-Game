package PaooGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyHandler implements KeyListener{
    public boolean up, down, left, right;   //miscaere WASD
    public boolean attack;                   //atac K
    public boolean critical;                //critica L
    public boolean dash;                    //dash J
    public boolean interact;                //interactionare F
    public boolean use;                     //folosire E
    public boolean change;                  //schimbare obiect O
    public boolean esc;                     //ESCape



    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch(key)
        {
            //      miscaere WASD
            case KeyEvent.VK_W:
                up = true;
                break;
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_S:
                down = true;
                break;
            case KeyEvent.VK_D:
                right = true;
                break;

            //      atac K
            case KeyEvent.VK_K:
                attack = true;
                break;

            //      critica L
            case KeyEvent.VK_L:
                critical = true;
                break;

            //      dash J
            case KeyEvent.VK_J:
                dash = true;
                break;

            //      interactionare F
            case KeyEvent.VK_F:
                interact = true;
                break;

            //      folosire E
            case KeyEvent.VK_E:
                use = true;
                break;

            //      schimbare obiect O
            case KeyEvent.VK_O:
                change = true;
                break;

            //      ESCape
            case KeyEvent.VK_ESCAPE:
                esc = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        switch(key)
        {
            //      miscaere WASD
            case KeyEvent.VK_W:
                up = false;
                break;
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_S:
                down = false;
                break;
            case KeyEvent.VK_D:
                right = false;
                break;

            //      atac K
            case KeyEvent.VK_K:
                attack = false;
                break;

            //      critica L
            case KeyEvent.VK_L:
                critical = false;
                break;

            //      dash J
            case KeyEvent.VK_J:
                dash = false;
                break;

            //      interactionare F
            case KeyEvent.VK_F:
                interact = false;
                break;

            //      folosire E
            case KeyEvent.VK_E:
                use = false;
                break;

            //      schimbare obiect O
            case KeyEvent.VK_O:
                change = false;
                break;

            //      ESCape
            case KeyEvent.VK_ESCAPE:
                esc = false;
                break;
        }
    }
}
