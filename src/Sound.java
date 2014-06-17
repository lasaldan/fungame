/**
 * Created by lasaldan on 3/19/14.
 */

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Sound {

	private File url;
	private Clip clip;
	private AudioInputStream ais;

	public Sound(String filepath) {
		try {
			url = new File(filepath);
			clip = AudioSystem.getClip();

			ais = AudioSystem.getAudioInputStream( url );
			clip.open(ais);
		}
		catch (MalformedURLException x) {
			System.out.println("Bad URL for Loading Sound");
		}
		catch (UnsupportedAudioFileException x) {
			System.out.println("Unsupported Audio File");
		}
		catch (LineUnavailableException x) {
			System.out.println("Unable to Send data to Sound Line");
		}
		catch (IOException x) {
			System.out.println("Basic IO Exception reading sound file");
		}
	}

	public void play() {
		clip.start();
	}

	public void stop() {
		clip.stop();
	}

	public void loop(int times) {
		if( times == 0 )
			times = Clip.LOOP_CONTINUOUSLY;
		clip.loop(times);
	}
}
