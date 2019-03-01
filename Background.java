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
	
		/**
		* Constructor of Background
		* @param x The x position of the map
		* @param y The y position of the map
		* @param r The rotation of the map
		* @param textureFile The image file of the map
		* @param startingArea The starting area of the NPC's
		* @param borderColor The colour of the border 
		* @param intersectionColor The colour of the intersection NPC's cross
		* @param placementColor1 The 1st colour towers can be placed on
		* @param placementColor2 The 2nd colour towers can be placed on
		* @param accuracy The value of how similar the colours should be
		*/
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

			scalingX = (background.getSize().x / 512f);
			scalingY = (background.getSize().y / 512f);
		}
		
		void calcMove(int minx, int miny, int maxx, int maxy, float time)
		{
		}
		/**Returns a random x position within the startingArea
		* @return A random x position within startingArea
		*/
		public int getStartingX()
		{
			Random r = new Random();
			return r.nextInt(startingArea.width) + startingArea.left;
		}
		/**Returns a random y position within the startingArea
		* @return A random y position within startingArea
		*/
		public int getStartingY()
		{
			Random r = new Random();
			return r.nextInt(startingArea.height)+startingArea.top;
		}
		/**
		* Returns Image Object
		* @return background An image object containing data about the background file
		*/
		public Image getBackground()
		{
			return background;
		}
		/**
		* Returns the colour of the border on the map
		* @return borderColor The colour of the border on the map
		*/
		public Color getBorderColour()
		{
			return borderColor;
		}
		/**
		* Returns the colour of the intersection on the map
		* @return intersectionColor The colour of the border on the map
		*/
		public Color getIntersectionColour()
		{
			return intersectionColor;
		}
		/**
		* Returns the 1st placement colour of the border on the map
		* @return placementColor1 The 1st colour of the placement on the map
		*/
		public Color getPlacementColor1()
		{
			return placementColor1;
		}
		/**
		* Returns the 2nd placement colour of the border on the map
		* @return placementColor2 The 2nd colour of the placement on the map
		*/
		public Color getPlacementColor2()
		{
			return placementColor2;
		}
		/**
		* Returns the accuracy variable
		* @return accuracy A value representing how similar colour should be
		*/
		public int getAccuracy()
		{
			return accuracy;
		}

	}