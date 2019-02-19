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
	private static String ImageFile = "Wallpaper.png";
	private static String SoundFile = "BGM.wav";
	private static String ButtonFile[] = {"Buttons/playButton.png", "Buttons/levelButton.png", "Buttons/exitButton.png"};
	private static ImageAct wallpaperIMG, ButtonsIMG[];
	private static GenSound BGM, sound1;
	private static GenButton rect[];
	private static Mouse mouseMov;
	private static Vector2i mouseLoc;
	
	private float buttonX = 50, buttonY = 0, adjustX, adjustY;
	private boolean mainMenuOn = false;
	
	public void run () {
		System.out.println("ButtonFile array length: " + ButtonFile.length);
		
		// Create a window
		RenderWindow window = new RenderWindow();
		window.create(new VideoMode(screenWidth, screenHeight), Title, WindowStyle.CLOSE);

		window.setFramerateLimit(30); // Avoid excessive updates

		// Create some actors
		BGM = new GenSound(SoundFile);
		wallpaperIMG = new ImageAct(ImageFile);
		ButtonsIMG = new ImageAct[ButtonFile.length];
		rect = new GenButton[ButtonFile.length];
		for (int i=0; i<ButtonFile.length; i++)
			ButtonsIMG[i] = new ImageAct(ButtonFile[i]);
		
		ButtonsIMG[0].setLocation(buttonX, buttonY);
		adjustX = 20; adjustY = -80;
		ButtonsIMG[1].setLocation(buttonX - adjustX, buttonY + adjustY);
		adjustX = 0; adjustY = 100;
		ButtonsIMG[2].setLocation(buttonX - adjustX, buttonY + adjustY);
		
		rect[0] = new GenButton(199, 80, Color.MAGENTA, 0);
		rect[1] = new GenButton(215, 63, Color.GREEN, 0);
		rect[2] = new GenButton(140, 50, Color.YELLOW, 0);
		rect[0].setLocation((float) 445, (float) 432);
		rect[1].setLocation((float) 440, (float) 540);
		rect[2].setLocation((float) 475, (float) 632);
		
		BGM.loop(true);
		BGM.playSound();

		// Main loop
		while (window.isOpen()) {
			// Clear the screen
			window.clear(Color.WHITE);
			
			//Space for sound/mute button
			
			wallpaperIMG.draw(window);
			for (ImageAct buttonsIMG: ButtonsIMG)
				buttonsIMG.draw(window);
			for (GenButton rects: rect)
				rects.draw(window);
			
			// Update the display with any changes
			window.display();
		
			mouseLoc = mouseMov.getPosition(window);

			//System.out.println(mouseMov.getPosition(window));
			//System.out.println(rect0.getRectDimensions().x);
			//System.out.println(rect0.getRectPosition().x);

			// Handle any events
			for (Event event : window.pollEvents()) {
				
				if (event.type == Event.Type.CLOSED) {
					// the user pressed the close button
					window.close();
				}
				
				if (rect[0].detectPos(rect[0].getRectPosition(), rect[0].getRectDimensions(), mouseLoc)) {
					rect[0].setRectColor(Color.MAGENTA, 40);
					if (event.type == Event.Type.MOUSE_BUTTON_RELEASED) {
						ImageAct wallpaperIMG1 = new ImageAct("Wallpaper.jpg");
						wallpaperIMG1.draw(window);
						//Need to save file to understand what needs setting
						//Pathing p = new Pathing(window,Which map,Which wave,Base health);
						
						//Creating a new save instance
						Save s = new Save();
						
						//Creating new game, use the .contGame() method to continue instead
						s.newGame("easy");
						
						//Creating the instance of the level
						Pathing p = new Pathing(window);
						p.run();
						
						//Method to save the game (level, round, difficulty)
						s.saveFile(1,1,"medium");
						
						
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
		}
	}

	public static void main (String args[]) {
		GameWindow g = new GameWindow();
		g.run();
	}
}	
