package model.block;

import java.awt.image.BufferedImage;

import controller.ControllerGame;
import model.other.Animation;

public class QuestionBlock extends ModelBlock {

	private int defaultY;
	private int moveHeight = -1;
	private int dy;
	private int moveHeightMax = -6;
	private BufferedImage[] endAni;

	public QuestionBlock(ControllerGame map, int type, int row, int col) {
		super(map, type, row, col);
		defaultY = this.y;
	}

	@Override
	protected void addAnimation() {
		BufferedImage[] images = { image[4], image[5], image[6] };

		endAni = new BufferedImage[1];
		endAni[0] = image[2];

		ani = new Animation(images, 8);

	}

	public void update() {
		this.y += dy;
		if (dy <= moveHeightMax) {
			dy = moveHeightMax;
			rise = false;
		}

		if (rise) {
			dy += moveHeight;
		}

		if (dy != 0 && y != defaultY && !rise) {
			dy -= moveHeight;
			if (y <= defaultY) {
				y = defaultY;
				dy = 0;
				isBreak = true;
				ani.changeAnimation(endAni);
			}
		}
	}

	private void move() {

		rise = true;
		dy = moveHeight;

	}

	public void isTouch(boolean isLarge) {
		if (!isBreak) {
			move();
		}
	}

}
