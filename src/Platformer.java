/**
 * Created by lasaldan on 3/14/14.
 */

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;


public class Platformer implements Runnable{

    // Set Game Canvas Size
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    long desiredFPS = 60;

    private double bgPos = 0;

    // how long do we want to take calculating each frame?
    long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;

    // Flag indicating whether the game is currently running.
    // This can be set to false while the game is running to
    // end the game loop and exit the program
    boolean running = true;

    // Where we'll be drawing all our graphics and such
    Canvas canvas;

    // This sets up our game environment
    public Platformer(){

        // Create the window we'll have the game run in
        Window window = new Window(WIDTH,HEIGHT);
        canvas = window.getCanvas();

        // Add a mouse listener to the window
        canvas.addMouseListener(new MouseControl());

        // Add a keyboard listenr to the window
        canvas.addKeyListener(new KeyControl());

    }


    // Implements the run() method required by Runnable
    @Override
    public void run() {

        //Sound bgSound = new Sound("concept/music/Ouroboros.wav");
        Sound bgSound = new Sound("concept/music/MonkeysSpinningMonkeys.wav");
        bgSound.loop(Clip.LOOP_CONTINUOUSLY);

        long beginLoopTime;
        long endLoopTime;
        long currentUpdateTime = System.nanoTime();
        long lastUpdateTime;
        long deltaLoop;

        loadGraphics();

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

            // Do it all over again, until the game quits
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

    // Game variables
    // keeps keyCodes of keys which are currently pressed
    protected void update(int deltaTime){
        bgPos -= 1;
        if(bgPos < -2000){
            bgPos = 0;
        }
    }

    // TODO: Build a structure/class for managing graphics
    Image background, midBg, foreground, logo;
    private void loadGraphics() {

        /*
        List<String> graphics = new ArrayList<String>();
        graphics.add("resources/gameFG.png");
        graphics.add("resources/gameMid.png");
        graphics.add("resources/background.jpg");
        graphics.add("resources/logo.png");
        */

        try {
            foreground = ImageIO.read(new File("resources/gameFG.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Loading Image");
        }

        try {
            midBg = ImageIO.read(new File("resources/gameMid.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Loading Image");
        }

        try {
            background = ImageIO.read(new File("resources/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Loading Image");
        }

        try {
            logo = ImageIO.read(new File("resources/logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Loading Image");
        }
    }

    // Draw all elements to the graphic buffer
    protected void render(Graphics2D g){
        g.drawImage(background, (int) bgPos, 0, canvas);
        g.drawImage(background, (int) bgPos + 2000, 0, canvas);

        g.drawImage(midBg, (int) bgPos * 2, 450, canvas);
        g.drawImage(midBg, (int) bgPos * 2 + 2000, 450, canvas);
        g.drawImage(midBg, (int) bgPos * 2 + 4000, 450, canvas);

        g.drawImage(foreground, (int) bgPos*4, 600, canvas);
        g.drawImage(foreground, (int) bgPos*4+2000, 600, canvas);
        g.drawImage(foreground, (int) bgPos*4+4000, 600, canvas);
        g.drawImage(foreground, (int) bgPos*4+6000, 600, canvas);
        g.drawImage(foreground, (int) bgPos*4+8000, 600, canvas);

        g.drawImage(logo, 300, 250, canvas);
    }

    public static void main(String [] args){
        Platformer ex = new Platformer();
        new Thread(ex).start();
    }
}
