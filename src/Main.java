import gifAnimation.*;
import processing.core.*;
import java.util.*;

public class Main extends PApplet {
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
    
    ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    ArrayList<Enemies> enemies = new ArrayList<Enemies>();
    
    void setup() {
        dashTime = new Timer(500);
        dashTime.start();
        friction = .8f;
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
            updateGame();
            s1.display();
            s1.move();
        }
    }
    
    void updateGame() {
        updateProjectiles();
        updateEnemies();
        updateWeapons();
    }
    
    void updateProjectiles() {
        Iterator<Projectile> it = projectiles.iterator();
        while (it.hasNext()) {
            Projectile p = it.next();
            p.move();
            if (!p.isActive()) {
                it.remove();
            }
        }
    }
    
    void updateEnemies() {
        for (Enemies e : enemies) {
            e.update();
        }
    }
    
    void updateWeapons() {
        if (s1.getCurrentWeapon() != null) {
            s1.getCurrentWeapon().update();
            
            if (s1.getCurrentWeapon() instanceof Scythe) {
                Scythe scythe = (Scythe)s1.getCurrentWeapon();
                scythe.display(s1.getX(), s1.getY());
            }
        }
    }
    
    void mousePressed() {
        if (start) {
            gameRun = true;
            start = false;
        } else if (gameRun) {
            s1.attack();
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
        if (key == '5') {
            s1.switchWeapon(4);
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
        private Weapon[] weaponSlots = new Weapon[5];
        private float x, y;
        private float xSpeed, ySpeed;
        private int health = 100;
        private int blood = 0;
        private int radius = 20;
        private boolean inAir = false;
        private String facing = "right";
        
        public Sal() {
            x = width / 2;
            y = height / 2;
            weaponSlots[0] = new Mace(this);
            weaponSlots[1] = new Scythe(Main.this);
            weaponSlots[2] = new Spear(this);
            weaponSlots[3] = new Gauntlets(this);
            weaponSlots[4] = new Spikes(this);
            currentWeapon = weaponSlots[0];
        }
        
        public void update() {
            x += xSpeed;
            y += ySpeed;
            
            if (y < ground) {
                ySpeed += gravity;
                inAir = true;
            } else {
                y = ground;
                ySpeed = 0;
                inAir = false;
            }
            
            xSpeed *= friction;
            x = constrain(x, radius, width - radius);
        }
        
        public void display() {
            imageMode(CENTER);
            if (facing.equals("left")) {
                sIdleLeft.play();
                image(sIdleLeft, x, y);
            } else {
                sIdleRight.play();
                image(sIdleRight, x, y);
            }
        }
        
        public void walk(int speed) {
            xSpeed += speed;
            facing = speed > 0 ? "right" : "left";
        }
        
        public void jump() {
            if (!inAir) {
                ySpeed -= 15;
                inAir = true;
            }
        }
        
        public void dash() {
            if (dashTime.isFinished()) {
                xSpeed += facing.equals("right") ? 20 : -20;
                dashTime.start();
            }
        }
        
        public void attack() {
            if (currentWeapon != null) {
                currentWeapon.startAttack(x, y, facing);
            }
        }
        
        public void switchWeapon(int index) {
            if (index >= 0 && index < weaponSlots.length) {
                currentWeapon = weaponSlots[index];
            }
        }
        
        public Weapon getCurrentWeapon() {
            return currentWeapon;
        }
        
        public float getX() {
            return x;
        }
        
        public float getY() {
            return y;
        }
    }
    
    public static void main(String[] args) {
        PApplet.main("Main");
    }
}
