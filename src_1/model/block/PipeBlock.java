package model.block;

import java.awt.image.BufferedImage;

import controller.ControllerGame;
import model.other.Animation;

public class PipeBlock extends ModelBlock {

	public PipeBlock(ControllerGame map,int type,int row,int col) {
		super(map,type,row,col);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void addAnimation() {

		if (getType() == 7) {
			BufferedImage[] images = { image[10] };

			ani = new Animation(images, -1);
		} else {
			BufferedImage[] images = { image[11] };

			ani = new Animation(images, -1);
		}
	}


}
