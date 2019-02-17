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
public class SpeedDemon extends NPC
{
	private Clock cooldownTimer = new Clock();
	private Bubble abilityRadius = new Bubble(x,y,45,Color.YELLOW,128);
	
	SpeedDemon(String ID,int health,int armour,float cooldown,float xPixelsPerSecond, float yPixelsPerSecond,int r, String textureFile, Background background)
	{
		super(ID,health,armour,cooldown,xPixelsPerSecond,yPixelsPerSecond,r,textureFile,background);
	}
	void performAction() //Maybe pass actors as parameters
	{
		//All nearby NPC'S movement speed is increased by 15% (doesnt stack)
		if(cooldownTimer.getElapsedTime().asSeconds()>cooldown)
		{
			//do move
			// x *= 1.15;
			// y *= 1.15; 
			cooldownTimer.restart();
		}
		
		abilityRadius.move(x,y);
	}
	Bubble getRadius()
	{
		return abilityRadius;
	}
	
	
	//Draw a circle of the radius
}

//Other abilites
//Another radius - share resitances with nearby enemies
//Invisible for a few seconds
//Levitate - avoid traps
//...