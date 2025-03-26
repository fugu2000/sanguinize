# Sanguinize

OOP Arcade arena hack and slash

You rely on blood to live!! Use it for moves and feeding your weapon!
The more blood you collect, the higher your final score!
Play as Sal, a stranded vampire who needs to send some blood back to the family to redeem himself
Fight growing waves of townsmen to feed their unending hunger

## Concept art
### Start screen

![Art](https://github.com/fugu2000/sanguinize/blob/main/assets/Sang.jpg?raw=true)

### Game Over screen

![Art](https://github.com/fugu2000/sanguinize/blob/main/assets/Gameover.jpg?raw=true)

### Weapon concepts

![Art](https://github.com/fugu2000/sanguinize/blob/main/assets/Weapons.png?raw=true)

### Sal

![Art](https://github.com/fugu2000/sanguinize/blob/main/assets/Sal.png?raw=true)

### Game

![Art](https://github.com/fugu2000/sanguinize/blob/main/assets/Game.png?raw=true)

### Enemies

![Art](https://github.com/fugu2000/sanguinize/blob/main/assets/Enemies.png?raw=true)

### Start screen pt.2 

![Art](https://github.com/fugu2000/sanguinize/blob/main/assets/SIGMA.xcf?raw=true)

### Diagram

![Art](https://github.com/fugu2000/sanguinize/blob/main/assets/Diagram.png?raw=true)



Timer:
// Example 10-5: Object-oriented timer
// By Daniel Shiffman

class Timer {

  int savedTime; // When Timer started
  int totalTime; // How long Timer should last

  Timer(int tempTotalTime) {
    totalTime = tempTotalTime;
  }

  // Starting the timer
  void start() {
    // When the timer starts it stores the current time in milliseconds.
    savedTime = millis();
  }

  // The function isFinished() returns true if 5,000 ms have passed. 
  // The work of the timer is farmed out to this method.
  boolean isFinished() { 
    // Check how much time has passed
    int passedTime = millis()- savedTime;
    if (passedTime > totalTime) {
      return true;
    } else {
      return false;
    }
  }
}


Director
Marcus Nishikawa
Concept Artist
Frankie Chia
Isaac Cheung
Enemy Design
Frankie Chia
Isaac Cheung
Weapon Design
Marcus Nishikawa
Final Artist
Tiger Yang
Main Programmer
?
Move Programmer
?
Enemy Programmer
?
Engine  and GUI Programming
?

------------------------------------------------------------------------------------
.



###### The other Projects ripository just in case: https://github.com/Yang775923/Cereal-Killers.git


