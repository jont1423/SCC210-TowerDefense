import org.jsfml.graphics.*;

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
		private boolean finalPath = false;
	 	private int frame = 0;// Frame used in animation
		
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
	 
		void die()
		{
			health=0;
			remove = true;
			//Animation funcions in animation
			//return (health <=0)
			 //--->In windows//Call to actor .remove() in actor
		}
		
		String getID()
		{
			return ID;
		}
		

		void setArmour(int damage)
		{
			//Need to calculate new damage
			armour -= damage;
		}
		
		int getArmour()
		{
			return armour;
		}
		
		int getDropCount()
		{
			return dropCount;
		}
		
		void setTypeNull()
		{
			type = new String[] {"null"};
		}
		
		void setDefaultType()
		{
			type = defaultType;
		}
		
		String getType()
		{
			String typeList = " ";
			for(int i=0;i<type.length;i++)
			{
				typeList += type[i];
			}
			return typeList;
		}
		
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
				System.out.println("Multiplier: " + multiplier);
			return multiplier;
		}
		
		void setHealth(String towerType, int damage)
		{
			System.out.println("DamageBefore: " + damage);
			damage = (int) (damage * calculateDamage(towerType));
			System.out.println("DamageAfter: " + damage);

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
		
		int getHealth()
		{
			return health;
		}

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

		void pathCheck()
		{
				Color borderColour = background.getBorderColour();
				Color borderIntersection = background.getIntersectionColour();
				int accuracy = background.getAccuracy(); //How similar the colours colours are
			//	System.out.println("X: " + x + "Y " + y + "Colour: " + iBackground.getPixel((int)x,(int)y));
				//System.out.println("highest: " +highest);
				//System.out.println("lowest: " +lowest);			

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
				

				//System.out.println("Compass: " + compass);
				//System.out.println("Highest: " + highest);
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
						changeLowest(3,1,false);
						changeLowest(1,3,false);

					}
					if(compass.equals("left") || compass.equals("right"))
					{
						changeLowest(0,2,true);
						changeLowest(2,0,true);
					}
					if(finalPath) lowest = 5;
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
			//if(finalPath) return;
			float tempLowest;
			float tempLowest2;
			//Only applies to intersection paths
			float difference = (highest-lowest);
			if(!multiply)
			{
				tempLowest = directionDistance[compassTwo]; //No Constant if pathWidthY/pathWidthX = 1
				tempLowest2 = directionDistance[compassOne];
			}
			else
			{
				tempLowest = directionDistance[compassTwo];
				tempLowest2 = directionDistance[compassOne];
			}
			if(oldDirectionDistance[compassOne] != directionDistance[compassOne])
			{
				if(directionDistance[compassTwo]<directionDistance[compassOne] && difference < 50)lowest = tempLowest;
				if(directionDistance[compassOne]<directionDistance[compassTwo] && difference < 50)lowest = tempLowest2;
				oldDirectionDistance[compassOne] = directionDistance[compassOne];
				//System.out.println("LowestShouldBe: "+ lowest);
			}
		}
		
		int calculateDistance(String direction1,String oppositeDirection, Color borderColour, Color borderIntersection, int accuracy)
		{
			int distance = 0;
			boolean collision = false;
			boolean intersection = false;
			boolean end = false;
			Color endColor = new Color(41,17,17);
			while(!collision)
			{
				if(direction1.equals("left") && x-distance<=150) break; //THESE NUMBERS NEED TO BE SIZE OF THE MAP
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
		//	System.out.println("direction: " + direction1+ "distance: " + distance);
		
			if(distance>highest && !compass.equals(oppositeDirection))  //Current direction is not oppositeSide
			{
				tempCompass = direction1;
				if(firstLoop)highest = distance;
			}
			if(distance<lowest  && !(distance<0) && compass.equals(" "))lowest = distance;
			return distance;
		}
	}
	
	//Getting stuck due to rounding in get pixel (test ceil and floor)
