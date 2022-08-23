package model.block;

import java.awt.image.BufferedImage;

import controller.ControllerGame;
import model.other.Animation;

public class BrickBlock extends ModelBlock {
	// 2
	private int defaultY;
	private boolean isPlayerLarge = false;

	private int moveHeight = -1;
	private int dy;
	private int moveHeightMax = -6;

	private BufferedImage[] endAni;

	public BrickBlock(ControllerGame map, int type, int row, int col) {
		super(map, type, row, col);
	}

	@Override
	protected void addAnimation() {

		BufferedImage[] images = { image[1] };
		ani = new Animation(images, -1);

		endAni = new BufferedImage[1];
		endAni[0] = image[7];

	}

	public void update() {

		if (isPlayerLarge == true) {
			isBreak = true;
			ani.changeAnimation(endAni);
			map.addMusic("blockbreak");
			return;
		}

		this.y += dy;
		if (dy <= moveHeightMax) {
			dy = moveHeightMax;
			rise = false;
			map.addMusic("blockhit");
		}

		if (rise) {
			dy += moveHeight;
		}

		if (dy != 0 && y != defaultY && !rise) {
			dy -= moveHeight;
			if (y <= defaultY) {
				y = defaultY;
				dy = 0;
			}
		}
	}

	private void move() {
		defaultY = this.y;
		rise = true;
		dy = moveHeight;

	}

	public void isTouch(boolean isLarge) {
		if (!isBreak) {
			move();
			isPlayerLarge = isLarge;
		}
	}

}
