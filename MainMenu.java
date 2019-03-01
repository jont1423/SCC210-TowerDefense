//
//	Class: MainMenu
//
//	Author: Jordan Young
//

import org.jsfml.system.*;
import org.jsfml.graphics.*;

public class MainMenu {
	private static String ImageFile = "Image/Home page.png";
	private static ImageAct wallpaperIMG;
	private static GenButton rects[];
	
	/**
	 * Constructor of the Main menu that 
	 * initiate all needed object instances
	**/
	public MainMenu(){
		wallpaperIMG = new ImageAct(ImageFile);	
		rects = new GenButton[4];
		
		rects[0] = new GenButton(369, 76, Color.MAGENTA, 0);
		rects[1] = new GenButton(377, 73, Color.GREEN, 0);
		rects[2] = new GenButton(381, 68, Color.YELLOW, 0);
		rects[3] = new GenButton(142, 47, Color.YELLOW, 0);
		rects[0].setLocation((float) 615, (float) 455);
		rects[1].setLocation((float) 493, (float) 560);
		rects[2].setLocation((float) 400, (float) 660);
		rects[3].setLocation((float) 19, (float) 19);
	}
	
	/**
	 * Update changes to the main menu screen when the boolean variable mainMenuOn is true and on the other hand return an
	 * integer value of 0
	 * 
	 * @ param 		window the instance of RenderWindow class from jsfml that control all visualisation of the game window
	 * @ param 		mainMenuOn the boolean variable that enable/ disable the update of the main menu visualisation
	 * @ param 		count the integer variable that allow this method to return value to the same passed variable
	 * @ return		Return an integer of value 0 to the count variable
	 * 	 
	**/
	public int display(RenderWindow window, boolean mainMenuOn, int count) {
		int countNumber = count;
		
		if (mainMenuOn) {
			wallpaperIMG.draw(window);
			for (GenButton rect: rects)
				rect.draw(window);
			countNumber = 0;
		}
		
		return countNumber;
	}
	
	/**
	 * Detect whether the cursor position is on top of the object or no. If yes, then return true along with changing
	 * object color. If no, then return false along with updating object color as transparent.
	 * 
	 * @ param 		mouseLoc the Vector2i variable from jsfml library that give real time cursor position update
	 * @ return		Return a boolean variable to indicate detection of cursor on top of the object
	 * 	 
	**/
	public boolean newGameEvent(Vector2i mouseLoc) {
		boolean detected = false;
		
		if (rects[0].detectPos(rects[0].getRectPosition(), rects[0].getRectDimensions(), mouseLoc)) {
			detected = true;
			rects[0].setRectColor(Color.MAGENTA, 40);
		}
		else rects[0].setRectColor(Color.TRANSPARENT, 0);

		return detected;
	}

	/**
	 * Detect whether the cursor position is on top of the object or no. If yes, then return true along with changing
	 * object color. If no, then return false along with updating object color as transparent.
	 * 
	 * @ param 		mouseLoc the Vector2i variable from jsfml library that give real time cursor position update
	 * @ return		Return a boolean variable to indicate detection of cursor on top of the object
	 * 	 
	**/
	public boolean continueEvent(Vector2i mouseLoc) {
		boolean detected = false;
		
		if (rects[1].detectPos(rects[1].getRectPosition(), rects[1].getRectDimensions(), mouseLoc)) {
			detected = true;
			rects[1].setRectColor(Color.WHITE, 40);
		}
		else {rects[1].setRectColor(Color.TRANSPARENT, 0);}
		
		return detected;
	}
	
	/**
	 * Detect whether the cursor position is on top of the object or no. If yes, then return true along with changing
	 * object color. If no, then return false along with updating object color as transparent.
	 * 
	 * @ param 		mouseLoc the Vector2i variable from jsfml library that give real time cursor position update
	 * @ return		Return a boolean variable to indicate detection of cursor on top of the object
	 * 	 
	**/
	public boolean quickPlayEvent(Vector2i mouseLoc) {
		boolean detected = false;
		
		if (rects[2].detectPos(rects[2].getRectPosition(), rects[2].getRectDimensions(), mouseLoc)) {
			detected = true;
			rects[2].setRectColor(Color.YELLOW, 40);
			//if (mouseMov.isButtonPressed(Mouse.Button.LEFT))
		}
		else {rects[2].setRectColor(Color.TRANSPARENT, 0);}
		
		return detected;
	}
	
	/**
	 * Detect whether the cursor position is on top of the object or no. If yes, then return true along with changing
	 * object color. If no, then return false along with updating object color as transparent.
	 * 
	 * @ param 		mouseLoc the Vector2i variable from jsfml library that give real time cursor position update
	 * @ return		Return a boolean variable to indicate detection of cursor on top of the object
	 * 	 
	**/
	public boolean exitEvent(Vector2i mouseLoc) {
		boolean detected = false;
		if (rects[3].detectPos(rects[3].getRectPosition(), rects[3].getRectDimensions(), mouseLoc)) {
			detected = true;
			rects[3].setRectColor(Color.RED, 40);
		}
		else rects[3].setRectColor(Color.TRANSPARENT, 0);
		
		return detected;
	}
}
