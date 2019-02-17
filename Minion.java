import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.*;
import java.lang.Math;

import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
public class Minion extends NPC
{
	private Clock cooldown = new Clock();
	private Bubble abilityRadius = new Bubble(x,y,15,Color.YELLOW,128);
	SpeedDemon(String ID,float xPixelsPerSecond, float yPixelsPerSecond,int r, String textureFile, Background background)
	{
		super(ID,xPixelsPerSecond,yPixelsPerSecond,r,textureFile,background);
	}
	void performAction()
	{
		//Count nearby enemies
		// 1-3 enemies health *= 1.03
		// 4-7 enemies health *= 1.05
		// 8+ enemies health *= 1.10
		//Can only be incremented
		if(cooldown.getElapsedTime().asSeconds()>5)
		{
			//count enemies in radius
			cooldown.restart();
		}
		
		abilityRadius.setX(x);
		abilityRadius.setY(x);
	}
	Bubble getRadius()
	{
		return abilityRadius;
	}
	//Draw a circle of the radius
}