import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.sql.Time;

import javax.swing.JPanel;
import javax.swing.plaf.TreeUI;

public class GamePanel extends JPanel implements Runnable   {

    //SCREEN SETTINGS
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 512;

    final int ORIGNAL_TILE_SIZE = 4; //16x16 pixels
    final int SCALE = 3; //scale up by 3 to fit modern screens.

    final int TILE_SIZE = ORIGNAL_TILE_SIZE * SCALE;

    final int MAX_SCREEN_COLLUMNS = 16;
    final int MAX_SCREEN_ROWS = 12;

    final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLLUMNS;
    final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROWS;

    Thread gameThread; //keep program running

    int FPS = 30;


    KeyHandler keyHandler = new KeyHandler();

    //set Default positions of Player
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;




    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); //imporve game rednering performance idk
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        
        
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start(); //calls the run method.

    }



    @Override
    public void run() {
        this.setBackground(Color.BLACK);

        double drawInterval = 1000000000/FPS; //draw every 60th of a second
        double nextDrawTime = System.nanoTime() + drawInterval; //current system time + draw interval


        while(gameThread != null){

            update();

            repaint();

          
            try{
                double remainingTime = nextDrawTime - System.nanoTime(); //time remaining to draw
                remainingTime = remainingTime/1000000; //convert to milliseconds


                if (remainingTime < 0){
                    remainingTime = 0; //doesnt need to sleep if remaining time is less than 0
                }
            
                Thread.sleep((long)(remainingTime)); //convert to milliseconds
                nextDrawTime += drawInterval; //add draw interval to next draw time


            }catch(Exception e){
                e.printStackTrace();
            }
        
        }
       
    }

    public void update(){
        //update game logic here

        long currentTime = System.nanoTime(); //delta Time
        handlePlayerMovement();
       
             
        
    }


        
    private void handlePlayerMovement() {
        if(playerY == 0){
            playerY += playerSpeed;

        }
        if(playerY == 500){
            playerY -= playerSpeed;

        }
        if(playerX == 0){
            playerX += playerSpeed;

        }
        if(playerX == 496){
            playerX -= playerSpeed;

        }

       

        if(keyHandler.wPressed == true){
            playerY -= playerSpeed;
        }
            if(keyHandler.sPressed == true){
            playerY += playerSpeed;
        }
            if(keyHandler.aPressed == true){
            playerX -= playerSpeed;

        }
            if(keyHandler.dPressed == true){
                System.out.println(playerX);

            playerX += playerSpeed;
        }  

        
    }
        
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
    
        // Draw the player using a separate method
        player(graphics2D);
        lineDownMiddle(graphics2D);
    
        // Draw line in the middle of the screen
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
    
        graphics2D.dispose();
    }

    private void player(Graphics2D graphics2D) {
        // Draw the player's rectangle and additional part
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(playerX, playerY, TILE_SIZE, TILE_SIZE);
        graphics2D.fillRect(playerX + 5, playerY, 2, 20);
    }

  
    public void lineDownMiddle(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
         
    }
    
 


    
}
