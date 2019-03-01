//import java.io.File;
import java.io.IOException;
import java.nio.file.*;
//import java.util.ArrayList;
//import java.util.function.*;

import org.jsfml.system.*;
import org.jsfml.window.*;
//import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

public class ChooseLevDif {
	private static String ImageFile[] = {"Image/chooseLevel.png", "Image/chooseDif.png", "IngameButtons/back_button.png"};
	private static ImageAct chooseLevelIMG, chooseDifficultiesIMG, backButtonIMG;
	private static GenButton mapButtons[], difButtons[], backButton;
	
	private final float buttonLocX = 0, buttonLocY = 0, adjustX = 0, adjustY = 0;
	
	public ChooseLevDif(String type) {
		backButtonIMG = new ImageAct(ImageFile[2]);
		backButtonIMG.setLocation((float) 10, (float) 10);
		backButton = new GenButton(40, 40, Color.BLUE, 0);
		backButton.setLocation((float) 6, (float) 6);
		if (type == "Level") {
			chooseLevelIMG = new ImageAct(ImageFile[0]);					//Background Image
			mapButtons = new GenButton[5];										//Buttons detection rectangle
		
			//for (int i=0; i<5; i++) {
				mapButtons[0] = new GenButton(169, 150, Color.RED, 0);
				mapButtons[1] = new GenButton(169, 150, Color.RED, 0);
				mapButtons[2] = new GenButton(169, 150, Color.RED, 0);
				mapButtons[3] = new GenButton(169, 150, Color.RED, 0);
				mapButtons[4] = new GenButton(169, 150, Color.RED, 0);
				mapButtons[0].setLocation((float) 87, (float) 150);
				mapButtons[1].setLocation((float) 327, (float) 150);
				mapButtons[2].setLocation((float) 87, (float) 370);
				mapButtons[3].setLocation((float) 327, (float) 370);
				mapButtons[4].setLocation((float) 87, (float) 596);
			//}
		}
	
		if (type == "Difficulties") {
			chooseDifficultiesIMG = new ImageAct(ImageFile[1]);
			difButtons = new GenButton[3];										//Buttons detection rectangle

			difButtons[0] = new GenButton(362, 107, Color.YELLOW, 0);
			difButtons[1] = new GenButton(473, 113, Color.YELLOW, 0);
			difButtons[2] = new GenButton(405, 104, Color.YELLOW, 0);
			difButtons[0].setLocation((float) 321, (float) 174);
			difButtons[1].setLocation((float) 267, (float) 337);
			difButtons[2].setLocation((float) 307, (float) 505);
		}
	}
	
	public void display(RenderWindow window, boolean onOff, String type) {
		if (onOff) {
			if (type == "level") {
				chooseLevelIMG.draw(window);
				backButtonIMG.draw(window);
				for (GenButton mapButton: mapButtons)
					mapButton.draw(window);
			}
			if (type == "difficulties") {
				chooseDifficultiesIMG.draw(window);
				backButtonIMG.draw(window);
				for (GenButton difButton: difButtons)
					difButton.draw(window);
			}
		}
	}
	
	public boolean difficultiesEvent(Vector2i mouseLoc, int i) {
		boolean detected = false;
		
		if (difButtons[i].detectPos(difButtons[i].getRectPosition(), difButtons[i].getRectDimensions(), mouseLoc)) {
			detected = true;
			difButtons[i].setRectColor(Color.YELLOW, 40);

		}
		else difButtons[i].setRectColor(Color.TRANSPARENT, i);

		return detected;
	}
	
	public boolean mapEvent(Vector2i mouseLoc, int i) {
		boolean detected = false;
		
			if (mapButtons[i].detectPos(mapButtons[i].getRectPosition(), mapButtons[i].getRectDimensions(), mouseLoc)) {
				detected = true;
				mapButtons[i].setRectColor(Color.RED, 40);
			}
			else mapButtons[i].setRectColor(Color.TRANSPARENT, 0);

		return detected;
	}
	
	public boolean backButtonEvent(Vector2i mouseLoc) {
		boolean detected = false;
		
		if (backButton.detectPos(backButton.getRectPosition(), backButton.getRectDimensions(), mouseLoc)) {
			detected = true;
			backButton.setRectColor(Color.BLUE, 40);
		}
		else backButton.setRectColor(Color.TRANSPARENT, 0);
		
		return detected;
	}
}











