import org.jsfml.graphics.*;
public class StarTower extends Tower
{
	public StarTower(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-5.png",background,placed);
		ID = "Star Tower";
		cost = 400;
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