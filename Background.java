import java.io.InputStream;
import java.io.IOException;
import java.util.Random;
import java.lang.Math;

import org.jsfml.graphics.*;
public class Background extends ImageActor
	{
		//These details need to be read from file
		private IntRect startingArea; //Area that enemy sprites can spawn
		private Color borderColor; //Color of the border
		private Color intersectionColor; //Color of the intersection
		private Color placementColor1; //Color of the intersection
		private Color placementColor2; //Color of the intersection
		private Color endColor; //Color of the intersection
		int accuracy; // How similar should the colours be
		
		Image background = new Image();
	
		
		Background(float x, float y, int r, String textureFile, IntRect startingArea, Color borderColor, Color intersectionColor,Color placementColor1,Color placementColor2,int accuracy)
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
			this.placementColor1 = placementColor1;
			this.placementColor2 = placementColor2;
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
			return r.nextInt(startingArea.width) + startingArea.left;
		}
		public int getStartingY()
		{
			Random r = new Random();
			return r.nextInt(startingArea.height)+startingArea.top;
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
		
		public Color getPlacementColor1()
		{
			return placementColor1;
		}
		
		public Color getPlacementColor2()
		{
			return placementColor2;
		}
		
		public int getAccuracy()
		{
			return accuracy;
		}

	}