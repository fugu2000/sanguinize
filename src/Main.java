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

void setup() {
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

void draw() {
  if (start) {
    startScreen();
  } else if (gameRun) {
    controlScheme();
    infoOverlay();
    s1.display();
    s1.move();
  }
}

void mousePressed() {
  if (start) {
    gameRun = true;
    start = false;
  }
}

void keyPressed() {
  if (key == '1') {
    s1.switchWeapon(0);
  }
  if (key == '2') {
    s1.switchWeapon(1);
  }
  if (key == '3') {
    s1.switchWeapon(2);
  }
  if (key == '4') {
    s1.switchWeapon(3);
  }

  if (key == jumpKey) {
    jumpKeyHeld = true;
  }
  if (key == leftKey) {
    leftKeyHeld = true;
  }
  if (key == rightKey) {
    rightKeyHeld = true;
  }
  if (key == dashKey) {
    s1.dash();
  }
}

void keyReleased() {
  if (key == jumpKey) {
    jumpKeyHeld = false;
  }
  if (key == leftKey) {
    leftKeyHeld = false;
  }
  if (key == rightKey) {
    rightKeyHeld = false;
  }
}

void controlScheme() {
  if (jumpKeyHeld) {
    s1.jump();
  }
  if (leftKeyHeld) {
    s1.walk(-3);
  }
  if (rightKeyHeld) {
    s1.walk(3);
  }
}

private static void startWave() {}

private void startScreen() {
  imageMode(CORNER);
  image(startBack, 0, 0);
  imageMode(CENTER);
  image(startText, width / 2, height / 2);
}

private void infoOverlay() {
  imageMode(CORNER);
  image(mainBack, 0, 0);
}

private static void upgradeScreen() {}

private static void deathScreen() {}

private static void runMusic() {}

class Sal {
  private Weapon currentWeapon;
  private Weapon[] weaponSlots = new Weapon[4];

  public Sal() {
    weaponSlots[0] = new Mace();
    weaponSlots[1] = new Spear();
    weaponSlots[2] = new Gauntlets();
    weaponSlots[3] = new Spikes();
    currentWeapon = weaponSlots[0];
  }

  public void switchWeapon(int index) {
    if (index >= 0 && index < weaponSlots.length) {
      currentWeapon = weaponSlots[index];
      System.out.println("Switched to: " + currentWeapon.getName());
    }
  }

  public void useWeapon(Direction dir) {
    if (currentWeapon != null && currentWeapon.canUse()) {
      currentWeapon.mainAttack(this, dir);
    }
  }

  public Weapon getCurrentWeapon() {
    return currentWeapon;
  }

  public void display() {}

  public void move() {}
}

abstract class Weapon {
  private String name;

  public Weapon(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public abstract void mainAttack(Sal s, Direction dir);

  public abstract void bloodwield(Sal s, Direction dir);

  public boolean canUse() {
    return true;
  }
}

class Mace extends Weapon {
  public Mace() {
    super("Mace");
  }

  @Override
  public void mainAttack(Sal s, Direction dir) {
    System.out.println("Swinging Mace!");
  }

  @Override
  public void bloodwield(Sal s, Direction dir) {
    System.out.println("Bloodwield Mace!");
  }
}

class Spear extends Weapon {
  public Spear() {
    super("Spear");
  }

  @Override
  public void mainAttack(Sal s, Direction dir) {
    System.out.println("Thrusting Spear!");
  }

  @Override
  public void bloodwield(Sal s, Direction dir) {
    System.out.println("Bloodwield Spear!");
  }
}

class Gauntlets extends Weapon {
  public Gauntlets() {
    super("Gauntlets");
  }

  @Override
  public void mainAttack(Sal s, Direction dir) {
    System.out.println("Punching with Gauntlets!");
  }

  @Override
  public void bloodwield(Sal s, Direction dir) {
    System.out.println("Bloodwield Gauntlets!");
  }
}

class Spikes extends Weapon {
  public Spikes() {
    super("Spikes");
  }

  @Override
  public void mainAttack(Sal s, Direction dir) {
    System.out.println("Firing Spikes!");
  }

  @Override
  public void bloodwield(Sal s, Direction dir) {
    System.out.println("Bloodwield Spikes!");
  }
}

enum Direction {
  LEFT, RIGHT;
}
