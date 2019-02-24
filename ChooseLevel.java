//import java.io.File;
import java.io.IOException;
import java.nio.file.*;
//import java.util.ArrayList;
//import java.util.function.*;

import org.jsfml.system.*;
import org.jsfml.window.*;
//import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

public class ChooseLevel {
	private static String ImageFile = "Image/chooseLevel.png";
	private static ImageAct chooseLevelIMG;
	private static GenButton buttons[];
	
	public ChooseLevel() {
		chooseLevelIMG = new ImageAct(ImageFile);							//Wallpaper
		buttons = new GenButton[3];										//Buttons detection rectangle
		
		buttons[0] = new GenButton(369, 76, Color.MAGENTA, 0);
		buttons[1] = new GenButton(377, 73, Color.GREEN, 0);
		buttons[2] = new GenButton(381, 68, Color.YELLOW, 0);
		buttons[0].setLocation((float) 615, (float) 455);
		buttons[1].setLocation((float) 493, (float) 560);
		buttons[2].setLocation((float) 400, (float) 660);
	}
	
	public void display() {
		if (mainMenuOn) {
			wallpaperIMG.draw(window);
			for (GenButton button: buttons)
				button.draw(window);
			countNumber = 0;
		}
	}
	
	public boolean easyEvent(Vector2i mouseLoc) {
		boolean detected = false;
		
		if (buttons[0].detectPos(buttons[0].getRectPosition(), buttons[0].getRectDimensions(), mouseLoc)) {
			detected = true;
			buttons[0].setRectColor(Color.MAGENTA, 40);
		}
		else rects[0].setRectColor(Color.TRANSPARENT, 0);

		return detected;
	}
	
	public boolean mediumEvent() {
		boolean detected = false;
		
		if (buttons[0].detectPos(buttons[0].getRectPosition(), buttons[0].getRectDimensions(), mouseLoc)) {
			detected = true;
			buttons[0].setRectColor(Color.MAGENTA, 40);
		}
		else rects[0].setRectColor(Color.TRANSPARENT, 0);

		return detected;
	}
	
	public boolean HardEvent() {
		boolean detected = false;
		
		if (buttons[0].detectPos(buttons[0].getRectPosition(), buttons[0].getRectDimensions(), mouseLoc)) {
			detected = true;
			buttons[0].setRectColor(Color.MAGENTA, 40);
		}
		else rects[0].setRectColor(Color.TRANSPARENT, 0);

		return detected;
	}
}