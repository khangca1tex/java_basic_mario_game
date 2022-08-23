package model.other;

import controller.ControllerLevel;

public class PlayMusic implements IObserve {

	private Music music;

	public PlayMusic(ControllerLevel level) {

		if (level.isTurnOnMusic) {
			music = new Music();
			music.loop("overworld");
		}
	}

	@Override
	public void updateObserve(String s) {

		if (s.equals("death")) {
			music.stop("overworld");
			music.play("death");
		} else if (s.equals("continue")) {
			music.stop("death");
			music.loop("overworld");
		} else if (s.equals("win")) {
			music.stop("overworld");
			music.play("win");
		} else
			music.play(s);

	}

}
