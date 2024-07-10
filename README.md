# dinoGame

A JavaFX implementation of the popular Chrome Dinosaur game.

## Description

dinoGame is a recreation of the classic Chrome Dinosaur game using JavaFX. Players control a dinosaur character, jumping over obstacles like cacti and birds while the game's difficulty increases over time.

## Features

- Dinosaur character with jumping ability
- Randomly generated obstacles (cacti and birds)
- Increasing difficulty levels
- Score tracking
- Responsive controls

## Technologies Used

- Java
- JavaFX
- Object-Oriented Programming principles

## Project Structure

- `src/MyGame.java`: Main game logic and JavaFX application
- `src/Dino.java`: Dinosaur character class
- `src/Cactus.java`: Cactus obstacle class
- `src/Bird.java`: Bird obstacle class
- `src/GameObject.java`: Base class for game objects
- `res/`: Directory containing game assets (images)

## How to Run

1. Ensure you have Java and JavaFX installed on your system.
2. Clone this repository:
```
git clone https://github.com/yourusername/dinoGame.git
```
3. Navigate to the project directory:
```
cd dinoGame
```
4. Compile and run the `MyGame.java` file:
```
javac src/*.java java -cp src MyGame
```

## Controls

- **Up Arrow**: Jump
- **Left Arrow**: Move left
- **Right Arrow**: Move right

## Game Mechanics

- The game starts with the dinosaur on a platform.
- Obstacles (cacti and birds) appear randomly from the right side of the screen.
- The player must jump over obstacles to avoid collision.
- The game speed and difficulty increase as the player's score goes up.
- The game ends when the dinosaur collides with an obstacle.

## Future Enhancements

- Add sound effects and background music
- Implement high score tracking
- Create power-ups and special abilities
- Add different themes or skins for the dinosaur and obstacles
