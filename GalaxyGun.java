import org.jsfml.graphics.*;
public class GalaxyGun extends Tower
{
	public GalaxyGun(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-6.png",background,placed);
		ID = "Galaxy Gun";
		cost = 300;
		upgradeCost = 560;
		baseDamage = 30;
		damage = baseDamage;
		baseCooldown = 100;
		cooldown = baseCooldown;
		range = 100;
		type = "galaxy";
		isTrap = false;
	}
	
	void upgrade()
	{
		if(rank!=1)rank++;
		if(rank==1)
		{
			baseDamage = 40;
			damage = baseDamage;
		}
	}
}