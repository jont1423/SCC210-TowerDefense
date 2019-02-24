import org.jsfml.graphics.*;
public class MiniCannon extends Tower
{
	public MiniCannon(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-3.png",background,placed);
		ID = "Mini Cannon";
		cost = 150;
		baseDamage = 5;
		damage = baseDamage;
		baseCooldown = 50;
		cooldown = baseCooldown;
		range = 100;
		type = "cryo";
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