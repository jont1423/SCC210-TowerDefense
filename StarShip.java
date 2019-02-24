import org.jsfml.graphics.*;
public class StarShip extends Tower
{
	public StarShip(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-4.png",background,placed);
		ID = "Star Ship";
		cost = 250;
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