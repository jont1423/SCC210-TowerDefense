import org.jsfml.graphics.*;
public class MiniCannon extends Tower
{
	/**
	* Constructor of MiniCannon
	* @param x The x position of the mini cannon
	* @param y The y position of the mini cannon
	* @param r The rotation of the mini cannon
	* @param Background The background mini cannon will be displayed on
	* @param placed Determines if the turret has been placed or not
	*/
	public MiniCannon(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-3.png",background,placed);
		ID = "Mini Cannon";
		cost = 150;
		upgradeCost = 250;
		baseDamage = 15;
		damage = baseDamage;
		baseCooldown = 100;
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
			baseDamage = 40;
			damage = baseDamage;
			type = "warp";
			changeSpriteImage("Towers/3-minicannon/2.png");
		}
		else if(rank==2)
		{
			baseDamage = 70;
			baseCooldown = 150;
			damage = baseDamage;
			type = "cryo";
			changeSpriteImage("Towers/3-minicannon/3.png");
		}
	}
}