/**
 *
 * Class name: ImageActor
 *
 *
**/

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.function.*;
import java.io.InputStream;

import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.audio.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.graphics.Image;

public class ImageAct extends Actor {
	private Sprite image;
	public ImageAct(String textureFile) {
		//Load Image / Texture
		Texture imgTexture = new Texture();
		try {imgTexture.loadFromFile(Paths.get(textureFile));}
		catch (IOException e) {System.out.println("Couldn't read image.");}
			
		imgTexture.setSmooth(true);
		image = new Sprite(imgTexture);

		//this.xPosition = x;
		//this.yPosition = y;
			
		item = image;
		setPosition = image :: setPosition;
		//setPosition.accept(x, y);
	}
}