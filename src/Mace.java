public class Mace extends Weapon {
    private float swingProgress;
    private boolean isSwinging;
    private PImage maceImg;

    public Mace() {
        super("Mace", 30, 3, 40);
        maceImg = loadImage("MACE.PNG");
    }

    @Override
    public void mainAttack(Sal s, Direction dir) {
        if (!isSwinging && canUse()) {
            isSwinging = true;
            swingProgress = 0;
            onCooldown = true;
            s.blood -= bloodCost;
            
            float launchX = dir == Direction.RIGHT ? 15 : -15;
            s.xSpeed = launchX;
            s.ySpeed = -10;
        }
    }

    @Override
    public void bloodwield(Sal s, Direction dir) {
        if (s.inAir) {
            s.ySpeed = 20;
        }
    }

    public void update() {
        if (isSwinging) {
            swingProgress += 0.1f;
            if (swingProgress >= 1.0f) {
                isSwinging = false;
            }
        }
    }

    public void display(float x, float y) {
        if (maceImg != null && isSwinging) {
            float angle = lerp(-PI/4, PI/2, swingProgress);
            pushMatrix();
            translate(x, y);
            rotate(angle);
            imageMode(CENTER);
            image(maceImg, 0, 0, 100, 100);
            popMatrix();
        }
    }
}
