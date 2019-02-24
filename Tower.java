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

abstract class Tower extends ImageActor {

	String ID;
	int killCount = 0 ;
	int rank = 0;
	String type;
	int damage;
	int baseDamage; //This variable needs to moved to subclasses
	int baseCooldown; //This variable needs to moved to subclasses
	private boolean placed;
	boolean isTrap;
	int trapHealth;
	int cost;

	float range;
	private int angleOffset = 90;
	private int angle = 0;
	private NPC nearest;
	private Bullet bullet;
	private Clock fireRate = new Clock();
	int cooldown; //Millseconds before turret can refire
	private Vector2f bulletOrigin = new Vector2f(x+2f,y-19f);
	private float bulletOriginX; //Where bullet spawns
	private float bulletOriginY; //Where bullet spawns
	private Image iBackground;
	private String towerImage;
	private int frame;// frame used for animation

	public Tower(float x, float y, int r, String textureFile, Background background, boolean placed) {
		super(x, y, r, textureFile);
		this.towerImage = textureFile;
		this.iBackground = background.getBackground();
	}
	
	abstract void upgrade();

	/**
	 * This function calculates the distance between a tower and a vector2f
	 * - ignoring the sqrt function as it is costly on the game
	 * 
	 * @param actor This actor were getting the distance to
	 * @return returns the squared distance between the two points
	 */
	float calcDistance(Actor a) {
		// calculating the x and y difference between the tower and the actor
		float xDiff = this.x - a.x;
		float yDiff = this.y - a.y;
		// calculating the squared distance between the tower and the actor
		return (float) (Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
	}
	
	/**
	 * This function calculates the distance between a tower and a vector2f
	 * - ignoring the sqrt function as it is costly on the game
	 * 
	 * @param point This point were getting the distance to
	 * @return returns the squared distance between the two points
	 */
	
	float calcDistance(Vector2f b) {
		// calculating the x and y difference between points
		float xDiff = this.x - b.x;
		float yDiff = this.y - b.y;
		// calculating the squared distance between the points
		return (float) (Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
	}
	
	void setFrame(int i)
	{
		frame = i;
	}
	
	int getFrame()
	{
		return frame;
	}
	
	void setBullet(Bullet bullet)
	{
		this.bullet = bullet;
	}
	
	String getID()
	{
		return ID;
	}
	
	int getKillCount()
	{
		return killCount;
	}
	
	int getRank()
	{
		return rank;
	}
	
	int getCost()
	{
		return cost;
	}
	
	void setDamage(float multiplier)
	{
		if(damage == baseDamage) damage *= (int) multiplier;			
	}
	
	void setPlaced(boolean placed) {
		this.placed = placed;
	}
	
	boolean getPlaced()
	{
		return placed;
	}
	
	boolean isTrap()
	{
		return isTrap;
	}
	
	void setTrapHealth()
	{
		trapHealth -= 5;
	}
	
	int getTrapHealth()
	{
		return trapHealth;
	}
	
	int getDamage()
	{
		return damage;
	}
	
	void setDefaultDamage()
	{
		damage = baseDamage;
	}
	
	String getType()
	{
		return type;
	}
	
	Bullet getBullet()
	{
		return bullet;
	}
	
	String getTowerImage()
	{
		return towerImage;
	}
	
	int getCooldown()
	{
		return cooldown;
	}
	
	int getBaseDamage()
	{
		return baseDamage;
	}
	
	int getBaseCooldown()
	{
		return baseCooldown;
	}
	
	void setCooldown(float multiplier)
	{
		cooldown *= multiplier;
	}
	
	void setDefaultCooldown()
	{
		cooldown = baseCooldown;
	}
	
	void setFireRate()
	{
		fireRate.restart();
	}
	
	long getFireRate()
	{
		return fireRate.getElapsedTime().asMilliseconds();
	}
	
	boolean placementCheck(int x, int y, FloatRect boundaries, ArrayList<Tower> towers,Background selectedMap)
	{
		// Looping over all the towers
		FloatRect towerBoundaries = this.getImg().getGlobalBounds();
		for(Tower tower : towers)
		{
			// Checking if the tower isnt the current tower and checking if the tower lies within the point being checked
			if(tower != this && towerBoundaries.contains(tower.x, tower.y))
			{
				return false;
			}
		}

		/*int boundaryLeft = (int) boundaries.left;
		int boundaryWidth = (int) boundaries.width;
		int boundaryTop = (int) boundaries.top;
		int boundaryHeight = (int) boundaries.height;*/

		//for(int i=boundaryLeft;i<boundaryLeft+boundaryWidth;i++)
		//{
			//for(int j=boundaryTop;j<boundaryTop+boundaryHeight;j++)
		//	{
				Color pixel = iBackground.getPixel(x,y);
				Color placementColor1 = selectedMap.getPlacementColor1();
				Color placementColor2 = selectedMap.getPlacementColor2();
				boolean place1 = isSimilar(pixel,placementColor1,50);
				boolean place2 = isSimilar(pixel,placementColor2,50);
				if(!isTrap && !(place1 || place2)) return false;
				if(isTrap && (place1 || place2)) return false;
		//	}
		//}
		
		return true;

	}
	
	boolean isSimilar(Color colour1, Color colour2, int accuracy)
	{	
		if(colour1.r < (colour2.r-accuracy) || colour1.r > (colour2.r+accuracy)) return false;			
		if(colour1.g < (colour2.g-accuracy) || colour1.g > (colour2.g+accuracy)) return false;			
		if(colour1.b < (colour2.b-accuracy) || colour1.b > (colour2.b+accuracy)) return false;
		return true;
	}
	/**
	 * This function calculates the angle between the nearest enemy and the current tower
	 * 
	 * @param minX This is the minimum x value that the tower can be set to
	 * @param minY This is the minimum y value that the tower can be set to
	 * @param maxX This is the maximum x value that the tower can be set to (Width of the window)
	 * @param maxY This is the maximum y value that the tower can be set to (Height of the window)
	 * @param time This holds the time variable
	 */
	void calcMove(int minx, int miny, int maxx, int maxy, float time) {
		// checking if the nearest enemy has been set
		if (nearest != null) {
			// calculating the angle we need to rotate to so that the tower is faceing the enemy (for shooting)
			angle =  (int) Math.toDegrees(Math.atan2(this.y - nearest.y, this.x - nearest.x)) - angleOffset;
			// setting the rotation of the current tower based off the obtained angle
			this.getImg().setRotation(angle);
		}
	
	}
	
	void calcBulletOrigin()
	{
		
		//Translate to origin
		//System.out.println("X: "+ x);
		//System.out.println("Y: "+ y);

	
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
		

		//System.out.println("Bulletoriginx: " + bulletOriginX);
		//System.out.println("Bulletoriginy: " + bulletOriginY);

	}
	
	float getBulletOriginX()
	{
		return bulletOrigin.x;
	}
	
	float getBulletOriginY()
	{
		return bulletOrigin.y;
	}
	
	
	int getRange()
	{
		return (int) range;
	}
	/**
	 * This function finds the nearest tower within range and returns it
	 * 
	 * @param enemies This is the list of enemies to check
	 * @return returns an instance of the nearest enemy to the tower
	 */
	
	NPC getNearestEnemy(ArrayList<NPC> enemies)
	{
		// squaring the range of the tower so that we don't need to use square root in our calculations
		float proximity = (float) Math.pow(range, 2);
		// setting the shortest distance to the maximum float value
		float shortestDistance = Float.MAX_VALUE;
		// setting the nearest enemy to null
		//nearest = null;
		
		if(nearest==null)
		{
			// looping through all the enemies
			for (NPC enemy : enemies) {
				// getting the distance to the enemy from the tower
				float distanceTo = calcDistance(enemy);

				// checking if the distance is valid and the shortest
				if (distanceTo <= proximity && distanceTo < shortestDistance) {
					// setting the nearest enemy
					nearest = enemy;
				}
			}
		}
		else
		{
			// getting the distance to the enemy from the tower
			float distanceTo = calcDistance(nearest);

			// checking if the distance is valid and the shortest
			if (!(distanceTo <= proximity && distanceTo < shortestDistance)) {
				// setting the nearest enemy
				nearest = null;
			}
		}


		// returning the nearest enemy
		return nearest;
	}
	
	void setNearestEnemy(NPC nearest)
	{
		 this.nearest = nearest;
	}
	
	NPC getNearest()
	{
		return nearest;
	}
	/**
	 * This function moves the current tower to the x and y of the mouse
	 * and limits it within the ranges supplied
	 * 
	 * @param x This is the x coordinate of the mouse
	 * @param y This is the y coordinate of the mouse
	 * @param minX This is the minimum x value that the tower can be set to
	 * @param minY This is the minimum y value that the tower can be set to
	 * @param maxX This is the maximum x value that the tower can be set to (Width of the window)
	 * @param maxY This is the maximum y value that the tower can be set to (Height of the window)
	 */
	public void calcMoveCursor(int x, int y, int minX, int minY, int maxX, int maxY) {
		// checking if the mouse x is less than the minimum x and clamping its value
		if (x <= minX) x = minX;
		// checking if the mouse x is greater than the maximum x and clamping its value
		else if (x >= maxX) x = maxX;
		// checking if the mouse y is less than the minimum y and clamping its value
		if (y <= minY) y = minY;
		// checking if the mouse y is greater than the maximum y and clamping its value
		else if (y >= maxY) y = maxY;

		// setting the towers x and y
		this.x = x;
		this.y = y;
	}

	/**
	 * This function draws a circle around the base of the tower and
	 * changes colour depending if there has been an intersection
	 * 
	 * @param window This is the games render window
	 * @param intersects The boolean which is set based off an intersection
	 */
	public void renderBorder(RenderWindow window, boolean intersects) {
		// creating a new circle with the radius set to range
		CircleShape circle = new CircleShape(range);
		Color colour;
		// if there is an intersection set the colour to red (invalid placement position)
		//if (intersects) 
		colour = new Color(255,0,0, 100);
		// otherwise set the colour to green (valid placement position)
		//else colour = new Color(0,255,0, 100);
		// setting the circles fill and position and drawing the circle to the window
		circle.setFillColor(colour);
		circle.setPosition(x - range, y - range);
		window.draw(circle);
	}

	/**
	 * This function checks if a point lies within the towers range
	 * 
	 * @param x The x coordinate of the point being checked
	 * @param y The y coordinate of the point being checked
	 * @return This returns a boolean based off if the intersection is true
	 */
	public boolean within(int x, int y) {
		// getting the distance between the point and the tower (ignoring the sqrt part of the maths as it is expensive)
		double d = Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2);
		// checking if the distance is less than the square of the range (lies within) and returning
		return (d < Math.pow(range, 2));
	}

	/**
	 * This function checks if a point lies within a specified range
	 * 
	 * @param x The x coordinate of the point being checked
	 * @param y The y coordinate of the point being checked
	 * @param r The r is the maximum radius you will check if there is an intersection
	 * @return This returns a boolean based off if the intersection is true
	 */
	public boolean within(int x, int y, int r) {
		// getting the distance between the point and the tower (ignoring the sqrt part of the maths as it is expensive)
		double d = Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2);
		// checking if the distance is less than the square of r (lies within) and returning
		return (d < Math.pow(r, 2));
	}

	/**
	 * This function upgrades the tower and reassigns the sprites texture
	 * 
	 * @param textureFiles This is an array of all the possible textures the tower can have
	 */
	public void upgrade(String[] textureFiles) {
		// incrementing the rank and modding it with the number of textures in the list and changing the sprite to have this texture
		changeSpriteImage(textureFiles[++rank % textureFiles.length]);
	}
}
