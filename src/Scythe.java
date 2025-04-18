public class Scythe extends Weapons {
  PImage scytheImg;
  private float x, y;
  private boolean isAttacking = false;
  private boolean isReturning = false;
  private int attackStartFrame = 0;
  private int cooldownStartFrame = -200;
  private float attackDuration = 60;
  private float cooldownDuration = 120;
  private float scytheAngle = 0;
  private float startAngle = -PI / 4;
  private float endAngle = PI / 4;

  Scythe(float x, float y) {
    name = "Scythe";
    damage = 25;
    cooldown = 2;
    bloodCost = 50;
    this.x = x;
    this.y = y;
    scytheImg = loadImage("SCYTHE.PNG");
  }

  public void attack() {
    if (!isAttacking && frameCount - cooldownStartFrame >= cooldownDuration) {
      isAttacking = true;
      isReturning = false;
      attackStartFrame = frameCount;
      cooldownStartFrame = frameCount;
      Player.health -= bloodCost;
    }
  }

  public void update() {
    if (isAttacking) {
      int elapsed = frameCount - attackStartFrame;
      float progress = (float)elapsed / attackDuration;
      if (!isReturning) {
        if (progress <= 1.0) {
          scytheAngle = lerp(startAngle, endAngle, progress);
        } else {
          isReturning = true;
          attackStartFrame = frameCount;
        }
      } else {
        float returnProgress = (float)(frameCount - attackStartFrame) / attackDuration;
        if (returnProgress <= 1.0) {
          scytheAngle = lerp(endAngle, startAngle, returnProgress);
        } else {
          isAttacking = false;
          scytheAngle = 0;
        }
      }
    }
  }

  public void display() {
    if (scytheImg != null) {
      pushMatrix();
      translate(x, y);
      rotate(scytheAngle);
      imageMode(CENTER);
      image(scytheImg, 0, 0, 120, 120);
      popMatrix();
    }
  }
}

