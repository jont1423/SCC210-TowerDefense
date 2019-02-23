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
	private static String towerFile[] = {
											"Towers/tower-1.png","Towers/tower-2.png","Towers/tower-3.png",
											"Towers/tower-4.png","Towers/tower-5.png","Towers/tower-6.png",
											"Towers/tower-7.png","Towers/tower-8.png","Towers/tower-9.png",
											"Towers/tower-11.png","Towers/tower-12.png","Towers/tower-13.png"};
	private static String backgroundFile[] = {"Maps/Map1.png","Maps/Map2.png","Maps/Map3.png"};
	private static Background background;
	private ArrayList<NPC> npcs = new ArrayList<NPC>( );
	private ArrayList<Tower> towers = new ArrayList<Tower>( );
	//private ArrayList<List<Actor>> actors = new ArrayList<List<Actor>>( );
	private ArrayList<Actor> actors = new ArrayList<Actor>( );
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>( );
	private ArrayList<Item> items = new ArrayList<Item>( );
	//private ArrayList<Word> Text = new ArrayList<Word>( );
	
	//All Maps
	//OriginX,OriginY,Rotation,filename,StartingArea,BorderColor,IntersectionColor,accuracy
	//Need to make sure the starting distance is 3 pixels away from either side
	//Background map1 = new Background(512,384,0, backgroundFile[0],new IntRect(171,97,32,31),new Color(255,0,157),new Color(255,255,255),20);
	private final Background map1 = new Background(512,384,0, backgroundFile[0],new IntRect(171,97,32,29),new Color(255,0,157),new Color(255,255,255),20);
	//Background map2 = new Background(512,384,0, backgroundFile[1],new IntRect(231,48,32,32),new Color(124,0,20),new Color(126,57,140),20);
	private final Background map2 = new Background(512,384,0, backgroundFile[1],new IntRect(231,48,28,32),new Color(124,0,20),new Color(126,57,140),20);
	private final Background map3 = new Background(512,384,0, backgroundFile[2],new IntRect(800,122,19,31),new Color(172,49,49),new Color(255,255,255),20);
	//Background map4 = new Background(512,384,0, ImageFile,new IntRect(49,96,55,131),new Color(239,4,161),new Color(255,255,255),20);
	//Background map5 = new Background(512,384,0, ImageFile,new IntRect(49,96,55,131),new Color(239,4,161),new Color(255,255,255),20);
	private Background selectedMap;
	
	private ImageAct ButtonsIMG[];
	private GenButton rect[];
	private static Mouse mouseMov;
	private static Vector2i mouseLoc;
	Color fontColour = new Color(88,189,211);
	
	//Game Stats
	private int currentLevel;
	private int round;
	private String difficulty;
	private int maxRound;
	private int baseHealth;
	private float roundTimer;
	private Clock wTime;
	private int enemyCount; //Number of enemies to spawn in
	private int enemiesAlive = 0; //Number of enemies to spawn in
	private int enemiesDead = 0; //Number of enemies dead
	private int scrap = 250;
	private final int baseScrapValue = 5;
	private int scrapValue = baseScrapValue;
	private int damageMultiplier;
	private int[] enemyComposition = new int[6]; //Each index represents the number of that enemy for the wave

	private Word gameInfo = new Word();
	private Word towerInfo = new Word();
	private ArrayList<Word> itemInfo = new ArrayList<Word>();
	
	private Word damageText = null;
	private Word firerateText = null;
	private Word piercingText = null;
	private Word fortuneText = null;
	
	private Clock damageTime; //Keep track of damage boost time
	private Clock firerateTime; //Keep track of fire rate
	private Clock piercingTime; //Keep track of piercing duration
	private Clock fortuneTime; //Keep track of money boost
	
	private int selectedTower; //number represented tower selected

	//Maybe an actor one for just drawing???

	Pathing(RenderWindow r, Save s)
	{
		window = r;
		currentLevel = s.getCurrLvl();
		round = s.getCurrRound();
		difficulty = s.getDiff();
		scrap = 0;
		displayInfo();
	}
	
	public void startTime()
	{
		if(wTime==null)
		{
			wTime = new Clock();
		}
	}
	
	public void setup(String difficulty, int currentLevel)
	{
		
		//Probably switch to a case statment
		if(currentLevel == 1)
		{
			maxRound = 10;
			selectedMap = map1;
			actors.add(map1);
		}
		else if(currentLevel == 2)
		{
			maxRound = 20;
			selectedMap = map2;
			actors.add(map2);
		}
		else if(currentLevel == 3)
		{
			maxRound = 30;
			selectedMap = map3;
			actors.add(map3);
		}
		else if(currentLevel == 4)
		{
			maxRound = 40;
			//selectedMap = map1;
			//actors.add(map1);
		}
		else if(currentLevel == 5)
		{		
			maxRound = 50;
			//selectedMap = map1;
			//actors.add(map1);
			
		}
		else
		{
			//Sandbox map
			System.out.println("Sandbox Map");
		}
		
		if(difficulty.equals("easy"))
		{
			baseHealth = 200;
			roundTimer = 5f; 
			enemyCount = 5;
		}
		else if(difficulty.equals("intermediate"))
		{
			baseHealth = 150;
			roundTimer = 45f; 
			enemyCount = 500;
		}
		else if(difficulty.equals("hard"))
		{
			baseHealth = 100;
			roundTimer = 30f; 
			enemyCount = 1000;
		}
	}
	
	public ArrayList getActors()
	{
		return actors;
	}
	
	public void displayInfo()
	{

		//Displays game information
		float ySpacing = 70f;
		gameInfo.setWord("Wave\n" + round +
							"\n\nBase Health\n" + baseHealth +
							"\n\nNext round in\n" + Math.round(roundTimer) + " Seconds"+
							"\n\nIncoming\nAliens\n" +(enemyCount-(enemiesAlive+enemiesDead))+
							"\n\nTotal Scrap\n" + scrap ,fontColour,16);
		
		if(!actors.contains(gameInfo))
		{
			setup(difficulty,currentLevel);
			gameInfo.setLocation(13f,ySpacing);
			actors.add(gameInfo);
		}
			
		//Updates the damage timer on screen
		if(damageTime != null)
		{
			ySpacing = 625f;
			if(damageText == null)
			{
				damageText = new Word();
				damageText.setWord("Damage Up\n" + (int) (15-damageTime.getElapsedTime().asSeconds()) + " Seconds",fontColour,16);
				damageText.setLocation(13f,ySpacing);
				itemInfo.add(damageText);
				actors.add(damageText);
			
			}
			else if((15-damageTime.getElapsedTime().asSeconds())>0)
			{
				damageText.setWord("Damage Up\n" + (int) (15-damageTime.getElapsedTime().asSeconds()) + " Seconds",fontColour,16);
			}
			else
			{
				for(Tower tow: towers)
				{
					tow.setDefaultDamage();
				}
				itemInfo.remove(damageText);
				actors.remove(damageText);
				damageTime = null;
				damageText = null;

			}

			
		}
		//Updates the firerate timer on screen
		if(firerateTime != null)
		{
			ySpacing = 670f;
			if(firerateText == null)
			{
				firerateText = new Word();
				firerateText.setWord("Fire Rate Up\n" + (int) (15-firerateTime.getElapsedTime().asSeconds()) + " Seconds",fontColour,16);
				firerateText.setLocation(13f,ySpacing);
				itemInfo.add(firerateText);
				actors.add(firerateText);
			
			}
			else if((15-firerateTime.getElapsedTime().asSeconds())>0)
			{
				firerateText.setWord("Fire Rate Up\n" + (int) (15-firerateTime.getElapsedTime().asSeconds()) + " Seconds",fontColour,16);
			}
			else
			{
				for(Tower tow: towers)
				{
					tow.setDefaultCooldown();
				}
				itemInfo.remove(firerateText);
				actors.remove(firerateText);
				firerateTime = null;
				firerateText = null;
			}

		}
		/*if(piercing != null)
		{
			ySpacing = 625f;
			if(itemText == null)
			{
				damageText = new Word();
				damageText.setWord("Damage Up\n" + (int) (15-damageTime.getElapsedTime().asSeconds()) + " Seconds",fontColour,16);
				damageText.setLocation(13f,ySpacing);
				itemInfo.add(damageText);
				actors.add(damageText);
				ySpacing+=45f;
			
			}
			else
			{
				itemText.setWord("Damage Up\n" + (int) (15-damageTime.getElapsedTime().asSeconds()) + " Seconds",fontColour,16);
			}

			
		}*/
		if(fortuneTime != null)
		{
			ySpacing = 715f;
			if(fortuneText == null)
			{
				fortuneText = new Word();
				fortuneText.setWord("Scrap Value Up\n" + (int) (15-fortuneTime.getElapsedTime().asSeconds()) + " Seconds",fontColour,16);
				fortuneText.setLocation(13f,ySpacing);
				itemInfo.add(fortuneText);
				actors.add(fortuneText);
			}
			else if((15-fortuneTime.getElapsedTime().asSeconds())>0)
			{
				fortuneText.setWord("Scrap Value Up\n" + (int) (15-fortuneTime.getElapsedTime().asSeconds()) + " Seconds",fontColour,16);
			}
			else
			{
				scrapValue  =  baseScrapValue;
				itemInfo.remove(fortuneText);
				actors.remove(fortuneText);
				fortuneTime = null;
				fortuneText = null;	
			}
		}
	}
	
	public void displayInfo(Tower t)
	{
		float ySpacing = 305f;
		towerInfo.setWord("\nName\n" + t.getID()+
						"\n\nKillcount\n" + t.getKillCount()+
						"\n\nRank " + t.getRank()+
						"\n\nDamage " + t.getDamage()+
						"\n\nType\n" + t.getType()+
						"\n\nRange " + t.getRange()+
						"\n\nFirerate\n" + 1000/t.getCooldown()+ "SPS" ,Color.YELLOW,16);

		if(!actors.contains(towerInfo))
		{
			towerInfo.setLocation(13f,ySpacing);
			actors.add(towerInfo);
		}
	}
	
	//NPC info cant be displayed at the same time as a tower
	/*public void displayInfo(NPC n)
	{

		Word[] towerInfo = new Word[6];
		for(int i=0;i<towerInfo.length;i++)
		{
			towerInfo[i] = new Word();
		}
		towerInfo[0].setWord("Name " + n.getID(),fontColour, 12);
		towerInfo[1].setWord("Health " + n.getHealth(),fontColour, 12);
		towerInfo[2].setWord("Armour " + n.getArmour(),fontColour, 12);
		towerInfo[4].setWord("Type " + n.getType(),fontColour, 12);
		float ySpacing = 305f;
		for(int i=0;i<towerInfo.length;i++)
		{
			towerInfo[i].setLocation(13f,ySpacing);
			actors.add(towerInfo[i]);
			ySpacing+=20f;
		}
	}*/
	
	
	void generateItem(NPC npc)
	{
		Random r = new Random();
		int number = r.nextInt(100);
		int itemNumber;
		//85% chance of scrap drop
		if(number < 50)
		{
			//effect = 0;
			itemNumber = 0;
		} //3% chance for powerups
		else
		{
			//effect = 1;
			itemNumber = 4;
		}
		/*else if(number > 87 && number < 91)
		{
			//effect = 2;
			itemNumber = 2;
		}
		else if(number > 90 && number < 94)
		{
			//effect = 3;
			itemNumber = 3;
		}
		else //6% chance for double money
		{
			//effect = 4;
			itemNumber = 4;
		}*/
		Item drop = new Item(npc.getX(),npc.getY(),itemNumber);
		items.add(drop);//Need to be changed so no items spawn in if enemy died at the end
		actors.add(drop);
	}
	
	public void updateTimers()
	{
		if(wTime != null) roundTimer -= wTime.restart().asSeconds();
		//if(damageTime != null)  -= wTime.restart().asSeconds();
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
		ButtonsIMG[6].setLocation(885f, 256f);
		ButtonsIMG[7].setLocation(949f, 256f);
		ButtonsIMG[8].setLocation(885f, 317f);
		ButtonsIMG[9].setLocation(888f, 449f); //Traps start
		ButtonsIMG[10].setLocation(950f, 449f);
		ButtonsIMG[11].setLocation(888f, 508f);
		
		rect = new GenButton[towerFile.length];
		for(int i=0; i<towerFile.length; i++)
			rect[i] = new GenButton(40, 43, Color.GREEN, 0);
		
		rect[0].setLocation((float) 888, (float) 82);	
		rect[1].setLocation((float) 950, (float) 82);	
		rect[2].setLocation((float) 888, (float) 142);	
		rect[3].setLocation((float) 950, (float) 142);	
		rect[4].setLocation((float) 888, (float) 202);	
		rect[5].setLocation((float) 950, (float) 202);	
		rect[6].setLocation((float) 888, (float) 263);	
		rect[7].setLocation((float) 950, (float) 263);	
		rect[8].setLocation((float) 888, (float) 323);	
		rect[9].setLocation((float) 888, (float) 449); //Traps start	
		rect[10].setLocation((float) 950, (float) 449);	
		rect[11].setLocation((float) 888, (float) 509);	
	
		Clock time = new Clock(); //Need to be done somewhere (as the game is running)
		Clock frameTime = new Clock(); //Movement is independent of framerate
	
		Tower towerToPlace = null; //Turret at cursor positon 
		Stack<Actor> toRemove = new Stack<>();
		Stack<Tower> towerToRemove = new Stack<>();
		Stack<NPC> npcToRemove = new Stack<>();
		Stack<Item> itemToRemove = new Stack<>();
	
		while (window.isOpen( )) 
		{

			float elapsedTime = frameTime.restart().asSeconds();
			//System.out.println("ElapsedTime :" + elapsedTime);
			
			// Clear the screen
			window.clear(Color.WHITE);
			
			mouseLoc = mouseMov.getPosition(window);
			//Actors displayed first due to map
			for (Actor actor : actors) {
				if (actor.needsRemoving(screenWidth, screenHeight))
					toRemove.push(actor);
				actor.calcMove(0, 0, screenWidth, screenHeight,elapsedTime);
				actor.performMove( );
				actor.draw(window);						
			}
			//System.out.println("WaveTimer " + roundTimer);
			//System.out.println("WaveTimerRounded " + Math.round(roundTimer));
			displayInfo();
			updateTimers();
			//System.out.println("Scrap value: " + scrap);

			//Make sure the timer can only go down to 0
			if(roundTimer<=0)
			{
				roundTimer = 0;
				wTime = null;
			
			}
			//New wave setup
			//System.out.println("EnemiesDead " + enemiesDead);
			//System.out.println("EnemyAliveDead " + (enemiesDead + enemiesAlive));
			if(enemiesDead == enemyCount)
			{
				if(difficulty.equals("easy"))
				{
					enemyCount = 5;
					roundTimer = 5;
				}
				if(difficulty.equals("medium")) enemyCount = 500;
				if(difficulty.equals("hard")) enemyCount = 1000;
				enemiesDead = 0;
				round++;
				//Go to next screen
				if(round==maxRound+1) 
				{
					GameWindow g = new GameWindow();
					g.run();
				}
				
				startTime();
			}

						
			// Update towers
			for(Tower tower : towers)
			{				
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
                    tower.calcMoveCursor(mouseLoc.x, mouseLoc.y, 0, 0, screenWidth, screenWidth);
                } else {
                    // otherwise calculating the rotation move to aim at players
                    NPC nearestEnemy = tower.getNearestEnemy(npcs);
                    // checking that the nearest enemy isn't null and then checking the health of the npc we are shooting to see if they are dead
                    if(nearestEnemy != null && nearestEnemy.getHealth() < 0 )
                        npcs.remove(nearestEnemy);
                    // rotating the tower to face the enemy
                    tower.calcMove(0, 0, screenWidth, screenWidth,elapsedTime);
                }
				
				//Shooting of the tower
				/*if (tower.getNearestEnemy(npcs) != null && tower.getFireRate() > tower.getCooldown())
				{
					tower.calcBulletOrigin();
					Bullet b = new Bullet(tower.getBulletOriginX(),tower.getBulletOriginY(),0,"bullet.png",0,500f);
					b.setTarget(tower.getNearestEnemy(npcs));
					actors.add(b);
					bullets.add(b);
					tower.setBullet(b);
					b.calculateDistanceToTarget();
					tower.setFireRate();
				}*/
				// performing the move and drawing to the window
                tower.performMove();
                tower.draw(window);
			}
			
			for(NPC npc: npcs)
			{
				if(npc.getHealth() <= 0 ) 
				{	
					toRemove.push(npc);
					npcToRemove.push(npc);
					System.out.println("NPC removed");
					enemiesAlive--;
					enemiesDead++;
					generateItem(npc);
				}
			}
			
			for(Bullet bl : bullets)
			{
				bl.calculateDistanceToTarget();
				if(bl.enemy != null)bl.checkProximity();
			}


			
			for (ImageAct buttonsIMG: ButtonsIMG)
				buttonsIMG.draw(window);
			
			for (GenButton rects: rect)
				rects.draw(window);

			for (Actor actor : toRemove) {
				actors.remove(actor);
			}
			
			for (Item item : itemToRemove) {
				items.remove(item);
			}
			
			for (Tower tow : towerToRemove) {
				towers.remove(tow);
			}
			
			for (NPC npc : npcToRemove) {
				npcs.remove(npc);
			}

			//Enemies spawn every .5  seconds
			if(roundTimer == 0)
			{		
				if((enemiesDead+enemiesAlive) < enemyCount && time.getElapsedTime().asMilliseconds() > 500)
				{
					Random r = new Random();
					int random = r.nextInt(enemyFile.length);
					Gremlin enemy = new Gremlin(134f, 134f, 0,difficulty, selectedMap);
					npcs.add(enemy);
					//npcs.add(new NPC(134f,134f,0,enemyFile[random], selectedMap));
					actors.add(enemy);
					time.restart();
					enemiesAlive++;
				}
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
					if(towerToPlace != null)towerToPlace.calcMoveCursor(mouseLoc.x, mouseLoc.y,0,0,screenWidth,screenHeight);
					for (Item item : items) {
						//Could probably rewrite this
						if(item.getImg().getGlobalBounds().contains(mouseLoc.x,mouseLoc.y))
						{
							//Adjust tower damage depending on difficulty
							if(item.getEffect()==1)
							{
								for(Tower tow : towers)
								{
									if(difficulty.equals("easy"))
									{
										if(tow.getDamage()==tow.getBaseDamage())tow.setDamage(2f);
										//Set clock to 15 seconds
										damageTime = new Clock();
									}
									else if(difficulty.equals("intermediate"))
									{
										if(tow.getDamage()==tow.getBaseDamage())tow.setDamage(1.5f);
									}
									else if(difficulty.equals("hard"))
									{
										if(tow.getDamage()==tow.getBaseDamage())tow.setDamage(1.2f);
									}
								}
							}
							else if(item.getEffect()==2)
							{
								//Adjust tower firerate depending on difficulty
								for(Tower tow : towers)
								{
									if(difficulty.equals("easy"))
									{					
										if(tow.getCooldown()==tow.getBaseCooldown())tow.setCooldown(0.5f);
										firerateTime = new Clock();
									}
									else if(difficulty.equals("intermediate"))
									{								
										if(tow.getCooldown()==tow.getBaseCooldown())tow.setCooldown(0.7f);
										firerateTime = new Clock();
									}
									else if(difficulty.equals("hard"))
									{
										if(tow.getCooldown()==tow.getBaseCooldown())tow.setCooldown(0.85f);
										firerateTime = new Clock();
									}
								}
							}/*
							else if(item.getEffect()==3)
							{
								//effect = "piercing";
								for(NPC npc : npcs)
								{
									npc.setType("normal");
								}
							}*/
							else if(item.getEffect()==4)
							{
								//Value of scrap increases depending on difficulty
								if(difficulty.equals("easy"))
								{
									if(scrapValue==baseScrapValue)scrapValue *= 3;
									fortuneTime = new Clock();
								}
								else if(difficulty.equals("intermediate"))
								{
									if(scrapValue==baseScrapValue)scrapValue *= 2;
									fortuneTime = new Clock();
								}
								else if(difficulty.equals("hard"))
								{
									if(scrapValue==baseScrapValue)scrapValue *= 1.5;
									fortuneTime = new Clock();
								}
							}
							else
							{
								//System.out.println("Scrap Value " + scrap);
								scrap+=scrapValue;
							}
							itemToRemove.push(item);
							toRemove.push(item);
							
						}
							
					}
				}
			
				//Draws a tower at the cursors position on map
				if((mouseLoc.x < 855f && mouseLoc.x > 150f) && (mouseLoc.y < 745f && mouseLoc.y > 40))
				{
					if(event.type == Event.Type.MOUSE_BUTTON_RELEASED){
						if(event.asMouseButtonEvent().button == Mouse.Button.LEFT)
						{
							
							// checking if we are currently trying to place a tower
							if (towerToPlace != null && !towerToPlace.getPlaced()) {
								// checking if the place we are trying to place the tower is valid
								if (towerToPlace.placementCheck(mouseLoc.x, mouseLoc.y, towerToPlace.getImg().getGlobalBounds(), towers)) {
									// updating the tower to acknowledge it has been placed
									towerToPlace.setPlaced(true);
									// resetting the towerToPlace variable to null
									towerToPlace = null;
									//Start round timer
									startTime();
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
										displayInfo(tower);
									}
								}
							}
							
							//NEED TO INTEGRATE THIS TO REMOVE TOWER INFO
							/*if(tow.getImg().getGlobalBounds().contains(mouseLoc.x,mouseLoc.y))
							{
								
							}
							else if(actors.contains(towerInfo[0]))//Good enough condition???
							{
								for(int i=0;i<towerInfo.length;i++)
								{
									toRemove.push(towerInfo[i]);
								}
							}*/

						}//Do this work after tower changes -- Should remove tower from mouse
					}	
				}
				
				// checking if the event is a keyboard press
                if (event.type == Event.Type.KEY_PRESSED) {
                    // getting the event as a key event
                    event.asKeyEvent();

                    // checking if the key pressed is T for creating / destroying a new tower
                    /*if (Keyboard.isKeyPressed(Keyboard.Key.T)) {
                        // checking if we are currently placing a tower
                        if (towerToPlace == null) {
                            System.out.println("T pressed, creating a new tower");
                            // creating a new tower based off the mouse coordinates and setting it up for placement
							towerToPlace = new Tower(mouseLoc.x, mouseLoc.y, 0, towerFile[selectedTower], 150, selectedMap, false);
                            // adding the new tower to the towers list
                            towers.add(towerToPlace);
                        } else {
                            // otherwise removing the towerToPlace from the list if T is pressed again whilst moving a tower
                            towers.remove(towerToPlace);
                            // resetting the towerToPlace 
                            towerToPlace = null;
                        }
                    }*/

                    // checking if the key pressed is U for upgrading the currently selected tower
                    if (Keyboard.isKeyPressed(Keyboard.Key.U)) {
                        // checking we currently have a tower selected
                        if (towerToPlace != null) {
                            // upgrading the tower
                            towerToPlace.upgrade(towerFile);
                        }
                    }
                }
				
				//Buttons
				for(int i=0;i<rect.length;i++)
				{
					if (rect[i].detectPos(rect[i].getRectPosition(), rect[i].getRectDimensions(), mouseLoc)) 
					{
						rect[i].setRectColor(Color.GREEN, 40); //If not enough money appear red
						if (event.type == Event.Type.MOUSE_BUTTON_RELEASED) {
							if(event.asMouseButtonEvent().button == Mouse.Button.LEFT)
							{	
								selectedTower = i;
								if (towerToPlace == null) {
									System.out.println("T pressed, creating a new tower");
									// creating a new tower based off the mouse coordinates and setting it up for placement
									towerToPlace = new Tower(mouseLoc.x, mouseLoc.y, 0, towerFile[selectedTower], 150, selectedMap, false);
									// adding the new tower to the towers list
									towers.add(towerToPlace);
								} else {
									// otherwise removing the towerToPlace from the list if T is pressed again whilst moving a tower
									towers.remove(towerToPlace);
									// resetting the towerToPlace 
									towerToPlace = null;
								}
							
							}
						}
					}
						else {rect[i].setRectColor(Color.TRANSPARENT, 0);}
				}
				
			}
		}
	}
}//BEFORE DOING NPC INFO COMBINE ALL OF TOWER INFO INTO ONE STRING
/* To do List 
// Pathing - Constructor for which level/map, save state(wave, base health, difficulty),Text for NPC's,Enemy composition for each wave e.g only 1 enemy type for level 1,remove towers and enemies from actors
// NPC - Need to test on last 2 maps,Need to randomise enemy spawns ,recalculate damage depending on resistance
// Towers -  Towers are 40 x 40, Need to make turret subclass, and implement turret upgrades
// Items - Prevent drops if they reach the base, harder enemies have more drops, Piercing left
// Bullet - Depends on how animation is implemented
// ***Relies on border colour being in at least 3 directions
// ***Equal distance should be a non-issue if enemys always starts close or at the edge of the screen
// ***Only works for straight edges on the borders - Limitation
// *** If boundaryboxes are used then sprite <= lane size wont fit
*/