import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.function.*;
import java.io.InputStream;

import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.Keyboard;
import org.jsfml.audio.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.graphics.Image;

public class Engine {
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 768;
    private static final String TITLE = "Engine";

    private static String JavaVersion = Runtime.class.getPackage( ).getImplementationVersion( );
	private static String JdkFontPath = "C:\\Program Files\\Java\\jdk" + JavaVersion + "\\jre\\lib\\fonts\\";
	private static String JreFontPath = "C:\\Program Files\\Java\\jre" + JavaVersion + "\\lib\\fonts\\";

	private static int fontSize = 48;
    private static String FontFile = "LucidaSansRegular.ttf";
	private String FontPath;
    
    
	private static Mouse mouseMov;
    private static Vector2i mouseLoc;
    
    //... Game Variables
    private final String BACKGROUNDS[] = {"Maps/Map1.png","Maps/Map2.png"};
    private final String TOWERS[] = {"Towers/tower-1.png", "Tower/tower-2.png"};
   // private final String BULLETS[] = {"data/bullet.png"};
    private final String ENEMIES[] = {"Enemies/enemy.png"};

    private ArrayList<Tower> towers = new ArrayList<Tower>();
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private ArrayList<Actor> actors = new ArrayList<Actor>();
    private ArrayList<NPC> npcs = new ArrayList<NPC>();

    private Background map;

    private Clock time = new Clock();
	private Clock frameTime = new Clock();
    private float elapsedTime;
	String difficulty = "easy";

    //////////////////// IMPORTANT VARIABLE - Andreea ////////////////////
    // placeholder tower for when new towers are created and destroyed
    private Tower towerToPlace = null;

    public void init() {
        map = new Background(512,384,0, BACKGROUNDS[0],new IntRect(171,97,32,29),new Color(255,0,157),new Color(255,255,255),20);

        actors.add(map);
    }
	
	public void run () {
        // Set the font path
        if ((new File(JreFontPath)).exists( )) FontPath = JreFontPath;
		else FontPath = JdkFontPath;

		// Create a window
		RenderWindow window = new RenderWindow();
		window.create(new VideoMode(WIDTH, HEIGHT), TITLE, WindowStyle.CLOSE);
		window.setFramerateLimit(60);

        // Time since last frame
        elapsedTime = frameTime.restart().asSeconds();

        // Create some actors
        Gremlin enemy = new Gremlin(134f, 134f, 0,difficulty, map);
        npcs.add(enemy);
        //NPC(String ID,int health, int armour,float xPixelsPerSecond, float yPixelsPerSecond,int r, String textureFile, Background background)
		
        
		// Main loop
		while (window.isOpen()) {
			// Clear the screen
            window.clear(Color.WHITE);
            
            // Getting the current mouse coordinates relative to the window
            mouseLoc = mouseMov.getPosition(window);
            
            // Move actors
            for (Actor actor : actors) {
				actor.calcMove(0, 0, WIDTH, HEIGHT,elapsedTime);
				actor.performMove( );
				actor.draw(window);						
            }

            //////////////////// NEW CODE STARTS HERE - Andreea ////////////////////

            // Testing with one enemy
            for (NPC npc : npcs) {
                npc.calcMove(0, 0, WIDTH, HEIGHT,elapsedTime);
				npc.performMove();
				npc.draw(window);		
            }

            // Handling the movement for towers
            for (Tower tower : towers) {
                // checking if the current tower is not the one we are placing and that towerToPlace isn't null
                if (towerToPlace != null && tower != towerToPlace) {
                    // checking if the mouse point lies within the range of the tower
                    boolean intersects = tower.within(mouseLoc.x, mouseLoc.y);
                    // rendering the circular display to show if invalid areas for placement
                    tower.renderBorder(window, intersects);
                }
                // checking if the current tower is the tower being placed by the user
                if (!tower.getPlaced()) {
                    // moveing the tower to the mouses location but preventing it from going off the screen
                    tower.calcMoveCursor(mouseLoc.x, mouseLoc.y, 0, 0, WIDTH, HEIGHT);
                } else {
                    // otherwise calculating the rotation move to aim at players
                    NPC nearestEnemy = tower.getNearestEnemy(npcs);
                    // checking that the nearest enemy isn't null and then checking the health of the npc we are shooting to see if they are dead
                    if(nearestEnemy != null && nearestEnemy.getHealth() < 0 )
                        npcs.remove(nearestEnemy);
                    // rotating the tower to face the enemy
                    tower.calcMove(0, 0, WIDTH, HEIGHT,elapsedTime);
                }
                // performing the move and drawing to the window
                tower.performMove();
                tower.draw(window);
			}

			// Update the display with any changes
			window.display();

			// Handle any events
			for (Event event : window.pollEvents()) {
                // checking if the event is a mouse click release
                if(event.type == Event.Type.MOUSE_BUTTON_RELEASED) {
                    // getting the event as a mouse event and checking if left was clicked
                    if(event.asMouseButtonEvent().button == Mouse.Button.LEFT) {
                        // checking if we are currently trying to place a tower
                        if (towerToPlace != null && !towerToPlace.getPlaced()) {
                            // checking if the place we are trying to place the tower is valid
                            if (towerToPlace.placementCheck(mouseLoc.x, mouseLoc.y, towerToPlace.getImg().getGlobalBounds(), towers)) {
                                // updating the tower to acknowledge it has been placed
                                towerToPlace.setPlaced(true);
                                // resetting the towerToPlace variable to null
                                towerToPlace = null;
                            }
                        }
                        else {
                            // otherwise looping over all the towers and checking if we are trying to pick up a tower
                            for (Tower tower : towers) {
                                // checking if the mouse click is within a region of the tower
                                if (tower.within(mouseLoc.x, mouseLoc.y, 25)) {
                                    // updating the tower for moving around the map
                                    tower.setPlaced(false);
                                    // removing the nearest enemy from the selected tower
                                    tower.setNearestEnemy(null);
                                    // setting the towerToPlace to the current tower
                                    towerToPlace = tower;
                                }
                            }
                        }
                    }
                }
                
                // checking if the event is a keyboard press
                if (event.type == Event.Type.KEY_PRESSED) {
                    // getting the event as a key event
                    event.asKeyEvent();

                    // checking if the key pressed is T for creating / destroying a new tower
                    if (Keyboard.isKeyPressed(Keyboard.Key.T)) {
                        // checking if we are currently placing a tower
                        if (towerToPlace == null) {
                            System.out.println("T pressed, creating a new tower");
                            // creating a new tower based off the mouse coordinates and setting it up for placement
                            towerToPlace = new Tower(mouseLoc.x, mouseLoc.y, 0, TOWERS[0], 150, map, false);
                            // adding the new tower to the towers list
                            towers.add(towerToPlace);
                        } else {
                            // otherwise removing the towerToPlace from the list if T is pressed again whilst moving a tower
                            towers.remove(towerToPlace);
                            // resetting the towerToPlace 
                            towerToPlace = null;
                        }
                    }

                    // checking if the key pressed is U for upgrading the currently selected tower
                    if (Keyboard.isKeyPressed(Keyboard.Key.U)) {
                        // checking we currently have a tower selected
                        if (towerToPlace != null) {
                            // upgrading the tower
                            towerToPlace.upgrade(TOWERS);
                        }
                    }
                }

                //////////////////// NEW CODE ENDS HERE - Andreea ////////////////////
                
				if (event.type == Event.Type.CLOSED) {
					// the user pressed the close button
					window.close();
                }
            }
		}
	}

	public static void main (String args[]) {
        Engine e = new Engine();
        e.init();
        e.run();
    }
}	