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
	
	private static String Title = "Tower Defense";

	private static BackgroundMusic BGM;
	private static Mouse mouseMov;
	private static Vector2i mouseLoc;
	private static MainMenu mainMenu;
	private static StoryMode storyMode;
	
	private boolean mainMenuOn = true;
	private boolean mainMenuFunc = true;
	private boolean storyModeOn = false;
	private boolean storyModeFunc = false;
	private boolean scenesOn = false;
	private boolean alertScreenOn = false;
	
	public GameWindow() {
		BGM = new BackgroundMusic();
		BGM.play();
		
		mainMenu = new MainMenu();
		storyMode = new StoryMode();
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
			storyMode.display(window, count, storyModeOn, scenesOn);
			
			window.display();

			//check mouse coordinates in each frame
			mouseLoc = mouseMov.getPosition(window);
		
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
							storyModeOn = true;
							storyModeFunc = true;
						}
					}
					
					if (mainMenu.continueEvent(mouseLoc)) {}
						
					if (mainMenu.quickPlayEvent(mouseLoc)) {}
					
					if (mainMenu.exitEvent(mouseLoc)) {
						if (event.type == Event.Type.MOUSE_BUTTON_RELEASED)
							window.close();
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
								}
								else {
									QuestionScreen q = new QuestionScreen();
									alertScreenOn = true;
									//try {window.setActive(false);}
									//catch (ContextActivationException e) {System.out.println(e);}
									if (q.run() == 0) {
										alertScreenOn = false;
										storyModeOn = false;
										storyModeFunc = false;
										mainMenuOn = true;
										mainMenuFunc = true;
										//try {window.setActive(true);}
										//catch (ContextActivationException e) {System.out.println(e);}
									}
									else {
										System.out.println("Alert screen closed.");
										alertScreenOn = false;
										//try {window.setActive(false);}
										//catch (ContextActivationException e) {System.out.println(e);}	
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public static void main (String args[]) {
		GameWindow g = new GameWindow();
		g.run();
	}
}	
