package model.other;

import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {
	private HashMap<String, Clip> clips;
	private int startFrame;
	private boolean mute = false;

	public Music() {
		init();

		putMusicIntoGame();
	}

	private void putMusicIntoGame() {
		load("/Music/overworld.wav", "overworld");
		load("/Music/blockbreak.wav", "blockbreak");
		load("/Music/blockhit.wav", "blockhit");
		load("/Music/coin.wav", "coin");
		load("/Music/death.wav", "death");
		load("/Music/fireball.wav", "fireball");
		load("/Music/gameover.wav", "gameover");
		load("/Music/jump.wav", "jump");
		load("/Music/mushroomappear.wav", "mushroomappear");
		load("/Music/mushroomeat.wav", "mushroomeat");
		load("/Music/shot.wav", "shot");
		load("/Music/stomp.wav", "enemydie");
		load("/Music/levelend.wav", "win");
		load("/Music/gameover.wav", "gameover");
	}

	public void init() {
		clips = new HashMap<String, Clip>();
		startFrame = 0;
	}

	public void load(String s, String n) {
		if (clips.get(n) != null)
			return;
		Clip clip;
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(Music.class.getResourceAsStream(s));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
					baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
			clips.put(n, clip);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play(String s) {
		if (mute)
			return;
		Clip c = clips.get(s);
		if (c == null)
			return;
		if (c.isRunning())
			c.stop();
		c.setFramePosition(0);
		while (!c.isRunning())
			c.start();
	}

	public void stop(String s) {
		if (clips.get(s) == null)
			return;
		if (clips.get(s).isRunning())
			clips.get(s).stop();
	}

	public void resume(String s) {
		if (mute)
			return;
		if (clips.get(s).isRunning())
			return;
		clips.get(s).start();
	}

	public void loop(String s) {
		loop(s, startFrame, startFrame, clips.get(s).getFrameLength() - 1);
	}

	private void loop(String s, int frame, int start, int end) {
		stop(s);
		if (mute)
			return;
		clips.get(s).setLoopPoints(start, end);
		clips.get(s).setFramePosition(frame);
		clips.get(s).loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void close(String s) {
		stop(s);
		clips.get(s).close();
	}
}
