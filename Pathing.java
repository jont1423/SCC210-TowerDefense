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
	private static String Title   = "Constellation";
	private static RenderWindow window;
	
	private static String enemyFile[] = {"Enemies/enemy.png","Enemies/enemy2.png","Enemies/enemy3.png","Enemies/enemy4.png","Enemies/enemy5.png","Enemies/enemy6.png"};
	private static String towerFile[] = {"Towers/tower-1.png","Towers/tower-2.png","Towers/tower-3.png","Towers/tower-4.png","Towers/tower-5.png","Towers/tower-6.png"};
	private static String backgroundFile[] = {"Maps/Map1.png","Maps/Map2.png","Maps/Map3.png"};
	private static Background background;
	private ArrayList<NPC> npcs = new ArrayList<NPC>( );
	private ArrayList<Tower> towers = new ArrayList<Tower>( );
	//private ArrayList<List<Actor>> actors = new ArrayList<List<Actor>>( );
	private ArrayList<Actor> actors = new ArrayList<Actor>( );
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>( );
	
	private ImageAct ButtonsIMG[];
	private GenButton rect[];
	private static Mouse mouseMov;
	private static Vector2i mouseLoc;
	//Maybe an actor one for just drawing???


	private static int enemyCount = 0;
	
	Pathing(RenderWindow r)
	{
		window = r;
	}
	
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
	//	RenderWindow window = new RenderWindow( );
		//ContextSettings settings = new ContextSettings(8);
		/*window.create(new VideoMode(screenWidth, screenHeight),
				"Testing",
				WindowStyle.DEFAULT,
				settings);*/
				
		window.setFramerateLimit(60); // Avoid excessive updates
		
	
	
		//All Maps
		//OriginX,OriginY,Rotation,filename,StartingArea,BorderColor,IntersectionColor,accuracy
		//Need to make sure the starting distance is 3 pixels away from either side
		//Background map1 = new Background(512,384,0, backgroundFile[0],new IntRect(171,97,32,31),new Color(255,0,157),new Color(255,255,255),20);
		Background map1 = new Background(512,384,0, backgroundFile[0],new IntRect(171,97,32,29),new Color(255,0,157),new Color(255,255,255),20);
		//Background map2 = new Background(512,384,0, backgroundFile[1],new IntRect(231,48,32,32),new Color(124,0,20),new Color(126,57,140),20);
		Background map2 = new Background(512,384,0, backgroundFile[1],new IntRect(231,48,28,32),new Color(124,0,20),new Color(126,57,140),20);
		Background map3 = new Background(512,384,0, backgroundFile[2],new IntRect(800,122,19,31),new Color(172,49,49),new Color(255,255,255),20);
		//Background map4 = new Background(512,384,0, ImageFile,new IntRect(49,96,55,131),new Color(239,4,161),new Color(255,255,255),20);
		//Background map5 = new Background(512,384,0, ImageFile,new IntRect(49,96,55,131),new Color(239,4,161),new Color(255,255,255),20);
		
		//Buttons
		ButtonsIMG = new ImageAct[towerFile.length];
		for (int i=0; i<towerFile.length; i++)
			ButtonsIMG[i] = new ImageAct(towerFile[i]);
		
		ButtonsIMG[0].setLocation(885f, 77f);
		ButtonsIMG[1].setLocation(949f, 77f);
		ButtonsIMG[2].setLocation(885f, 138f);
		ButtonsIMG[3].setLocation(949f, 138f);
		ButtonsIMG[4].setLocation(885f, 197f);
		ButtonsIMG[5].setLocation(949f, 197f);
		
		rect = new GenButton[towerFile.length];
		for(int i=0; i<towerFile.length; i++)
			rect[i] = new GenButton(40, 43, Color.GREEN, 0);
		
		rect[0].setLocation((float) 888, (float) 82);	
		rect[1].setLocation((float) 950, (float) 82);	
		rect[2].setLocation((float) 888, (float) 142);	
		rect[3].setLocation((float) 950, (float) 142);	
		rect[4].setLocation((float) 888, (float) 202);	
		rect[5].setLocation((float) 950, (float) 202);	
	
		
		
		
		actors.add(map1);														//128,175 //50,95,73,132
		Clock time = new Clock(); //Need to be done somewhere (as the game is running)
		Clock frameTime = new Clock(); //Movement is independent of framerate
	//	Clock firerate = new Clock(); //Unique to each turret
		
		//npcs.add(new NPC("ID",50,0,134f,134f,0,enemyFile[1], map1));
		//actors.add(npcs.get(0));

		Tower t = new Tower(screenWidth / 2, screenHeight / 2, 0, towerFile[0],0,map1); 
		actors.add(t);
	
		while (window.isOpen( )) 
		{
			Stack<Actor> toRemove = new Stack<>();
			float elapsedTime = frameTime.restart().asSeconds();
			//System.out.println("ElapsedTime :" + elapsedTime);
			
			// Clear the screen
			window.clear(Color.WHITE);
			
			mouseLoc = mouseMov.getPosition(window);

						
			// Move all the actors around
			for(Tower tower : towers)
			{
				float proximity = (float) Math.pow(tower.getRange(), 2);
				float shortestDistance = Float.MAX_VALUE;
			
				//System.out.println("npc empty: " + npcs.isEmpty());
				//if(!npcs.isEmpty())
				//{
				
					for (NPC npc : npcs) {
						// check if actor is an instance of NPC (enemy class)
						float currentDistance = tower.calcDistance(npc);
		
						if (currentDistance <= proximity && currentDistance < shortestDistance)
						{
							tower.setNearestEnemy(npc);
						}	
						if(npc.getHealth() < 0 ) actors.remove(npc);
						
					}
				
				//}
				
									
				//If statement - Make sure it each turret has 1 bullet at a time, theres a target and checks its been 0.5 seconds since the last bullet was created
				if (tower.getNearestEnemy() != null && tower.getFireRate() > tower.getCooldown())
				{
					
					tower.calcBulletOrigin();
					Bullet b = new Bullet(tower.getBulletOriginX(),tower.getBulletOriginY(),0,"bullet.png",0,500f);
					b.setTarget(tower.getNearestEnemy());
					actors.add(b);
					bullets.add(b);
					tower.setBullet(b);
					b.calculateDistanceToTarget();
					tower.setFireRate();
				}
			}
			
			for(Bullet bl : bullets)
			{
				bl.calculateDistanceToTarget();
				if(bl.enemy != null)bl.checkProximity();
			}

			for (Actor actor : actors) {
				if (actor.needsRemoving(screenWidth, screenHeight))
					toRemove.push(actor);
				actor.calcMove(0, 0, screenWidth, screenHeight,elapsedTime);
				actor.performMove( );
				actor.draw(window);						
			}
			
			for (ImageAct buttonsIMG: ButtonsIMG)
				buttonsIMG.draw(window);
			for (GenButton rects: rect)
				rects.draw(window);

			
			
			for (Actor actor : toRemove) {
				actors.remove(actor);
			}

			//Enemies spawn every .5  seconds
			if(enemyCount < 250 && time.getElapsedTime().asMilliseconds() > 500)
			{
				Random r = new Random();
				int random = r.nextInt(enemyFile.length);
				npcs.add(new NPC("ID",50,0,134f,134f,0,enemyFile[random], map1));
				actors.add(npcs.get(npcs.size()-1));
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
				if (event.type == Event.Type.MOUSE_MOVED) {
				
					//Remove this method in tower and use the one in Actor???
					t.calcMoveCursor(event.asMouseEvent().position.x, event.asMouseEvent().position.y);
				}
				
				//Buttons

				
				for(int i=0;i<rect.length;i++)
				{
					if (rect[i].detectPos(rect[i].getRectPosition(), rect[i].getRectDimensions(), mouseLoc)) 
					{
						rect[i].setRectColor(Color.GREEN, 40); //If not enough money appear red
						if (event.type == Event.Type.MOUSE_BUTTON_RELEASED) {
							actors.remove(t);
							t = new Tower(mouseLoc.x, mouseLoc.y, 0, towerFile[i],0,map1);
							actors.add(t);
						}
					}
						else {rect[i].setRectColor(Color.TRANSPARENT, 0);}
				}
				//Draws a tower at the cursors position
			
				if((mouseLoc.x < 855f && mouseLoc.x > 150f) && (mouseLoc.y < 745f && mouseLoc.y > 40))
				{
					if(event.type == Event.Type.MOUSE_BUTTON_RELEASED){
						if(event.asMouseButtonEvent().button == Mouse.Button.LEFT)
						{
							
							if(actors.indexOf(t) !=-1 && t.placementCheck(t.getImg().getGlobalBounds(),towers)) 
							{
								towers.add(new Tower(mouseLoc.x, mouseLoc.y, 0, t.getTowerImage(),100,map1));
								actors.add(towers.get(towers.size()-1));
							}
						}
						else if(event.asMouseButtonEvent().button == Mouse.Button.RIGHT)
						{

						}
					}	
				}
				
			}
		}
	}
}
/* To do List 
// Pathing - Constructor for which level/map, save state(wave, base health, difficulty)
// NPC - Need to test on last 2 maps, need to finalise enemies (stats and sprites) using DB
// Towers - Towers are still shooting when the enemy is dead (npc arralist wont decrease in size), only allows one bullet at at time across all turrets, Towers are 40 x 40
// Items - Start this class and make them drop from enemies, including currency and powerups
// Bullet - Depends on how animation is implemented
// Program Items
// ***Relies on border colour being in at least 3 directions
// ***Equal distance should be a non-issue if enemys always starts close or at the edge of the screen
// ***Only works for straight edges on the borders - Limitation
// *** If boundaryboxes are used then sprite <= lane size wont fit
// StartingArea and other values associated with the background will be stored with level
*/