import org.jsfml.graphics.*;
public class IonTurret extends Tower
{
	public IonTurret(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-7.png",background,placed);
		ID = "Ion Turret";
		cost = 550;
		baseDamage = 5;
		damage = baseDamage;
		baseCooldown = 50;
		cooldown = baseCooldown;
		range = 100;
		type = "laser";
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