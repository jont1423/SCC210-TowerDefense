import org.jsfml.graphics.*;
public class MiniCannon extends Tower
{
	public MiniCannon(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-3.png",background,placed);
		ID = "Mini Cannon";
		cost = 150;
		upgradeCost = 225;
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
		if(rank!=2)rank++;
		if(rank==1)
		{
			baseDamage = 10;
			damage = baseDamage;
			type = "warp";
			changeSpriteImage("Towers/3-minicannon/2.png");
		}
		else if(rank==2)
		{
			baseDamage = 50;
			damage = baseDamage;
			type = "cryo";
			changeSpriteImage("Towers/3-minicannon/3.png");
		}
	}
}