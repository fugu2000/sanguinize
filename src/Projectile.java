public class Projectile {
    private int x, y, speed, damage, radius;
    private double direction;
    private boolean isActive, isAirborne, hasStunEffect;
    private long launchTime;
    private static final long AIR_TIME = 3000, EFFECT_DURATION = 3000;
    private String type;

    public Projectile(String type, int startX, int startY, int speed, int damage, double direction, int radius) {
        this.type = type;
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.damage = damage;
        this.direction = direction;
        this.radius = radius;
        this.isActive = true;
        this.hasStunEffect = type.equals("bullet");
        if (type.equals("spear")) {
            this.isAirborne = true;
            this.launchTime = System.currentTimeMillis();
        }
        if (type.equals("potion")) {
            this.launchTime = System.currentTimeMillis();
        }
    }

    public void move() {
        if (!isActive) return;
        if (type.equals("spear")) {
            if (isAirborne) {
                x += (int) (speed * Math.cos(direction));
                y += (int) (speed * Math.sin(direction));
                if ((System.currentTimeMillis() - launchTime) >= AIR_TIME) {
                    isAirborne = false;
                }
            }
        } else if (type.equals("bullet")) {
            x += (int) (speed * Math.cos(direction));
            y += (int) (speed * Math.sin(direction));
        }
    }

    public boolean checkHit(int targetX, int targetY, int targetRadius) {
        if (!isActive) return false;
        double distance = Math.sqrt(Math.pow(targetX - x, 2) + Math.pow(targetY - y, 2));
        return distance < targetRadius + radius;
    }

    public boolean isEffectActive() {
        if (type.equals("potion")) {
            return (System.currentTimeMillis() - launchTime) < EFFECT_DURATION;
        }
        return false;
    }

    public void applyEffect(Player player) {
        if (!isActive) return;
        if (type.equals("potion") && isEffectActive() && checkHit(player.getX(), player.getY(), player.getRadius())) {
            player.takeDamage(damage);
        } else if (type.equals("spear") && checkHit(player.getX(), player.getY(), player.getRadius())) {
            player.takeDamage(damage);
            deactivate();
        } else if (type.equals("bullet") && checkHit(player.getX(), player.getY(), player.getRadius())) {
            player.takeDamage(damage);
            player.stun();
            deactivate();
        }
    }

    public int getDamage() {
        return damage;
    }

    public boolean isAirborne() {
        return isAirborne;
    }

    public boolean isActive() {
        return isActive;
    }

    public void deactivate() {
        isActive = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

