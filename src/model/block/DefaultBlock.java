package model.block;

import java.awt.image.BufferedImage;

import controller.ControllerGame;
import model.other.Animation;

public class DefaultBlock extends ModelBlock {

	//3
	public DefaultBlock(ControllerGame map,int type,int row,int col) {
		super(map,type, row,col);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void addAnimation() {
		BufferedImage[] images = { image[3] };
		ani = new Animation(images, -1);
	}


}
