package model.entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.ControllerGame;
import view.View;

public abstract class Item extends ModelGame {

	protected Player mario;
	protected BufferedImage[] image;
	protected boolean remove;

	public Item(ControllerGame map, Player mario) {
		super(map);
		this.mario = mario;
		width = 40;
		height = 40;

		getSprite();
		addAnimation();
	}

	@Override
	public void update() {

		if (!mario.isDeath()) {
			
			if (this.getX() < 0 || this.getY() > View.HEIGHT) {
				remove = true;
			}
		}

	}

	protected abstract void addAnimation();

	private void getSprite() {
		image = new BufferedImage[15];

		BufferedImage im = null;
		try {
			im = ImageIO.read(getClass().getResourceAsStream("/Item/items.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 12; i++) {
			image[i] = im.getSubimage(16 * i, 0, 16, 16);
		}
	}

	public boolean isRemove() {
		return remove;
	}

}
