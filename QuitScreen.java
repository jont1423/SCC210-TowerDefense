//import java.io.File;
//mport java.io.IOException;
//import java.nio.file.*;
//import java.util.ArrayList;
//import java.util.function.*;
//import java.io.InputStream;

import org.jsfml.system.*;
import org.jsfml.window.*;
//import org.jsfml.audio.*;
//import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
//import org.jsfml.graphics.Image;

public class QuitScreen{
	private static String ImageFile = "Image/quitScreen.png";
	private static ImageAct quitScreenWindow;
	private static GenButton buttons[], screenCover;
	
	public QuitScreen() {
		quitScreenWindow = new ImageAct(ImageFile);
		screenCover = new GenButton(1024, 768, Color.BLACK, 200);
		buttons = new GenButton[2];
		
		for (int i=0; i<2; i++)
			buttons[i] = new GenButton(125, 70, Color.GREEN, 0);
		
		quitScreenWindow.setLocation((float) 12, (float) 250);
		buttons[0].setLocation((float) 235, (float) 445);
		buttons[1].setLocation((float) 670, (float) 445);
	}
	
	public void display(RenderWindow window, boolean alertScreenOn) {
		if (alertScreenOn) {
			screenCover.draw(window);
			quitScreenWindow.draw(window);
			for (GenButton button: buttons)
				button.draw(window);
		}
	}
	
	public boolean yesEvent(Vector2i mouseLoc) {
		boolean detected = false;
		
		if (buttons[0].detectPos(buttons[0].getRectPosition(), buttons[0].getRectDimensions(), mouseLoc)) {
			detected = true;
			buttons[0].setRectColor(Color.GREEN, 0);
		}
		else buttons[0].setRectColor(Color.TRANSPARENT, 100);

		return detected;
	}
	
	public boolean noEvent(Vector2i mouseLoc) {
		boolean detected = false;
		
		if (buttons[1].detectPos(buttons[1].getRectPosition(), buttons[1].getRectDimensions(), mouseLoc)) {
			detected = true;
			buttons[1].setRectColor(Color.RED, 0);
		}
		else buttons[1].setRectColor(Color.TRANSPARENT, 100);
		
		return detected;
	}
}
