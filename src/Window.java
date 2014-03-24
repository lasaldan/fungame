import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Created by lasaldan on 3/19/14.
 */
public class Window {


    private JFrame frame;
    private Canvas canvas;
    private BufferStrategy bufferStrategy;
    private int WindowWidth;
    private int WindowHeight;

    public Window(int width, int height) {

        WindowWidth = width;
        WindowHeight = height;

        // Create the Window (with the name at the top and the close/minimize buttons, etc)
        frame = new JFrame("Simple Platformer");

        JPanel panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WindowWidth, WindowHeight));
        panel.setLayout(null);

        // Create a new canvas on which we can draw images and such
        canvas = new Canvas();
        canvas.setBounds(0, 0, WindowWidth, WindowHeight);
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

        // Try to bring the canvas to the front of the monitor
        canvas.requestFocus();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void addMouseListener( MouseControl mouse ) {
        canvas.addMouseListener(mouse);
    }

    public void addKeyboardListener( KeyControl keyboard ) {
        canvas.addKeyListener(keyboard);
    }

    public BufferStrategy getBufferStrategy() {
        return canvas.getBufferStrategy();
    }
}
