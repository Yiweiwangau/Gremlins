# Wizard in Dungeon

An exciting game built using the Processing framework. The game is based on the concept of a player navigating through different levels by defeating or avoiding gremlins.
![image](https://github.com/yiweiwang-yw/Wizard-in-Dungeon/assets/81563224/61045569-4ee7-45ac-9752-94352d04f225)


## Design

The game has been structured using the following design elements:

1. **Classes**:
    - Main classes: `App`, `BrokenWall`, `Door`, `Fireball`, `Gremlin`, `MoveObjectBase`, `Player`, `Portal`, `PowerUp`, and `Slime`.
    - Extension classes: `BulletBase` and `MoveObjectBase`.

2. **Inheritance**:
    - The `Player` and `Gremlin` classes extend `MoveObjectBase`. 
    - `Fireball` and `Slime` extend the abstract `BulletBase` class.

3. **Functionalities**:
    - `Player`: Moves according to key inputs.
    - `Gremlin`: Detects possible directions and chooses paths.
    - `Fireball`: Attacks slime and gremlin.
    - `Slime`: Only attacks the player.

4. **App Class**:
    - This class binds everything together, extending the `PApplet` which provides the core functionalities of processing.

### Powerup Implementation

- **Icon**: Represented by a star icon. ![image](https://github.com/yiweiwang-yw/Wizard-in-Dungeon/assets/81563224/ddfb1201-001e-4402-bef5-e15b68208190)
- **Functionality**: Boosts player's speed twofold upon collection.
- **Duration**: Lasts for 10 seconds with a cooldown of another 10 seconds. Settings can be adjusted in the `generateLayout()` function in `App.java`.

### Portal Implementation

- Adds an interesting element to the game where a player can step onto a portal and get teleported to a random tile on another random portal in the direction without a wall. Four portals are included in the game.
  ![image](https://github.com/yiweiwang-yw/Wizard-in-Dungeon/assets/81563224/6c96f136-12f0-4bbb-94f6-3d38e8ade36b)


## Test Coverage

- Instruction coverage: **99%**
- Branch coverage: **91%**

## Tech Stack

- **Processing**: A flexible software sketchbook and a language for learning how to code within the context of the visual arts.

- **Java**: The main programming language used for the project. Utilized for its OOP capabilities, robustness, and extensive libraries.

- **JUnit**: A framework to write repeatable tests in Java.


## Setup

1. Clone the repository.
2. Ensure you have the required libraries installed.
3. Compile and run the `App.java` file to start the game.

Enjoy the game and feel free to contribute!
