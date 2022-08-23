package model.block;

import java.awt.image.BufferedImage;

import controller.ControllerGame;
import model.other.Animation;

//1
public class GroundBlock extends ModelBlock {

	public GroundBlock(ControllerGame map,int type,int row,int col) {
		super(map,type,row,col);
	}

	@Override
	protected void addAnimation() {
		BufferedImage[] images = { image[0] };
		ani = new Animation(images, -1);
	}




}
