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
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

public class StoryMode {

	private static ImageAct storyModeIMG[], inGameButtonsIMG[];
	private static GenButton buttonBox[];
	private final String inGameButtonFile[] = {"IngameButtons/playButton.png", "IngameButtons/back_button.png"};
	private final String ImageFile[] = {"StoryMode/Intro_screen.png", "StoryMode/level1.png", "StoryMode/level2_WithoutKidd.png",
										"StoryMode/level2_WithKidd.png", "StoryMode/level3_WithoutKidd.png",
										"StoryMode/level3_WithKidd.png", "StoryMode/preLevel4.png",
										"StoryMode/level4.png", "StoryMode/level5.png", "StoryMode/blackbeardsTreasure.png"};
	private static Word storyModeSubScene1[], storyModeSubScene2[], storyModeSubScene3[],
						storyModeSubScene4[], storyModeSubScene5[], storyModeSubScene6[], 
						storyModeSubScene7[], storyModeSubScene8[], storyModeSubScene9[];
	
	private final float subLocX = 67, subLocY = 605;
	private final float lineGap = 30;
	
	public StoryMode() {
		storyModeIMG = new ImageAct[ImageFile.length];
		for (int i=0; i<ImageFile.length; i++)
			storyModeIMG[i] = new ImageAct(ImageFile[i]);
		
		int nextLine[] = {0,0,0,0,0,0,0,0,0}, sizeAdjust = 0;
		storyModeSubScene1 = new Word[24];
		storyModeSubScene2 = new Word[14];
		storyModeSubScene3 = new Word[5];
		storyModeSubScene4 = new Word[13];
		storyModeSubScene5 = new Word[7];
		storyModeSubScene6 = new Word[19];
		storyModeSubScene7 = new Word[12];
		storyModeSubScene8 = new Word[10];
		storyModeSubScene9 = new Word[10];
		for (int i=0; i<storyModeSubScene1.length; i++) {
			storyModeSubScene1[i] = new Word();
			if (i < 14) storyModeSubScene2[i] = new Word();
			if (i < 5) storyModeSubScene3[i] = new Word();
			if (i < 13) storyModeSubScene4[i] = new Word();
			if (i < 7) storyModeSubScene5[i] = new Word();
			if (i < 19) storyModeSubScene6[i] = new Word();
			if (i < 12) storyModeSubScene7[i] = new Word();
			if (i < 10) {
				storyModeSubScene8[i] = new Word();
				storyModeSubScene9[i] = new Word();
			}
		}
					
		for (int i=0; i<storyModeSubScene1.length; i++) {
			if (!((i > 16 && i < 19) || (i > 20 && i < 23)))
				storyModeSubScene1[i].setWord(storyModeSubScene1[i].scene1(i), Color.BLACK, 24);
			else storyModeSubScene1[i].setWord(storyModeSubScene1[i].scene1(i), Color.RED, 24);
			
			if ((!(i > 9 && i < 13)) && i < 14)
				storyModeSubScene2[i].setWord(storyModeSubScene2[i].scene2(i), Color.BLACK, 24);
			else if (i < 14)
				storyModeSubScene2[i].setWord(storyModeSubScene2[i].scene2(i), Color.RED, 24);

			if (i < 5)
				storyModeSubScene3[i].setWord(storyModeSubScene3[i].scene3(i), Color.BLACK, 24);

			if (i < 13)
				storyModeSubScene4[i].setWord(storyModeSubScene4[i].scene4(i), Color.BLACK, 24);

			if ((!((i > 2 && i < 6) || i == 6)) && i < 7)
				storyModeSubScene5[i].setWord(storyModeSubScene5[i].scene5(i), Color.BLACK, 24);
			else if (i < 7)
				storyModeSubScene5[i].setWord(storyModeSubScene5[i].scene5(i), Color.RED, 24);
			
			if (i < 19)
				storyModeSubScene6[i].setWord(storyModeSubScene6[i].scene6(i), Color.BLACK, 24);
				
			if ((!(i > 8 && i < 12)) && i < 12)
				storyModeSubScene7[i].setWord(storyModeSubScene7[i].scene7(i), Color.BLACK, 24);
			else if (i < 12)
				storyModeSubScene7[i].setWord(storyModeSubScene7[i].scene7(i), Color.RED, 24);
				
			if (i < 10)
				storyModeSubScene8[i].setWord(storyModeSubScene8[i].scene8(i), Color.BLACK, 24);
			
			if ((!(i > 6 && i < 10)) && i < 10)
				storyModeSubScene9[i].setWord(storyModeSubScene9[i].scene9(i), Color.BLACK, 24);
			else if (i < 10)
				storyModeSubScene9[i].setWord(storyModeSubScene9[i].scene9(i), Color.RED, 24);
				
			storyModeSubScene1[i].setLocation(subLocX, subLocY + nextLine[0]);
			nextLine[0] += lineGap;																	//Bring subtitle to the next line
			if (i == 1 || i == 4 || i == 8 || i == 10 | i == 11 || i == 12 || i == 14 ||
				i == 16 || i == 18 || i == 19 || i == 20 || i == 22)					//Return subtitles to the first line
				nextLine[0] = 0;
				
			if (i < 14) {
				storyModeSubScene2[i].setLocation(subLocX, subLocY + nextLine[1]);
				nextLine[1] += lineGap;
				if (i == 1 || i == 3 || i == 5 || i == 6 | i == 9 || i == 12)
					nextLine[1] = 0;
			}
			
			if (i < 5) {
				storyModeSubScene3[i].setLocation(subLocX, subLocY + nextLine[2]);
				nextLine[2] += lineGap;
				if (i == 0 || i == 3)
					nextLine[2] = 0;
			}
			
			if (i < 13) {
				storyModeSubScene4[i].setLocation(subLocX, subLocY + nextLine[3]);
				nextLine[3] += lineGap;
				if (i == 1 || i == 4 || i == 5 || i == 8 | i == 10 || i == 11)
					nextLine[3] = 0;
			}
			
			if (i < 7) {
				storyModeSubScene5[i].setLocation(subLocX, subLocY + nextLine[4]);
				nextLine[4] += lineGap;
				if (i == 1 || i == 2 || i == 5)
					nextLine[4] = 0;
			}
			
			if (i < 19) {
				storyModeSubScene6[i].setLocation(subLocX, subLocY + nextLine[5]);
				nextLine[5] += lineGap;
				if (i == 0 || i == 2 || i == 3 || i == 5 | i == 6 || i == 7 || i == 8 ||
					i == 9 || i == 10 || i == 12 || i == 13 || i == 14 || i == 16)
					nextLine[5] = 0;
			}	
				
			if (i < 12) {
				storyModeSubScene7[i].setLocation(subLocX, subLocY + nextLine[6]);
				nextLine[6] += lineGap;
				if (i == 0 || i == 2 || i == 3 || i == 4 | i == 7 || i == 8)
					nextLine[6] = 0;
			}
			
			if (i < 10) {
				storyModeSubScene8[i].setLocation(subLocX, subLocY + nextLine[7]);
				nextLine[7] += lineGap;
				if (i == 2 || i == 4 || i == 6 || i == 8)
					nextLine[7] = 0;
			
				storyModeSubScene9[i].setLocation(subLocX, subLocY + nextLine[8]);
				nextLine[8] += lineGap;
				if (i == 0 || i == 2 || i == 4 || i == 6)
					nextLine[8] = 0;
			}
		}
		
		inGameButtonsIMG = new ImageAct[inGameButtonFile.length];
		buttonBox = new GenButton[inGameButtonFile.length];
		
		for (int i=0; i<inGameButtonFile.length; i++) {
			inGameButtonsIMG[i] = new ImageAct(inGameButtonFile[i]);
			buttonBox[i] = new GenButton(35 + sizeAdjust, 35 + sizeAdjust, Color.BLUE, 0);
			sizeAdjust += 5;
		}

		inGameButtonsIMG[0].setLocation((float) 980, (float) 10);
		inGameButtonsIMG[1].setLocation((float) 10, (float) 10);
		buttonBox[0].setLocation((float) 978, (float) 8);
		buttonBox[1].setLocation((float) 6, (float) 6);
	}
	
	public void display(RenderWindow window, int count, boolean storyModeOn, boolean scenesOn) {
		if (storyModeOn) {
			storyModeIMG[0].draw(window);
			if (scenesOn) {
				for (int i=0; i<storyModeSubScene1.length; i++) {
					if (count >= 1 && count <= 14 && i == 1)
						storyModeIMG[i].draw(window);
					if (count >= 15 && count <= 22 && i == 2)																	//Game on
						storyModeIMG[i].draw(window);
					if (count >= 23 && count <= 26 && i == 3)																	//Transition to screen
						storyModeIMG[i].draw(window);
					if (count >= 27 && count <= 34 && i == 4)																	//Game on
						storyModeIMG[i].draw(window);
					if (count >= 35 && count <= 39 && i == 5)																		//Transition to screen
						storyModeIMG[i].draw(window);
					if (count >= 40 && i <= 54 && i == 6)																		//Game on
						storyModeIMG[i].draw(window);
					if (count >= 55 && i <= 62 && i == 7)																		//Transition to screen
						storyModeIMG[i].draw(window);
					if (count >= 63 && i <= 8 && i == 8)																		//Game on
						storyModeIMG[i].draw(window);
					if (count >= 69 && i <= 74 && i == 9)																		//Transition to screen
						storyModeIMG[i].draw(window);
				}
				for (int i=0; i<storyModeSubScene1.length; i++) {
					
					//Level 1 (Scene 1)
					if ((count == 2 && i < 2) || (count == 3 && i > 1 && i < 5) || 
						(count == 4 && i > 4 && i < 9) || (count == 5 && i > 8 && i < 11) ||
						(count == 6 && i == 11) || (count == 7 && i == 12) ||
						(count == 8 && i > 12 && i < 15) || (count == 9 && i > 14 && i < 17) || 
						(count == 10 && i > 16 && i < 19) || (count == 11 && i == 19) ||
						(count == 12 && i == 20) || (count == 13 && i > 20 && i < 23) ||
						(count == 14 && i == 23))
						storyModeSubScene1[i].draw(window);
					
					//Level 2 without kidd (Scene 2)
					if ((count == 16 && i < 2) || (count == 17 && i > 1 && i < 4) ||
						(count == 18 && i > 3 && i < 6) || (count == 19 && i == 6) ||
						(count == 20 && i > 6 && i < 10) || (count == 21 && i > 9 && i < 13) ||
						(count == 22 && i == 13))
						storyModeSubScene2[i].draw(window);
					
					//Level 2 with kidd (Scene 3)
					if ((count == 24 && i == 0) || (count == 25 && i > 0 && i < 4) ||
						(count == 26 && i == 4))
						storyModeSubScene3[i].draw(window);
					
					//Level 3 without kidd (Scene 4)
					if ((count == 28 && i < 2) || (count == 29 && i > 1 && i < 5) ||
						(count == 30 && i == 5) || (count == 31 && i > 5 && i < 9) ||
						(count == 32 && i > 8 && i < 11) || (count == 33 && i == 11) ||
						(count == 34 && i == 12))
						storyModeSubScene4[i].draw(window);
					
					//level 3 with kidd (Scene 5)
					if ((count == 36 && i < 2) || (count == 37 && i == 2) ||
						(count == 38 && i > 2 && i < 6) || (count == 39 && i == 6))
						storyModeSubScene5[i].draw(window);
					
					//Pre level 4 (Scene 6)
					if ((count == 41 && i == 0) || (count == 42 && i > 0 && i < 3) ||
						(count == 43 && i == 3) || (count == 44 && i > 3 && i < 6) ||
						(count == 45 && i == 6) || (count == 46 && i == 7) ||
						(count == 47 && i == 8) || (count == 48 && i == 9) || 
						(count == 49 && i == 10) || (count == 50 && i > 10 && i < 13) ||
						(count == 51 && i == 13) || (count == 52 && i == 14) ||
						(count == 53 && i > 14 && i < 17) || (count == 54 && i > 16 && i < 19))
						storyModeSubScene6[i].draw(window);
					
					//level 4 aliens (Scene 7)
					if ((count == 56 && i == 0) || (count == 57 && i > 0 && i < 3) ||
						(count == 58 && i == 3) || (count == 59 && i == 4) ||
						(count == 60 && i > 4 && i < 8) || (count == 61 && i == 8) ||
						(count == 62 && i > 8 && i < 12))
						storyModeSubScene7[i].draw(window);
					
					//level 5 (Scene 8)
					if ((count == 64 && i < 2) || (count == 65 && i > 2 && i < 5) ||
						(count == 66 && i > 4 && i < 7) || (count == 67 && i > 6 && i < 9) ||
						(count == 68 && i == 9))
						storyModeSubScene8[i].draw(window);
					
					//level 5 blackbeard treasures (Scene 9)
					if ((count == 70 && i == 1) || (count == 71 && i > 0 && i < 3) ||
						(count == 72 && i > 2 && i < 5) || (count == 73 && i > 4 && i < 7) ||
						(count == 74 && i > 6 && i < 10))
						storyModeSubScene9[i].draw(window);
																													//Game on
				}
			}
			
			for (int i=0; i<inGameButtonFile.length; i++) {
				inGameButtonsIMG[i].draw(window);
				buttonBox[i].draw(window);
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
