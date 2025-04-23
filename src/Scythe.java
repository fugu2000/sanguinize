public class Scythe extends Weapon {
    private float currentAngle;
    private float attackStartAngle;
    private float attackEndAngle;
    private boolean isSwingReturning;
    private PImage weaponImage;
    private int swingDirection;
    private float swingSpeed;
    private float hitboxRadius;
    private ArrayList<PVector> hitPositions;

    public Scythe(PApplet processingContext) {
        super("Scythe", 35, 90, 30);
        this.parent = processingContext;
        this.currentAngle = 0;
        this.attackStartAngle = -PI/3;
        this.attackEndAngle = PI/3;
        this.isSwingReturning = false;
        this.swingSpeed = 0.15f;
        this.hitboxRadius = 100f;
        this.swingDirection = 1;
        this.hitPositions = new ArrayList<PVector>();
        loadImage("weapons/scythe.png");
    }

    protected void updateAttack() {
        if (!isSwingReturning) {
            currentAngle = lerp(attackStartAngle, attackEndAngle, attackProgress);
            if (attackProgress >= 1.0f) {
                isSwingReturning = true;
                attackProgress = 0f;
            }
        } else {
            currentAngle = lerp(attackEndAngle, attackStartAngle, attackProgress);
            if (attackProgress >= 1.0f) {
                isAttacking = false;
                isSwingReturning = false;
                hitPositions.clear();
            }
        }
        attackProgress += swingSpeed;
        updateHitboxPositions();
    }

    protected void renderWeapon() {
        pushMatrix();
        rotate(currentAngle);
        if (weaponImage != null) {
            imageMode(CENTER);
            image(weaponImage, 0, 0);
        } else {
            fill(150, 75, 0);
            rect(0, 0, 100, 20);
        }
        popMatrix();
    }

    private void updateHitboxPositions() {
        hitPositions.clear();
        float radius = 80f;
        float x = cos(currentAngle) * radius;
        float y = sin(currentAngle) * radius;
        hitPositions.add(new PVector(x, y));
        for(int i = 1; i <= 3; i++) {
            float angle = currentAngle - i * 0.1f * swingDirection;
            hitPositions.add(new PVector(cos(angle) * radius, sin(angle) * radius));
        }
    }

    protected void onAttackStart(float x, float y, String direction) {
        isAttacking = true;
        isSwingReturning = false;
        attackProgress = 0f;
        if (direction.equals("left")) {
            attackStartAngle = -PI/2;
            attackEndAngle = PI/2;
            swingDirection = -1;
        } else {
            attackStartAngle = PI/2;
            attackEndAngle = -PI/2;
            swingDirection = 1;
        }
        currentAngle = attackStartAngle;
    }

    public void render(float x, float y) {
        pushMatrix();
        translate(x, y);
        if (lastDirection.equals("left")) {
            scale(-1, 1);
        }
        renderWeapon();
        popMatrix();
    }

    public void render(float x, float y, String direction) {
        pushMatrix();
        translate(x, y);
        if (direction.equals("left")) {
            scale(-1, 1);
        }
        renderWeapon();
        popMatrix();
    }

    public ArrayList<PVector> getHitboxPositions() {
        return new ArrayList<PVector>(hitPositions);
    }

    public float getHitboxRadius() {
        return hitboxRadius;
    }

    public boolean isInAttack() {
        return isAttacking;
    }

    public boolean canInterrupt() {
        return isSwingReturning && attackProgress > 0.5f;
    }

    public void cancelAttack() {
        if (canInterrupt()) {
            isAttacking = false;
            isSwingReturning = false;
            hitPositions.clear();
        }
    }
}
