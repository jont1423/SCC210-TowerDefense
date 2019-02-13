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

		//Calculate distance to target move to target and delete on impact
	}

	void setTarget(NPC enemy) {
		this.enemy = enemy;
	}
	
	void calculateDistanceToTarget()
	{
		//Calculate distance
		
		//Adjust dx and dy accordingly
		
		//Once it reaches target remove
		
	}

	@Override
	void calcMove(int minx, int miny, int maxx, int maxy, float time) {
		x += dx;
		y += dy;
		//if(distanceToTarget<targetSize) removeBullet
	}
}
