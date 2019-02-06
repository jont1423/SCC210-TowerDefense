import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.*;

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
		private String ID;
		private int health;
		private int armour;
		private int fireRate; //No of seconds until the next action can be performed
		private Image iBackground;
		private Background background;
		private int[] direction = new int[] {0,0,0,0}; //Compass clockwise
		private float highest = 0;
		private float lowest = 500; //Not best way to determine a number
		private String tempCompass = " ";
		private String compass = " ";
		//dx and dy dependent on enemyID

		
		//Database will be used to retrieve ID, health and armour
		
		NPC(String ID,float xPixelsPerSecond, float yPixelsPerSecond,int r, String textureFile, Background background)
		{
			super(background.getStartingX(),background.getStartingY(),r,textureFile);
			super.setDx(xPixelsPerSecond);
			super.setDy(yPixelsPerSecond);
			this.iBackground = background.getBackground();
			this.background = background;
			this.ID = ID;
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
		void calcMove(int minx, int miny, int maxx, int maxy,float time) //Implemented by sub-classes using calcMove
		{
			//System.out.println());
			if(direction[2]==1)
			{
				y += dy*time;
				highest-= dy*time;
				//To ensure lowest distance remains the same throughout - solves the issue dx/dy being too high
				if(highest-(dx*time)<lowest)
				{
					y += (highest-lowest)*time;
					highest = lowest;
				}
		
			}
			else if(direction[0] == 1)
			{
				y -= dy*time;
				highest-= dy*time;
				//To ensure lowest distance remains the same throughout
				if(highest-(dx*time)<lowest)
				{
					y -= (highest-lowest)*time;
					highest = lowest;
				}
	
			}
			if(direction[3] ==1)
			{
				//x -= dx;
				//pixelsPerSecond * secondsElapseSinceLastFrame
				x -= dx*time;
				//System.out.println("X: " + x);
				highest-= dx*time;
				//To ensure lowest distance remains the same throughout
				if(highest-(dx*time)<lowest)
				{
					//x -= (highest-lowest);
					x -= (highest-lowest)*time;
						//System.out.println("X: " + x);
					highest = lowest;
				}
		
			}
			else if(direction[1] == 1)
			{
				x += dx*time;
				//x += dx;
				highest-= dx*time;
				//To ensure lowest distance remains the same throughout
				if(highest-(dx*time)<lowest)
				{
					//x += (highest-lowest);
					x += (highest-lowest)*time;
					highest = lowest;
				}
			
			}
	
			pathCheck();

		}
		void pathCheck() //Implemented by sub classes
		{
				//Hard-code values need to be changed for any map
				Color borderColour = background.getBorderColour();
				Color borderIntersection = background.getIntersectionColour();
				int accuracy = background.getAccuracy(); //How similar the colours colours are
				Color pixelColour = iBackground.getPixel((int)x,(int)y);
				System.out.println("ID: " + ID +"X: " + x + "Y " + y + "Colour: " + iBackground.getPixel((int)x,(int)y));

				System.out.println("highest: " +highest);
				System.out.println("lowest: " +lowest);
				//Probably make this into a method since code is very similar
				if(highest<=lowest)
				{
					//Works out direction
					calculateDistance("left","right",borderColour,borderIntersection,accuracy);
					calculateDistance("right","left",borderColour,borderIntersection,accuracy);
					calculateDistance("up","down",borderColour,borderIntersection,accuracy);
					calculateDistance("down","up",borderColour,borderIntersection,accuracy);
					compass = tempCompass;
					System.out.println("Compass: " + compass);
					
					if(compass.equals("left"))
					{
						direction[0] = 0;
						direction[1] = 0;
						direction[2] = 0;
						direction[3] = 1;
					}
					else if(compass.equals("right"))
					{
						direction[0] = 0;
						direction[1] = 1;
						direction[2] = 0;
						direction[3] = 0;
					}
					else if(compass.equals("up"))
					{
						direction[0] = 1;
						direction[1] = 0;
						direction[2] = 0;
						direction[3] = 0;
					}
					else if(compass.equals("down"))
					{
						direction[0] = 0;
						direction[1] = 0;
						direction[2] = 1;
						direction[3] = 0;
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
		
		void calculateDistance(String direction1,String oppositeDirection, Color borderColour, Color borderIntersection, int accuracy)
		{
			int distance = 0;
			boolean collision = false;
			while(!collision)
			{
				//if(direction+-distance is > screenHeight ||screenWidth || <0)
				if(direction1.equals("left") && x-distance<=0) break;
				if(direction1.equals("right")&& x+distance>=screenWidth) break;
				if(direction1.equals("up")&& y-distance<=0) break;	
				if(direction1.equals("down")&& y+distance>=screenHeight) break;
				
				
				boolean intersectionCollision = false;
				if(direction1.equals("left"))intersectionCollision = isSimilar(iBackground.getPixel((int)x-distance,(int)y),borderIntersection,accuracy);
				if(direction1.equals("right"))intersectionCollision = isSimilar(iBackground.getPixel((int)x+distance,(int)y),borderIntersection,accuracy);
				if(direction1.equals("up"))intersectionCollision = isSimilar(iBackground.getPixel((int)x,(int)y-distance),borderIntersection,accuracy);
				if(direction1.equals("down"))intersectionCollision = isSimilar(iBackground.getPixel((int)x,(int)y+distance),borderIntersection,accuracy);
				if(intersectionCollision)
				{
					distance+=80;
					continue;
				}
				
				if(direction1.equals("left"))collision = isSimilar(iBackground.getPixel((int)x-distance,(int)y),borderColour,accuracy);
				if(direction1.equals("right"))collision = isSimilar(iBackground.getPixel((int)x+distance,(int)y),borderColour,accuracy);
				if(direction1.equals("up"))collision = isSimilar(iBackground.getPixel((int)x,(int)y-distance),borderColour,accuracy);
				if(direction1.equals("down"))collision = isSimilar(iBackground.getPixel((int)x,(int)y+distance),borderColour,accuracy);
				distance++;
			}
			System.out.println("direction: " + direction1+ "distance: " + distance);
		
			if(distance>highest && !compass.equals(oppositeDirection))  //Current direction is not oppositeSide
			{
				tempCompass = direction1;
				highest = distance;
			}
			if(distance<lowest  && !(distance<0))lowest = distance;
		}
		//Other specialised methods in subclasses - classActions
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
		//OriginX,OriginY,Rotation,filename,StartingArea,BorderColor,IntersectionColor- What about accuracy????
		Background background1 = new Background(512,384,0, backgroundImage,new IntRect(129,53,174,63),new Color(125,0,18),new Color(118,63,140),20);
		actors.add(background1);
		Clock time = new Clock(); //Need to be done somewhere (as the game is running)
		Clock frameTime = new Clock(); //Movement is independent of framerate
		
		//actors.add(new NPC(String.valueOf(enemyCount),33.5f,33.5f,0,ImageFile,background1));
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
			if(enemyCount < 250 && time.getElapsedTime().asMilliseconds() > 50)
			{
				actors.add(new NPC(String.valueOf(enemyCount),33.5f,33.5f,0,ImageFile,background1));
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
// ***Bigger sprites obscure small sprites for visibility
// *** Games lags due to large amount of enemies
// Enemies not going the correct distance before changing direction e.g. enemy on right wall will go to the bottom
// Program Items
// Need to make enemies pass through/under intersections - Hardcoded only works if line sizes stay the same and map size stays the same
// ***Relies on border colour being in at least 3 directions
// ***Equal distance should be a non-issue if enemys always starts close or at the edge of the screen
// ***Only works for straight edges on the borders - Limitation
// *** If boundaryboxes are used then sprite <= lane size wont fit
// StartingArea and other values associated with the background will be stored with level
// Need to make easier to change between maps (use less hardcoded values)
*/