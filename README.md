# Astroids FXGL

Asteroids-style learning project built with [FXGL](https://github.com/AlmasB/FXGL).

## Prerequisites

- Java 21 (or compatible JDK)
- JavaFX runtime available on the system or via your IDE / JDK distribution
- Gradle (or use IntelliJ IDEA to import the Gradle project)

## Run

From the `atroidsFXGL` directory:

```bash
./gradlew run
```

(or use the Gradle "run" task from your IDE).

This should open a blank FXGL window titled **"Astroids FXGL"** at 1280x720.

## Next Steps: Building Out the Game

Below is a typical sequence of steps to turn this blank window into a playable Asteroids-style game, with resources to consult for each step.

### 1. Learn FXGL basics and project structure
- **Goal:** Understand how `GameApplication`, `initSettings`, `initGame`, and the FXGL DSL work.
- **What to do:**
  - Read through `MainApp.java` and trace where FXGL calls into your code.
  - Skim a few official FXGL examples to see common patterns.
- **Resources:**
  - FXGL GitHub repository (examples in `samples/`): https://github.com/AlmasB/FXGL
  - FXGL Wiki – Getting Started: https://github.com/AlmasB/FXGL/wiki

### 2. Define game entities (player ship, asteroids, bullets)
- **Goal:** Represent everything in the game as FXGL entities with types and components.
- **What to do:**
  - Define an `enum` of entity types (e.g., `PLAYER`, `ASTEROID`, `BULLET`).
  - Create entity factories (e.g., `AstroidsFactory`) and register them in `initGame()`.
- **Resources:**
  - FXGL Wiki – Entities & Components: https://github.com/AlmasB/FXGL/wiki/Entities-and-Components
  - FXGL Javadoc – `EntityFactory` and `@Spawns`: https://almasb.github.io/FXGL/javadoc/

### 3. Player movement and controls
- **Goal:** Move and rotate the ship with keyboard input (thrust, rotate left/right, fire).
- **What to do:**
  - Use FXGL input bindings in `initInput()` (e.g., bind keys to actions).
  - Apply thrust/velocity to the player entity based on input.
- **Resources:**
  - FXGL Wiki – Input: https://github.com/AlmasB/FXGL/wiki/Input
  - Example: FXGL platformer / shooter samples in the FXGL repo.

### 4. Screen wrapping and world boundaries
- **Goal:** When the ship or asteroids leave one edge of the screen, they appear on the opposite edge.
- **What to do:**
  - In a component’s `onUpdate()` method, check the entity’s position and wrap it around.
- **Resources:**
  - FXGL Wiki – Game Loop & Components: https://github.com/AlmasB/FXGL/wiki/Game-Loop

### 5. Shooting and bullets
- **Goal:** Fire bullets from the ship’s nose in the direction it’s facing.
- **What to do:**
  - Add an input action for "fire".
  - In that action, spawn a `BULLET` entity with initial position/rotation based on the player.
- **Resources:**
  - FXGL Wiki – Spawning Entities: https://github.com/AlmasB/FXGL/wiki/Spawning-Entities
  - FXGL samples that show simple shooting mechanics.

### 6. Asteroid spawning and movement
- **Goal:** Continuously spawn asteroids that drift across the screen.
- **What to do:**
  - Use FXGL timers or `onUpdate()` logic to spawn asteroids periodically.
  - Give each asteroid a random direction and speed via a component.
- **Resources:**
  - FXGL Wiki – Timers: https://github.com/AlmasB/FXGL/wiki/Timers
  - FXGL Wiki – Components again for movement logic.

### 7. Collision detection and physics
- **Goal:** Detect and respond to collisions (bullet–asteroid, ship–asteroid).
- **What to do:**
  - Attach bounding boxes and collision components to relevant entities.
  - Register collision handlers in `initPhysics()`.
- **Resources:**
  - FXGL Wiki – Collision & Physics: https://github.com/AlmasB/FXGL/wiki/Physics-and-Collisions

### 8. Scoring, lives, and HUD
- **Goal:** Track score, player lives, and display a simple HUD.
- **What to do:**
  - Store game state in FXGL variables (e.g., `set("score", 0)`).
  - Update score on asteroid destruction; decrement lives on player hit.
  - Show text nodes / UI elements via `getGameScene().addUINode(...)`.
- **Resources:**
  - FXGL Wiki – UI & HUD: https://github.com/AlmasB/FXGL/wiki/UI
  - FXGL Wiki – Game State / Variables: https://github.com/AlmasB/FXGL/wiki/Game-State

### 9. Audio: sounds and music
- **Goal:** Play sound effects for shooting, explosions, and optional background music.
- **What to do:**
  - Load audio assets into your `/assets` directory structure.
  - Use FXGL’s audio service to play sounds on events (e.g., collisions, firing).
- **Resources:**
  - FXGL Wiki – Audio: https://github.com/AlmasB/FXGL/wiki/Audio

### 10. Game flow and polish
- **Goal:** Add menus, pause, game-over screen, and difficulty progression.
- **What to do:**
  - Implement main menu / game-over screens using FXGL’s menu system or custom UI.
  - Adjust spawn rates and asteroid speed for difficulty.
- **Resources:**
  - FXGL Wiki – Menus & Scenes: https://github.com/AlmasB/FXGL/wiki/Menus-and-Scenes
  - Existing FXGL sample games for inspiration.

As you work through these steps, treat this project as a sandbox: add one feature at a time, use the linked FXGL docs and samples to see idiomatic patterns, and keep the game running frequently while you iterate.
