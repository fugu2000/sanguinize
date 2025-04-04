import gifAnimation.*;

Sal s1;
static public Gif sIdleLeft, sIdleRight;
static public int gravity, wave, waveDifficulty, ground;
static public float friction;
static public char jumpKey, leftKey, rightKey, dashKey;
static private boolean start, gameRun, upgrade, death, jumpKeyHeld, leftKeyHeld, rightKeyHeld;

PImage startText;
PImage startBack;
PImage mainBack;
PFont dos;
public static Timer dashTime;

void setup()
{
  dashTime = new Timer(500);
  dashTime.start();
  friction = .8;
  jumpKey = 'w';
  leftKey = 'a';
  rightKey = 'd';
  dashKey = 'q';
  ground = height - 300;
  s1 = new Sal();
  sIdleLeft = new Gif(this, "guyLeft.gif");
  sIdleRight = new Gif(this, "guyRight.gif");
  startText = loadImage("startText.png");
  startBack = loadImage("startBack.png");
  mainBack = loadImage("Map.png");
  size(1720, 900);
  start = true;
  wave = 1;
  gravity = 5;
  waveDifficulty = 10;
  dos = createFont("PerfectDOSVGA437.ttf", 35);
}
void draw() 
{
  if (start)
  {
    startScreen();
  }
  else if (gameRun)
  {
    controlScheme();
    infoOverlay();
    s1.display();
    s1.move();
    
    }
}
void mousePressed()
{
  if (start)
  {
    gameRun = true;
    start = false;
  }
}
void keyPressed()
{
  if (key == jumpKey)
  {
    jumpKeyHeld = true;
  }
  if (key == leftKey)
  {
    leftKeyHeld = true;
  }
  if (key == rightKey)
  {
    rightKeyHeld = true;
  }
  if (key == dashKey)
  {
    s1.dash();
  }
}
void keyReleased()
{
  if (key == jumpKey)
  {
    jumpKeyHeld = false;
  }
  if (key == leftKey)
  {
    leftKeyHeld = false;
  }
  if (key == rightKey)
  {
    rightKeyHeld = false;
  }
}
void controlScheme()
{
  if(jumpKeyHeld)
  {
    s1.jump();
  }
  if(leftKeyHeld)
  {
    s1.walk(-3);
  }
  if(rightKeyHeld)
  {
    s1.walk(3);
  }
}
private static void startWave() {}
private void startScreen()
{
  imageMode(CORNER);
  image(startBack, 0, 0);
  imageMode(CENTER);
  image(startText, width / 2, height / 2);
}

private void infoOverlay()
{
  imageMode(CORNER);
  image(mainBack, 0, 0);
}
private static void upgradeScreen() {}
private static void deathScreen() {}
private static void runMusic() {}
