/**
 * Created by lasaldan on 3/14/14.
 */
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Platformer implements Runnable{

    // Set Game Canvas Size
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    JFrame frame;
    Canvas canvas;
    BufferStrategy bufferStrategy;

    // This sets up our game environment
    public Platformer(){

        // Create the Window (with the name at the top and the close/minimize buttons, etc)
        frame = new JFrame("Simple Platformer");

        JPanel panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);

        // Create a new canvas on which we can draw images and such
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        // Add the canvas to the Window we created earlier
        panel.add(canvas);

        // Ask the canvas to listen for mouse input
        canvas.addMouseListener(new MouseControl());

        // Ask the canvas to listen for keyboard input
        canvas.addKeyListener(new KeyControl());

        // Let the Window know what to do if the X is clicked and set some other vars
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        // Set a double buffering strategy we'll use for graphics rendering
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();

        // Try to bring the canvas to the front of the monitor
        canvas.requestFocus();
    }

    // Mouse Control Definitions
    private class MouseControl extends MouseAdapter{

        // What to do when a mouse button is clicked
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println(keys.getKeyMap());
        }
    }

    // Keyboard Control Definitions
    private class KeyControl extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) {
            keys.setKeyPressed(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            keys.setKeyReleased(e.getKeyCode());
        }
    }

    long desiredFPS = 60;
    long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;

    boolean running = true;

    // Implements the run() method required by Runnable
    @Override
    public void run() {

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
            if(deltaLoop > desiredDeltaLoop){
                // Do Nothing! We are behind so we need to start the next frame!
            }else{
                // Calculate how long we should sleep and try to sleep until then
                try{
                    Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000));
                }catch(InterruptedException e){
                    //Do nothing
                }
            }

            // Do it all over again, until the game quits
        }
    }

    // Swaps between buffers and draws the current buffer to canvas
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        render(g);
        g.dispose();
        bufferStrategy.show();
    }

    // Game variables
    private double bgPos = 0;
    // keeps keyCodes of keys which are currently pressed
    private KeyManager keys = new KeyManager();

    /**
     * Rewrite this method for your game
     */
    protected void update(int deltaTime){
        bgPos -= 1;
        if(bgPos < -2000){
            bgPos = 0;
        }
    }

    Image background, midBg, foreground, logo;
    private void loadGraphics() {

        List<String> graphics = new ArrayList<String>();
        graphics.add("resources/gameFG.png");
        graphics.add("resources/gameMid.png");
        graphics.add("resources/background.jpg");
        graphics.add("resources/logo.png");

        for(String filepath : graphics) {}

        try {
            foreground = ImageIO.read(new File("resources/gameFG.png"));
            System.out.println("Image Loaded");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Loading Image");
        }

        try {
            midBg = ImageIO.read(new File("resources/gameMid.png"));
            System.out.println("Image Loaded");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Loading Image");
        }

        try {
            background = ImageIO.read(new File("resources/background.jpg"));
            System.out.println("Image Loaded");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Loading Image");
        }

        try {
            logo = ImageIO.read(new File("resources/logo.png"));
            System.out.println("Image Loaded");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Loading Image");
        }
    }

    // Draw all elements to the graphic buffer
    protected void render(Graphics2D g){
        g.drawImage(background, (int) bgPos, 0, (ImageObserver) canvas);
        g.drawImage(background, (int) bgPos+2000, 0, (ImageObserver) canvas);

        g.drawImage(midBg, (int) bgPos*2, 450, (ImageObserver) canvas);
        g.drawImage(midBg, (int) bgPos*2+2000, 450, (ImageObserver) canvas);
        g.drawImage(midBg, (int) bgPos*2+4000, 450, (ImageObserver) canvas);

        g.drawImage(foreground, (int) bgPos*4, 600, (ImageObserver) canvas);
        g.drawImage(foreground, (int) bgPos*4+2000, 600, (ImageObserver) canvas);
        g.drawImage(foreground, (int) bgPos*4+4000, 600, (ImageObserver) canvas);
        g.drawImage(foreground, (int) bgPos*4+6000, 600, (ImageObserver) canvas);
        g.drawImage(foreground, (int) bgPos*4+8000, 600, (ImageObserver) canvas);

        g.drawImage(logo, (int) 300, 250, (ImageObserver) canvas);
    }

    public static void main(String [] args){
        Platformer ex = new Platformer();
        new Thread(ex).start();
    }

    private class KeyManager {

        // Keeps track of which keys are currently being held down
        private Map<Integer, Boolean> keys = new HashMap<Integer,Boolean>();

        // list of keys we want to watch - everything else will be ignored
        // the list of keys is set in the KeyManager Constructor
        private ArrayList<Integer> eventKeys = new ArrayList<Integer>();

        protected KeyManager() {

            eventKeys.add(KeyEvent.VK_UP);
            eventKeys.add(KeyEvent.VK_DOWN);
            eventKeys.add(KeyEvent.VK_LEFT);
            eventKeys.add(KeyEvent.VK_RIGHT);
            eventKeys.add(KeyEvent.VK_ESCAPE);

            for (int i : eventKeys) {
                keys.put(i,false);
            }

        }

        public void setKeyPressed(int key) {
            if(eventKeys.contains(key))
                keys.put(key,true);
        }

        public void setKeyReleased(int key) {
            if(eventKeys.contains(key))
                keys.put(key,false);
        }

        public boolean isKeyPressed(int key){
            return keys.get(key);
        }

        public String getKeyMap() {
            String out = "KEYS: ";
            for (int i : eventKeys) {
                if( keys.get(i) )
                    out += "Pressed,";
                else
                    out += "Not Pressed,";
            }
            return out;
        }

    }

}
