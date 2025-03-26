
static public int gravity, wave, waveDifficulty;
static private boolean start, gameRun, upgrade, death;
PImage startImage;
PFont dos;

void setup()
{
  startImage = loadImage("Sang.jpg");
  size(1720, 900);
  start = true;
  wave = 1;
  gravity = 2;
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
private static void startWave() {}
private void startScreen()
{
  image(startImage, 0, 0);
}

private void infoOverlay()
{
  //image(backGround, 0, 0);
}
private static void upgradeScreen() {}
private static void deathScreen() {}
private static void runMusic() {}
