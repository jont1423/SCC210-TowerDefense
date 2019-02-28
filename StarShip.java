import org.jsfml.graphics.*;
public class StarShip extends Tower
{
	public StarShip(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-4.png",background,placed);
		ID = "Star Ship";
		cost = 250;
		upgradeCost = 600;
		baseDamage = 5;
		damage = baseDamage;
		baseCooldown = 50;
		cooldown = baseCooldown;
		range = 100;
		type = "star";
		isTrap = false;
	}
	
	void upgrade()
	{
		if(rank!=2)rank++;
		if(rank==1)
		{
			baseDamage = 10;
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