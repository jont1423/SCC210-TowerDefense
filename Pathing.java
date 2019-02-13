import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.*;
import java.lang.Math;
import java.util.*; 

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
	private String FontPath;	// Where fonts were found
	private static String ImageFile = "background2Scaled.png";
	private static String Title   = "Tower Defence";
	
	private static String enemyFile[] = {"enemy.png","enemy2.png","enemy4.png"};
	private static String towerFile[] = {"tower-1.png"};
	private static Background background;
	private ArrayList<NPC> npcs = new ArrayList<NPC>( );
	private ArrayList<Tower> towers = new ArrayList<Tower>( );
	//private ArrayList<List<Actor>> actors = new ArrayList<List<Actor>>( );
	private ArrayList<Actor> actors = new ArrayList<Actor>( );
	//Maybe an actor one for just drawing???


	private static int enemyCount = 0;
	
	public ArrayList getActors()
	{
		return actors;
	}

	
	public void run () {

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
				
		window.setFramerateLimit(10); // Avoid excessive updates

		//
		// Create some actors
		//OriginX,OriginY,Rotation,filename,StartingArea,BorderColor,IntersectionColor,accuracy
		//Background background1 = new Background(512,384,0, ImageFile,new IntRect(128,53,175,63),new Color(125,0,18),new Color(118,63,140),20);
		Background background1 = new Background(512,384,0, ImageFile,new IntRect(49,96,55,131),new Color(239,4,161),new Color(255,255,255),20);
		actors.add(background1);														//128,175 //50,95,73,132
		Clock time = new Clock(); //Need to be done somewhere (as the game is running)
		Clock frameTime = new Clock(); //Movement is independent of framerate
		
		
		npcs.add(new NPC("ID",100,50,134f,134f,0,"enemy2.png", background1));
		actors.add(npcs.get(0));
		Tower t = new Tower(screenWidth / 2, screenHeight / 2, 0, towerFile[0]); 
		towers.add(t);
		actors.add(t);
		//
		while (window.isOpen( )) 
		{
			Stack<Actor> toRemove = new Stack<>();
			float elapsedTime = frameTime.restart().asSeconds();
			//System.out.println("ElapsedTime :" + elapsedTime);
			
			// Clear the screen
			window.clear(Color.WHITE);
			
			// Move all the actors around
			for(Tower tower : towers)
			{
				float proximity = (float) Math.pow(tower.getRange(), 2);
				float shortestDistance = Float.MAX_VALUE;

				for (NPC npc : npcs) {
					// check if actor is an instance of NPC (enemy class)
					float currentDistance = tower.calcDistance(npc);

					if (currentDistance <= proximity && currentDistance < shortestDistance)
					{
						tower.setNearestEnemy(npc);
						tower.calcBulletOrigin();
						actors.add(new Bullet(tower.getBulletOriginX(),tower.getBulletOriginY(),0,"bullet.png",0,33.5f));
					}	
				}
				
				
			}

			for (Actor actor : actors) {
				if (actor.needsRemoving(screenWidth, screenHeight))
					toRemove.push(actor);
				actor.calcMove(0, 0, screenWidth, screenHeight,elapsedTime);
				actor.performMove( );
				actor.draw(window);						
			}
			
			for (Actor actor : toRemove) {
				actors.remove(actor);
			}

			//Enemies spawn every .5  seconds
			/*if(enemyCount < 250 && time.getElapsedTime().asMilliseconds() > 500)
			{
				actors.add(new SpeedDemon("SD",25,150,5f,134f,134f,0,"enemy4.png",background1));
				enemyCount++;
				time.restart();
			}*/
			
			// Update the display with any changes
			window.display( );

			// Handle any events
			for (Event event : window.pollEvents( ))
			{
				if (event.type == Event.Type.CLOSED) {
				// the user pressed the close button
				window.close( );
				}
				if (event.type == Event.Type.MOUSE_MOVED) {
				
					//Mapping is no longer necessary as its 1:1
					t.calcMoveCursor(event.asMouseEvent().position.x, event.asMouseEvent().position.y);
					
					/*if(t.within(t.x,t.y)) //This needs to be removed
					{
						collision = true;
					}
					else
					{
						collision = false;
					}*/
				
				}
				//Draws a tower at the cursors position
			
				if(event.type == Event.Type.MOUSE_BUTTON_RELEASED){
					if(event.asMouseButtonEvent().button == Mouse.Button.LEFT)
					{

						if(actors.indexOf(t) !=-1) 
						{
							towers.add(new Tower((int) event.asMouseEvent().position.x, (int) event.asMouseEvent().position.y, 0, towerFile[0]));
							actors.add(towers.get(towers.size()-1));
						}
						//if(!collision) actors.remove(b);
						/*if(t.within(t.x,t.y)) //If cursor is not holding anything, check if the cursor selects any actors????
						{
							collision = true;
						}
						else
						{
							collision = false;
						}*/
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

	public static void main (String args[ ]) {
		Pathing p = new Pathing( );
		p.run( );
	}
}
/* To do List 
//Towers - Make projectiles shoot enemies and do damage,avoid placing and rotating 
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