import org.jsfml.graphics.*;
public class MiniShip extends Tower
{
	public MiniShip(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-2.png",background,placed);
		ID = "Mini Ship";
		cost = 100;
		upgradeCost = 250;
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
			changeSpriteImage("Towers/2-miniship/2.png");
		}
		else if(rank==2)
		{
			baseDamage = 50;
			damage = baseDamage;
			type = "cryo";
			changeSpriteImage("Towers/2-miniship/3.png");
		}
	}
}