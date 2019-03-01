import org.jsfml.graphics.*;
public class Dreadnought extends Tower
{
	/**
	* Constructor of Dreadnought
	* @param x The x position of the dreadnought
	* @param y The y position of the dreadnought
	* @param r The rotation of the dreadnought
	* @param Background The background dreadnought will be displayed on
	* @param placed Determines if the turret has been placed or not
	*/
	public Dreadnought(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-7.png",background,placed);
		ID = "Dreadnought";
		cost = 1000;
		upgradeCost = 1500;
		baseDamage = 5;
		damage = baseDamage;
		baseCooldown = 35;
		cooldown = baseCooldown;
		range = 100;
		type = "laser";
		isTrap = false;
	}
	/**
	* Changes the sprite, rank and some other attributes such as damage and type of the dreadnought
	*/
	void upgrade()
	{
		if(rank!=1)rank++;
		if(rank==1)
		{
			baseDamage = 45;
			damage = baseDamage;
			changeSpriteImage("Towers/7-dreadnought/2.png");
			type = "electric";
		}
	}
}