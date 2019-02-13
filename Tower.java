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
	private Vector2f bulletOrigin = new Vector2f(x+2f,y-19f);
	private float bulletOriginX; //Where bullet spawns
	private float bulletOriginY; //Where bullet spawns

	public Tower(int x, int y, int r, String textureFile) {
		super(x, y, r, textureFile);
		rank = 0;
		killCount = 0;
		health = 100;
		range = 100;
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

	NPC getNearestEnemy(ArrayList<Actor> actors) {
		NPC nearestEnemy = null;
		float proximity = (float) Math.pow(range, 2);
		float shortestDistance = Float.MAX_VALUE;

		for (Actor actor : actors) {
			// check if actor is an instance of NPC (enemy class)
			if (actor != this && actor instanceof NPC)
			{
				float currentDistance = calcDistance(actor);

				if (currentDistance <= proximity && currentDistance < shortestDistance)
					nearestEnemy = (NPC) actor;
			}
		}

		return nearestEnemy;
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
		float angle2 = (int) Math.toRadians((double)angle-180);
		System.out.println("Angle: "+ angle2);
		System.out.println("Math.sin(angle) "+ Math.sin(angle2));
		System.out.println("Math.cos(angle) "+ Math.cos(angle2));
		
		//Couldnt distance be r from polar cooridinates
		float ra = (float) Math.sqrt(calcDistance(bulletOrigin)); //This never changes as radius remains the same
		System.out.println("ra: "+ ra);
		//First part distance from the origin + origin of turret
		bulletOriginX = (float)(ra*Math.sin(-angle2) + x);
		bulletOriginY = (float)(ra*Math.cos(-angle2) + y);
		System.out.println("Bulletoriginx: " + bulletOriginX);
		System.out.println("Bulletoriginy: " + bulletOriginY);

	}
	
	float getBulletOriginX()
	{
		return bulletOriginX;
	}
	
	float getBulletOriginY()
	{
		return bulletOriginY;
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