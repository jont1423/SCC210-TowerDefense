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
public class Bullet extends ImageActor
{
	float velocity;
	NPC enemy;
	int maxDistance;

	public Bullet(float x, float y, int rotation, String textureFile, int maxDistance, float velocity) {
		super(x, y, 0, textureFile);
		this.getImg().setRotation(rotation);
		this.maxDistance = maxDistance;
		this.velocity = velocity;
		super.setDx(velocity);
		super.setDy(velocity);

		//Calculate distance to target move to target and delete on impact
	}
	
	float calcDistanceX() {
		if(enemy==null) return 0;
		return this.x - enemy.x;
	}
	
	float calcDistanceY() {
		if(enemy==null) return 0;
		return this.y - enemy.y;
	}
	
	void setTarget(NPC enemy) {
		this.enemy = enemy;
	}
	
	void checkProximity()
	{
		//Remove bullet
		boolean check = ((this.x > enemy.x-10) && (this.x < enemy.x+10) && (this.y > enemy.y-10) && (this.y < enemy.y+10) && enemy!=null );  
		//System.out.println("Statement "+ check);  
		if(check)
		{	
				//System.out.println("Hit");
				remove = true;
				enemy.setHealth(5);
		}
	
	}
	
	void calculateDistanceToTarget() //Might be able to implement anothter classes method
	{
		//Calculate distance
		double xDiff = calcDistanceX();
		double yDiff = calcDistanceY();
		if(xDiff==0 || yDiff==0) 
		{
				//super.setDx(0);
				//super.setDy(0);
				remove = true;
		}	
		
		float length = (float) Math.sqrt(xDiff*xDiff + yDiff*yDiff);
		float fractionX = (float) xDiff/length;
		float fractionY = (float) yDiff/length;
		//Adjust dx and dy accordingly
		//System.out.println("Dx: " + dx*fractionX + "Dy: " + dy*fractionY);
		//System.out.println("x: " + this.x + "y: " + this.y);
		super.setDx(-dx*fractionX);
		super.setDy(-dy*fractionY);
		//Once it reaches target remove

		
	}

	@Override
	void calcMove(int minx, int miny, int maxx, int maxy, float time) {
		x += dx*time;
		y += dy*time;
		//if(distanceToTarget<targetSize) removeBullet
	}
}
