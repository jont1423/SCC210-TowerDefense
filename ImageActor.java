import java.io.IOException;
import java.nio.file.*;
import org.jsfml.system.*;
import org.jsfml.graphics.*;

abstract class ImageActor extends Actor {
		private Sprite img;
		float scalingX;
		float scalingY;
		/**
		* Constructor of ImageActor
		* @param x The x position of the ImageActor object
		* @param y The y position of the ImageActor objects
		* @param r The rotation of ImageActor objects
		* @param textureFile The file path to sprite
		*/
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
		/**
		* Sets the value Dx to the parameter passed int
		* @param newDx The new value of dx
		*/
		public void setDx(float newDx)
		{
			this.dx = newDx;
		}
		/**
		* Sets the value Dy to the parameter passed int
		* @param newDy The new value of dy
		*/
		public void setDy(float newDy)
		{
			this.dy = newDy;
		}
		/**
		* Returns sprite object
		* @return img The sprite of the ImageActor
		*/
		public Sprite getImg()
		{
			return img;
		}
		
		/**
		* Animation method that changes the current view of the sprite's spritesheet
		* @param left The left most point of the sprite to read given in pixels (int)
		* @param height The total height of the sprite in pixels (int)
		* @param imgType The type of sprite, used for different sizes of animation (1 for Enemies, 2 for Towers, 3 for Ships)
		*/
		public void anim(int left, int height, int imgType)
		{	
			//If enemy
			if(imgType ==1){
					//Read the given coordinates of the sprite's spritesheet image
					IntRect rect = new IntRect(left,0,32,32);
					//Set the new sprite image to the sprite
					img.setTextureRect(rect);
					//Set the new origin (sprite centre) to the new sprite image
					img.setOrigin ( new Vector2f(16,16));
			
			//Else if tower
			}else if(imgType == 2){
					//Read the given coordinates of the sprite's spritesheet image
					IntRect rect = new IntRect(left,0,128,138);
					//Set the new sprite image to the sprite
					img.setTextureRect(rect);
					//Set the new origin (sprite centre) to the new sprite image
					img.setOrigin ( new Vector2f(64,height));
			
			//Else if ship	
			}else if(imgType == 3){			
					//Read the given coordinates of the sprite's spritesheet image
					IntRect rect = new IntRect(left,0,48,465);
					//Set the new sprite image to the sprite
					img.setTextureRect(rect);
					//Set the new origin (sprite centre) to the new sprite image
					img.setOrigin ( new Vector2f(24,height));
			
			}
			
		}
		
}
