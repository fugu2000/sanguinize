
static public int gravity, wave, waveDifficulty;
static private boolean start, gameRun, upgrade, death;
PImage startText;
PImage startBack;
PImage mainBack;
PFont dos;

void setup()
{
  startText = loadImage("startText.png");
  startBack = loadImage("startBack.png");
  mainBack = loadImage("Map.png");
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
    infoOverlay();
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
