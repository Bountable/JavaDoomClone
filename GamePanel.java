import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.sql.Time;

import javax.swing.JPanel;
import javax.swing.plaf.TreeUI;

public class GamePanel extends JPanel implements Runnable   {

    //SCREEN SETTINGS
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 512;

    final int ORIGNAL_TILE_SIZE = 50; //16x16 pixels
    final int SCALE = 1; //scale up by 3 to fit modern screens.

    final int TILE_SIZE = ORIGNAL_TILE_SIZE * SCALE;

    final int MAX_SCREEN_COLLUMNS = 16;
    final int MAX_SCREEN_ROWS = 12;

    final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLLUMNS;
    final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROWS;

    final int HALFWAY_POINT_WIDTH = 504;

    Shape player;

    Thread gameThread; //keep program running

    int FPS = 30;

    double playerRotation = 0; // Rotation angle in radians

    


    KeyHandler keyHandler = new KeyHandler();

    int map[][] = 
    {
        {1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,1,1,1,0,0,1},
        {1,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,1},
        {1,1,1,1,1,1,1,1,1,1},
    };

    //set Default positions of Player
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 2;




    public GamePanel() {
        player = new Rectangle2D.Double(playerX, playerY, TILE_SIZE, TILE_SIZE);
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
        handlePlayerMovement();
        repaint();
        
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
        if(playerX == 460){
            playerX -= playerSpeed;

        }

        if (keyHandler.wPressed) playerY -= playerSpeed;
        if (keyHandler.sPressed) playerY += playerSpeed;
        if (keyHandler.aPressed) playerX -= playerSpeed;
        if (keyHandler.dPressed) playerX += playerSpeed;
    
        // Adjust rotation angle
        if (keyHandler.rightPressed) {
            playerRotation += Math.toRadians(5); // Rotate clockwise
            if (playerRotation >= 2 * Math.PI) playerRotation -= 2 * Math.PI; // Keep angle within 360 degrees
            System.out.println("Rotate Right: " + Math.toDegrees(playerRotation));
        }
        if (keyHandler.leftPressed) {
            playerRotation -= Math.toRadians(5); // Rotate counter-clockwise
            if (playerRotation < 0) playerRotation += 2 * Math.PI; // Keep angle within 360 degrees
            System.out.println("Rotate Left: " + Math.toDegrees(playerRotation));
        }
    }
        
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
    
        // Apply rotation and draw player
        graphics2D.setColor(Color.WHITE);
    
        // Save the original transform
        AffineTransform originalTransform = graphics2D.getTransform();
    
        // Apply rotation
        graphics2D.translate(playerX + TILE_SIZE / 2, playerY + TILE_SIZE / 2); // Move to center of player
        graphics2D.rotate(playerRotation); // Apply rotation
        graphics2D.translate(-TILE_SIZE / 2, -TILE_SIZE / 2); // Adjust back for drawing
    
        // Draw the player
        graphics2D.fill(new Rectangle2D.Double(0, 0, TILE_SIZE, TILE_SIZE)); // Player rectangle


        Shape ray;
        ray = new Rectangle2D.Double(25, 0, 1, 200);
       //check if
      
    
        // Restore original transform
        graphics2D.setTransform(originalTransform);
    
        // Draw other components
        drawGrid(graphics2D);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawLine(HALFWAY_POINT_WIDTH, 0, HALFWAY_POINT_WIDTH, 512);

        drawMap(graphics2D);

   
    }



    public void drawGrid(Graphics graphics2D) {
    
        for (int i = 0; i < 10; i++) { // Loop through columns
            for (int j = 0; j < 24; j++) { // Loop through rows
                // Draw each tile
                graphics2D.setColor(Color.BLUE);
                graphics2D.drawRect(i * 50, j * 50  , 50, 50);
            }
        }
    }


    public void drawWall(int x1, int x2, int b1, int b2){
     

    }

    public void drawMap(Graphics graphics2D){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(map[j][i] == 1){
                    graphics2D.setColor(Color.WHITE);
                    graphics2D.fillRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }
    

    public void drawRays3D(Graphics graphics2D){
        //draw 3d rays
        
    }


  


    
}
