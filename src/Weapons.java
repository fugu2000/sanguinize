import processing.core.*;
import java.util.ArrayList;

public abstract class Weapon {
    protected PApplet parent;
    protected String name;
    protected int damage;
    protected int cooldown;
    protected int bloodCost;
    protected int currentCooldown;
    protected boolean isActive;
    protected boolean isAttacking;
    protected float attackProgress;
    protected PImage weaponImage;
    protected ArrayList<PVector> hitPoints;
    protected float hitRadius;
    protected String lastDirection;

    public Weapon(PApplet parent, String name, int damage, int cooldown, int bloodCost) {
        this.parent = parent;
        this.name = name;
        this.damage = damage;
        this.cooldown = cooldown;
        this.bloodCost = bloodCost;
        this.currentCooldown = 0;
        this.isActive = true;
        this.isAttacking = false;
        this.attackProgress = 0;
        this.hitPoints = new ArrayList<PVector>();
        this.hitRadius = 50;
        this.lastDirection = "right";
    }

    public void update() {
        if (currentCooldown > 0) {
            currentCooldown--;
        }
        
        if (isAttacking) {
            attackProgress += 0.05f;
            updateAttack();
            
            if (attackProgress >= 1.0f) {
                isAttacking = false;
                attackProgress = 0;
            }
        }
    }

    protected abstract void updateAttack();

    public void startAttack(float x, float y, String direction) {
        if (currentCooldown <= 0 && !isAttacking) {
            isAttacking = true;
            attackProgress = 0;
            currentCooldown = cooldown;
            lastDirection = direction;
            onAttackStart(x, y, direction);
        }
    }

    protected abstract void onAttackStart(float x, float y, String direction);

    public void render(float x, float y) {
        if (weaponImage != null && isAttacking) {
            parent.pushMatrix();
            parent.translate(x, y);
            if (lastDirection.equals("left")) {
                parent.scale(-1, 1);
            }
            renderWeapon();
            parent.popMatrix();
        }
    }

    protected abstract void renderWeapon();

    public ArrayList<PVector> getHitPoints() {
        return new ArrayList<PVector>(hitPoints);
    }

    public float getHitRadius() {
        return hitRadius;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public boolean canAttack() {
        return currentCooldown <= 0 && !isAttacking;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public boolean hasMovementEffect() {
        return false;
    }

    public PVector getMovementEffect() {
        return new PVector(0, 0);
    }

    protected void loadImage(String imagePath) {
        try {
            weaponImage = parent.loadImage(imagePath);
            if (weaponImage != null) {
                weaponImage.resize(100, 100);
            }
        } catch (Exception e) {
            System.err.println("Failed to load weapon image: " + e.getMessage());
        }
    }
}
