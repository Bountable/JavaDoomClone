import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    boolean wPressed = false;
    boolean aPressed = false;
    boolean sPressed = false;
    boolean dPressed = false;


    boolean leftPressed = false;
    boolean rightPressed = false;
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();


       if(keyCode == KeyEvent.VK_W){
            wPressed = true;
       }
       if(keyCode == KeyEvent.VK_A){
            aPressed = true;
       }
       if(keyCode == KeyEvent.VK_S){
            sPressed = true;
       }
        if(keyCode == KeyEvent.VK_D){
            dPressed = true;
        }


        //ROTATION
        if(keyCode == KeyEvent.VK_LEFT){
            leftPressed = true;
        }
        if(keyCode == KeyEvent.VK_RIGHT){
            //rotate right
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_W){
             wPressed = false;
        }
        if(keyCode == KeyEvent.VK_A){
             aPressed = false;
        }
        if(keyCode == KeyEvent.VK_S){
             sPressed = false;
        }
        if(keyCode == KeyEvent.VK_D){
            dPressed = false;
        }


        //rotation
        if(keyCode == KeyEvent.VK_LEFT){
            leftPressed = false;
        }
        if(keyCode == KeyEvent.VK_RIGHT){
            //rotate right
            rightPressed = false;
        }
    }

    
    
}
