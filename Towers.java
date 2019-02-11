import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.*;
import java.util.*; 

import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

class Towers {
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
    private static String ImageFile = "tower.png";
    private static String ImageFile1 = "tower-1.png";
    private static String BulletTexture = "bullet.png";

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

		boolean within (float x, float y) {
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

		boolean needsRemoving(int width, int height) {
			if (x < 0 || x > width || y < 0 || y > height)
				return true;
			else
				return false;
		}

		//
		// Render the object at its new position
		//
		void draw(RenderWindow w) {
			w.draw(obj);
		}
	}
	
	
	private abstract class ImageActor extends Actor {
		Sprite img;
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
		
		public NPC(String ID,int x, int y, float xPixelsPerSecond, float yPixelsPerSecond,int r, String textureFile)
		{
			super(x,y,r,textureFile);
			super.setDx(xPixelsPerSecond);
			super.setDy(yPixelsPerSecond);
			this.ID = ID;
		}
		


		void calcMove(int minx, int miny, int maxx, int maxy,float time) //Implemented by sub-classes using calcMove
		{
            y += dy;
            x += dx;
        }
        
		void pathCheck() //Implemented by sub classes
		{
				
		}
		
		//Other specialised methods in subclasses - classActions
	}

    private class Tower extends ImageActor {
        /*
        place tower
        remove tower
        attack enemies
        manual control
        rank up tower
        damage - what type (fire damage, normal damage)
        cost of the tower
        kill count
        */

        /* TODO:
        - check valid placement
        - rotate to nearest enemy
        - proximity detection
        - rotate to enemy with lowest health
        - rotate to random enemy
        - rotate to furthest advanced enemy
        - rotate to enemy with most health
        */


        private String ID;
        private float health;
        private int killCount;
        private int rank;

        private float range;
        private int angleOffset = 90;

        public Tower(int x, int y, int r, String textureFile) {
            super(x, y, r, textureFile);
            rank = 0;
            killCount = 0;
            health = 100;
            range = 400;
        }

        // ignoring the sqrt part of the distance calculation as expensive  
        float calcDistance(Actor a) {
            float xDiff = this.x - a.x;
            float yDiff = this.y - a.y;
            return (float) (Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
        }

        NPC getNearestEnemy() {
            NPC nearestEnemy = null;
            float proximity = (float) Math.pow(range, 2);
            float shortestDistance = Float.MAX_VALUE;

            for (Actor actor : actors) {
                // check if actor is an instance of NPC (enemy class)
                if (actor != this && actor instanceof NPC)
                {
                    float currentDistance = calcDistance(actor);

                    if (currentDistance <= proximity && currentDistance < shortestDistance)
                        nearestEnemy = (NPC) actor;
                }
            }

            return nearestEnemy;
        }

        @Override
        void calcMove(int minx, int miny, int maxx, int maxy, float time) {
            NPC nearest = getNearestEnemy();
            
            if (nearest != null) {
                // calculating the angle we need to rotate to so that the tower is faceing the enemy (for shooting)
                int angle =  (int) Math.toDegrees(Math.atan2(this.y - nearest.y, this.x - nearest.x)) - angleOffset;
                this.img.setRotation(angle);
            }
        }

        public void calcMoveCursor(int newX, int newY) {
			x = newX;
			y = newY;
		}
    }

    /*
    public class Bullet extends ImageActor
    {
        float velocity;
        NPC enemy;
        int maxDistance;

        public Bullet(float x, float y, int rotation, String textureFile, int maxDistance, float velocity) {
            super(x, y, 0, textureFile);
            this.img.setRotation(rotation);
            this.maxDistance = maxDistance;
            this.velocity = velocity;

            super.setDx(4);
            super.setDy
        }

        void setTarget(NPC enemy) {
            this.enemy = enemy;
        }

        @Override
        void calcMove(int minx, int miny, int maxx, int maxy, float time) {
            x += dx;
            y += dy;
        }
    }
    */

	
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
				
		boolean collision = true;

		window.setFramerateLimit(60); // Avoid excessive updates

		//
		// Create some actors
		//
		//OriginX,OriginY,Rotation,filename,StartingArea,BorderColor,IntersectionColor- What about accuracy????
		Background background1 = new Background(screenWidth / 2,screenHeight / 2, 0, backgroundImage,new IntRect(400,400,400,400),new Color(125,0,18),new Color(118,63,140),20);
		actors.add(background1);
		Clock time = new Clock(); //Need to be done somewhere (as the game is running)
		Clock frameTime = new Clock(); //Movement is independent of framerate
		
        actors.add(new NPC(String.valueOf(enemyCount), 0, 100, 1,0,0,ImageFile1));
        actors.add(new NPC(String.valueOf(enemyCount), 500, 700, 1,0,0,ImageFile1));
        //actors.add(new Tower(screenWidth / 2, screenHeight / 2, 0, ImageFile));


        //actors.add(new Tower(screenWidth / 2, screenHeight / 2, 0, ImageFile));
		Tower t = new Tower(screenWidth / 2, screenHeight / 2, 0, ImageFile); 
		actors.add(t);

		// Main loop
		//

		while (window.isOpen( )) 
		{
			Stack<Actor> toRemove = new Stack<>();
			float elapsedTime = frameTime.restart().asSeconds();
			
			// Clear the screen
			window.clear(Color.WHITE);
			
			// Move all the actors around
			for (Actor actor : actors) {
				if (actor.needsRemoving(screenWidth, screenHeight))
					toRemove.push(actor);
				actor.calcMove(0, 0, screenWidth, screenHeight,elapsedTime);
				actor.performMove();
				actor.draw(window);			
			}

			for (Actor actor : toRemove) {
				actors.remove(actor);
			}


			
			// Update the display with any changes
			window.display( );

			
			// Handle any events
			for (Event event : window.pollEvents( )) {
				if (event.type == Event.Type.CLOSED) {
					// the user pressed the close button
					window.close( );
				}
				//if(!r.within(event.asMouseEvent().position.x,event.asMouseEvent().position.y)) //System.out.println("HELLO"); //<----THIS
				if (event.type == Event.Type.MOUSE_MOVED) {
				
					//Mapping is no longer necessary as its 1:1
					t.calcMoveCursor(event.asMouseEvent().position.x, event.asMouseEvent().position.y);
					
					if(t.within(t.x,t.y)) //This needs to be removed
					{
						collision = true;
					}
					else
					{
						collision = false;
					}
				
				}
				//Draws a tower at the cursors position
			
				if(event.type == Event.Type.MOUSE_BUTTON_RELEASED){
					if(event.asMouseButtonEvent().button == Mouse.Button.LEFT)
					{

						if(actors.indexOf(t) !=-1 && !collision)  actors.add(new Tower((int) event.asMouseEvent().position.x, (int) event.asMouseEvent().position.y, 0, ImageFile));
						//if(!collision) actors.remove(b);
						if(t.within(t.x,t.y)) //If cursor is not holding anything, check if the cursor selects any actors????
						{
							collision = true;
						}
						else
						{
							collision = false;
						}
					}
					else if(event.asMouseButtonEvent().button == Mouse.Button.RIGHT)
					{
						if(actors.indexOf(t) ==-1) 
						{
							actors.add(t);
							t.calcMoveCursor((int) event.asMouseEvent().position.x, (int) event.asMouseEvent().position.y);
						}
					}
				}	
			}
		}
	}

	public static void main (String args[ ]) 
	{
		Towers towers = new Towers( );
		towers.run( );
	}
}