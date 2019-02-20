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
	private static String ImageFile = "Image/Wallpaper.png";
	private static String SoundFile = "Sounds/BGM.wav";
	private static String menuButtonFile[] = {"MenuButtons/playButton.png", "MenuButtons/levelButton.png", "MenuButtons/exitButton.png"};
	private static String inGameButtonFile[] = {"IngameButtons/playButton.png", "IngameButtons/back_button.png", "IngameButtons/pause.png", "IngameButtons/mute_button.png", "IngameButtons/unmute_button.png", "IngameButtons/ff_button.png"};
	private static ImageAct wallpaperIMG, menuButtonsIMG[], storyModeIMG, inGameButtonsIMG[];
	private static GenSound BGM, sound1;
	private static GenButton rect[], buttonBox[];
	private static Mouse mouseMov;
	private static Vector2i mouseLoc;
	private static MainMenu mainMenu;
	private static Word storyModeSubScene1[], storyModeSubScene2[], storyModeSubScene3[], storyModeSubScene4[], storyModeSubScene5[], storyModeSubScene6[], storyModeSubScene7[], storyModeSubScene8[], storyModeSubScene9[];
	
	private float buttonX = 50, buttonY = 0, adjustX, adjustY;
	private boolean mainMenuOn = true;
	private boolean storyModeOn = false;
	private boolean scenesOn = false;
	private boolean alertScreenOn = false;
	
	public void MainMenu(boolean on) {
		wallpaperIMG = new ImageAct(ImageFile);							//Wallpaper
		menuButtonsIMG = new ImageAct[menuButtonFile.length];			//Buttons Image
		rect = new GenButton[menuButtonFile.length];					//Buttons detection rectangle
		
		for (int i=0; i<menuButtonFile.length; i++)
			menuButtonsIMG[i] = new ImageAct(menuButtonFile[i]);
	
		menuButtonsIMG[0].setLocation(buttonX, buttonY);
		adjustX = 20; adjustY = -80;
		menuButtonsIMG[1].setLocation(buttonX - adjustX, buttonY + adjustY);
		adjustX = 0; adjustY = 100;
		menuButtonsIMG[2].setLocation(buttonX - adjustX, buttonY + adjustY);
		
		rect[0] = new GenButton(199, 80, Color.MAGENTA, 0);
		rect[1] = new GenButton(215, 63, Color.GREEN, 0);
		rect[2] = new GenButton(140, 50, Color.YELLOW, 0);
		rect[0].setLocation((float) 445, (float) 432);
		rect[1].setLocation((float) 440, (float) 540);
		rect[2].setLocation((float) 475, (float) 632);
	}
	
	public void StoryMode(boolean on) {
		int nextLine = 0, sizeAdjust = 0;
		storyModeSubScene1 = new Word[24];
		storyModeSubScene2 = new Word[14];
		storyModeSubScene3 = new Word[5];
		storyModeSubScene4 = new Word[13];
		storyModeSubScene5 = new Word[7];
		storyModeSubScene6 = new Word[19];
		storyModeSubScene7 = new Word[12];
		storyModeSubScene8 = new Word[10];
		storyModeSubScene9 = new Word[10];
		for (int i=0; i<24; i++) storyModeSubScene1[i] = new Word();
		//for (Word s: storyModeSubScene1) s = new Word();
		storyModeIMG = new ImageAct("StoryModeNew/level1.png"); 
		inGameButtonsIMG = new ImageAct[2];
		buttonBox = new GenButton[2];
		
		for (int i=0; i<inGameButtonsIMG.length; i++) {
			inGameButtonsIMG[i] = new ImageAct(inGameButtonFile[i]);
			buttonBox[i] = new GenButton(35 + sizeAdjust, 35 + sizeAdjust, Color.BLUE, 0);
			sizeAdjust += 5;
		}

		inGameButtonsIMG[0].setLocation((float) 980, (float) 10);
		inGameButtonsIMG[1].setLocation((float) 90, (float) 10);
		buttonBox[0].setLocation((float) 978, (float) 8);
		buttonBox[1].setLocation((float) 86, (float) 6);
					
		for (int i=0; i<24; i++) {
			if (!((i > 16 && i < 19) || (i > 20 && i < 23)))
				storyModeSubScene1[i].setWord(storyModeSubScene1[i].scene1(i), Color.BLACK, 24);
			else storyModeSubScene1[i].setWord(storyModeSubScene1[i].scene1(i), Color.RED, 24);
				
			storyModeSubScene1[i].setLocation((float) 67, (float) 605 + nextLine);
			nextLine += 30;
			if (i == 1 || i == 4 || i == 8 || i == 10 | i == 11 || i == 12 || i == 14 || i == 16 || i == 18 || i == 19 || i == 20 || i == 22 || i == 23)
				nextLine = 0;
		}
	}
	
	public void LevelScreen() {
		//scale all level screen to matching square
	}
	
	public void BackgroundMusic(boolean on) {
		BGM = new GenSound(SoundFile);
		BGM.loop(true);
		if (on == true) BGM.playSound();
	}
	
	public void run () {
		//System.out.println("ButtonFile array length: " + ButtonFile.length);
		
		// Create a window
		RenderWindow window = new RenderWindow();
		window.create(new VideoMode(screenWidth, screenHeight), Title, WindowStyle.CLOSE);

		window.setFramerateLimit(30); // Avoid excessive updates
			
		MainMenu(mainMenuOn);
		StoryMode(storyModeOn);
		BackgroundMusic(true);
		
		int count = 0;
		
		// Main loop
		while (window.isOpen()) {
			// Clear the screen
			window.clear(Color.WHITE);
			
			//check = false;
			
			if (mainMenuOn == true) {
				wallpaperIMG.draw(window);
				for (ImageAct buttonsIMG: menuButtonsIMG)
					buttonsIMG.draw(window);
				for (GenButton rects: rect)
					rects.draw(window);
			}
			
			if (storyModeOn == true) {					//DO SUB VALIDATION HERE *************
				storyModeIMG.draw(window);
				for (int i=0; i<2; i++) {
					inGameButtonsIMG[i].draw(window);
					buttonBox[i].draw(window);
				}
				if (scenesOn == true) {
					for (int i=0; i<24; i++) {
						if ((count == 1  && i < 2) || (count == 2 && i > 1 && i < 5) || 
							(count == 3 && i > 4 && i < 9) || (count == 4 && i > 8 && i < 11) ||
							(count == 5 && i == 11) || (count == 6 && i == 12) ||
							(count == 7 && i > 12 && i < 15) || (count == 8 && i > 14 && i < 17) || 
							(count == 9 && i > 16 && i < 19) || (count == 10 && i == 19) ||
							(count == 11 && i == 20) || (count == 12 && i > 20 && i < 23) ||
							(count == 13 && i == 23)) {
							storyModeSubScene1[i].draw(window);

						}
					}
				}
			}
			
			//if (alertScreenOn == true) {
				//check mouse coordinates in each frame
				mouseLoc = mouseMov.getPosition(window);
			//}
			
			// Update the display with any changes
			window.display();
		
			// Handle any events
			for (Event event : window.pollEvents()) {
				if (event.type == Event.Type.CLOSED) {
					// the user pressed the close button
					window.close();
				}
				//Main Menu Event 
				if (mainMenuOn == true) {
					if (rect[0].detectPos(rect[0].getRectPosition(), rect[0].getRectDimensions(), mouseLoc)) {
						rect[0].setRectColor(Color.MAGENTA, 40);
						if (event.type == Event.Type.MOUSE_BUTTON_RELEASED) {
							mainMenuOn = false;
							storyModeOn = true;
						}
					}
					else {rect[0].setRectColor(Color.TRANSPARENT, 0);}

					if (rect[1].detectPos(rect[1].getRectPosition(), rect[1].getRectDimensions(), mouseLoc))
						rect[1].setRectColor(Color.GREEN, 40);
					else {rect[1].setRectColor(Color.TRANSPARENT, 0);}

					if (rect[2].detectPos(rect[2].getRectPosition(), rect[2].getRectDimensions(), mouseLoc)) {
						rect[2].setRectColor(Color.YELLOW, 40);
						//if (mouseMov.isButtonPressed(Mouse.Button.LEFT))
						if (event.type == Event.Type.MOUSE_BUTTON_RELEASED)
							window.close();
					}	
					else {rect[2].setRectColor(Color.TRANSPARENT, 0);}
				}
				
				//Story Mode Event
				if (storyModeOn == true) {
					for (int i=0; i<inGameButtonsIMG.length; i++) {
						if (buttonBox[i].detectPos(buttonBox[i].getRectPosition(), buttonBox[i].getRectDimensions(), mouseLoc)) {
							buttonBox[i].setRectColor(Color.BLUE, 40);
							if (mouseMov.isButtonPressed(Mouse.Button.LEFT)) {
								if (i == 0) {
									System.out.println("button pressed: " + count);
									scenesOn = true;
									count ++;
								}
								else {

									QuestionScreen q = new QuestionScreen();
									//try {window.setActive(false);}
									//catch (ContextActivationException e) {System.out.println(e);}
									if (q.run() == 0) {
										storyModeOn = false;
										mainMenuOn = true;
										//try {window.setActive(true);}
										//catch (ContextActivationException e) {System.out.println(e);}
									}
									else {
										System.out.println("Alert screen closed.");
										
										//try {window.setActive(false);}
										//catch (ContextActivationException e) {System.out.println(e);}	
									}
								}
							}
						}
						else{buttonBox[i].setRectColor(Color.TRANSPARENT, 0);}
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
