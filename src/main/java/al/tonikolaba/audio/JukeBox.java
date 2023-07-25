package al.tonikolaba.audio;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import al.tonikolaba.handlers.LoggingHelper;

/**
 * @author ArtOfSoul
 */

public class JukeBox {

	public static HashMap<String, Clip> clips;
	private static int gap;
	private static boolean mute = false;

	public JukeBox() {
		throw new IllegalStateException("Utility Class");
	}

	public static void init() {
		clips = new HashMap<>();
		gap = 0;
	}

	public static void load(String s, String n) {
		// if (clips.get(s) != null)
		// return;
		final Clip clip;
		try {
			final AudioInputStream ais = AudioSystem.getAudioInputStream(Objects.requireNonNull(JukeBox.class.getResourceAsStream(s)));
			final AudioFormat baseFormat = ais.getFormat();
			final AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
					baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
			final AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
			clips.put(n, clip);
		} catch (Exception e) {
			LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	public static void play(String s) {
		play(s, gap);
	}

	public static void play(String s, int i) {
		if (mute)
			return;
		Clip c = clips.get(s);
		if (c == null)
			return;
		if (c.isRunning())
			c.stop();
		c.setFramePosition(i);
		while (!c.isRunning())
			c.start();
	}

	public static void stop(String s) {
		if (clips.get(s) == null)
			return;
		if (clips.get(s).isRunning())
			clips.get(s).stop();
	}

	public static void resume(String s) {
		if (mute)
			return;
		if (clips.get(s).isRunning())
			return;
		clips.get(s).start();
	}

	public static void loop(String s) {
		loop(s, gap, gap, clips.get(s).getFrameLength() - 1);
	}

	public static void loop(String s, int frame) {
		loop(s, frame, gap, clips.get(s).getFrameLength() - 1);
	}

	public static void loop(String s, int start, int end) {
		loop(s, gap, start, end);
	}

	public static void loop(String s, int frame, int start, int end) {
		stop(s);
		if (mute)
			return;
		clips.get(s).setLoopPoints(start, end);
		clips.get(s).setFramePosition(frame);
		clips.get(s).loop(Clip.LOOP_CONTINUOUSLY);
	}

	public static void setPosition(String s, int frame) {
		clips.get(s).setFramePosition(frame);
	}

	public static int getFrames(String s) {
		return clips.get(s).getFrameLength();
	}

	public static int getPosition(String s) {
		return clips.get(s).getFramePosition();
	}

	public static void close(String s) {
		stop(s);
		clips.get(s).close();
	}

}