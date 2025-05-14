import gifAnimation.*;
public class Fly extends enemies

{
  Gif f1;
  final Timer flyCooldown = new Timer(400);
  final Timer flyRampup = new Timer(200);
  final Timer flyDamaging = new Timer(300);
  float yDirection;
  float xDirection;
  float yLoc;
  float mainWalkSpeed;
  Fly(float x)
  {
    hp = 1;
    damage = 10;
    isAttacking = false;
    walking = true;
    this.x = x;
    y = height / 2;
    eWidth = (int)(Math.random() * 10) + 45;
    eHeight = (int)(Math.random() * 10) + 45;
    name = "Fly";
    mainWalkSpeed = (float)(Math.random() * 0.5) + 2;
    enemyCount++;
    waveReq = 1;
    isStunned = false;
    falling = false;
    f1 = fRun;
  }
  void display()
  {
    imageMode(CENTER);

    f1.play();
    noTint();
    image(f1, x, y, eWidth, eHeight);
  }
  void move()
  {
      if (isAttacking) walking = false;
      if (walking) walk();
      x += xSpeed * timeScale;
      y += ySpeed * timeScale;
      attack();
      xSpeed *= friction * timeScale;
      ySpeed *= friction * timeScale;
      borderCheck();
      y -= sin(radians(frameCount * 5)) * 0.5 * timeScale;

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
    yLoc = (float)(Math.random() * 100 + 450);
    if(y > yLoc)
    {
      ySpeed -= 2;
      walkSpeed = mainWalkSpeed * .5;
      //ySpeed -= cos(atan2(height / 2 - y, s1.x - x))* 2;
    }
    else
    {
      walkSpeed = mainWalkSpeed;
      ySpeed += 0.5;
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
    if (!isAttacking && flyCooldown.isFinished())
    {
      isAttacking = true;
      walking = false;
      println("starting attack");
      println("stopped walking");
      float angle = atan2(s1.y-y, s1.x-x);
      xDirection = cos(angle) * 32;
      yDirection = sin(angle) * 32;
      flyRampup.start();
    }
  }
  public void attack()
  {
    if (flyRampup.isFinished() && isAttacking)
    {
      damaging = true;
      println("attacking");

      isAttacking = false;
      flyDamaging.start();
      xSpeed += xDirection;
      ySpeed += yDirection;
      flyCooldown.start();
      walking = false;
    }
  }
  public void cooldown()
  {
    if (flyDamaging.isFinished())
    {
      damaging = false;
    }
    if (flyCooldown.isFinished())
    {
      walking = true;
      ySpeed = 0;
    }
  }
  public void damage(int damage)
  {
    hp -= damage;
    s1.blood += damage * 50;
  }
}
