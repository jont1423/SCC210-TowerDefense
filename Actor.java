import java.util.function.*;
import org.jsfml.graphics.*;

abstract class Actor {
		Drawable obj;
		IntConsumer rotate;
		BiConsumer<Float, Float> setPosition;

		float x  = 0;	// Current X-coordinate
		float y  = 0;	// Current Y-coordinate
		int r  = 0;	// Change in rotation per cycle
		float dx = 0;	// Change in X-coordinate per cycle
		float dy = 0;	// Change in Y-coordinate per cycle
		boolean remove = false;

		//
		// Work out where object should be for next frame
		void calcMove(int minx, int miny, int maxx, int maxy, float time)
		{
			
		}
		/**
		* Sets the position of the actor onscreen
		* @param x The x position onscreen
		* @param y The y position onscreen
		*/
		public void setLocation (Float x, Float y) {
			this.x = x;
			this.y = y;
			setPosition.accept((float) x, (float) y);
		}
		/**
		* Reposition the object
		*/
		void performMove( ) {
			rotate.accept(r);
			setPosition.accept(x, y);
		}
		/**
		* Determines whether the actor needs removing or not
		* @param width The width of the screen
		* @param height The height of the screen
		*/
		boolean needsRemoving(int width, int height) {
			if(remove)
				return true;
			if (x < 0 || x > width || y < 0 || y > height)
				return true;
			else
				return false;
		}

		/**
		* Render the object at its new position
		* @param w The window that the actors are drawn on
		*/
		void draw(RenderWindow w) {
			w.draw(obj);
		}
	}