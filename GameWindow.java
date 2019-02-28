/**
 *
 * Class name: GameWindow
 *
 * Author: Jordan Young
 *
**/

//import java.io.File;
//mport java.io.IOException;
//import java.nio.file.*;
//import java.util.ArrayList;
//import java.util.function.*;
//import java.io.InputStream;

import org.jsfml.system.*;
import org.jsfml.window.*;
//import org.jsfml.audio.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
//import org.jsfml.graphics.Image;

public class GameWindow {
	private static int screenWidth = 1024;
	private static int screenHeight = 768;
	
	private static String Title = "Constellation";

	private static BackgroundMusic BGM;
	private static Mouse mouseMov;
	private static Vector2i mouseLoc;
	private static MainMenu mainMenu;
	private static StoryMode storyMode;
	private static AlertScreen quitScreen, pauseScreen;
	private static ChooseLevDif chooseLevel, chooseDif;
	private static Save s;
	
	private boolean mainMenuOn = true, mainMenuFunc = true, storyModeOn = false, storyModeFunc = false,
					scenesOn = false, quitScreenOn = false, pauseScreenOn = true, chooseLevOn = false,
					chooseDifOn = false;
	
	public GameWindow() {
		BGM = new BackgroundMusic();
		BGM.play();
		
		mainMenu = new MainMenu();
		storyMode = new StoryMode();
		quitScreen = new AlertScreen("quitScreen");
		pauseScreen = new AlertScreen("pauseScreen");
		chooseLevel = new ChooseLevDif("Level");
		chooseDif = new ChooseLevDif("Difficulties");
	}
	
	public void run () {		
		// Create a window
		RenderWindow window = new RenderWindow();
		window.create(new VideoMode(screenWidth, screenHeight), Title, WindowStyle.CLOSE);

		window.setFramerateLimit(30); // Avoid excessive updates
		
		int count = 0;
		
		// Main loop
		while (window.isOpen()) {
			// Clear the screen	
			window.clear(Color.WHITE);
			
			// Update the display with any changes
		
			count = mainMenu.display(window, mainMenuOn, count);			//Draw mainmenu
			storyMode.display(window, count, storyModeOn, scenesOn,s);
			quitScreen.display(window, quitScreenOn, "quitScreen");
			pauseScreen.display(window, pauseScreenOn, "pauseScreen");
			chooseLevel.display(window, chooseLevOn, "level");		
			chooseDif.display(window, chooseDifOn, "difficulties");		

			//check mouse coordinates in each frame
			mouseLoc = mouseMov.getPosition(window);
			//System.out.println(mouseLoc);
		
			// Handle any events
			for (Event event : window.pollEvents()) {
				if (event.type == Event.Type.CLOSED) {
					// the user pressed the close button
					window.close();
				}
				
				//Main Menu Event 
				if (mainMenuFunc) {
					//*************** New Main Menu Event ****************
					if (mainMenu.newGameEvent(mouseLoc)) {
						if (event.type == Event.Type.MOUSE_BUTTON_RELEASED) {
							mainMenuOn = false;
							mainMenuFunc = false;
							chooseDifOn = true;
							
							//storyModeOn = true;
							//storyModeFunc = true;
						}
					}
					
					if (mainMenu.continueEvent(mouseLoc)) {
						if (event.type == Event.Type.MOUSE_BUTTON_RELEASED)
							System.out.println("Continue Clicked!");
							if (mouseMov.isButtonPressed(Mouse.Button.LEFT)) {
								s = new Save();
								s.contGame();
								storyModeOn = true;
								storyModeFunc = true;
								Pathing level = new Pathing(window,s);
								level.run();
								s.IncreaseLevel();
								if(s.getCurrLvl()==1) count = 23;
								if(s.getCurrLvl()==2) count = 35;
								if(s.getCurrLvl()==3) count = 55;
								if(s.getCurrLvl()==4) count = 69;
							}
					}
						
					if (mainMenu.quickPlayEvent(mouseLoc)) {
						if (event.type == Event.Type.MOUSE_BUTTON_RELEASED) {
							mainMenuOn = false;
							mainMenuFunc = false;
							chooseLevOn = true;
						}
					}
					
					if (mainMenu.exitEvent(mouseLoc)) {
						if (event.type == Event.Type.MOUSE_BUTTON_RELEASED)
							quitScreenOn = true;	
					}
				}
								
				
				//Story Mode Event
				if (storyModeFunc) {	
					for (int i=0; i<2; i++) {
						if (storyMode.buttonsEvent(mouseLoc, i)) {						//New Code****
							if (mouseMov.isButtonPressed(Mouse.Button.LEFT)) {
								if (i == 0) {
									count ++;
									System.out.println("button pressed: " + count);
									//chooseLevel = true;
									scenesOn = true;
									if(count==15)
									{
										scenesOn = false;
										BGM.stop();
										Pathing level1 = new Pathing(window,s);
										level1.run();
										s.IncreaseLevel();
										
										s.saveFile(s.getCurrLvl(),s.getCurrRound(),s.getDiff());
									}
									else if(count==23)
									{
										scenesOn = false;
										BGM.stop();
										Pathing level1 = new Pathing(window,s);
										level1.run();
										s.IncreaseLevel();
									}
									else if(count==35)
									{
										scenesOn = false;
										BGM.stop();
										Pathing level1 = new Pathing(window,s);
										level1.run();
										s.IncreaseLevel();
									}
									else if(count==55)
									{
										scenesOn = false;
										BGM.stop();
										Pathing level1 = new Pathing(window,s);
										level1.run();
										s.IncreaseLevel();
									}
									else if(count == 69)
									{
										scenesOn = false;
										BGM.stop();
										Pathing level1 = new Pathing(window,s);
										level1.run();
										s.IncreaseLevel();
										
									}
							

								}
								else {
									quitScreenOn = true;
									storyModeFunc = false;
								}
							}
						}
					}
				}
				
				//Pop-up alert Event
				if (quitScreenOn) {
					mainMenuFunc = false;
					storyModeFunc = false;
					for (int i=0; i<2; i++) {
						if (quitScreen.yesnoEvent(mouseLoc, i)) {
							if (event.type == Event.Type.MOUSE_BUTTON_RELEASED) {
								if (i == 0) {
									quitScreenOn = false;
									if (mainMenuOn)
										window.close();
									else {
										mainMenuOn = true;
										mainMenuFunc = true;
										storyModeOn = false;
										storyModeFunc = false;
									}
								}
								
								if (i == 1) {
									if (event.type == Event.Type.MOUSE_BUTTON_RELEASED) {
										quitScreenOn = false;
										if (mainMenuOn)
											mainMenuFunc = true;
										else if (storyModeOn)
											storyModeFunc = true;
									}
								}
							}
						}
					}
				}
				
				if (chooseLevOn) {
					for (int i=0; i<5; i++) {
						if (chooseLevel.mapEvent(mouseLoc, i)) {
							if (event.type == Event.Type.MOUSE_BUTTON_RELEASED) {
									System.out.println("Hello");
								
									Save quickSave = new Save();
									quickSave.newGame("easy");
									quickSave.setLevel(i+1);
								
									Pathing level = new Pathing(window,quickSave);
									level.run();
							}
						}
					}
					if (chooseLevel.backButtonEvent(mouseLoc)) {
						if (event.type == Event.Type.MOUSE_BUTTON_RELEASED) {
							chooseLevOn = false;
							mainMenuOn = true;
							mainMenuFunc = true;
						}
					}
				}
				
				if (chooseDifOn) {
					for (int i=0; i<3; i++) {
						if (chooseDif.difficultiesEvent(mouseLoc, i)) {
								if(mouseMov.isButtonPressed(Mouse.Button.LEFT))
								{	
									chooseDifOn = false;
									storyModeOn = true;
									storyModeFunc = true;
										
							
									//Creating new game, use the .contGame() method to continue instead
									s = new Save();
									if(i==0) 	s.newGame("easy");
									if(i==1) 	s.newGame("intermediate");
									if(i==2) 	s.newGame("hard");
								}
								
							
						}
					}
					if (chooseDif.backButtonEvent(mouseLoc)) {
						if (event.type == Event.Type.MOUSE_BUTTON_RELEASED) {
							chooseDifOn = false;
							mainMenuOn = true;
							mainMenuFunc = true;
						}
					}
				}
				
				/*if (pauseScreenOn) {
					for (int i=0; i<3; i++) {
						if (pauseScreen.pauseEvent(mouseLoc, i)) {
							if (event.type == Event.Type.MOUSE_BUTTON_RELEASED) {
								pauseScreenOn = false;
								if (i == 0) {
									
								}
								if (i == 1) {
									
								}
								if (i == 2) {
									
								}
							}
						}
					}
				}*/
			}
			
			window.display();

		}
	}

	public static void main (String args[]) {
		GameWindow g = new GameWindow();
		g.run();
	}
}	
