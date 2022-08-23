package model.block;

import java.awt.image.BufferedImage;

import controller.ControllerGame;
import model.other.Animation;

public class PipeTop extends ModelBlock {

	public PipeTop(ControllerGame map,int type,int row,int col) {
		super(map,type,row,col);
	}

	@Override
	protected void addAnimation() {
		
		if (getType() == 5) {
			BufferedImage[] images = { image[8] };

			ani = new Animation(images, -1);
		} else {
			BufferedImage[] images = { image[9] };

			ani = new Animation(images, -1);
		}

	}


}
