public class Mace extends Weapon {
    float angle = 0; // Track rotation angle
    boolean isSwinging = false; // Track if the mace is swinging

    Mace() {
        name = "Mace";
        damage = 10;  // Example damage value
        cooldown = 500;
        available = true;
        onCooldown = false;
    }

    final Timer mCooldown = new Timer(cooldown);
    final Timer attackDuration = new Timer(200);  // Timer for the duration of the attack animation

    public void mainAttack(Sal s) {
        // Check if the mace is on cooldown
        if (onCooldown) {
            if (mCooldown.isFinished()) {
                onCooldown = false;
                mCooldown.start();
            } else {
                return; // Still cooling down
            }
        }

        // Start cooldown
        onCooldown = true;
        mCooldown.start();

        // Trigger the attack animation (start the timer for the mace attack duration)
        s.isAttacking = true;
        attackDuration.start();  // Start the attack duration timer
        isSwinging = true; // Start the swinging animation

        // Reset angle to start a fresh swing on each attack
        if (s.direction.equals("left")) angle = 120;
        else angle = -120;

        // Handle damage and collision (we'll assume the player's hitbox is a simple rectangle)
        if (s.direction.equals("left")) {
            // Check if any enemy is in range
            for (Chud c : chuds) {
                if (dist(s.x, s.y, c.x, c.y) < 50) {  // Example distance for hit detection
                    c.damage(damage);  // Deal damage to the enemy
                    // Apply force to the player (Sal) when the attack hits an enemy
                    s.xSpeed -= 10; // Adjust xSpeed
                    s.ySpeed -= 30; // Apply upward force to launch the player
                }
            }
            for (Fly f : flies) {
                if (dist(s.x, s.y, f.x, f.y) < 50) {  // Example distance for hit detection
                    f.damage(damage);  // Deal damage to the enemy
                    // Apply force to the player (Sal) when the attack hits an enemy
                    s.xSpeed -= 5; // Adjust xSpeed
                    s.ySpeed -= 30; // Apply upward force to launch the player
                }
            }
        } else {
            // Right-facing hitbox logic
            for (Chud c : chuds) {
                if (dist(s.x, s.y, c.x, c.y) < 50) {
                    c.damage(damage);
                    // Apply force to the player (Sal) when the attack hits an enemy
                    s.xSpeed += 5; // Adjust xSpeed
                    s.ySpeed -= 30; // Apply upward force to launch the player
                }
            }
            for (Fly f : flies) {
                if (dist(s.x, s.y, f.x, f.y) < 50) {
                    f.damage(damage);
                    // Apply force to the player (Sal) when the attack hits an enemy
                    s.xSpeed += 5; // Adjust xSpeed
                    s.ySpeed -= 30; // Apply upward force to launch the player
                }
            }
        }
    }

    // Display the mace swing when the player is attacking
    public void display(Sal s) {
        if (s.isAttacking) {
            imageMode(CENTER);

            // Rotate the mace as it swings (clockwise or counterclockwise based on direction)
            if (isSwinging) {
                if (s.direction.equals("left")) {
                    // Counterclockwise rotation when facing left (negative angle)
                    angle -= 20; // Adjust rotation speed here (negative for counterclockwise)
                } else {
                    // Clockwise rotation when facing right (positive angle)
                    angle += 20; // Adjust rotation speed here (positive for clockwise)
                }

                // Stop the swinging after a full rotation (360 degrees)
                if (Math.abs(angle) >= 240) {
                    angle = 120;
                    isSwinging = false;
                }
            }

            // Depending on the player's direction, adjust the position of the mace
            if (s.direction.equals("left")) {
                // Position for left-facing direction (handle stays near player)
                pushMatrix();
                translate(s.x - 10, s.y);  // Adjust for the handle's position near the player
                rotate(radians(angle));   // Rotate around the base of the handle
                image(maceSwing, 0, -30);  // Draw mace head; Adjust the -30 to fine-tune position
                popMatrix();
            } else {
                // Position for right-facing direction (handle stays near player)
                pushMatrix();
                translate(s.x + 10, s.y);  // Adjust for the handle's position near the player
                rotate(radians(angle));   // Rotate around the base of the handle
                image(maceSwing, 0, -30);  // Draw mace head; Adjust the -30 to fine-tune position
                popMatrix();
            }
        }

        // If the attack duration is over, stop the animation and reset the angle
        if (attackDuration.isFinished()) {
            s.isAttacking = false;  // Reset the attacking state after animation
            angle = 0;  // Reset the angle after each attack
        }
    }
}
