import org.jsfml.graphics.*;
public class Alien extends Tower
{
	public Alien(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-1.png",background,placed);
		ID = "Alien";
		cost = 50;
		baseDamage = 5;
		damage = baseDamage;
		baseCooldown = 50;
		cooldown = baseCooldown;
		range = 100;
		type = "electric";
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