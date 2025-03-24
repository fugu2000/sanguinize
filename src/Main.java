// Marcus Nishikawa

static private int gravity, wave, waveDifficulty;
static private boolean start, gameRun, upgrade, death;
PImage startImage;

private static void startWave() {}
void setup()
{
  startImage = loadImage("Sang.jpg");
  size(1800, 600);
  start = true;
}
void draw() 
{
  if (start)
  {
    startScreen();
  }
}
private void startScreen()
{
  image(startImage, 0, 0);
}
private static void infoOverlay() {}
private static void upgradeScreen() {}
private static void deathScreen() {}
private static void runMusic() {}
