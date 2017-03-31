package com.educonnect.common.beans;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageBean extends Bean {
	
	private Image image = null;
	
	public ImageBean( String payload, Image image ) {
		this.image = image;
		this.payload = payload;
	}
	
	public ImageBean( String payload, String imgFilePath ) throws IOException {
		this.payload = payload;
		this.image = ImageIO.read( new File( imgFilePath ) );
	}
	
	public Image getImage() {
		return image;
	}

}
