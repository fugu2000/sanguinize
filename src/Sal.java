public class Sal
{
  Gif s1;
  private float x, y, xSpeed, ySpeed, dashSpeed;
  private int sWidth, sHeight, blood, totalBlood, jumpHeight;
  private String weaponSelected, currentAction;
  private boolean isDashing, inAir, isAttacking, isHit, iFrame, isDead;
  private String direction = "left";
  Sal()
  {
    x = width / 2;
    y = height / 2;
    jumpHeight = 20;
    sWidth = 18;
    sHeight = 100;
    blood = 0;
    totalBlood = 0;
    dashSpeed = 30;
  }
  
  void display()
  {
    imageMode(CENTER);
    if(direction.equals("left"))
    {
      sIdleLeft.play();
      image(sIdleLeft, x, y);
    }
    else
    {
      sIdleRight.play();
      image(sIdleRight, x, y);
    }
    if (y >= ground)
    {
      inAir = false;
      y = ground;
      if(ySpeed > 0)
      {
        ySpeed = 0;
      }
    }
    else
    {
      inAir = true;
    }
  }
  void move()
  {
    x += xSpeed;
    y += ySpeed;
    if(inAir)
    {
      ySpeed += gravity;
    }
    xSpeed *= friction;
    ySpeed *= friction;
    if(x < sWidth)
    {
      x = 0 + sWidth;
    }
    if(x > width - sWidth)
    {
      x = width - sWidth;
    }
  }
  void walk(int tempX)
  {
    xSpeed += tempX;
    if(tempX < 0)
    {
      direction = "left";
    }
    else
    {
      direction = "right";
    }
  }
  void dash()
  {
    if(dashTime.isFinished())
    {
      if(direction == "left")
      {
        xSpeed -= dashSpeed;
        dashTime.start();
      }
      else
      {
        xSpeed += dashSpeed;
        dashTime.start();
      }
    }
  }
  void jump()
  {
    if(!inAir)
    {
      ySpeed -= jumpHeight;
    }
  }
  void bloodWield(){
  }
}
