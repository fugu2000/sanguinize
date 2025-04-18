public abstract class Weapons
  {
      public String name;
      public int damage, cooldown, bloodCost;
      public boolean available, onCooldown;


    public boolean canUse() {
        return !onCooldown;
    }
}
