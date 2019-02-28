import org.jsfml.graphics.*;
public class Dreadnought extends Tower
{
	public Dreadnought(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-7.png",background,placed);
		ID = "Dreadnought";
		cost = 1000;
		upgradeCost = 1500;
		baseDamage = 5;
		damage = baseDamage;
		baseCooldown = 50;
		cooldown = baseCooldown;
		range = 100;
		type = "laser";
		isTrap = false;
	}
	
	void upgrade()
	{
		if(rank!=1)rank++;
		if(rank==1)
		{
			baseDamage = 10;
			damage = baseDamage;
			changeSpriteImage("Towers/7-dreadnought/2.png");
			type = "electric";
		}
	}
}