public abstract class Weapon {
  String name;
  int damage;
  int cooldown;
  int bloodCost;
  boolean available;
  boolean onCooldown;
  float direction;

//  public Weapon(String name, int damage, int cooldown, int bloodCost) {
 //   this.name = name;
//    this.damage = damage;
//    this.cooldown = cooldown;
//    this.bloodCost = bloodCost;
//    this.available = true;
//    this.onCooldown = false;
//  }

  public boolean canUse() {
    return !onCooldown;
  }

  public abstract void mainAttack(Sal s);
  //public abstract void bloodwield(Sal s, Weapon name);
}
