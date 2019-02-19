import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.*;
import java.util.*;
import java.lang.Math; 

import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;



class Tower extends ImageActor {
	/*
	place tower
	remove tower
	attack enemies
	manual control
	rank up tower
	damage - what type (fire damage, normal damage)
	cost of the tower
	kill count
	*/

	/* TODO:
	- check valid placement
	- rotate to nearest enemy
	- proximity detection
	- rotate to enemy with lowest health
	- rotate to random enemy
	- rotate to furthest advanced enemy
	- rotate to enemy with most health
	*/


	private String ID;
	private float health;
	private int killCount;
	private int rank;

	private float range;
	private int angleOffset = 90;
	private int angle = 0;
	private NPC nearest;
	private Bullet bullet;
	private Clock fireRate = new Clock();
	private int cooldown = 50; //Millseconds before turret can refire
	private Vector2f bulletOrigin = new Vector2f(x+2f,y-19f);
	private float bulletOriginX; //Where bullet spawns
	private float bulletOriginY; //Where bullet spawns
	private Image iBackground;
	private String towerImage;

	public Tower(int x, int y, int r, String textureFile, int range, Background background) {
		super(x, y, r, textureFile);
		rank = 0;
		killCount = 0;
		health = 100;
		this.towerImage = textureFile;
		this.iBackground = background.getBackground();
		this.range = range;
	}

	// ignoring the sqrt part of the distance calculation as expensive  
	float calcDistance(Actor a) {
		float xDiff = this.x - a.x;
		float yDiff = this.y - a.y;
		return (float) (Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
	}
	
	float calcDistance(Vector2f b) {
		float xDiff = this.x - b.x;
		float yDiff = this.y - b.y;
		return (float) (Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
	}
	
	void setBullet(Bullet bullet)
	{
		this.bullet = bullet;
	}
	
	Bullet getBullet()
	{
		return bullet;
	}
	
	NPC getNearestEnemy()
	{
		return nearest;
	}
	
	String getTowerImage()
	{
		return towerImage;
	}
	
	int getCooldown()
	{
		return cooldown;
	}
	
	void setFireRate()
	{
		fireRate.restart();
	}
	
	long getFireRate()
	{
		return fireRate.getElapsedTime().asMilliseconds();
	}
	
	boolean placementCheck( FloatRect boundaries, ArrayList<Tower> towers) //Need to add more parameters
	{	
		int boundaryLeft = (int) boundaries.left;
		int boundaryWidth = (int) boundaries.width;
		int boundaryTop = (int) boundaries.top;
		int boundaryHeight = (int) boundaries.height;
		//System.out.println("Boundaryx " + boundaryLeft + "BoundaryY" + boundaryTop);
		//System.out.println("BoundaryWidth " + boundaryWidth + "BoundaryYHeight" + boundaryHeight);
		for(int i=boundaryLeft;i<boundaryLeft+boundaryWidth;i++)
		{
			for(int j=boundaryTop;j<boundaryTop+boundaryHeight;j++)
			{
				Color pixel = iBackground.getPixel(i,j);
				//System.out.println("Colour: " + pixel);
				//System.out.println("X: " + i + "Y: " + j);
				Color colourGreen = new Color(75,105,47);
				Color colourGrey = new Color(58,68,78);
				boolean place1 = isSimilar(pixel,colourGreen,40);
				boolean place2 = isSimilar(pixel,colourGrey,40);
				if(!(place1 || place2)) return false;
			}
		}
		
		for(Tower t : towers)
		{
			if(t != this && boundaries.contains(t.x,t.y))
			{
				return false;
			}
		}
		
		return true;
	}
	
	boolean isSimilar(Color colour1, Color colour2, int accuracy)
	{	
		if(colour1.r < (colour2.r-accuracy) || colour1.r > (colour2.r+accuracy)) return false;			
		if(colour1.g < (colour2.g-accuracy) || colour1.g > (colour2.g+accuracy)) return false;			
		if(colour1.b < (colour2.b-accuracy) || colour1.b > (colour2.b+accuracy)) return false;
		return true;
	}

	void calcMove(int minx, int miny, int maxx, int maxy, float time) {
		//NPC nearest = getNearestEnemy();
		
		if (nearest != null) {
			// calculating the angle we need to rotate to so that the tower is faceing the enemy (for shooting)
			angle =  (int) Math.toDegrees(Math.atan2(this.y - nearest.y, this.x - nearest.x)) - angleOffset;
			this.getImg().setRotation(angle);
		}
	
	}
	
	void calcBulletOrigin()
	{
		
		//Translate to origin
		System.out.println("X: "+ x);
		System.out.println("Y: "+ y);

	
		//Assumes rotation of picture returns to face up
		//if(angle <0) angle = Math.abs(angle) + 360;
		float angle2 = (int) Math.toRadians((double)angle);
		/*System.out.println("Angle: "+ angle2);
		System.out.println("Math.sin(angle) "+ Math.sin(angle2));
		System.out.println("Math.cos(angle) "+ Math.cos(angle2));*/
		
		//Couldnt distance be r from polar cooridinates
		float ra = (float) Math.sqrt(calcDistance(bulletOrigin)); //This never changes as radius remains the same
		//System.out.println("ra: "+ ra);
		//First part distance from the origin + origin of turret
	
		bulletOriginX = (float)(ra*Math.sin(-angle2) + x);
		bulletOriginY = (float)(ra*Math.cos(-angle2) + y);
		

		System.out.println("Bulletoriginx: " + bulletOriginX);
		System.out.println("Bulletoriginy: " + bulletOriginY);

	}
	
	float getBulletOriginX()
	{
		return bulletOrigin.x;
	}
	
	float getBulletOriginY()
	{
		return bulletOrigin.y;
	}
	
	
	float getRange()
	{
		return range;
	}
	
	void setNearestEnemy(NPC nearest)
	{
		 this.nearest = nearest;
	}

	public void calcMoveCursor(int newX, int newY) {
		x = newX;
		y = newY;
	}
}