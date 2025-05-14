import gifAnimation.*;
public class Chud extends enemies

{
  Gif c1;
  final Timer chudCooldown = new Timer(800);
  final Timer chudRampup = new Timer(400);
  final Timer chudDamaging = new Timer(400);
  float aDirection;
  Chud(float x)
  {
    hp = 1;
    damage = 25;
    isAttacking = false;
    walking = true;
    this.x = x;
    y = height / 2;
    eWidth = (int)(Math.random() * 10) + 45;
    eHeight = (int)(Math.random() * 10) + 45;
    name = "Chud";
    walkSpeed = (float)(Math.random() * 1.0) + 1.5;
    enemyCount++;
    waveReq = 1;
  }
  void display()
  {
    imageMode(CENTER);
    
    cRun.play();
    tint(0);
    if (direction == 1 && walking)
    {
      noTint();
      image(cRun, x, y, eWidth, eHeight);
    } else if (direction == -1 && walking)
    {
      pushMatrix();
      translate(x, y);
      scale(-1, 1);
      noTint();
      image(cRun, 0, 0, eWidth, eHeight);
      popMatrix();
    } else if (aDirection == 1)
    {
      cFall.play();
      noTint();
      image(cFall, x, y, eWidth, eHeight);
    } else if (aDirection == -1)
    {
      cFall.play();
      pushMatrix();
      translate(x, y);
      scale(-1, 1);
      noTint();
      image(cFall, 0, 0, eWidth, eHeight);
      popMatrix();
    }
  }
  void move(ArrayList<Chud> others)
  {
    if (isAttacking) walking = false;
    fall();
    if (walking) walk();
    x += xSpeed * timeScale;
    y += ySpeed * timeScale;
    attack();
    xSpeed *= friction * timeScale;
    ySpeed *= friction * timeScale;
    borderCheck();
    avoidClumping(others);
  }
  public void playerCheck()
  {
    if (abs(s1.x - x) < 150 && !isAttacking)
    {
      startAttack();
    }
    if (abs(s1.x - x) < 50 && damaging)
    {
      damaging = false;
      s1.damage(damage);
    }
  }
  private void walk()
  {

    direction = ((s1.x - x) / abs(s1.x - x));
    xSpeed += walkSpeed * direction;
  }
  private void fall()
  {
    if (inAir)
    {
      ySpeed += gravity * timeScale;
    }
  }
  private void borderCheck()
  {
    if (x < eWidth)
    {
      x = 0 + eWidth;
    }
    if (x > width - eWidth)
    {
      x = width - eWidth;
    }
    if (y >= ground)
    {
      inAir = false;
      y = ground;
      if (ySpeed > 0)
      {
        ySpeed = 0;
      }
    } else
    {
      inAir = true;
    }
  }
  public void startAttack()
  {
    if (!isAttacking && chudCooldown.isFinished())
    {
      cFall.jump(0);
      isAttacking = true;
      walking = false;
      println("starting attack");
      println("stopped walking");
      aDirection = ((s1.x - x) / abs(s1.x - x));
      chudRampup.start();
    }
  }
  public void attack()
  {
    if (chudRampup.isFinished() && isAttacking)
    {
      damaging = true;
      println("attacking");
      xSpeed = 50 * aDirection;
      isAttacking = false;
      chudDamaging.start();
      ;
      chudCooldown.start();
      walking = false;
    }
  }
  public void cooldown()
  {
    if (chudDamaging.isFinished())
    {
      damaging = false;
    }
    if (chudCooldown.isFinished())
    {
      walking = true;
    }
  }
  public void damage(int damage)
  {
    hp -= damage;
    s1.blood += damage * 50;
  }
  void avoidClumping(ArrayList<Chud> others) {
  for (Chud other : others) {
    if (other != this) {
      float dx = x - other.x;
      float dy = y - other.y;
      float distance = dist(x, y, other.x, other.y);
      float minDist = (eWidth + other.eWidth) / 2;

      if (distance < minDist && distance > 0) {
        float overlap = minDist - distance;
        float pushX = (dx / distance) * (overlap / 2);
        float pushY = (dy / distance) * (overlap / 2);
        x += pushX;
        y += pushY;
        other.x -= pushX;
        other.y -= pushY;
      }
    }
  }
}

}
