//
//	Class: MainMenu
//
//	Author: Jordan Young
//

//import java.io.File;
//import java.io.IOException;
//import java.nio.file.*;
//import java.util.ArrayList;
//import java.util.function.*;

import org.jsfml.system.*;
//import org.jsfml.window.*;
//import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

public class MainMenu {
	private static String ImageFile = "Image/Home page.png";
	private static ImageAct wallpaperIMG;
	private static GenButton rects[];
	
	public MainMenu(){
		wallpaperIMG = new ImageAct(ImageFile);							//Wallpaper
		rects = new GenButton[4];										//Buttons detection rectangle
		
		rects[0] = new GenButton(369, 76, Color.MAGENTA, 0);
		rects[1] = new GenButton(377, 73, Color.GREEN, 0);
		rects[2] = new GenButton(381, 68, Color.YELLOW, 0);
		rects[3] = new GenButton(142, 47, Color.YELLOW, 0);
		rects[0].setLocation((float) 615, (float) 455);
		rects[1].setLocation((float) 493, (float) 560);
		rects[2].setLocation((float) 400, (float) 660);
		rects[3].setLocation((float) 19, (float) 19);
	}
	
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
	
	public boolean newGameEvent(Vector2i mouseLoc) {
		boolean detected = false;
		
		if (rects[0].detectPos(rects[0].getRectPosition(), rects[0].getRectDimensions(), mouseLoc)) {
			detected = true;
			rects[0].setRectColor(Color.MAGENTA, 40);
		}
		else rects[0].setRectColor(Color.TRANSPARENT, 0);

		return detected;
	}
	
	public boolean continueEvent(Vector2i mouseLoc) {
		boolean detected = false;
		
		if (rects[1].detectPos(rects[1].getRectPosition(), rects[1].getRectDimensions(), mouseLoc)) {
			detected = true;
			rects[1].setRectColor(Color.WHITE, 40);
		}
		else {rects[1].setRectColor(Color.TRANSPARENT, 0);}
		
		return detected;
	}
	
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
