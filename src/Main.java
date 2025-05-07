import gifAnimation.*;

Sal s1;
Mace s;
ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
ArrayList<Chud> chuds = new ArrayList<Chud>();
ArrayList<Fly> flies = new ArrayList<Fly>();

public float timeScale = 1.0;
static public final int chudDifficulty = 1;
static public final int flyDifficulty = 1;
static public int enemyTypes;
static public Gif sIdleLeft, sIdleRight, cRun, fRun;
static public int gravity, wave, waveDifficulty, ground, enemyCount;
static public float friction;
static public char jumpKey, leftKey, rightKey, dashKey;
static private boolean start, gameRun, death, jumpKeyHeld, leftKeyHeld, rightKeyHeld, debug;
static public boolean upgrade;
PImage bloodBar;
PImage startText;
PImage startBack;
PImage mainBack;
PFont dos;
PImage dead;
public PImage maceSwing;
public static Timer dashTime, startTime, slowMo;

void setup()
{
  upgrade = false;
  enemyTypes = 2;
  dashTime = new Timer(500);
  startTime = new Timer(500);
  slowMo = new Timer(100);
  dashTime.start();
  friction = .8;
  jumpKey = 'w';
  leftKey = 'a';
  rightKey = 'd';
  dashKey = 'q';
  size(1720, 900);
  ground = height - 300;
  s1 = new Sal();
  s = new Mace();
  maceSwing = loadImage("Mace .png");
  cRun = new Gif(this, "Chud.gif");
  fRun = new Gif(this, "mosquito gif.gif");
  sIdleLeft = new Gif(this, "guyLeft.gif");
  sIdleRight = new Gif(this, "guyRight.gif");
  startText = loadImage("startText.png");
  startBack = loadImage("startBack.png");
  mainBack = loadImage("Map.png");
  bloodBar = loadImage("bloodBar.png");
  dead = loadImage("death.png");
  start = true;
  wave = 1;
  gravity = 5;
  waveDifficulty = 10;
  dos = createFont("PerfectDOSVGA437.ttf", 35);
  death = false;
}
void draw()
{
  noTint();
  if (start)
  {
    startScreen();
  } else if (gameRun && !death)
  {
    controlScheme();
    infoOverlay();
    projectileDisplay();
    s1.display();
    s1.move();
    s.display(s1);
    chudDisplay();
    flyDisplay();
    timeScale = 1.0;
    imageMode(CENTER);
    if (waveDifficulty == 0)
    {
      startWave();
    }
    if (upgrade)
    {
      upgradeScreen();
    }
    if (debug)
    {
      debug();
    }
  }
  else
  {
    deathScreen();
  }
}
void mousePressed()
{
  println("MousePress");
  if (start)
  {
    gameRun = true;
    start = false;
    startWave();
    s1.blood = 190;
    
  } else if (gameRun)
  {
    s.mainAttack(s1);
    projectiles.add(new Projectile("spear", mouseX, mouseY, 10, 10, degrees(180), 40));
  } else if (death)
  {
    start = true;
    death = false;
    startWave();
    s1.blood = 190;
    wave = 1;
  }
}
void keyPressed()
{
  if (key == jumpKey || key == ' ')
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
  if (key == '0')
  {
    debug = !debug;
  }
  if (key == '.')
  {
    upgrade = !upgrade;
  }
}
void keyReleased()
{
  if (key == jumpKey || key == ' ')
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
private void projectileDisplay()
{
  for (int i = 0; i < projectiles.size(); i++)
  {
    Projectile p = projectiles.get(i);
    //p.display();
    p.move();
  }
}
private void chudDisplay()
{
  for (int i = 0; i < chuds.size(); i++)
  {
    Chud c = chuds.get(i);
    c.display();
    c.move();
    c.cooldown();
    c.playerCheck();
    if (c.hp < 1)
    {
      chuds.remove(c);
      waveDifficulty -= 1;
    }
  }
}
private void flyDisplay()
{
  for (int i = 0; i < flies.size(); i++)
  {
    Fly f = flies.get(i);
    f.display();
    f.move();
    f.cooldown();
    f.playerCheck();
    if (f.hp < 1)
    {
      flies.remove(f);
      waveDifficulty -= 1;
    }
  }
}
void controlScheme()
{
  if (jumpKeyHeld)
  {
    s1.jump();
  }
  if (leftKeyHeld)
  {
    s1.walk(-3);
  }
  if (rightKeyHeld)
  {
    s1.walk(3);
  }
}
private void startWave()
{
  wave ++;
  waveDifficulty = (int)Math.pow(2, wave);
  for (int i = 0; i < waveDifficulty; )
  {
    int enemySelect = (int)(Math.random() * enemyTypes);
    if (enemySelect == 0)
    {

      if (!(i + chudDifficulty > waveDifficulty))
      {
        spawn("Chud");
        i += chudDifficulty;
      }
    } else if (enemySelect == 1)
    {

      if (!(i + flyDifficulty > waveDifficulty))
      {
        spawn("Fly");
        i += flyDifficulty;
      }
    }
  }
}
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
  fill(100, 50);
  rect(width / 2 - 390, 30, 775, 15);
  fill(255, 0, 0, 100);
  rectMode(CORNER);
  rect(width / 2 - 390, 30, s1.blood * (750f / s1.maxBlood), 15);
  imageMode(CENTER);
  image(bloodBar, width / 2, 35);
}
private void upgradeScreen()
{
  timeScale = 0.05;
  if(slowMo.isFinished()) slowMo.start();
  timeScale = lerp(timeScale, 1.0, 0.05);
  if(slowMo.isFinished()) upgrade = false;
  //rectMode(CORNER);
  //fill(100, 50);
  //rect(0, 0, width, height);
  //rectMode(CENTER);
  //rect(width / 6.0, height / 2, 500, height / 1.25);
  //rect(width / 2, height / 2, 500, height / 1.25);
  //rect(width * (5.0/6.0), height / 2, 500, height / 1.25);
}
private void deathScreen() 
{
  imageMode(CORNER);
  image(dead, 0, 0);
  
}
private static void runMusic() 
{
  
}
private void debug()
{
  imageMode(CORNER);
  text("ENEMY COUNT: " + enemyCount, 20, 20);
  text("WAVE: " + wave, 20, 40);
  text("WAVE DIFFICULTY: " + waveDifficulty, 20, 60);
}
private void spawn(String name)
{
  float side = ((float)(Math.random() * 1) - 0.5) * 1000000;
  if (name.equals("Chud"))
  {
    println("spawning chud");
    chuds.add(new Chud(side * 2 * width));
  }
  if (name.equals("Fly"))
  {
    flies.add(new Fly(side * 2 * width));
    println("spawning fly");
  }
}
