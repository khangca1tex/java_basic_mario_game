package model.other;

import java.awt.image.BufferedImage;

public class Animation {

	private int delay;
	private int currentIm = 0;
	BufferedImage[] sprites;
	private int count = 0;

	public Animation(BufferedImage[] sprites, int f) {

		this.sprites = sprites;

		if (sprites.length == 1)
			f = -1;
		else
			delay = f;

	}

	public void changeAnimation(BufferedImage[] sprite) {
		this.sprites = sprite;
		currentIm = 0;
	}

	public void update() {

		if (delay == -1)
			return;
		count++;

		if (count > delay) {
			currentIm++;
			count = 0;
		}
		if (currentIm == sprites.length) {
			currentIm = 0;
		}
	}

	public BufferedImage getImage() {
		return sprites[currentIm];

	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
}
