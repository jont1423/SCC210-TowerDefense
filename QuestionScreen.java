/**
 *
 * Class name: QuestionScreen
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

public class QuestionScreen {
	private static int screenWidth = 512;
	private static int screenHeight = 192;
	
	private static String Title = "Alert!";
	private static GenButton buttonBox[];
	private static Mouse mouseMov;
	private static Word alertMessage[];
	
	public int run () {		
		// Create a window
		RenderWindow window = new RenderWindow();
		window.create(new VideoMode(screenWidth, screenHeight), Title, WindowStyle.CLOSE);

		window.setFramerateLimit(30); 		// Avoid excessive updates
		
		int value = 1;
		alertMessage = new Word[4];
		buttonBox = new GenButton[2];
		for (int i=0; i<4; i++) {
			alertMessage[i] = new Word();
			if (!(i >= 2))
				buttonBox[i] = new GenButton(90, 30, Color.RED, 500);
		}
		
		alertMessage[0].setWord("Yes!", Color.WHITE, 24);
		alertMessage[0].setLocation((float) 157, (float) 140);
		alertMessage[1].setWord("No!", Color.WHITE, 24);
		alertMessage[1].setLocation((float) 313, (float) 140);
		alertMessage[2].setWord("Are you sure you want to quit", Color.BLACK, 20);
		alertMessage[2].setLocation((float) 70, (float) 50);
		alertMessage[3].setWord("without saving your progress?", Color.BLACK, 20);
		alertMessage[3].setLocation((float) 75, (float) 80);
		buttonBox[0].setLocation((float) 140, (float) 140);
		buttonBox[1].setLocation((float) 288, (float) 140);
		
		Vector2i mouseLoc;
		
		// Main loop
		while (window.isOpen()) {
			// Clear the screen
			window.clear(Color.WHITE);
						
			for (int i=0; i<4; i++) {
				if (!(i >= 2))
					buttonBox[i].draw(window);
				alertMessage[i].draw(window);
			}
			
			//check mouse coordinates in each frame
			mouseLoc = mouseMov.getPosition(window);
			
			// Update the display with any changes
			window.display();
		
			// Handle any events
			for (Event event : window.pollEvents()) {
				if (event.type == Event.Type.CLOSED) {
					// the user pressed the close button
					window.close();
				}
				
				if (event.type == Event.Type.LOST_FOCUS) System.out.println("Focus lost **BOOOOOOOOOOOOOOOOOM**");
				
				for (int i=0; i<2; i++) {
					if (buttonBox[i].detectPos(buttonBox[i].getRectPosition(), buttonBox[i].getRectDimensions(), mouseLoc)) {
						buttonBox[i].setRectColor(Color.TRANSPARENT, 0);
						alertMessage[i].setColor(Color.BLACK);
						if (event.type == Event.Type.MOUSE_BUTTON_RELEASED) {
							window.close();
							if (buttonBox[i] == buttonBox[0]) value = 0;
							else value = 1;
						}
					}
					else {
						buttonBox[i].setRectColor(Color.RED, 500);
						alertMessage[i].setColor(Color.WHITE);
					}
				}
			}
		}
		
		return value;
	}
}	
