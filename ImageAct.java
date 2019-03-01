/**
 *
 * Class name: ImageActor
 *
 *
**/

import java.io.IOException;
import java.nio.file.*;
import org.jsfml.graphics.*;

public class ImageAct extends Actor {
	private Sprite image;
	public ImageAct(String textureFile) {
		//Load Image / Texture
		Texture imgTexture = new Texture();
		try {imgTexture.loadFromFile(Paths.get(textureFile));}
		catch (IOException e) {System.out.println("Couldn't read image.");}
			
		imgTexture.setSmooth(true);
		image = new Sprite(imgTexture);

		obj = image;
		setPosition = image :: setPosition;

	}
}