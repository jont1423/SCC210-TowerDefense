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

class Pathing {
	private static int screenWidth  = 1024;
	private static int screenHeight = 768;


	//
	// The Java install comes with a set of fonts but these will
	// be on different filesystem paths depending on the version
	// of Java and whether the JDK or JRE version is being used.
	//
	private static String JavaVersion = 
		Runtime.class.getPackage( ).getImplementationVersion( );
	private static String JdkFontPath =
		"C:\\Program Files\\Java\\jdk" + JavaVersion +
		"\\jre\\lib\\fonts\\";
	private static String JreFontPath =
		"C:\\Program Files\\Java\\jre" + JavaVersion +
		"\\lib\\fonts\\";

	private static int fontSize     = 48;
	private static String FontFile  = "LucidaSansRegular.ttf";
	private static String ImageFile = "enemy2.png";

	private static String Title   = "";
	private static String Message = "";

	private String FontPath;	// Where fonts were found

	private ArrayList<Actor> actors = new ArrayList<Actor>( );
	private final String backgroundImage = ("background3.png");
	private static int enemyCount = 0;

	private abstract class Actor {
		Drawable obj;
		IntConsumer rotate;
		BiConsumer<Float, Float> setPosition;

		float x  = 0;	// Current X-coordinate
		float y  = 0;	// Current Y-coordinate
		int r  = 0;	// Change in rotation per cycle
		float dx = 0;	// Change in X-coordinate per cycle
		float dy = 0;	// Change in Y-coordinate per cycle

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

		//
		// Render the object at its new position
		//
		void draw(RenderWindow w) {
			w.draw(obj);
		}
	}
	
	
	private abstract class ImageActor extends Actor {
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
		
	}
	
	private class NPC extends ImageActor //This consists of both Enemies and Friendlies enemy.java is redundant???
	{
		private String ID; 		//dx and dy dependent on enemyID
		private int health;
		private int armour;
		private int fireRate; //No of seconds until the next action can be performed
		private Image iBackground;
		private Background background;
		private int[] direction = new int[] {0,0,0,0}; //Compass clockwise
		private int[] directionDistance = new int[] {0,0,0,0}; //Compass clockwise
		private int[] oldDirectionDistance = new int[] {0,0,0,0}; //Compass clockwise
		private float highest = 0; //Highest distance from NPC
		private float lowest = 5000; //Lowest distance from NPC
		private String tempCompass = " ";
		private String compass = " ";
		private boolean firstLoop = false;
		//private boolean intersectionCollision = false;

		//Database will be used to retrieve ID, health and armour
		
		NPC(String ID,float xPixelsPerSecond, float yPixelsPerSecond,int r, String textureFile, Background background)
		{
	
			super(background.getStartingX(),background.getStartingY(),r,textureFile);
			super.setDx(xPixelsPerSecond);
			super.setDy(yPixelsPerSecond);
			this.iBackground = background.getBackground();
			this.background = background;
			this.ID = ID;
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
		}
		abstract performAttack() 
		if(cooldownTimer==0)
		{
			//callAnimationFunctions
			//calculateDamageTaken(Tower target)
			//Cooldown/fire rate
			cooldownTimer == fireRate
		}	*/
		void calcMove(int minx, int miny, int maxx, int maxy,float time)
		{
			pathCheck();
			if(highest-(dx*time)<lowest)
			{
				//To ensure lowest distance remains the same throughout - solves the issue dx/dy being too high
				if(direction[2]==1) y += (highest-lowest);
				else if(direction[0] == 1) y -= (highest-lowest);
				else if(direction[3] ==1) x -= (highest-lowest);
				else if(direction[1] == 1) x += (highest-lowest);
				highest = lowest;
			}
			else
			{
				if(direction[2]==1)	y += dy*time;
				else if(direction[0] == 1) y -= dy*time;
				else if(direction[3] ==1) x -= dx*time;
				else if(direction[1] == 1) x += dx*time;
				highest-= dy*time;
			}
		}

		void pathCheck() //Implemented by sub classes
		{
				//Hard-code values need to be changed for any map
				Color borderColour = background.getBorderColour();
				Color borderIntersection = background.getIntersectionColour();
				int accuracy = background.getAccuracy(); //How similar the colours colours are
				System.out.println("X: " + x + "Y " + y + "Colour: " + iBackground.getPixel((int)x,(int)y));
				System.out.println("highest: " +highest);
				System.out.println("lowest: " +lowest);			
				
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

					System.out.println("Compass: " + compass);
					System.out.println("Highest: " + highest);
					
					//Use for loops, maybe a function???
					for(int i=0;i<direction.length;i++)
					{
						direction[i] = 0;
					}
					if(compass.equals("left")) direction[3] = 1;
					else if(compass.equals("right")) direction[1] = 1;
					else if(compass.equals("up")) direction[0] = 1;
					else if(compass.equals("down"))	direction[2] = 1;

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
				System.out.println("LowestShouldBe: "+ lowest);
			}
		}
		
		int calculateDistance(String direction1,String oppositeDirection, Color borderColour, Color borderIntersection, int accuracy)
		{
			int distance = 0;
			boolean collision = false;
			while(!collision)
			{
				if(direction1.equals("left") && x-distance<=0) break;
				if(direction1.equals("right")&& x+distance>=screenWidth) break;
				if(direction1.equals("up")&& y-distance<=0) break;	
				if(direction1.equals("down")&& y+distance>=screenHeight) break;
				
				/*boolean intersectionCollision = false;
				if(direction1.equals("left"))intersectionCollision = isSimilar(iBackground.getPixel((int)Math.round(x-distance),(int)Math.round(y)),borderIntersection,accuracy);
				if(intersectionCollision)
				{
					//Always distance between intersectionColours * stretchX/Y
					distance+=45*scalingX;
					continue;
				}
				if(direction1.equals("right"))intersectionCollision = isSimilar(iBackground.getPixel((int)Math.round(x+distance),(int)Math.round(y)),borderIntersection,accuracy);
				if(intersectionCollision)
				{
					distance+=45*scalingX;
					continue;
				}
				if(direction1.equals("up"))intersectionCollision = isSimilar(iBackground.getPixel((int)Math.round(x),(int)Math.round(y-distance)),borderIntersection,accuracy);
				if(intersectionCollision)
				{
					distance+=35*scalingY;
					continue;
				}
				if(direction1.equals("down"))intersectionCollision = isSimilar(iBackground.getPixel((int)Math.round(x),(int)Math.round(y+distance)),borderIntersection,accuracy);
				if(intersectionCollision)
				{
					distance+=35*scalingY;
					continue;
				}*/

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
			System.out.println("direction: " + direction1+ "distance: " + distance);
		
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
				InputStream img1 = Pathing.class.getResourceAsStream(backgroundImage);
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
	
	public void run ( ) {

		//
		// Check whether we're running from a JDK or JRE install
		// ...and set FontPath appropriately.
		//
		if ((new File(JreFontPath)).exists( )) FontPath = JreFontPath;
		else FontPath = JdkFontPath;

		//
		// Create a window
		//
		RenderWindow window = new RenderWindow( );
		ContextSettings settings = new ContextSettings(8);
		window.create(new VideoMode(screenWidth, screenHeight),
				"Testing",
				WindowStyle.DEFAULT,
				settings);
				
		window.setFramerateLimit(60); // Avoid excessive updates

		//
		// Create some actors
		//
		//OriginX,OriginY,Rotation,filename,StartingArea,BorderColor,IntersectionColor,accuracy
		Background background1 = new Background(512,384,0, backgroundImage,new IntRect(128,53,175,63),new Color(125,0,18),new Color(118,63,140),20);
		//Background background1 = new Background(512,384,0, backgroundImage,new IntRect(50,95,73,132),new Color(239,4,161),new Color(255,255,255),20);
		actors.add(background1);														//128,175 //50,95,73,132
		Clock time = new Clock(); //Need to be done somewhere (as the game is running)
		Clock frameTime = new Clock(); //Movement is independent of framerate
		
		//actors.add(new NPC(String.valueOf(enemyCount),268f,268f,0,ImageFile,background1));
		// Main loop
		//
		while (window.isOpen( )) 
		{
			float elapsedTime = frameTime.restart().asSeconds();
			System.out.println("ElapsedTime :" + elapsedTime);
			
			// Clear the screen
			window.clear(Color.WHITE);
			
			// Move all the actors around
			for (Actor actor : actors) {
				actor.calcMove(0, 0, screenWidth, screenHeight,elapsedTime);
				actor.performMove( );
				actor.draw(window);						
			}
			//Enemies spawn every 5 or more milliseconds
			if(enemyCount < 250 && time.getElapsedTime().asMilliseconds() > 500)
			{
				actors.add(new NPC(String.valueOf(enemyCount),268f,268f,0,ImageFile,background1));
				enemyCount++;
				time.restart();
			}
			
			// Update the display with any changes
			window.display( );

			// Handle any events
			for (Event event : window.pollEvents( ))
			{
				if (event.type == Event.Type.CLOSED) {
				// the user pressed the close button
				window.close( );
				}
			}
		}
	}

	public static void main (String args[ ]) {
		Pathing p = new Pathing( );
		p.run( );
	}
}
/* To do List 
// Pathing now seems to work fine just need to deal with intersectionCollision
// Using scaling to deal with the problem needs to make sure it works for all sides - Works for 1st corner but not subsequent corners
// ***Bigger sprites obscure small sprites for visibility
// *** Games lags due to large amount of enemies
// Enemies not going the correct distance before changing direction e.g. enemy on right wall will go to the bottom
// Program Items
// ** Maybe try to return x and y to ints for simplicity
// Need to make enemies pass through/under intersections - Hardcoded only works if line sizes stay the same and map size stays the same
// ***Relies on border colour being in at least 3 directions
// ***Equal distance should be a non-issue if enemys always starts close or at the edge of the screen
// ***Only works for straight edges on the borders - Limitation
// *** If boundaryboxes are used then sprite <= lane size wont fit
// StartingArea and other values associated with the background will be stored with level
// Need to make easier to change between maps (use less hardcoded values)
*/