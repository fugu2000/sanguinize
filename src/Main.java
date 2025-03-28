public class Sal
{
  Gif s1;
  private float x, y, xSpeed, ySpeed;
  private int sWidth, sHeight, blood, totalBlood, jumpHeight;
  private String weaponSelected, currentAction;
  private boolean isDashing, inAir, isAttacking, isHit, iFrame, isDead;
  
  // PImage sal;
  Sal()
  {
    x = width / 2;
    y = height / 2;
    jumpHeight = 20;
    sWidth = 100;
    sHeight = 100;
    blood = 0;
    totalBlood = 0;
    
  }
  
  void display()
  {
    imageMode(CENTER);
    sIdle.play();
    image(sIdle, x, y);
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
  }
  void walk(int tempX)
  {
    xSpeed += tempX;
  }
  void dash(){
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
