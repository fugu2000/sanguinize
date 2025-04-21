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
        this.currentAngle = 0;
        this.attackStartAngle = -PI/3;
        this.attackEndAngle = PI/3;
        this.isSwingReturning = false;
        this.swingSpeed = 0.15f;
        this.hitboxRadius = 100f;
        this.swingDirection = 1;
        this.hitPositions = new ArrayList<PVector>();
        
        try {
            this.weaponImage = processingContext.loadImage("weapons/scythe.png");
            this.weaponImage.resize(120, 120);
        } catch (Exception e) {
            System.err.println("Failed to load scythe image: " + e.getMessage());
        }
    }

    public void update() {
        super.update();
        
        if (isAttacking) {
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
    }

    private void updateHitboxPositions() {
        hitPositions.clear();
        float radius = 80f;
        float x = cos(currentAngle) * radius;
        float y = sin(currentAngle) * radius;
        hitPositions.add(new PVector(x, y));
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

    protected void onAirAttack(float x, float y) {
    }

    public void render(float x, float y, String direction) {
        if (weaponImage != null) {
            pushMatrix();
            translate(x, y);
            rotate(currentAngle);
            imageMode(CENTER);
            image(weaponImage, 0, 0);
            popMatrix();
        } else {
            pushMatrix();
            translate(x, y);
            rotate(currentAngle);
            fill(150, 75, 0);
            rect(0, 0, 100, 20);
            popMatrix();
        }
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
