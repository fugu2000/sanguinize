public class Scythe extends Weapons
{
  PImage Scythe;
  private float x, y;
  Scythe()
  {
    name = "Scythe";
    damage = 25;
    cooldown = 2;
    bloodCost = 50;
    Scythe = loadImage("SCYTHE.PNG");
  }
  public void attack()
  {
    
  }
}
