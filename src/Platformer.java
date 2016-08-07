/**
 * Created by lasaldan on 3/14/14.
 */

import java.awt.*;
import java.awt.event.KeyEvent;


public class Platformer extends GameLoop{

	// Set Game Canvas Size
	final int WIDTH = 1000;
	final int HEIGHT = 700;

	long desiredFPS = 60;

	ScrollingBackground2d bg2d;
	Stats stats;

	// how long do we want to take calculating each frame?
	long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;

	// Flag indicating whether the game is currently running.
	// This can be set to false while the game is running to
	// end the game loop and exit the program
	boolean running = true;

	float speed = 0;

	// Where we'll be drawing all our graphics and such
	Canvas canvas;

	// Used to write and manage TTF fonts on the game canvas
	GameFont fontWriter;

	// Used to keep track of the state of the keyboard
	KeyControl keys;

	// This sets up our game environment
	public Platformer(double delta){

		super(delta);

		// Create the window we'll have the game run in
		Window window = new Window(WIDTH,HEIGHT);
		canvas = window.getCanvas();

		// Add a mouse listener to the window
		canvas.addMouseListener(new MouseControl());

		// Add a keyboard listener to the window
		keys = new KeyControl();
		canvas.addKeyListener(keys);

		this.run();

	}


	@Override
	public void startup() {

		Sound bgSound = new Sound("resources/sound/loops/Ouroboros.wav");
		//Sound bgSound = new Sound("resources/sound/loops/game.wav");
		//Sound bgSound = new Sound("concept/music/MonkeysSpinningMonkeys.wav");

		bgSound.loop(0); // 0 means loop forever!

		// Create a new Background manager and add some parallax layers
		bg2d = new ScrollingBackground2d();
		bg2d.addLayer("resources/graphics/graphPaper.png", 0, 0, -1, true);
		bg2d.addLayer("resources/graphics/mountains.png", 0, 0, -2, true);
		bg2d.addLayer("resources/graphics/trees.png", 0, 0, -4, true);
		bg2d.addLayer("resources/graphics/grass.png", 0, 0, -8, true);
		bg2d.addLayer("resources/graphics/road.png", 0, 0, -1, true);
		bg2d.addLayer("resources/graphics/car3.png", 0, 0, 0, true);
		/*
		bg2d.addLayer("resources/graphics/background.jpg", 0, 0, -1, true);
		bg2d.addLayer("resources/graphics/gameMid.png", 0, 450, -2, true);
		bg2d.addLayer("resources/graphics/gameFG.png", 0, 600, -4, true);


		// Add the logo as a layer that doesn't repeat or scroll
		bg2d.addLayer("resources/graphics/logo.png", 300, 150, 0, false);
		*/
		// Set the font we want to use in the game and pass in a reference to the canvas to draw on
		fontWriter = new GameFont("resources/fonts/computer_pixel-7.ttf", canvas);

		// Set default font weight, color and size
		fontWriter.setWeight(Font.BOLD);
		fontWriter.setColor(253,187,67);
		fontWriter.setSize(36f);

		long beginLoopTime;
		long endLoopTime;
		long currentUpdateTime = System.nanoTime();
		long lastUpdateTime;
		long deltaLoop;
	}

	@Override
	public void shutdown() {

	}

	@Override
	public void update() {

		// Check if the LEFT keyboard key is held down
		if(keys.isPressed(KeyEvent.VK_LEFT))
			speed -= .01;

		// Check if the RIGHT keyboard key is held down
		else if(keys.isPressed(KeyEvent.VK_RIGHT))
			speed += .01;

		else if(keys.isPressed(KeyEvent.VK_DOWN))
			speed = 0;

		else if (speed > 0)
			speed -= .01;

		else if (speed < 0)
			speed += .01;

		bg2d.update(speed,0);
	}

	@Override
	public void draw() {
		Graphics2D g = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
		render(g);
		g.dispose();
		canvas.getBufferStrategy().show();
	}

	// Draw all elements to the graphic buffer
	protected void render(Graphics2D g){

		// Update all parallax background layers
		bg2d.draw(g,canvas);

		// Draw text to the screen
		fontWriter.drawString("Speed: " + speed*10, 20, 40);
		//fontWriter.drawString("Load Game", 400, 430);
		//fontWriter.drawString("Options", 400, 460);
		//fontWriter.drawString("Exit", 400, 490);
	}

}