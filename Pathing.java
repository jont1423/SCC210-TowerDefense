//
//	SCC210 Example code
//
//		Andrew Scott, 2015
//
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.function.*;

import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

class Pathing {
	private static int screenWidth  = 512;
	private static int screenHeight = 512;


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
	private static String ImageFile = "enemy.png";

	private static String Title   = "";
	private static String Message = "";

	private String FontPath;	// Where fonts were found

	private ArrayList<Actor> actors = new ArrayList<Actor>( );
	
	private Image background = new Image();
	private final String backgroundImage = ("background1.png");

	private abstract class Actor {
		Drawable obj;
		IntConsumer rotate;
		BiConsumer<Float, Float> setPosition;

		int x  = 0;	// Current X-coordinate
		int y  = 0;	// Current Y-coordinate

		int r  = 0;	// Change in rotation per cycle
		int dx = 1;	// Change in X-coordinate per cycle
		int dy = 1;	// Change in Y-coordinate per cycle

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
		abstract void calcMove(int minx, int miny, int maxx, int maxy);
		

		//
		// Reposition the object
		//
		void performMove( ) {
			rotate.accept(r);
			setPosition.accept((float)x, (float)y);
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
		public ImageActor(int x, int y, int r, String textureFile) {
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
		
		public void setDx(int newDx)
		{
			this.dx = newDx;
		}
		
		public void setDy(int newDy)
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
		private Image background;
		private int[] direction = new int[] {0,0,1,0}; //Compass clockwise
		private int cooldown = 0; //Needs to be removed too hardcoded
		NPC(int x, int y, int r, String textureFile, Image background)
		{
			super(x,y,r,textureFile);
			this.background = background;
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
		//Need to be removed - implemented by subclasses
		//DEFAULT_DIRECTION = South;
		void calcMove(int minx, int miny, int maxx, int maxy) //Implemented by sub-classes using calcMove
		{
			//System.out.println());
			if(direction[2]==1)
			{
				y += dy;
			}
			else if(direction[0] == 1)
			{
				y -= dy;
			}
			if(direction[3] ==1)
			{
				x -= dx;
			}
			else if(direction[1] == 1)
			{
				x += dx;
			}
			//Use pathcheck
			pathCheck(new Color(126,0,18));

		}//What about rotation???
		void pathCheck(Color colorParam) //Implemented by sub classes
		{
				//Hard-code values need to be changed for any map
				Color borderColour = colorParam;
				Color borderIntersection = new Color(118,63,140);//color2Param;
				int accuracy = 20; //How similar the colours colours are
				int collisionSpacing = 12; //Depends on width of the lane//How far away from boundaries
				Color pixelColour = background.getPixel(x,y);
				System.out.println("X: " + x + "Y " + y + "Colour: " + background.getPixel(x,y));
				System.out.println("X: " + x + "YUp " + (y-collisionSpacing));
				System.out.println("X: " + x + "YDown " + (y+collisionSpacing));
				System.out.println("XLeft: " + (x-collisionSpacing) + "Y " + y);
				System.out.println("XRight: " + (x+collisionSpacing) + "Y " + y);

					//This checks distant pixels match the border colour
					//Needs to make it pass through intersections with ease
					boolean leftCollision = isSimilar(background.getPixel(x-collisionSpacing,y),borderColour,accuracy);
					boolean rightCollision = isSimilar(background.getPixel(x+collisionSpacing,y),borderColour,accuracy);
					boolean upCollision = isSimilar(background.getPixel(x,y-collisionSpacing),borderColour,accuracy);
					boolean downCollision = isSimilar(background.getPixel(x,y+collisionSpacing),borderColour,accuracy);
				
					
					if(cooldown>0)
					{
						cooldown--;
						return;
					}
					
					if(isSimilar(background.getPixel(x+collisionSpacing,y),borderIntersection,accuracy) ^
							isSimilar(background.getPixel(x-collisionSpacing,y),borderIntersection,accuracy)) 
							{//Hardcoded stop checks for 40 pixels
								cooldown = 40; //Number of pixels between both purple colours
							}
					
					if(isSimilar(background.getPixel(x,y-collisionSpacing),borderIntersection,accuracy) ^
							isSimilar(background.getPixel(x,y+collisionSpacing),borderIntersection,accuracy)) return;
					
					System.out.println("Left " + leftCollision);
					System.out.println("Right " + rightCollision);
					System.out.println("Up " + upCollision);
					System.out.println("Down " + downCollision);
					if(leftCollision ^ rightCollision )
					{

						if(leftCollision)
						{
							direction[3] = 0;
							direction[1] = 1;
						}
						
						if(rightCollision)
						{
							direction[3] = 1;
							direction[1] = 0;
						}
						System.out.println("Reverse X");
					}
					else if(leftCollision && rightCollision)
					{
							direction[3] = 0;
							direction[1] = 0;
					}
					
					if(upCollision ^ downCollision )
					{
						if(upCollision)
						{
							direction[0] = 0;
							direction[2] = 1;
						}
						if(downCollision)
						{
							direction[0] = 1;
							direction[2] = 0;
						}
						System.out.println("Reverse Y");
					}
					else if(upCollision && downCollision)
					{
							direction[0] = 0;
							direction[2] = 0;
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
		//Other specialised methods in subclasses - classActions
	}
	
	public class Background extends ImageActor
	{
		Background(int x, int y, int r, String textureFile)
		{
			super(x,y,r,textureFile);
			super.setDx(0);
			super.setDx(0);
			//Get background details
			try
			{
				InputStream img1 = Pathing.class.getResourceAsStream("background.png");
				background.loadFromStream(img1);
			}
			catch(IOException e)
			{
				System.out.println("Couldnt read image");
			}
		}
		
		void calcMove(int minx, int miny, int maxx, int maxy) //Implemented by sub-classes using calcMove
		{
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
		//Use the clock class for movement
		

		
		//
		// Create some actors
		//
		Background background1 = new Background(256,256,0, "background.png");
		actors.add(background1);
		actors.add(new NPC(75,32,0, ImageFile,background));
		//actors.add(new NPC(75,48,0, "enemy3.png",background));
		//actors.add(new NPC(75,16,0, "enemy2.png",background));
		//actors.add(new NPC(75,16,0, "enemy4.png",background));
		//
		// Main loop
		//
		while (window.isOpen( )) 
		{
			// Clear the screen
			window.clear(Color.WHITE);

			// Move all the actors around
			for (Actor actor : actors) {
				actor.calcMove(0, 0, screenWidth, screenHeight);
				actor.performMove( );
				actor.draw(window);						
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
// Need to make enemies pass through/under intersections - Works but its hardcoded
// Only works for straight lines - Limitation
// Enemies have to start in the middle of the lane, this wont work using single pixels need to use a globalbounds box
// Cant start at the edge or else illegal pixel out of bound exception
// With each new background change, the starting position, direction,collisionSpacing and background values need to change -- Add these to background class
// *** If boundaryboxes are used then sprite <= lane size wont fit
// Need to make easier to change between maps (use less hardcoded values)
*/