/**
 * Created by daniel on 6/17/14.
 */
public abstract class GameLoop implements Runnable
{

	private boolean runFlag = false;
	private Stats stats = new Stats();
	private boolean keepStats = false;
	private double delta;

	GameLoop(double delta) {
		this.delta = delta;
	}

	/**
	* Begin the game loop
	*/
	public void run()
	{
		runFlag = true;

		// load resources and such
		startup();

		// convert the time to seconds
		double nextTime = (double)System.nanoTime() / 1000000000.0;

		// Loop is guaranteed to update at least 1 time every maxTimeDiff seconds
		double maxTimeDiff = 0.5;

		// skippedFrames keeps track of how many frames we've dropped trying to keep up
		// with the logic calculations
		int skippedFrames = 1;

		// Loop will drop frames up to maxSkippedFrames times
		int maxSkippedFrames = 5;

		// keeps track of our fps
		int frames = 0;
		double secondTick = 0;

		// Here goes the main game loop!
		while(runFlag)
		{
			// convert the time the loop was started to seconds
			double currTime = (double)System.nanoTime() / 1000000000.0;

			if(keepStats && secondTick < currTime)
			{
				secondTick += 1;
				stats.addMessage("FPS",frames);
				frames = 0;
			}


			if((currTime - nextTime) > maxTimeDiff)
				nextTime = currTime;

			if(currTime >= nextTime)
			{
				// assign the time for the next update
				nextTime += delta;
				update();

				// check if we have time to draw, or draw anyway if we haven't drawn in a while
				if((currTime < nextTime) || (skippedFrames > maxSkippedFrames))
				{
					draw();
					skippedFrames = 1;
					frames++;
				}
				else
				{
					skippedFrames++;
					if(keepStats)
						stats.addMessage("Dropped Frames",skippedFrames);
				}
			}
			else
			{
				// calculate the time to sleep
				int sleepTime = (int)(1000.0 * (nextTime - currTime));

				// check if we are behind
				if(sleepTime > 0)
				{
					try
					{
						Thread.sleep(sleepTime);
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		shutdown();
	}

	public void stop()
	{
		runFlag = false;
	}

	public abstract void startup();
	public abstract void shutdown();
	public abstract void update();
	public abstract void draw();
}
