import org.jsfml.graphics.*;
public class WarpShip extends Tower
{
	public WarpShip(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-8.png",background,placed);
		ID = "Warp Ship";
		cost = 900;
		baseDamage = 5;
		damage = baseDamage;
		baseCooldown = 50;
		cooldown = baseCooldown;
		range = 100;
		type = "warp";
		isTrap = false;
	}
	
	void upgrade()
	{
		rank++;
		if(rank==1)
		{
			baseDamage = 10;
			damage = baseDamage;
			//Could also change firerate and type
			//Also change spritesheet
		}
		else if(rank==2)
		{
			
		}
	}
}