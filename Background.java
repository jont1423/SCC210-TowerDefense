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
public class Background extends ImageActor
	{
		//These details need to be read from file
		IntRect startingArea; //Area that enemy sprites can spawn
		Color borderColor; //Color of the border
		Color intersectionColor; //Color of the intersection
		int accuracy; // How similar should the colours be
		
		Image background = new Image();
	
		
		Background(float x, float y, int r, String textureFile, IntRect startingArea, Color borderColor, Color intersectionColor, int accuracy)
		{
			super(x,y,r,textureFile);
			super.setDx(0);
			super.setDy(0);
			//Get background details
			try
			{
				InputStream img1 = Pathing.class.getResourceAsStream(textureFile);
				background.loadFromStream(img1);
			}
			catch(IOException e)
			{
				System.out.println("Couldnt read image");
			}
			this.startingArea = startingArea; 
			this.borderColor = borderColor;
			this.intersectionColor = intersectionColor;
			this.accuracy = accuracy;
			System.out.println("BackgroundSizeX: " + background.getSize().x);
			System.out.println("BackgroundSizeY: " + background.getSize().y);
		
	
			scalingX = (background.getSize().x / 512f);
			scalingY = (background.getSize().y / 512f);
		}
		
		void calcMove(int minx, int miny, int maxx, int maxy, float time)
		{
		}
		
		public int getStartingX()
		{
			Random r = new Random();
			return r.nextInt((startingArea.width - startingArea.left)+1)+startingArea.left;
		}
		public int getStartingY()
		{
			Random r = new Random();
			return r.nextInt((startingArea.height - startingArea.top)+1)+startingArea.top;
		}
			
		public Image getBackground()
		{
			return background;
		}
		
		public Color getBorderColour()
		{
			return borderColor;
		}

		public Color getIntersectionColour()
		{
			return intersectionColor;
		}
		
		public int getAccuracy()
		{
			return accuracy;
		}

	}