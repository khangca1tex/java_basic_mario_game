package model.block;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.ControllerGame;
import model.other.Animation;

public abstract class ModelBlock {

	private int type;
	protected boolean isBreak = false;
	public boolean rise = false;
	protected int x;
	protected int y;
	protected Animation ani;
	protected BufferedImage[] image;
	protected ControllerGame map;
	private int specialType = 0;

	public ModelBlock(ControllerGame map, int type, int row, int col) {
		this.type = type;
		this.map = map;
		setLocation(col, row);

		addSprite();

		addAnimation();
	}

	private void addSprite() {

		image = new BufferedImage[12];

		BufferedImage im = null;
		try {
			im = ImageIO.read(getClass().getResourceAsStream("/Sprite/block.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 12; i++) {
			image[i] = im.getSubimage(16 * i, 0, 16, 16);
		}
	}

	public BufferedImage getImage() {
		ani.update();
		return ani.getImage();
	}

	public int getType() {
		return type;
	}

	protected void setLocation(int x, int y) {
		this.y = y * ControllerGame.tileSize;
		this.x = x * ControllerGame.tileSize;

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	protected abstract void addAnimation();

	public void isTouch(boolean isLarge) {

	}

	public void setBreak(boolean isBreak) {
		this.isBreak = isBreak;
	}

	public boolean isBreak() {
		return isBreak;
	}

	public void update() {

	}

	public void setSpecialType(int type) {
		this.specialType = type;
	}

	public int getSpeicalType() {
		return specialType;
	}

}
