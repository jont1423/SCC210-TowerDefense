import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.*;
import java.lang.Math;

import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
public class Bubble extends Actor { //Maybe extend with Radius class
		private CircleShape circle;

		private int radius;

		public Bubble(float x, float y, int radius, Color c,
				int transparency) {
			circle = new CircleShape(radius);
			//circle.setFillColor(new Color(c, transparency));
			circle.setFillColor(Color.TRANSPARENT); //Radius class
			circle.setOutlineColor(c); //Radius class
			circle.setOutlineThickness(2f); //Radius class
			circle.setOrigin(radius, radius);

			this.x = x;
			this.y = y;
			this.radius = radius;

			//
			// Store references to object and key methods
			//
			obj = circle;
			rotate = circle::rotate;
			setPosition = circle::setPosition;
		}

		//
		// Default method typically assumes a rectangle,
		// so do something a little different
		//
	
		public void calcMove(int minx, int miny, int maxx, int maxy,float time) {

		}
		//Radius class
		public void move(float npcX, float npcY)
		{
			x = npcX;
			y = npcY;
		}
		
}