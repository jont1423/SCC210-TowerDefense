import org.jsfml.graphics.*;
public class WarpShip extends Tower
{
	public WarpShip(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-9.png",background,placed);
		ID = "Warp Ship";
		cost = 900;
		upgradeCost = 1350;
		baseDamage = 55;
		damage = baseDamage;
		baseCooldown = 50;
		cooldown = baseCooldown;
		range = 100;
		type = "warp";
		isTrap = false;
	}
	
	void upgrade()
	{
		if(rank!=1)rank++;
		if(rank==1)
		{
			baseDamage = 90;
			damage = baseDamage;
		}
	}
}