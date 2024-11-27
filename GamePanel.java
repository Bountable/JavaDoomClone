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

    final int ORIGNAL_TILE_SIZE = 21; //16x16 pixels
    final int SCALE = 1; //scale up by 3 to fit modern screens.

    final int TILE_SIZE = ORIGNAL_TILE_SIZE * SCALE;

    final int MAX_SCREEN_COLLUMNS = 16;
    final int MAX_SCREEN_ROWS = 12;

    final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLLUMNS;
    final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROWS;

    final int HALFWAY_POINT_WIDTH = 504;

    Thread gameThread; //keep program running

    int FPS = 30;


    KeyHandler keyHandler = new KeyHandler();

    //set Default positions of Player
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 2;




    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, 505));
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
        
        if (keyHandler.rightPressed == true) {
            //TODO ROTATE 


            
        }
        if (keyHandler.leftPressed == true) {
            //TODO ROTATE 
            

            
        }

        
    }
        
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
    
        // Draw the player using a separate method
        player(graphics2D);
        drawGrid(graphics2D);

    
        // Draw line in the middle of the screen
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawLine(HALFWAY_POINT_WIDTH, 0, HALFWAY_POINT_WIDTH, 512);

    }

    private void player(Graphics2D graphics2D) {
        // Draw the player's rectangle and additional part
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(playerX, playerY, TILE_SIZE-1, TILE_SIZE-1); //minus 1 to fit in the grid i think
        graphics2D.fillRect(playerX+9, playerY+10, 2, 20);


    }




    public void drawGrid(Graphics graphics2D) {
        int numCols = (WIDTH / 2) / 21; // Number of tiles horizontally
    
        for (int i = 0; i < numCols; i++) { // Loop through columns
            for (int j = 0; j < 24; j++) { // Loop through rows
                // Draw each tile
                graphics2D.setColor(Color.BLUE);
                graphics2D.drawRect(i * 21, j * 21, 21, 21);
            }
        }
    }
    
 


    
}
