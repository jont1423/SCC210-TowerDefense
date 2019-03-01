import org.jsfml.graphics.*;
public class Alien extends Tower
{
	/**
	* Constructor of Alien
	* @param x The x position of the alien
	* @param y The y position of the alien
	* @param r The rotation of the alien
	* @param Background The background alien will be displayed on
	* @param placed Determines if the turret has been placed or not
	*/
	public Alien(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-1.png",background,placed);
		ID = "Alien";
		cost = 50;
		upgradeCost = 100;
		baseDamage = 10;
		damage = baseDamage;
		baseCooldown = 50;
		cooldown = baseCooldown;
		range = 100;
		type = "electric";
		isTrap = false;
	}
	/**
	* Changes the sprite, rank and some other attributes such as damage and type of the alien
	*/
	void upgrade()
	{
		if(rank!=1)rank++;
		if(rank==1)
		{
			baseDamage = 17;
			baseCooldown = 35;
			damage = baseDamage;
			type = "warp";
			changeSpriteImage("Towers/1-alien/2.png");
		}
	}
}