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
		// Is point x, y within area occupied by this object?
		//
		// This should really be done with bounding boxes not points
		//

		boolean within (int x, int y) {
			// Should check object bounds here
			// -- we'd normally assume a simple rectangle
			//    ...and override as necessary
			return false;
		}
		//
		// Work out where object should be for next frame
		//
		abstract void calcMove(int minx, int miny, int maxx, int maxy, float time);
		

		//
		// Reposition the object
		//
		void performMove( ) {
			rotate.accept(r);
			setPosition.accept(x, y);
		}
		//Tower Method
		boolean needsRemoving(int width, int height) {
			if(remove)
				return true;
			if (x < 0 || x > width || y < 0 || y > height)
				return true;
			else
				return false;
		}

		//
		// Render the object at its new position
		//
		void draw(RenderWindow w) {
			w.draw(obj);
		}
	}