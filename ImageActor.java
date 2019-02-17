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
			Texture imgTexture = new Texture( );
			try {
				imgTexture.loadFromFile(Paths.get(textureFile));
			} catch (IOException ex) {
				ex.printStackTrace( );
			}
			imgTexture.setSmooth(true);

			img = new Sprite(imgTexture);
			img.setOrigin(Vector2f.div(
					new Vector2f(imgTexture.getSize()), 2));

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