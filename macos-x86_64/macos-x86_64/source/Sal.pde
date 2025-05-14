public class Sal
{
  Gif s1;
  private float xSpeed, ySpeed, dashSpeed;
  public float x, y, totalBlood;
  private int sWidth, sHeight, jumpHeight, flashes, tempFlashes, level;
  private float bloodDrain;
  public float blood, maxBlood;
  private String weaponSelected, currentAction;
  private boolean isDashing, inAir, isAttacking, isHit, iFrame, isDead, flashing;
  public String direction = "left";
  final private Timer dashFrames = new Timer(100);
  final private Timer damageFrames = new Timer(100);
  final private Timer flashFrames = new Timer(100);
  Sal()
  {
    level = 1;
    flashing = false;
    tempFlashes = 0;
    maxBlood = 200;
    x = width / 2;
    y = height / 2;
    jumpHeight = 20;
    sWidth = 18;
    sHeight = 100;
    blood = 190;
    totalBlood = 0;
    dashSpeed = 30;
    bloodDrain = 0.1;
  }

  void display()
  {
    if (dashFrames.isFinished() && damageFrames.isFinished()) iFrame = false;
    if (flashFrames.isFinished()) flashing = false;
    imageMode(CENTER);
    if (mouseX < x)
    {
      sIdleLeft.play();
      direction = "left";
      if (flashing) tint(255, 0, 0);
      image(sIdleLeft, x, y);
      noTint();
    } else
    {
      direction = "right";
      sIdleRight.play();
      if (flashing) tint(255, 0, 0);
      image(sIdleRight, x, y);
      noTint();
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
    if (blood >= maxBlood)
    {
      upgrade = true;
      level ++;
      bloodDrain = level / 10.0;
      maxBlood = 200 * level;
      blood = maxBlood / 2;
    }
    if (gameRun) blood -= bloodDrain * timeScale;
    if (blood < 0) {
      death = true;
      gameRun = false;
    }
  }
  void move()
  {
    x += xSpeed * timeScale;
    y += ySpeed * (timeScale);
    if (inAir)
    {
      ySpeed += gravity * timeScale;
    }
    xSpeed *= pow(friction, timeScale);
    ySpeed *= pow(friction, timeScale);
    if (x < sWidth)
    {
      x = 0 + sWidth;
    }
    if (x > width - sWidth)
    {
      x = width - sWidth;
    }
  }
  void walk(int tempX)
  {
    xSpeed += tempX * timeScale;
    if (tempX < 0)
    {
      direction = "left";
    } else
    {
      direction = "right";
    }
  }
  void dash()
  {
    if (dashTime.isFinished())
    {
      if (direction == "left")
      {
        xSpeed -= dashSpeed;
        iFrame = true;
        dashFrames.start();
        dashTime.start();
      } else
      {
        xSpeed += dashSpeed;
        iFrame = true;
        dashFrames.start();
        dashTime.start();
      }
    }
  }
  void jump()
  {
    if (!inAir)
    {
      ySpeed -= jumpHeight;
    }
  }
  void bloodWield() {
  }
  void damage(int damage)
  {
    if (!iFrame)
    {
      blood -= damage;
      damageFrames.start();
      iFrame = true;
      flash();
    }
  }
  void flash()
  {
    flashing = true;
    flashFrames.start();
  }
}
