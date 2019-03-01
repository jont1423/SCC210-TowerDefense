import org.jsfml.graphics.*;
public class GalaxyGun extends Tower
{
	/**
	* Constructor of GalaxyGun
	* @param x The x position of the galaxy gun
	* @param y The y position of the galaxy gun
	* @param r The rotation of the galaxy gun
	* @param Background The background galaxy gun will be displayed on
	* @param placed Determines if the turret has been placed or not
	*/
	public GalaxyGun(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-6.png",background,placed);
		ID = "Galaxy Gun";
		cost = 750;
		upgradeCost = 900;
		baseDamage = 30;
		damage = baseDamage;
		baseCooldown = 100;
		cooldown = baseCooldown;
		range = 100;
		type = "galaxy";
		isTrap = false;
	}
	/**
	* Changes the sprite, rank and some other attributes such as damage and type of the galaxy gun
	*/
	void upgrade()
	{
		if(rank!=1)rank++;
		if(rank==1)
		{
			baseDamage = 40;
			damage = baseDamage;
			changeSpriteImage("Towers/6-galaxygun/2.png");
		}
	}
}