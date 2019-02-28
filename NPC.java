import org.jsfml.graphics.*;
import org.jsfml.system.*;

 abstract class NPC extends ImageActor //This consists of both Enemies and Friendlies enemy.java is redundant???
	{
		String ID; 
		int health;
		int armour;
		String[] type; //Element of enemy -- 6 types {electric,star,cryo,warp,laser,galaxy} 1 indicates type
		String[] defaultType;
		int dropCount; //Number of drops when killed
		private Image iBackground;
		private Background background;
		private int[] directionDistance = new int[] {0,0,0,0}; //Compass clockwise
		private int[] oldDirectionDistance = new int[] {0,0,0,0}; //Compass clockwise
		private float highest = 0; //Highest distance from border
		private float lowest = 5000; //Lowest distance from border
		private String tempCompass = " ";
		private String compass = " ";
		private boolean firstLoop = false;
		private  int screenWidth  = 1024;
		private  int screenHeight = 768;
		private boolean finalPath = false;
		private boolean isDead = false;
		private int frame = 0;// Frame used in animation
		private Clock animationTime = new Clock();
		
		NPC(float xPixelsPerSecond, float yPixelsPerSecond,int r, String textureFile, Background background)
		{
	
			super(background.getStartingX(),background.getStartingY(),r,textureFile);
			super.setDx(xPixelsPerSecond);
			super.setDy(yPixelsPerSecond);
			this.iBackground = background.getBackground();
			this.background = background;
			this.ID = ID;
			this.health = health;
			this.armour = armour;
		}
		void setFrame(int i){
			frame = i;
		}
		
		int getFrame(){
			return frame;
		}
		/**
		* Sets the health to 0 and remove variable to true so the NPC can be removed from screen
		*/
		void die()
		{
			health=0;
			remove = true;
		}
		/**
		* Returns isDead variable
		* @return isDead boolean that determines if NPC is dead
		*/
		boolean getIsDead()
		{
			return isDead;
		}
		/**
		* Sets isDead variable to true
		*/
		void setIsDead()
		{
			isDead = true;
		}
		/**
		* Returns the animationTime as milliseconds
		* @return animationTime as seconds
		*/
		float getAnimationTime()
		{
			return animationTime.getElapsedTime().asMilliseconds();
		}
	
		/**
		* Restarts animationTime
		*/
		void resetAnimationTime()
		{
			animationTime.restart();
		}
		/**
		* Returns ID 
		* @return ID The ID representing tower subclass
		*/
		String getID()
		{
			return ID;
		}
		
		/**
		* Reduces the armour variable by the amount passed as a paramter
		* @param damage The value deducted from armour
		*/
		void setArmour(int damage)
		{
			//Need to calculate new damage
			armour -= damage;
		}
		/**
		* Returns the value of armour
		* @return armour The armour value
		*/
		int getArmour()
		{
			return armour;
		}
		/**
		* Returns the amount of items the NPC can drop
		* @return dropCount The number of items the NPC can drop
		*/
		int getDropCount()
		{
			return dropCount;
		}
		/**
		* Sets the type of the NPC to 'null'
		*/
		void setTypeNull()
		{
			type = new String[] {"null"};
		}
		/**
		* Sets the type of the NPC back to its defaults type(s)
		*/
		void setDefaultType()
		{
			type = defaultType;
		}
		/**
		* Returns the type(s) of the NPC
		* @return typeList String containing all the types of the NPC
		**/
		String getType()
		{
			String typeList = " ";
			for(int i=0;i<type.length;i++)
			{
				typeList += type[i]+"\n";
			}
			return typeList;
		}
		/**
		* Calculates the multiplier to be used in a calculation with the tower damage 
		* @param towerType The type of the tower that the damage is being calculated for
		*/
		float calculateDamage(String towerType)
		{
			float multiplier = 1;
			
			if(towerType.equals("electric"))
			{
				for(int i=0;i<type.length;i++)
				{
					if(type[i].equals("electric")) multiplier /= 2;
					else if(type[i].equals("star")) multiplier *= 2;
					else if(type[i].equals("warp")) multiplier *= 2;
					else if(type[i].equals("laser")) multiplier *= 2;
					else if(type[i].equals("galaxy")) multiplier *= 2;
				
				}
			}
			else if(towerType.equals("star"))
			{
				for(int i=0;i<type.length;i++)
				{
					if(type[i].equals("electric")) multiplier *= 2;
					else if(type[i].equals("cryo"))  multiplier /= 2;
					else if(type[i].equals("warp")) multiplier *= 2;
					else if(type[i].equals("laser")) multiplier *= 2;
					else if(type[i].equals("galaxy")) multiplier /= 2;
				}
			}
			else if(towerType.equals("cryo"))
			{
				for(int i=0;i<type.length;i++)
				{
					if(type[i].equals("star"))  multiplier *= 2;
					else if(type[i].equals("cryo"))  multiplier /= 2;
					else if(type[i].equals("laser")) multiplier /= 2;
					else if(type[i].equals("galaxy")) multiplier *= 2;
				}
			}
			else if(towerType.equals("warp"))
			{
				for(int i=0;i<type.length;i++)
				{
					if(type[i].equals("electric")) multiplier /= 2;
					else if(type[i].equals("star"))  multiplier *= 2;
					else if(type[i].equals("laser")) multiplier *= 2;
				}
			}
			else if(towerType.equals("laser"))
			{
				for(int i=0;i<type.length;i++)
				{
					if(type[i].equals("star"))  multiplier *= 2;
					else if(type[i].equals("cryo"))  multiplier *= 2;
					else if(type[i].equals("warp")) multiplier /= 2;
					else if(type[i].equals("laser")) multiplier /= 2;
					else if(type[i].equals("galaxy")) multiplier *= 2;
				}
			}
			else if(towerType.equals("galaxy"))
			{
				for(int i=0;i<type.length;i++)
				{
					if(type[i].equals("electric")) multiplier /= 2;
					else if(type[i].equals("star"))  multiplier *= 2;
					else if(type[i].equals("cryo"))  multiplier *= 2;
					else if(type[i].equals("warp")) multiplier /= 2;
					else if(type[i].equals("laser")) multiplier /= 2;
					else if(type[i].equals("galaxy")) multiplier *= 2;
				}
			}
			
			return multiplier;
		}
		/**
		* Reduces the health variable by the damage calculated within this method
		* @param towerType The type of the tower causing damage
		* @param damage The damage of the tower without considering type
		*/
		void setHealth(String towerType, int damage)
		{
		
			damage = (int) (damage * calculateDamage(towerType));
		

			if(armour>0)
			{
				if(armour<damage) health -= (damage-armour);	
				setArmour(damage);
			}
			else 
			{
				health -= damage;
			}
		}
		/**
		* Returns the value of the health variable
		* @return health The value of the health variable
		*/
		int getHealth()
		{
			return health;
		}
		/**
		* Calculates how much the NPC should move depending on direction and independent of framerate
		* @param minx The minimum x value
		* @param miny The minimum y value
		* @param maxx The maximum x value
		* @param maxy The maximum y value
		* @param time The time since the last game loop
		*/
		void calcMove(int minx, int miny, int maxx, int maxy,float time)
		{
			pathCheck();
			if(highest-(dx*time)<lowest || highest-(dy*time)<lowest)
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
				
				if(compass.equals("up") || compass.equals("down"))highest-= dy*time;
				if(compass.equals("left") || compass.equals("right"))highest-= dx*time;
			}
		}
		/**
		* Determines direction to move in whilst also constantly cheking the sides of the path for any changes
		*/
		void pathCheck()
		{
				Color borderColour = background.getBorderColour();
				Color borderIntersection = background.getIntersectionColour();
				int accuracy = background.getAccuracy(); //How similar the colours colours are		

				//Calculates the distances to determine the new direction
				if(highest<=lowest)
				{
					//Works out direction
					firstLoop = true;
					calculateDistance("left","right",borderColour,borderIntersection,accuracy);
					calculateDistance("right","left",borderColour,borderIntersection,accuracy);
					calculateDistance("up","down",borderColour,borderIntersection,accuracy);
					calculateDistance("down","up",borderColour,borderIntersection,accuracy);
					oldDirectionDistance[0] = directionDistance[0];
					oldDirectionDistance[1] = directionDistance[1];
					oldDirectionDistance[2] = directionDistance[2];
					oldDirectionDistance[3] = directionDistance[3];
					compass = tempCompass;
				
				if(finalPath) lowest = 5;

					firstLoop=false;
				}//Constantly checks distance of the 2 closest sides until one side changes and then it reevaulates the lowest distance
				else
				{
					calculateDistance("left","right",borderColour,borderIntersection,accuracy);
					calculateDistance("right","left",borderColour,borderIntersection,accuracy);
					calculateDistance("up","down",borderColour,borderIntersection,accuracy);
					calculateDistance("down","up",borderColour,borderIntersection,accuracy);

					//lowest = side that doesnt change distance
					if(compass.equals("up") || compass.equals("down"))
					{
						changeLowest(3,1);
						changeLowest(1,3);

					}
					if(compass.equals("left") || compass.equals("right"))
					{
						changeLowest(0,2);
						changeLowest(2,0);
					}
					if(finalPath) lowest = 5;
				}
		}
		/**
		* Returns true if colours are similar
		* @return true
		*/
		boolean isSimilar(Color colour1, Color colour2, int accuracy)
		{	
			if(colour1.r < (colour2.r-accuracy) || colour1.r > (colour2.r+accuracy)) return false;			
			if(colour1.g < (colour2.g-accuracy) || colour1.g > (colour2.g+accuracy)) return false;			
			if(colour1.b < (colour2.b-accuracy) || colour1.b > (colour2.b+accuracy)) return false;
			return true;
		}
		/**
		* Changes the value of the lowest variable
		* @param compassOne The 1st direction
		* @param compassTwo The direction opposite compassOne
		*/
		void changeLowest(int compassOne, int compassTwo)
		{
			float tempLowest;
			float tempLowest2;
			//Only applies to intersection paths
			float difference = (highest-lowest);
	
			tempLowest = directionDistance[compassTwo];
			tempLowest2 = directionDistance[compassOne];
	
			if(oldDirectionDistance[compassOne] != directionDistance[compassOne])
			{
				if(directionDistance[compassTwo]<directionDistance[compassOne] && difference < 50)lowest = tempLowest;
				if(directionDistance[compassOne]<directionDistance[compassTwo] && difference < 50)lowest = tempLowest2;
				oldDirectionDistance[compassOne] = directionDistance[compassOne];
			}
		}
		/**
		* Calculates the distance the NPC is from the border in the driection passed in
		* @param direction1 The direction that distance is determined
		* @param oppositeDirection The direction opposite direction1
		* @param borderColour The colour of the border
		* @param borderIntersection The colour of the intersection
		* @param accuracy The value for how similar the colours need to be
		*/
		int calculateDistance(String direction1,String oppositeDirection, Color borderColour, Color borderIntersection, int accuracy)
		{
			int distance = 0;
			boolean collision = false;
			boolean intersection = false;
			boolean end = false;
			Color endColor = new Color(41,17,17);
			while(!collision)
			{
				if(direction1.equals("left") && x-distance<=150) break; 
				if(direction1.equals("right") && x+distance>=855) break;
				if(direction1.equals("up") && y-distance<=40) break;	
				if(direction1.equals("down") && y+distance>=765) break;
				//Dealing with intersection code
				intersection = false;
				if(direction1.equals("left"))intersection = isSimilar(iBackground.getPixel((int)Math.round(x-distance),(int)Math.round(y)),borderIntersection,accuracy);
				if(direction1.equals("right"))intersection = isSimilar(iBackground.getPixel((int)Math.round(x+distance),(int)Math.round(y)),borderIntersection,accuracy);
				if(direction1.equals("up"))intersection = isSimilar(iBackground.getPixel((int)Math.round(x),(int)Math.round(y-distance)),borderIntersection,accuracy);
				if(direction1.equals("down"))intersection = isSimilar(iBackground.getPixel((int)Math.round(x),(int)Math.round(y+distance)),borderIntersection,accuracy);
				if(intersection)
				{
					//Increase distance so ending point is correct
					if(highest<=lowest)
					{
						highest += 64;
					}
					//System.out.println("Intersection");
					distance += 64;
					continue;
				}
				//Dealing with exit code
				end = isSimilar(iBackground.getPixel((int)Math.round(x),(int)Math.round(y)),endColor,5);
				if(end)
				{
					die();
				}
				if(direction1.equals("left"))end = isSimilar(iBackground.getPixel((int)Math.round(x-distance),(int)Math.round(y)),endColor,5);
				if(direction1.equals("right"))end = isSimilar(iBackground.getPixel((int)Math.round(x+distance),(int)Math.round(y)),endColor,5);
				if(direction1.equals("up"))end = isSimilar(iBackground.getPixel((int)Math.round(x),(int)Math.round(y-distance)),endColor,5);
				if(direction1.equals("down"))end = isSimilar(iBackground.getPixel((int)Math.round(x),(int)Math.round(y+distance)),endColor,5);
				if(end && distance < 40)
				{
					finalPath=true;
				
				}
				
				//Dealing with movement code
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
		
			if(distance>highest && !compass.equals(oppositeDirection))  //Current direction is not oppositeSide
			{
				tempCompass = direction1;
				if(firstLoop)highest = distance;
			}
			if(distance<lowest  && !(distance<0) && compass.equals(" "))lowest = distance;
			return distance;
		}
	}