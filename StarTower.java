import org.jsfml.graphics.*;
public class StarTower extends Tower
{
	public StarTower(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-5.png",background,placed);
		ID = "Star Tower";
		cost = 400;
		upgradeCost = 705;
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
			baseDamage = 15;
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