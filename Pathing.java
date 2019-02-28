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

	private static String Title   = "Constellation";
	private static RenderWindow window;
	private static String enemyFile[] = {"Enemies/Death/enemy.png","Enemies/Death/enemy2.png","Enemies/Death/enemy3.png","Enemies/Death/enemy4.png","Enemies/Death/enemy5.png","Enemies/Death/enemy6.png"};
	private static String towerFile[] = {
											"Towers/tower-1.png","Towers/tower-2.png","Towers/tower-3.png",
											"Towers/tower-4.png","Towers/tower-5.png","Towers/tower-6.png",
											"Towers/tower-7.png","Towers/tower-8.png","Towers/tower-9.png","Towers/tower-10.png",
											"Towers/tower-11.png","Towers/tower-12.png","Towers/tower-13.png"};
	private static String tAnimFile[]  = {
											"Towers/1-alien/1.png","Towers/2-miniship/1.png","Towers/3-minicannon/1.png",
											"Towers/4-starship/1.png","Towers/5-startower/1.png","Towers/6-galaxygun/1.png",
											"Towers/7-dreadnought/1.png","Towers/8-ionturret/1.png","Towers/9-warpship/1.png","Towers/tower-10.png",
											"Towers/tower-11.png","Towers/tower-12.png","Towers/tower-13.png"};
											
	private static String backgroundFile[] = {"Maps/Map1.png","Maps/Map2.png","Maps/Map3.png","Maps/Map4.png","Maps/Map5.png"};
	private static Background background;
	private ArrayList<NPC> npcs = new ArrayList<NPC>( );
	private ArrayList<Tower> towers = new ArrayList<Tower>( );
	private ArrayList<Actor> actors = new ArrayList<Actor>( );
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>( );
	private ArrayList<Item> items = new ArrayList<Item>( );
	
	//All Maps
	//OriginX,OriginY,Rotation,filename,StartingArea,BorderColor,IntersectionColor,placementColor1,placementColor2,accuracy
	private final Background map1 = new Background(512,384,0, backgroundFile[0],new IntRect(177,100,1,28),new Color(255,0,157),new Color(255,255,255),new Color(75,105,47),new Color(58,68,78),20);
	private final Background map2 = new Background(512,384,0, backgroundFile[1],new IntRect(233,51,26,1),new Color(124,0,20),new Color(126,57,140),new Color(75,255,255),new Color(78,163,147),20);
	private final Background map3 = new Background(512,384,0, backgroundFile[2],new IntRect(818,123,1,29),new Color(172,49,49),new Color(255,255,255),new Color(155,173,183),new Color(164,119,98),20);
	private final Background map4 = new Background(512,384,0, backgroundFile[3],new IntRect(333,27,27,1),new Color(135,18,20),new Color(255,255,255),new Color(156,102,97),new Color(103,255,37),20);
	private final Background map5 = new Background(512,384,0, backgroundFile[4],new IntRect(260,39,27,1),new Color(255,255,255),new Color(0,0,0),new Color(187,244,131),new Color(102,57,49),20);
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
	private int scrap;
	private final int baseScrapValue = 5;
	private int scrapValue = baseScrapValue;
	private int damageMultiplier;
	private int[] enemyComposition = new int[6]; //Each index represents the number of that enemy for the wave
	private int noOfEnemyTypes;
	
	private Word gameInfo = new Word();
	private Word costUpgradeInfo = new Word();
	private Word towerInfo = new Word();
	private Word enemyInfo = new Word();
	private ArrayList<Word> itemInfo = new ArrayList<Word>();
	private final float xSpacing = 20f;
	
	private Word damageText = null;
	private Word firerateText = null;
	private Word piercingText = null;
	private Word fortuneText = null;
	
	private Clock damageTime; //Keeps track of damage boost time
	private Clock firerateTime; //Keeps track of fire rate
	private Clock piercingTime; //Keeps track of piercing duration
	private Clock fortuneTime; //Keeps track of money boost
	private final int powerDuration = 15;
	
	private boolean pauseScreenOn = false;
	private int count = 0;
	
	//private AlertScreen pauseScreen;
	private BackgroundMusic BGM;
	
	private int selectedTower; //number represented tower selected
	/**
	* Constructor for intialising variables using the save file
	* @param r The windows the game is displayed on
	* @param s The save file used to determine what the state the game is in
	*/
	Pathing(RenderWindow r, Save s)
	{
		window = r;
		currentLevel = s.getCurrLvl();
		round = s.getCurrRound();
		difficulty = s.getDiff();
		scrap = 10000;
		BGM = new BackgroundMusic();
		BGM.play();
		//pauseScreen = new AlertScreen("pause");
		displayInfo();
	}
	/**
	* Starts wave timer
	*/
	public void startTime()
	{
		if(wTime==null)
		{
			wTime = new Clock();
		}
	}
	/**
	* Initialises variables depending on difficuly and current level/map
	* @param difficulty The difficulty of the game
	* @param currentLevel The level the player is on
	*/
	public void setup(String difficulty, int currentLevel)
	{
		
		//Probably switch to a case statment
		if(currentLevel == 1)
		{
			maxRound = 2;
			selectedMap = map1;
			noOfEnemyTypes = 1;
			actors.add(map1);
		}
		else if(currentLevel == 2)
		{
			maxRound = 20;
			selectedMap = map2;
			noOfEnemyTypes = 3;
			actors.add(map2);
		}
		else if(currentLevel == 3)
		{
			maxRound = 30;
			selectedMap = map3;
			noOfEnemyTypes = 5;
			actors.add(map3);
		}
		else if(currentLevel == 4)
		{
			maxRound = 40;
			selectedMap = map4;
			noOfEnemyTypes = 6;
			actors.add(map4);
		}
		else if(currentLevel == 5)
		{		
			maxRound = 50;
			selectedMap = map5;
			noOfEnemyTypes = 6;
			actors.add(map5);
		}
		else
		{
			//Sandbox map
			System.out.println("Sandbox Map");
		}
		
		if(difficulty.equals("easy"))
		{
			baseHealth = 5;
			roundTimer = 5f; 
			enemyCount = 50;
			//Randomly generates how many of each type of enemy
			for(int i=0;i<enemyCount;i++)
			{
				Random r = new Random();
				enemyComposition[r.nextInt(noOfEnemyTypes)]++;
			}
		}
		else if(difficulty.equals("intermediate"))
		{
			baseHealth = 150;
			roundTimer = 45f; 
			enemyCount = 500;
			for(int i=0;i<enemyCount;i++)
			{
				Random r = new Random();
				enemyComposition[r.nextInt(noOfEnemyTypes)]++;
			}
		}
		else if(difficulty.equals("hard"))
		{
			baseHealth = 100;
			roundTimer = 30f; 
			enemyCount = 1000;
			for(int i=0;i<enemyCount;i++)
			{
				Random r = new Random();
				enemyComposition[r.nextInt(noOfEnemyTypes)]++;
			}
		}
	}
	/**
	* Displays information about the level on the 'stats' bar and timers for power ups
	*/
	public void displayInfo()
	{
		//Displays game information
		float ySpacing = 70f;
		gameInfo.setFont("ARCADECLASSIC.ttf");
		gameInfo.setWord("Wave\n" + round +
							"\n\nBase Health\n" + baseHealth +
							"\n\nNext round in\n" + Math.round(roundTimer) + " Seconds"+
							"\n\nIncoming\nAliens\n" +(enemyCount-(enemiesAlive+enemiesDead))+
							"\n\nTotal Scrap\n" + scrap ,fontColour,16);
		
		if(!actors.contains(gameInfo))
		{
			setup(difficulty,currentLevel);
			gameInfo.setLocation(xSpacing,ySpacing);
			actors.add(gameInfo);
		}
					
		//Updates the damage timer on screen
		if(damageTime != null)
		{
			ySpacing = 600f;
			if(damageText == null)
			{
				damageText = new Word();
				damageText.setWord("Damage Up\n" + (int) (powerDuration-damageTime.getElapsedTime().asSeconds()) + " Seconds",Color.RED,16);
				damageText.setLocation(xSpacing,ySpacing);
				itemInfo.add(damageText);
				actors.add(damageText);
			
			}
			else if((powerDuration-damageTime.getElapsedTime().asSeconds())>0)
			{
				damageText.setWord("Damage Up\n" + (int) (powerDuration-damageTime.getElapsedTime().asSeconds()) + " Seconds",Color.RED,16);
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
			ySpacing = 635f;
			if(firerateText == null)
			{
				firerateText = new Word();
				firerateText.setWord("Fire Rate Up\n" + (int) (powerDuration-firerateTime.getElapsedTime().asSeconds()) + " Seconds",Color.MAGENTA,16);
				firerateText.setLocation(xSpacing,ySpacing);
				itemInfo.add(firerateText);
				actors.add(firerateText);
			
			}
			else if((powerDuration-firerateTime.getElapsedTime().asSeconds())>0)
			{
				firerateText.setWord("Fire Rate Up\n" + (int) (powerDuration-firerateTime.getElapsedTime().asSeconds()) + " Seconds",Color.MAGENTA,16);
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
		//Updates the piercing timer on screen
		if(piercingTime != null)
		{
			ySpacing = 670f;
			if(piercingText == null)
			{
				piercingText = new Word();
				piercingText.setWord("Alien Type NULL\n" + (int) (powerDuration-piercingTime.getElapsedTime().asSeconds()) + " Seconds",Color.WHITE,16);
				piercingText.setLocation(xSpacing,ySpacing);
				itemInfo.add(piercingText);
				actors.add(piercingText);
			
			}
			else if((powerDuration-piercingTime.getElapsedTime().asSeconds())>0)
			{
				piercingText.setWord("Alien Type NULL\n" + (int) (powerDuration-piercingTime.getElapsedTime().asSeconds()) + " Seconds",Color.WHITE,16);
			}
			else
			{
				for(Tower tow: towers)
				{
					tow.setDefaultCooldown();
				}
				itemInfo.remove(piercingText);
				actors.remove(piercingText);
				piercingTime = null;
				piercingText = null;
			}
		}
		//Updates the fortune timer on screen
		if(fortuneTime != null)
		{
			ySpacing = 705f;
			if(fortuneText == null)
			{
				fortuneText = new Word();
				fortuneText.setWord("Scrap Value Up\n" + (int) (powerDuration-fortuneTime.getElapsedTime().asSeconds()) + " Seconds",Color.YELLOW,16);
				fortuneText.setLocation(xSpacing,ySpacing);
				itemInfo.add(fortuneText);
				actors.add(fortuneText);
			}
			else if((powerDuration-fortuneTime.getElapsedTime().asSeconds())>0)
			{
				fortuneText.setWord("Scrap Value Up\n" + (int) (powerDuration-fortuneTime.getElapsedTime().asSeconds()) + " Seconds",Color.YELLOW,16);
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
	
	/**Displays information relating to the selected tower on screen
	* @param t The tower that the information will be about
	*/
	public void displayInfo(Tower t)
	{
		float ySpacing = 305f;
		towerInfo.setWord("\nName\n" + t.getID()+
						"\n\nKillcount\n" + t.getKillCount()+
						"\n\nRank " + t.getRank()+
						"\n\nDamage " + t.getDamage()+
						"\n\nType\n" + t.getType()+
						"\n\nRange " + t.getRange()+
						"\n\nFirerate\n" + 1000/t.getCooldown()+ "SPS" ,new Color(255,165,0),16);

		if(!actors.contains(towerInfo) && !actors.contains(enemyInfo))
		{
			towerInfo.setLocation(xSpacing,ySpacing);
			actors.add(towerInfo);
		}//Displays range of the selected tower
		t.renderBorder(window, false);
	}
	
	/**Displays information relating to the selected NPC on screen
	* @param n The NPC that the information will be about
	*/
	public void displayInfo(NPC n)
	{	
		float ySpacing = 305f;
		enemyInfo.setWord("\nName\n" + n.getID()+
						"\n\nHealth\n" + n.getHealth()+
						"\n\nArmour\n" + n.getArmour()+
						"\n\nType\n" + n.getType(),Color.GREEN, 16);
		if(!actors.contains(enemyInfo)&& !actors.contains(towerInfo))
		{
			enemyInfo.setLocation(xSpacing,ySpacing);
			actors.add(enemyInfo);
		}
	}
	/**Displays the cost and upgrade cost of the selected tower on screen
	* @param towerName The tower whose cost will be displayed
	*/
	public void displayCost(String towerName)
	{
		float ySpacing = 132f;
		if(towerName.equals("alien")) costUpgradeInfo.setWord("Cost 50 \nUpgrade 100",Color.WHITE,18);
		if(towerName.equals("miniship")) costUpgradeInfo.setWord("Cost 100 \nUpgrade 250",Color.WHITE,18);
		if(towerName.equals("minicannon")) costUpgradeInfo.setWord("Cost 150 \nUpgrade 225",Color.WHITE,18);
		if(towerName.equals("starship")) costUpgradeInfo.setWord("Cost 250 \nUpgrade 600",Color.WHITE,18);
		if(towerName.equals("startower")) costUpgradeInfo.setWord("Cost 400 \nUpgrade 705",Color.WHITE,18);
		if(towerName.equals("galaxygun")) costUpgradeInfo.setWord("Cost 300 \nUpgrade 560",Color.WHITE,18);
		if(towerName.equals("dreadnought")) costUpgradeInfo.setWord("Cost 550 \nUpgrade 885",Color.WHITE,18);
		if(towerName.equals("ionturret")) costUpgradeInfo.setWord("Cost 900 \nUpgrade 1350",Color.WHITE,18);
		if(towerName.equals("warship")) costUpgradeInfo.setWord("Cost 1000 \nUpgrade 1500",Color.WHITE,18);
		if(towerName.equals("electricswarm")) costUpgradeInfo.setWord("Cost 100",Color.WHITE,18);
		if(towerName.equals("wormhole")) costUpgradeInfo.setWord("Cost 350",Color.WHITE,18);
		if(towerName.equals("blackhole")) costUpgradeInfo.setWord("Cost 900",Color.WHITE,18);
	
		if(!actors.contains(costUpgradeInfo))
		{
			costUpgradeInfo.setLocation(885f, 615f);
			actors.add(costUpgradeInfo);
		}
	}
	

	
	/**
	* Generates an item and drops it at the dead NPC's position
	* @param npc The npc where the item will be generated at
	*/
	void generateItem(NPC npc)
	{
		Random r = new Random();
		int number = r.nextInt(200);
		int itemNumber;
		//95% chance of scrap drop
		if(number < 195) itemNumber = 0;
		//1% chance for powerups
		else if(number > 194 && number < 196) itemNumber = 1;
		else if(number > 195 && number < 197)	itemNumber = 2;
		else if(number > 196 && number < 198)	itemNumber = 3;
		//1% chance for double money
		else itemNumber = 4;
		
		Item drop = new Item(npc.x,npc.y,itemNumber);
		items.add(drop);
		actors.add(drop);
	}
	/**
	* Updates the round timer
	*/
	public void updateTimers()
	{
		if(wTime != null) roundTimer -= wTime.restart().asSeconds();
	}

	
	public void run () {
				
		window.setFramerateLimit(60);
				
		//Buttons
		ButtonsIMG = new ImageAct[towerFile.length];
		for (int i=0; i<towerFile.length; i++)
			ButtonsIMG[i] = new ImageAct(towerFile[i]);
			
		float xSpacing = 895f;
		float ySpacing = 85f;
		for(int i=0; i<towerFile.length;i++)
		{
			ButtonsIMG[i].setLocation(xSpacing,ySpacing);
			if(i!=0 && i%2==1)ySpacing += 60;
			if(xSpacing==895f) xSpacing = 955f;
			else xSpacing = 895f;
			if(i==9) ySpacing = 455f;
		}

		rect = new GenButton[towerFile.length];
		for(int i=0; i<towerFile.length; i++)
			rect[i] = new GenButton(40, 43, Color.GREEN, 0);
		
		xSpacing = 888f;
		ySpacing = 82f;
		for(int i=0; i<towerFile.length;i++)
		{
			if(i==9)
			{
				ySpacing = 449f;
				continue;
			}
			rect[i].setLocation(xSpacing,ySpacing);
			if(i!=0 && i%2==1)ySpacing += 60;
			if(xSpacing==888f) xSpacing = 950f;
			else xSpacing = 888f;
			
		}	
		Clock time = new Clock(); //Timer used for enemy spawns
		Clock frameTime = new Clock(); //Movement is independent of framerate
	
		Tower towerToPlace = null; //Turret at cursor positon 
		Tower statTower = null; //Display stats of this turret 
		NPC statEnemy = null; //Display stats of this enemy 
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
			//Actors displayed first due to map being an actor
			for (Actor actor : actors) {
				if (actor.needsRemoving(screenWidth, screenHeight))
					toRemove.push(actor);
				actor.calcMove(0, 0, screenWidth, screenHeight,elapsedTime);
				actor.performMove( );
				actor.draw(window);						
			}

			displayInfo();
			updateTimers();
			if(statTower!=null) displayInfo(statTower);
			if(statEnemy!=null) displayInfo(statEnemy);

			//Make sure the timer can only go down to 0
			if(roundTimer<=0)
			{
				roundTimer = 0;
				wTime = null;
			
			}
			//New wave setup
			if(enemiesDead == enemyCount && round == maxRound) return;
			else if(enemiesDead == enemyCount)
			{
				if(difficulty.equals("easy"))
				{
					//Need to change this
					enemyCount = 50 + (15 * round);
					roundTimer = 5;
					for(int i=0;i<enemyCount;i++)
					{
						Random r = new Random();
						enemyComposition[r.nextInt(noOfEnemyTypes)]++;
					}
				}
				if(difficulty.equals("medium")) 
				{
					enemyCount = 500;
					roundTimer = 5;
					for(int i=0;i<enemyCount;i++)
					{
						Random r = new Random();
						enemyComposition[r.nextInt(noOfEnemyTypes)]++;
					}
			
				}
				if(difficulty.equals("hard"))
				{
					enemyCount = 1000;
					roundTimer = 5;
					for(int i=0;i<enemyCount;i++)
					{
						Random r = new Random();
						enemyComposition[r.nextInt(noOfEnemyTypes)]++;
					}
				}
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
                    // rendering the circular display to show range
                    tower.renderBorder(window, intersects);
                }
                // checking if the current tower is the tower being placed by the user
                if (!tower.getPlaced()) {
                    // moveing the tower to the mouses location but preventing it from going off the screen
                    tower.calcMoveCursor(mouseLoc.x, mouseLoc.y, 0, 0, screenWidth, screenWidth);
                } else {
                    // otherwise calculating the rotation move to aim at players
                    NPC nearestEnemy = tower.getNearestEnemy(npcs);
                    // rotating the tower to face the enemy
                    tower.calcMove(0, 0, screenWidth, screenWidth,elapsedTime);
                }
				
				//Shooting of the tower
				if (tower.getNearestEnemy(npcs) != null && tower.getFireRate() > tower.getCooldown() && tower.getPlaced())
				{
					tower.getNearest().setHealth(tower.getType(),tower.getDamage());
					
						if((tower.getFrame() < tower.calcDistance(tower.getNearest()))&&(tower.getFrame() < 896)){
							if(tower.getAnimationTime() > 100)
							{
								tower.setFrame(tower.getFrame()+128);
								tower.resetAnimationTime();
							}
						}else{
							tower.setFrame(0);
						}
					tower.anim(tower.getFrame(),115,2);
					tower.setFireRate();
				}
				else if(tower.getPlaced()==true && !tower.isTrap())
				{
					tower.anim(0,115,2);	
				}
				
				// performing the move and drawing to the window
                tower.performMove();
                tower.draw(window);
			}
			// Update NPC's
			for(NPC npc: npcs)
			{
				if(npc.getHealth() <= 0 ) 
				{	
					if(npc.getAnimationTime() > 225)
					{
						npc.setFrame(npc.getFrame()+32);
						npc.anim(npc.getFrame(),32,1);
						npc.resetAnimationTime();
						npc.setIsDead();
						//When the death animation has finished
						if(npc.getFrame() >= 256)
						{
							npcToRemove.push(npc);
							enemiesAlive--;
							enemiesDead++;
							Color exitColour = new Color(41,17,17);
							//Only drop items if killed by turrets
							if(!npc.isSimilar(selectedMap.getBackground().getPixel((int)Math.round(npc.x),(int)Math.round(npc.y)),exitColour,17))
							{
								for(int i=0;i<npc.getDropCount();i++)
									generateItem(npc);
							}
							else
							{
								baseHealth--;
								//Game over
								if(baseHealth==0)
								{
									//Display game over screen
								}
							}
							for(Tower tower: towers)
							{
								if(tower.getNearest()==npc)
								{
									tower.setKillCount();
									tower.setNearestEnemy(null);
								}
							}
						}
					}
				}
				for(Tower tower: towers)
				{
					if(tower.isTrap() && tower.getImg().getGlobalBounds().contains(npc.x,npc.y) && tower.getFireRate() > tower.getCooldown())
					{
						if(npc.getHealth() > 0)
						{
							npc.setHealth(tower.getType(),tower.getDamage());
							tower.setTrapHealth();
							tower.setFireRate();
						}
						if(tower.getTrapHealth()<=0)
						{
							towerToRemove.push(tower);
						}
					}
				}
				if(npc.getHealth() > 0 ) npc.calcMove(0, 0, screenWidth, screenHeight,elapsedTime);
				if(npc.getHealth() > 0 )npc.performMove();
                npc.draw(window);
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
					int random = r.nextInt(noOfEnemyTypes);
					//Randomly chooses an enemy type from the available options 
					while(true)
					{
						if(noOfEnemyTypes==0)
							break;
						else if(enemyComposition[random]==0)//Reduces the liklihood of getting an enemyType which is 0
						{
							if(random==noOfEnemyTypes-1)
							{
								noOfEnemyTypes -=1;
							}
						}
						else
						{
								if(random==0)
								{
									Gremlin enemy = new Gremlin(100f, 100f, 0,difficulty, selectedMap);
									npcs.add(enemy);
									enemy.anim(0,16,1);
								}
								else if(random==1)
								{
									SpeedDemon enemy = new SpeedDemon(100f, 100f, 0,difficulty, selectedMap);
									npcs.add(enemy);
									enemy.anim(0,16,1);
								}
								else if(random==2)
								{
									Yeti enemy = new Yeti(100f, 100f, 0,difficulty, selectedMap);
									npcs.add(enemy);
									enemy.anim(0,16,1);
								}
								else if(random==3)
								{
									Fenrir enemy = new Fenrir(100f, 100f, 0,difficulty, selectedMap);
									npcs.add(enemy);
									enemy.anim(0,16,1);
								}
								else if(random==4)
								{
									Phoenix enemy = new Phoenix(100f, 100f, 0,difficulty, selectedMap);
									npcs.add(enemy);
									enemy.anim(0,16,1);
								}
								else if(random==5)
								{
									Cthulhu enemy = new Cthulhu(100f, 100f, 0,difficulty, selectedMap);
									npcs.add(enemy);
									enemy.anim(0,16,1);
								}
								break;
						}
					}
					time.restart();
					enemiesAlive++;
				}
			}
			
			
			String ImageFile[] = {"InGameButtons/ff_button.png", "InGameButtons/pause_button.png", "InGameButtons/mute_button.png", "InGameButtons/unmute_button.png"};
			ImageAct inGameButtons[];
			GenButton settingsButtons[];
			inGameButtons = new ImageAct[ImageFile.length];
			settingsButtons = new GenButton[ImageFile.length];

			for (int i=0; i<ImageFile.length; i++) {
				inGameButtons[i] = new ImageAct(ImageFile[i]);
				settingsButtons[i] = new GenButton(30, 30, Color.BLUE, 70);
			}
		
			inGameButtons[0].setLocation((float) 880, (float) 715);
			inGameButtons[1].setLocation((float) 925, (float) 715);
			inGameButtons[2].setLocation((float) 970, (float) 715);
			settingsButtons[0].setLocation((float) 880, (float) 715);
			settingsButtons[1].setLocation((float) 925, (float) 715);
			settingsButtons[2].setLocation((float) 970, (float) 715);
			
			for (int i=0; i<3; i++) {
				inGameButtons[i].draw(window);
				settingsButtons[i].draw(window);
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
						//Apply item effects
						if(item.getImg().getGlobalBounds().contains(mouseLoc.x,mouseLoc.y))
						{
								
									if(item.getEffect()==1)//Adjust tower damage depending on difficulty
									{
										for(Tower tow : towers)
										{
											if(difficulty.equals("easy"))if(tow.getDamage()==tow.getBaseDamage())tow.setDamage(2f);
											else if(difficulty.equals("intermediate")) if(tow.getDamage()==tow.getBaseDamage())tow.setDamage(1.5f);
											else if(difficulty.equals("hard")) if(tow.getDamage()==tow.getBaseDamage())tow.setDamage(1.2f);
											damageTime = new Clock();
										}
									}
									else if(item.getEffect()==2)//Adjust tower firerate depending on difficulty
									{
										for(Tower tow : towers)
										{
											if(difficulty.equals("easy"))if(tow.getCooldown()==tow.getBaseCooldown())tow.setCooldown(0.5f);
											else if(difficulty.equals("intermediate")) if(tow.getCooldown()==tow.getBaseCooldown())tow.setCooldown(0.7f);
											else if(difficulty.equals("hard")) if(tow.getCooldown()==tow.getBaseCooldown())tow.setCooldown(0.85f);
											firerateTime = new Clock();
										}
									}
									else if(item.getEffect()==3)//Remove enemy defences
									{
										//effect = "piercing";
										for(NPC npc : npcs)
										{
											npc.setTypeNull();
										}
										piercingTime = new Clock();
									}
									else if(item.getEffect()==4) //Increase scrapValue
									{
										if(difficulty.equals("easy"))scrapValue *= 3;
										else if(difficulty.equals("intermediate")) scrapValue *= 2;
										else if(difficulty.equals("hard")) scrapValue *= 1.5;
										fortuneTime = new Clock();
									}
									else
									{
										scrap+=scrapValue;
									}
								
							itemToRemove.push(item);
							toRemove.push(item);
						}
					}
				}
				//Removes info about enemy or tower if click is register of the map
				if(event.type == Event.Type.MOUSE_BUTTON_RELEASED){
					if(event.asMouseButtonEvent().button == Mouse.Button.LEFT)
					{
							if(actors.contains(towerInfo))
							{
								actors.remove(towerInfo);
								statTower = null;
							}
							if(actors.contains(enemyInfo))
							{	
								actors.remove(enemyInfo);
								statEnemy = null;
							}
					}
				}
			
				//Draws a tower at the cursors position on map
				if((mouseLoc.x < 855f && mouseLoc.x > 150f) && (mouseLoc.y < 765f && mouseLoc.y > 40))
				{
					if(event.type == Event.Type.MOUSE_BUTTON_RELEASED){
						if(event.asMouseButtonEvent().button == Mouse.Button.LEFT)
						{
							
							// checking if we are currently trying to place a tower
							if (towerToPlace != null && !towerToPlace.getPlaced()) {
								// checking if the place we are trying to place the tower is valid
								if (towerToPlace.placementCheck(mouseLoc.x, mouseLoc.y, towerToPlace.getImg().getGlobalBounds(), towers,selectedMap)) {
									// updating the tower to acknowledge it has been placed
									towerToPlace.changeSpriteImage(tAnimFile[selectedTower]);
									towerToPlace.setPlaced(true);
									// resetting the towerToPlace variable to null
									towerToPlace = null;
									//Start round timer
									startTime();
								}
							}
							
							//Actions performed when a placed tower is clicked
							for(Tower tower : towers)
							{
								if(tower.within(mouseLoc.x,mouseLoc.y,10))
								{
										if(actors.contains(enemyInfo))
										{	
											actors.remove(enemyInfo);
											statEnemy = null;
										}
										displayInfo(tower);
										statTower = tower;
								}
							}
							//Actions performed when an enemy is clicked
							for(NPC npc : npcs)
							{
								if(npc.getImg().getGlobalBounds().contains(mouseLoc.x,mouseLoc.y))
								{
									if(actors.contains(towerInfo))
									{
										actors.remove(towerInfo);
										statTower = null;
									}
									displayInfo(npc);
									statEnemy = npc;
								}
							}
						}//Removes the tower
						else if(event.asMouseButtonEvent().button == Mouse.Button.RIGHT)
						{	
								// otherwise looping over all the towers and checking if we are trying to pick up a tower
								for (Tower tower : towers) {
									// checking if the mouse click is within a region of the tower
										if(tower.within(mouseLoc.x,mouseLoc.y,10))
										{
												towerToRemove.push(tower);
												scrap += (int)tower.getCost()/2;
										}
								}
						}
					}	
				}
				
				// checking if the event is a keyboard press
                if (event.type == Event.Type.KEY_PRESSED) {
                    // getting the event as a key event
                    event.asKeyEvent();

                    // checking if the key pressed is U for upgrading the currently selected tower
                    if (Keyboard.isKeyPressed(Keyboard.Key.U)) {
                        // checking we currently have a tower selected
						for(Tower tower : towers)
						{	
								if(tower.within(mouseLoc.x,mouseLoc.y,10))
								{
									// upgrading the tower
									if(scrap > tower.getUpgradeCost()) tower.upgrade();
								}
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
									switch(selectedTower){
										case 0:	towerToPlace = new Alien(mouseLoc.x, mouseLoc.y, 0, selectedMap, false);
												break;
										case 1: towerToPlace = new MiniShip(mouseLoc.x, mouseLoc.y, 0, selectedMap, false);
												break;
										case 2: towerToPlace = new MiniCannon(mouseLoc.x, mouseLoc.y, 0, selectedMap, false);
												break;
										case 3: towerToPlace = new StarShip(mouseLoc.x, mouseLoc.y, 0, selectedMap, false);
												break;
										case 4: towerToPlace = new StarTower(mouseLoc.x, mouseLoc.y, 0, selectedMap, false);
												break;
										case 5: towerToPlace = new GalaxyGun(mouseLoc.x, mouseLoc.y, 0, selectedMap, false);
												break;
										case 6: towerToPlace = new Dreadnought(mouseLoc.x, mouseLoc.y, 0, selectedMap, false);
												break;
										case 7: towerToPlace = new IonTurret(mouseLoc.x, mouseLoc.y, 0, selectedMap, false);
												break;
										case 8: towerToPlace = new WarpShip(mouseLoc.x, mouseLoc.y, 0, selectedMap, false);
												break;
										//case 9: towerToPlace = new DarkMatterCannon(mouseLoc.x, mouseLoc.y, 0, selectedMap, false);
											//	break;
										case 10: towerToPlace = new ElectricSwarm(mouseLoc.x, mouseLoc.y, 0, selectedMap, false);
												 break;									
										case 11: towerToPlace = new Wormhole(mouseLoc.x, mouseLoc.y, 0, selectedMap, false);
												 break;
										case 12: towerToPlace = new BlackHole(mouseLoc.x, mouseLoc.y, 0, selectedMap, false);
												 break;
										default: break;
									}
								
									// adding the new tower to the towers list
									if(scrap < towerToPlace.getCost())
									{
										towerToPlace = null;
										rect[i].setRectColor(Color.RED, 40);
										break;
									}
									else 
									{
										scrap -= towerToPlace.getCost();
										towers.add(towerToPlace);
									}
									
								} else {
									// otherwise removing the towerToPlace from the list if T is pressed again whilst moving a tower
									scrap += (int)towerToPlace.getCost()/2;
									towers.remove(towerToPlace);
									// resetting the towerToPlace 
									towerToPlace = null;
								}
							
							}
						}
						else if(event.type == Event.Type.MOUSE_MOVED) {
							int selectedTower2 = i;
								
									switch(selectedTower2){
									case 0:	displayCost("alien");
											break;
									case 1: displayCost("miniship");
											break;
									case 2: displayCost("minicannon");
											break;
									case 3: displayCost("starship");
											break;
									case 4: displayCost("startower");
											break;
									case 5: displayCost("galaxygun");
											break;
									case 6: displayCost("dreadnought");
											break;
									case 7: displayCost("ionturret");
											break;
									case 8: displayCost("warship");
											break;
									//case 9: towerToPlace = new DarkMatterCannon(mouseLoc.x, mouseLoc.y, 0, selectedMap, false);
										//	break;
									case 10: displayCost("electricswarm");
											 break;									
									case 11: displayCost("wormhole");
											 break;
									case 12: displayCost("blackhole");
											 break;
									default: break;
								}
						}
					}
						else {rect[i].setRectColor(Color.TRANSPARENT, 0);}
				}
				
				if (settingsButtons[2].detectPos(settingsButtons[2].getRectPosition(), settingsButtons[2].getRectDimensions(), mouseLoc)) {
					if (event.type == Event.Type.MOUSE_BUTTON_RELEASED) {

						if (count == 0) {
							count ++;
							BGM.stop();
						}
						else if (count == 1) {
							count = 0;
							BGM.play();
						}
					}
				}
				//if ( )
			}
		}
	}
}