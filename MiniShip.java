import org.jsfml.graphics.*;
public class MiniShip extends Tower
{
	/**
	* Constructor of MiniShip
	* @param x The x position of the mini ship
	* @param y The y position of the mini ship
	* @param r The rotation of the mini ship
	* @param Background The background mini ship will be displayed on
	* @param placed Determines if the turret has been placed or not
	*/
	public MiniShip(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-2.png",background,placed);
		ID = "Mini Ship";
		cost = 100;
		upgradeCost = 225;
		baseDamage = 5;
		damage = baseDamage;
		baseCooldown = 50;
		cooldown = baseCooldown;
		range = 100;
		type = "cryo";
		isTrap = false;
	}
	/**
	* Changes the sprite, rank and some other attributes such as damage and type of the mini cannon
	*/
	void upgrade()
	{
		if(rank!=2)rank++;
		if(rank==1)
		{
			baseDamage = 10;
			damage = baseDamage;
			type = "warp";
			changeSpriteImage("Towers/2-miniship/2.png");
		}
		else if(rank==2)
		{
			baseDamage = 35;
			baseCooldown = 40;
			damage = baseDamage;
			type = "cryo";
			changeSpriteImage("Towers/2-miniship/3.png");
		}
	}
}