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

public class AlertScreen{
private static String ImageFile[] = {"Image/quitScreen.png", "Image/pauseScreen.png"};
	private static ImageAct quitScreenWindow, pauseScreenWindow;
	private static GenButton yesnobuttons[], pauseButtons[], screenCover;
	
	public AlertScreen(String type) {
		screenCover = new GenButton(1024, 768, Color.BLACK, 200);
		if (type == "quitScreen") {
			quitScreenWindow = new ImageAct(ImageFile[0]);
			yesnobuttons = new GenButton[2];
			
			for (int i=0; i<2; i++)
				yesnobuttons[i] = new GenButton(125, 70, Color.GREEN, 0);
			
			quitScreenWindow.setLocation((float) 12, (float) 250);
			yesnobuttons[0].setLocation((float) 235, (float) 445);
			yesnobuttons[1].setLocation((float) 670, (float) 445);
		}
		
		if (type == "pause") {
			pauseScreenWindow = new ImageAct(ImageFile[1]);
			pauseButtons = new GenButton[3];
			
			for (int i=0; i<3; i++)
				pauseButtons[i] = new GenButton(125, 70, Color.TRANSPARENT, 0);
			
			pauseScreenWindow.setLocation((float) 256, (float) 192);
			yesnobuttons[0].setLocation((float) 235, (float) 445);
			yesnobuttons[1].setLocation((float) 670, (float) 445);
			yesnobuttons[2].setLocation((float) 670, (float) 445);
		}
	}
	
	public void display(RenderWindow window, boolean onOff, String type) {
		if (type == "quitScreen") {
			if (onOff) {
				screenCover.draw(window);
				quitScreenWindow.draw(window);
				for (GenButton yesnobutton: yesnobuttons)
					yesnobutton.draw(window);
			}
		}
		
		if (type == "pause") {
			if (onOff) {
				screenCover.draw(window);
				pauseScreenWindow.draw(window);
				for (GenButton pausebutton: pauseButtons)
					pausebutton.draw(window);
			}
		}
	}
	
	public boolean yesnoEvent(Vector2i mouseLoc, int i) {
		boolean detected = false;
		
		if (yesnobuttons[i].detectPos(yesnobuttons[i].getRectPosition(), yesnobuttons[i].getRectDimensions(), mouseLoc)) {
			detected = true;
			if (i == 0)
				yesnobuttons[i].setRectColor(Color.GREEN, 0);
			else yesnobuttons[i].setRectColor(Color.RED, 0);
		}
		else yesnobuttons[i].setRectColor(Color.TRANSPARENT, 100);
		
		return detected;
	}
	
	public boolean pauseEvent(Vector2i mouseLoc, int i) {
		boolean detected = false;
		
		if (pauseButtons[i].detectPos(pauseButtons[i].getRectPosition(), pauseButtons[i].getRectDimensions(), mouseLoc)) {
			detected = true;
			if (i == 0)
				pauseButtons[i].setRectColor(Color.BLUE, 0);
			else if (i == 1) pauseButtons[i].setRectColor(Color.YELLOW, 0);
			else pauseButtons[i].setRectColor(Color.RED, 0);
		}
		else pauseButtons[i].setRectColor(Color.TRANSPARENT, 100);
		
		return detected;
	}
}
