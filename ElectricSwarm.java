import org.jsfml.graphics.*;
public class ElectricSwarm extends Tower
{
	public ElectricSwarm(float x, float y, int r, Background background, boolean placed)
	{
		super(x,y,r,"Towers/tower-11.png",background,placed);
		ID = "Electric Swarm";
		cost = 100;
		upgradeCost = 0;
		trapHealth =  50;
		baseDamage = 50;
		damage = baseDamage;
		baseCooldown = 150;
		cooldown = baseCooldown;
		range = 0;

		type = "electric";
		isTrap = true;
	}
	
	void upgrade()
	{

	}
}