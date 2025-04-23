public abstract class Weapon {
    public String name;
    public int damage;
    public int cooldown;
    public int bloodCost;
    public boolean available;
    public boolean onCooldown;

    public Weapon(String name, int damage, int cooldown, int bloodCost) {
        this.name = name;
        this.damage = damage;
        this.cooldown = cooldown;
        this.bloodCost = bloodCost;
        this.available = true;
        this.onCooldown = false;
    }

    public boolean canUse() {
        return !onCooldown;
    }

    public abstract void mainAttack(Sal s, Direction dir);
    public abstract void bloodwield(Sal s, Direction dir);
}
