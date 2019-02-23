import java.io.IOException;
import java.nio.file.*;
import org.jsfml.system.*;
import org.jsfml.graphics.*;

abstract class ImageActor extends Actor {
		private Sprite img;
		float scalingX;
		float scalingY;

		public ImageActor(float x, float y, int r, String textureFile) {
			//
			// Load image/ texture
			//
			Texture texture = getTextureFromFile(textureFile);
			img = new Sprite(texture);
			img.setOrigin(Vector2f.div(new Vector2f(texture.getSize()), 2));

			this.x = x;
			this.y = y;
			this.r = r;

			//
			// Store references to object and key methods
			//
			obj = img;
			rotate = img::rotate;
			setPosition = img::setPosition;
		}
		
		/**
		 * This function gets a texture from file location
		 * 
		 * @param textureFile This is the location of the file that contains the texture data
		 * @return Returning the new texture
		 */
		public Texture getTextureFromFile(String textureFile) {
			// Creating a new empty texture
			Texture imgTexture = new Texture();
			// Attempting to load the texture from file
			try {
				imgTexture.loadFromFile(Paths.get(textureFile));
			} catch (IOException ex) {
				ex.printStackTrace( );
			}
			// Setting the texture to be smooth and returning it
			imgTexture.setSmooth(true);
			return imgTexture;			
		}
		
		/**
		 * This function takes a texture file and updates the current sprites texture
		 * 
		 * @param textureFile This is the location of the file that contains the texture data
		 */
		public void changeSpriteImage(String textureFile) {
			// Creating a new texture from the textureFile
			Texture texture = getTextureFromFile(textureFile);
			// Setting the new texture and updating the size of the sprite if necessary
			img.setTexture(texture, true);
		}
		
		public void setDx(float newDx)
		{
			this.dx = newDx;
		}
		
		public void setDy(float newDy)
		{
			this.dy = newDy;
		}
		
		public Sprite getImg()
		{
			return img;
		}
		
}