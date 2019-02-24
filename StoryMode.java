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

public class StoryMode {
	private static String inGameButtonFile[] = {"IngameButtons/playButton.png", "IngameButtons/back_button.png", "IngameButtons/pause.png", "IngameButtons/mute_button.png", "IngameButtons/unmute_button.png", "IngameButtons/ff_button.png"};
	private static ImageAct storyModeIMG, inGameButtonsIMG[];
	private static GenButton buttonBox[];
	private static Word storyModeSubScene1[], storyModeSubScene2[], storyModeSubScene3[], storyModeSubScene4[], storyModeSubScene5[], storyModeSubScene6[], storyModeSubScene7[], storyModeSubScene8[], storyModeSubScene9[];
	
	public StoryMode() {
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
		for (int i=0; i<24; i++) {
			storyModeSubScene1[i] = new Word();
			if (i < 13) storyModeSubScene2[i] = new Word();
			if (i < 4) storyModeSubScene3[i] = new Word();
			if (i < 12) storyModeSubScene4[i] = new Word();
			if (i < 6) storyModeSubScene5[i] = new Word();
			if (i < 18) storyModeSubScene6[i] = new Word();
			if (i < 9) {
				storyModeSubScene7[i] = new Word();
				storyModeSubScene8[i] = new Word();
			}
		}
		//for (Word s: storyModeSubScene1) s = new Word();
		storyModeIMG = new ImageAct("StoryModeNew/Intro_screen.png");
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
			if (i == 1 || i == 4 || i == 8 || i == 10 | i == 11 || i == 12 || i == 14 ||
			i == 16 || i == 18 || i == 19 || i == 20 || i == 22 || i == 23)				//Return subtitles to the first line
				nextLine = 0;
		}
	}
	
	public void display(RenderWindow window, int count, boolean storyModeOn, boolean scenesOn) {
		if (storyModeOn) {					//DO SUB VALIDATION HERE *************
			storyModeIMG.draw(window);
			for (int i=0; i<2; i++) {
				inGameButtonsIMG[i].draw(window);
				buttonBox[i].draw(window);
			}
			if (scenesOn) {
				for (int i=0; i<24; i++) {
					if ((count == 2  && i < 2) || (count == 3 && i > 1 && i < 5) || 
						(count == 4 && i > 4 && i < 9) || (count == 5 && i > 8 && i < 11) ||
						(count == 6 && i == 11) || (count == 7 && i == 12) ||
						(count == 8 && i > 12 && i < 15) || (count == 9 && i > 14 && i < 17) || 
						(count == 10 && i > 16 && i < 19) || (count == 11 && i == 19) ||
						(count == 12 && i == 20) || (count == 13 && i > 20 && i < 23) ||
						(count == 14 && i == 23)) {
						storyModeSubScene1[i].draw(window);
					}
				}
			}
		}
	}
	
	public boolean buttonsEvent(Vector2i mouseLoc, int i) {
		boolean detected = false;
		
		if (buttonBox[i].detectPos(buttonBox[i].getRectPosition(), buttonBox[i].getRectDimensions(), mouseLoc)) {
			detected = true;
			buttonBox[i].setRectColor(Color.BLUE, 40);
		}
		else{buttonBox[i].setRectColor(Color.TRANSPARENT, 0);}

		return detected;
	}
}
