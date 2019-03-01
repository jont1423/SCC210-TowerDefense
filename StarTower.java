import org.jsfml.graphics.*;
public class StarTower extends Tower
{
	/**
	* Constructor of StarTower
	* @param x The x position of the star tower
	* @param y The y position of the star tower
	* @param r The rotation of the star tower
	* @param Background The background star tower will be displayed on
	* @param placed Determines if the turret has been placed or not
	*/
	public StarTower(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-5.png",background,placed);
		ID = "Star Tower";
		cost = 450;
		upgradeCost = 750;
		baseDamage = 10;
		damage = baseDamage;
		baseCooldown = 50;
		cooldown = baseCooldown;
		range = 100;
		type = "star";
		isTrap = false;
	}
	/**
	* Changes the sprite, rank and some other attributes such as damage and type of the star tower
	*/
	void upgrade()
	{
		if(rank!=2)rank++;
		if(rank==1)
		{
			baseDamage = 20;
			damage = baseDamage;
			changeSpriteImage("Towers/5-startower/2.png");
		}
		else if(rank==2)
		{
			baseDamage = 35;
			damage = baseDamage;
			changeSpriteImage("Towers/5-startower/3.png");
		}
	}
}