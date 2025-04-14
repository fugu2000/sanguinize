public abstract class Weapon {
    protected String name;
    protected int baseDamage;
    protected int cooldownFrames;
    protected int bloodCost;

    protected boolean onCooldown = false;
    protected int cooldownTimer = 0;

    public Weapon(String name, int baseDamage, int cooldownFrames, int bloodCost) {
        this.name = name;
        this.baseDamage = baseDamage;
        this.cooldownFrames = cooldownFrames;
        this.bloodCost = bloodCost;
    }

    public void update() {
        if (onCooldown) {
            cooldownTimer--;
            if (cooldownTimer <= 0) {
                onCooldown = false;
                cooldownTimer = 0;
            }
        }
    }

    public boolean canUse() {
        return !onCooldown;
    }

    public void triggerCooldown() {
        onCooldown = true;
        cooldownTimer = cooldownFrames;
    }

    public abstract void mainAttack(Player player, Direction dir);
    public abstract void bloodwield(Player player, Direction dir);

    public String getName() {
        return name;
    }
}

// Mace.java
public class Mace extends Weapon {
    public Mace() {
        super("Mace", 30, 40, 25);
    }

    @Override
    public void mainAttack(Player player, Direction dir) {
        if (!canUse()) return;
        player.applyForce(dir, 10);
        player.dealDamageInArc(baseDamage, dir);
        triggerCooldown();
    }

    @Override
    public void bloodwield(Player player, Direction dir) {
        if (!canUse() || !player.consumeBlood(bloodCost)) return;
        if (player.isInAir()) {
            double height = player.getAirHeight();
            player.slamDownWithAoE((int)(baseDamage + height * 2));
            triggerCooldown();
        }
    }
}

// Spear.java
public class Spear extends Weapon {
    public Spear() {
        super("Spear", 20, 25, 20);
    }

    @Override
    public void mainAttack(Player player, Direction dir) {
        if (!canUse()) return;
        player.dash(dir, 12);
        player.dealPiercingDamage(baseDamage, dir);
        triggerCooldown();
    }

    @Override
    public void bloodwield(Player player, Direction dir) {
        if (!canUse() || !player.consumeBlood(bloodCost)) return;
        player.beginSpearCharge(dir);
        triggerCooldown();
    }
}

// Gauntlets.java
public class Gauntlets extends Weapon {
    public Gauntlets() {
        super("Gauntlets", 15, 20, 15);
    }

    @Override
    public void mainAttack(Player player, Direction dir) {
        if (!canUse()) return;
        player.punch(dir, baseDamage, 1.5f);
        triggerCooldown();
    }

    @Override
    public void bloodwield(Player player, Direction dir) {
        if (!canUse() || !player.consumeBlood(bloodCost)) return;
        player.groundSlamLaunchEnemies();
        triggerCooldown();
    }
}

// Spikes.java
public class Spikes extends Weapon {
    public Spikes() {
        super("Spikes", 10, 15, 20);
    }

    @Override
    public void mainAttack(Player player, Direction dir) {
        if (!canUse()) return;
        player.shootBloodSpike(dir, baseDamage);
        triggerCooldown();
    }

    @Override
    public void bloodwield(Player player, Direction dir) {
        if (!canUse() || !player.consumeBlood(bloodCost)) return;
        if (player.isInAir() || player.isAttackingMultipleEnemies()) {
            player.shotgunBloodSpikes(dir, baseDamage + 15);
            triggerCooldown();
        }
    }
}
