/**
 * Created by lasaldan on 3/14/14.
 */

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;


public class Platformer implements Runnable{

    // Set Game Canvas Size
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    long desiredFPS = 60;

    private double bgPos = 0;
    ScrollingBackground2d bg2d;

    // how long do we want to take calculating each frame?
    long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;

    // Flag indicating whether the game is currently running.
    // This can be set to false while the game is running to
    // end the game loop and exit the program
    boolean running = true;

    // Where we'll be drawing all our graphics and such
    Canvas canvas;
    KeyControl keys;

    // This sets up our game environment
    public Platformer(){

        // Create the window we'll have the game run in
        Window window = new Window(WIDTH,HEIGHT);
        canvas = window.getCanvas();

        // Add a mouse listener to the window
        canvas.addMouseListener(new MouseControl());

        // Add a keyboard listener to the window
        keys = new KeyControl();
        canvas.addKeyListener(keys);

    }


    // Implements the run() method required by Runnable
    @Override
    public void run() {

        Sound bgSound = new Sound("concept/music/Ouroboros.wav");
        //Sound bgSound = new Sound("concept/music/MonkeysSpinningMonkeys.wav");

        bgSound.loop(0); // 0 means loop forever!

        bg2d = new ScrollingBackground2d();
        bg2d.addLayer("resources/background.jpg", 0, 0, -1, true);
        bg2d.addLayer("resources/gameMid.png", 0, 450, -2, true);
        bg2d.addLayer("resources/gameFG.png", 0, 600, -4, true);
        bg2d.addLayer("resources/logo.png", 300, 250, 0, false);

        long beginLoopTime;
        long endLoopTime;
        long currentUpdateTime = System.nanoTime();
        long lastUpdateTime;
        long deltaLoop;

        // Main game loop
        while(running){

            // Stores time the current frame was rendered
            beginLoopTime = System.nanoTime();

            // Draws the buffer content to the main canvas
            render();

            lastUpdateTime = currentUpdateTime;
            currentUpdateTime = System.nanoTime();
            update((int) ((currentUpdateTime - lastUpdateTime)/(1000*1000)));

            // Calculate how long it took to render
            endLoopTime = System.nanoTime();
            deltaLoop = endLoopTime - beginLoopTime;

            // Check if we should wait before drawing the next frame, or if we
            // should just jump right into the next frame.
            if(deltaLoop <= desiredDeltaLoop){
                // We're ahead! Calculate how long we should sleep and try to sleep
                try{
                    Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000));
                }catch(InterruptedException e){
                    System.out.println("Cannot Sleep");
                }
            }
        }
    }

    // Swaps between buffers and draws the current buffer to canvas
    private void render() {
        Graphics2D g = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        render(g);
        g.dispose();
        canvas.getBufferStrategy().show();
    }

    // Update the position of all the elements in the game
    protected void update(int deltaTime){
        if(keys.isPressed(KeyEvent.VK_LEFT))
            bg2d.update(-1,0);
        if(keys.isPressed(KeyEvent.VK_RIGHT))
            bg2d.update(1,0);
    }

    // Draw all elements to the graphic buffer
    protected void render(Graphics2D g){
        bg2d.draw(g,canvas);
    }

    public static void main(String [] args){
        Platformer ex = new Platformer();
        new Thread(ex).start();
    }
}