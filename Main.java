import java.awt.FlowLayout;  
import javax.swing.JButton;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JPanel;  

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("JFrame Example");  
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("RPG QUEST");

        GamePanel gamePanel = new GamePanel();
        gamePanel.startGameThread();
        
        window.add(gamePanel);
        window.pack();


        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
    
    }  
}
