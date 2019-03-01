import org.jsfml.graphics.*;
public class StarShip extends Tower
{
	/**
	* Constructor of StarShip
	* @param x The x position of the star ship
	* @param y The y position of the star ship
	* @param r The rotation of the star ship
	* @param Background The background star ship will be displayed on
	* @param placed Determines if the turret has been placed or not
	*/
	public StarShip(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-4.png",background,placed);
		ID = "Star Ship";
		cost = 250;
		upgradeCost = 450;
		baseDamage = 10;
		damage = baseDamage;
		baseCooldown = 50;
		cooldown = baseCooldown;
		range = 100;
		type = "star";
		isTrap = false;
	}
	/**
	* Changes the sprite, rank and some other attributes such as damage and type of the star ship
	*/
	void upgrade()
	{
		if(rank!=2)rank++;
		if(rank==1)
		{
			baseDamage = 25;
			damage = baseDamage;
			type = "laser";
			changeSpriteImage("Towers/4-starship/2.png");
		}
		else if(rank==2)
		{
			baseDamage = 50;
			damage = baseDamage;
			type = "electric";
			changeSpriteImage("Towers/4-starship/3.png");
		}
	}
}