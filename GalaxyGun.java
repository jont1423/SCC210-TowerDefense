import org.jsfml.graphics.*;
public class GalaxyGun extends Tower
{
	public GalaxyGun(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-6.png",background,placed);
		ID = "Galaxy Gun";
		cost = 300;
		baseDamage = 5;
		damage = baseDamage;
		baseCooldown = 50;
		cooldown = baseCooldown;
		range = 100;
		type = "galaxy";
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