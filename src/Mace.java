import processing.core.*;
import java.util.ArrayList;

public class Mace extends Weapon {
    private PImage maceImage;
    private float swingAngle;
    private float swingProgress;
    private boolean isSwinging;
    private boolean isReturning;
    private float launchPower;
    private ArrayList<PVector> hitPoints;
    private float hitRadius;
    private int swingDirection;

    public Mace(PApplet parent) {
        super("Mace", 30, 90, 40);
        this.maceImage = parent.loadImage("mace.png");
        this.maceImage.resize(100, 100);
        this.swingAngle = -PI/4;
        this.swingProgress = 0;
        this.isSwinging = false;
        this.isReturning = false;
        this.launchPower = 15;
        this.hitPoints = new ArrayList<PVector>();
        this.hitRadius = 80;
        this.swingDirection = 1;
    }

    public void update() {
        if (isSwinging) {
            swingProgress += 0.1;
            
            if (!isReturning) {
                swingAngle = lerp(-PI/4, PI/2, swingProgress);
                if (swingProgress >= 1.0) {
                    isReturning = true;
                    swingProgress = 0;
                }
            } else {
                swingAngle = lerp(PI/2, PI/4, swingProgress);
                if (swingProgress >= 1.0) {
                    isSwinging = false;
                    isReturning = false;
                }
            }
            
            updateHitPoints();
        }
        
        if (currentCooldown > 0) {
            currentCooldown--;
        }
    }

    private void updateHitPoints() {
        hitPoints.clear();
        float x = cos(swingAngle) * hitRadius;
        float y = sin(swingAngle) * hitRadius;
        hitPoints.add(new PVector(x, y));
    }

    public void startAttack(float x, float y, String direction) {
        if (currentCooldown <= 0 && !isSwinging) {
            isSwinging = true;
            isReturning = false;
            swingProgress = 0;
            currentCooldown = cooldown;
            swingDirection = direction.equals("right") ? 1 : -1;
        }
    }

    public void render(float x, float y) {
        if (maceImage != null && isSwinging) {
            pushMatrix();
            translate(x, y);
            rotate(swingAngle * swingDirection);
            imageMode(CENTER);
            image(maceImage, 0, 0);
            popMatrix();
        }
    }

    public boolean hasLaunchEffect() {
        return isSwinging && swingProgress < 0.3 && !isReturning;
    }

    public PVector getLaunchVector() {
        return new PVector(
            launchPower * swingDirection, 
            -launchPower * 0.7f
        );
    }

    public ArrayList<PVector> getHitPoints() {
        return new ArrayList<PVector>(hitPoints);
    }

    public float getHitRadius() {
        return hitRadius;
    }

    public boolean isAttacking() {
        return isSwinging;
    }
}