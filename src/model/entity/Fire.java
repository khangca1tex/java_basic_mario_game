package model.entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.ControllerGame;
import model.other.Animation;

public class Fire extends ModelGame {

	private boolean remove;
	private Player mario;
	private double maxHeight;
	private int preY;
	private int preDx;
	private int count;

	public Fire(ControllerGame map, Player mario) {
		super(map);
		this.mario = mario;
		addAnimation();
		width = 20;
		height = 20;
		maxHeight = -80;

		dy = 10;

		defineLocation();

	}

	private void defineLocation() {
		x = mario.getX();
		y = mario.getY() + 40;

		if (mario.isMoveRight()) {
			dx = 20;
		} else
			dx = -20;

		preDx = (int) dx;
	}

	@Override
	public void update() {
		checkMapCollision();
		checkChangeDirection();
		shouldRemove();
		killEnemies();

	}

	private void checkChangeDirection() {
		if (downleft || downright) {
			count++;
			dy = -10;
			dx = preDx;
			preY = y;
		} else if (dx==0) {
			remove = true;
		}

		if (y - preY <= maxHeight) {
			dy = 10;
			count++;
		}
	}

	private void shouldRemove() {
		if (count == 10) {
			remove = true;
		}
	}

	private void killEnemies() {
		for (int i = 0; i < map.getEnemies().size(); i++) {
			if (this.intersecs(map.getEnemies().get(i))) {
				map.getEnemies().get(i).setDie(true);
				this.remove = true;
			}
		}
	}

	private void addAnimation() {
		BufferedImage[] images = new BufferedImage[4];
		BufferedImage im = null;
		try {
			im = ImageIO.read(getClass().getResourceAsStream("/Item/fire.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 2; i++) {
			images[i] = im.getSubimage(8 * i, 0, 8, 8);
			images[i + 2] = im.getSubimage(8 * i, 8, 8, 8);
		}

		ani = new Animation(images, 2);

	}

	public boolean getRemove() {
		return this.remove;
	}

}
