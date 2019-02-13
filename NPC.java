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
 class NPC extends ImageActor //This consists of both Enemies and Friendlies enemy.java is redundant???
	{
		private String ID; 		//dx and dy dependent on enemyID
		private int health;
		private int armour;
		private Image iBackground;
		private Background background;
		//private int[] direction = new int[] {0,0,0,0}; //Can be removed use compass variable instead
		private int[] directionDistance = new int[] {0,0,0,0}; //Compass clockwise
		private int[] oldDirectionDistance = new int[] {0,0,0,0}; //Compass clockwise
		private float highest = 0; //Highest distance from NPC
		private float lowest = 5000; //Lowest distance from NPC
		private String tempCompass = " ";
		private String compass = " ";
		private boolean firstLoop = false;
		private  int screenWidth  = 1024;
		private  int screenHeight = 768;
		//private boolean intersectionCollision = false;

		//Database will be used to retrieve ID, health and armour
		
		NPC(String ID,int health, int armour,float xPixelsPerSecond, float yPixelsPerSecond,int r, String textureFile, Background background)
		{
	
			super(background.getStartingX(),background.getStartingY(),r,textureFile);
			super.setDx(xPixelsPerSecond);
			super.setDy(yPixelsPerSecond);
			this.iBackground = background.getBackground();
			this.background = background;
			this.ID = ID;
			this.health = health;
			this.armour = armour;
			System.out.println("ScalingY: "+scalingY);
		}
		//Cant put into actor its too generic
		/*place() -- In actor
		{
			//Animaton functions in animation
			//call to actor .add() in actor
		}*/
		/*die()
		{
			//Animation funcions in animation
			//return (health <=0)
			 --->In windows//Call to actor .remove() in actor
		}*/

		void calcMove(int minx, int miny, int maxx, int maxy,float time)
		{
			pathCheck();
			if(highest-(dx*time)<lowest)
			{
				//To ensure lowest distance remains the same throughout - solves the issue dx/dy being too high
				if(compass.equals("down")) y += (highest-lowest);
				else if(compass.equals("up")) y -= (highest-lowest);
				else if(compass.equals("left")) x -= (highest-lowest);
				else if(compass.equals("right")) x += (highest-lowest);
				highest = lowest;
			}
			else
			{
				if(compass.equals("down"))	y += dy*time;
				else if(compass.equals("up")) y -= dy*time;
				else if(compass.equals("left")) x -= dx*time;
				else if(compass.equals("right")) x += dx*time;
				highest-= dy*time;
			}
		}

		void pathCheck() //Implemented by sub classes
		{
				//Hard-code values need to be changed for any map
				Color borderColour = background.getBorderColour();
				Color borderIntersection = background.getIntersectionColour();
				int accuracy = background.getAccuracy(); //How similar the colours colours are
				//System.out.println("X: " + x + "Y " + y + "Colour: " + iBackground.getPixel((int)x,(int)y));
				//System.out.println("highest: " +highest);
				//System.out.println("lowest: " +lowest);			

				//Need clearer maps
				/*if(isSimilar(iBackground.getPixel((int)Math.round(x),(int)Math.round(y)),borderIntersection,accuracy))
				{
					System.out.println("FNERUJNFERFNOUJEFNFNJ");
					//Need a x and y lane width variable from background
					if(compass.equals("left"))
					{
						x-= 45 * scalingY;
						System.out.println("Left Here");
					}
					if(compass.equals("right"))
					{				
						x+= 45 * scalingY;
						System.out.println("Right Here");
					}
					if(compass.equals("up")) y-= 40 * scalingX;
					if(compass.equals("down")) y+= 40 * scalingX;
					
				}*/
				//Calculates the distances to determine the new direction
				if(highest<=lowest)
				{
					//Works out direction
					firstLoop = true;
					calculateDistance("left","right",borderColour,accuracy);
					calculateDistance("right","left",borderColour,accuracy);
					calculateDistance("up","down",borderColour,accuracy);
					calculateDistance("down","up",borderColour,accuracy);
					oldDirectionDistance[0] = directionDistance[0];
					oldDirectionDistance[1] = directionDistance[1];
					oldDirectionDistance[2] = directionDistance[2];
					oldDirectionDistance[3] = directionDistance[3];
					compass = tempCompass;

					System.out.println("Compass: " + compass);
					System.out.println("Highest: " + highest);

					firstLoop=false;
				}//Constantly checks distance of the 2 closest sides until one side changes and then it reevaulates the lowest distance
				else
				{
					calculateDistance("left","right",borderColour,accuracy);
					calculateDistance("right","left",borderColour,accuracy);
					calculateDistance("up","down",borderColour,accuracy);
					calculateDistance("down","up",borderColour,accuracy);

					//lowest = side that doesnt change distance
					if(compass.equals("up") || compass.equals("down"))
					{
						changeLowest(3,1,false);
						changeLowest(1,3,false);

					}
					if(compass.equals("left") || compass.equals("right"))
					{
						changeLowest(0,2,true);
						changeLowest(2,0,true);
					}
					
				}
		}
		//returns true if colours are similar
		boolean isSimilar(Color colour1, Color colour2, int accuracy)
		{	
			if(colour1.r < (colour2.r-accuracy) || colour1.r > (colour2.r+accuracy)) return false;			
			if(colour1.g < (colour2.g-accuracy) || colour1.g > (colour2.g+accuracy)) return false;			
			if(colour1.b < (colour2.b-accuracy) || colour1.b > (colour2.b+accuracy)) return false;
			return true;
		}
		
		void changeLowest(int compassOne, int compassTwo,boolean multiply)
		{
			float tempLowest;
			float tempLowest2;
			if(!multiply)
			{
				tempLowest = directionDistance[compassTwo]/1.33f;
				tempLowest2 = directionDistance[compassOne]/1.33f;
			}
			else
			{
				tempLowest = directionDistance[compassTwo]*1.33f;
				tempLowest2 = directionDistance[compassOne]*1.33f;
			}
			if(oldDirectionDistance[compassOne] != directionDistance[compassOne])
			{
				if(directionDistance[compassTwo]<directionDistance[compassOne])lowest = tempLowest;
				if(directionDistance[compassOne]<directionDistance[compassTwo])lowest = tempLowest2;
				oldDirectionDistance[compassOne] = directionDistance[compassOne];
				//System.out.println("LowestShouldBe: "+ lowest);
			}
		}
		
		int calculateDistance(String direction1,String oppositeDirection, Color borderColour, int accuracy)
		{
			int distance = 0;
			boolean collision = false;
			while(!collision)
			{
				if(direction1.equals("left") && x-distance<=0) break;
				if(direction1.equals("right")&& x+distance>=screenWidth) break;
				if(direction1.equals("up")&& y-distance<=0) break;	
				if(direction1.equals("down")&& y+distance>=screenHeight) break;
				
				if(direction1.equals("left"))collision = isSimilar(iBackground.getPixel((int)Math.round(x-distance),(int)Math.round(y)),borderColour,accuracy);
				if(direction1.equals("right"))collision = isSimilar(iBackground.getPixel((int)Math.round(x+distance),(int)Math.round(y)),borderColour,accuracy);
				if(direction1.equals("up"))collision = isSimilar(iBackground.getPixel((int)Math.round(x),(int)Math.round(y-distance)),borderColour,accuracy);
				if(direction1.equals("down"))collision = isSimilar(iBackground.getPixel((int)Math.round(x),(int)Math.round(y+distance)),borderColour,accuracy);
				distance++;
			}
			if(direction1.equals("left")) directionDistance[3]=distance;
			if(direction1.equals("right")) directionDistance[1]=distance;
			if(direction1.equals("up")) directionDistance[0]=distance;
			if(direction1.equals("down")) directionDistance[2]=distance;
		//	System.out.println("direction: " + direction1+ "distance: " + distance);
		
			if(distance>highest && !compass.equals(oppositeDirection))  //Current direction is not oppositeSide
			{
				tempCompass = direction1;
				//NEED AN IF STATEMENT FOR THIS (can be executed unless highest<=lowest, which its not at the start)
				if(firstLoop)highest = distance;
			}
			if(distance<lowest  && !(distance<0) && compass.equals(" "))lowest = distance;
			return distance;
		}
	}
	
	//Getting stuck due to rounding in get pixel (test ceil and floor)