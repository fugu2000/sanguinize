public class Sal
{
  private float x, y, xSpeed, ySpeed;
  private int sWidth, sHeight, blood, totalBlood;
  private String weaponSelected, currentAction;
  private boolean isDashing, inAir, isAttacking, isHit, iFrame, isDead;
  
  // PImage sal;
  Sal()
  {
    x = width / 2;
    y = height / 2;
    sWidth = 100;
    sHeight = 100;
    blood = 0;
    totalBlood = 0;
  }
  
  void display()
  {
    imageMode(CENTER);
  }
  void move(int tempX, int tempY)
  {
    x = tempX;
    y = tempY;
  }
  void dash(){
  }
  void jump(){
  }
  void bloodWield(){
  }
}
