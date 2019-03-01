import org.jsfml.graphics.*;
public class IonTurret extends Tower
{
	/**
	* Constructor of IonTurret
	* @param x The x position of the ion turret
	* @param y The y position of the ion turret
	* @param r The rotation of the ion turret
	* @param Background The background ion turret will be displayed on
	* @param placed Determines if the turret has been placed or not
	*/
	public IonTurret(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-8.png",background,placed);
		ID = "Ion Turret";
		cost = 3000;
		upgradeCost = 4500;
		baseDamage = 5;
		damage = baseDamage;
		baseCooldown = 35;
		cooldown = baseCooldown;
		range = 100;
		type = "laser";
		isTrap = false;
	}
	/**
	* Changes the sprite, rank and some other attributes such as damage and type of the ion turret
	*/
	void upgrade()
	{
		if(rank!=1)rank++;
		if(rank==1)
		{
			baseDamage = 65;
			damage = baseDamage;
			changeSpriteImage("Towers/8-ionturret/2.png");
			type = "galaxy";
		}
	}
}