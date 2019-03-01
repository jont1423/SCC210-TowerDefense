import org.jsfml.graphics.*;
public class WarpShip extends Tower
{
	/**
	* Constructor of WarpShip
	* @param x The x position of the warp ship
	* @param y The y position of the warp ship
	* @param r The rotation of the warp ship
	* @param Background The background warp ship will be displayed on
	* @param placed Determines if the turret has been placed or not
	*/
	public WarpShip(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-9.png",background,placed);
		ID = "Warp Ship";
		cost = 5000;
		upgradeCost = 6350;
		baseDamage = 55;
		damage = baseDamage;
		baseCooldown = 50;
		cooldown = baseCooldown;
		range = 100;
		type = "warp";
		isTrap = false;
	}
	/**
	* Changes the sprite, rank and some other attributes such as damage and type of the warp ship
	*/
	void upgrade()
	{
		if(rank!=1)rank++;
		if(rank==1)
		{
			baseDamage = 90;
			damage = baseDamage;
			changeSpriteImage("Towers/9-warpship/2.png");
		}
	}
}