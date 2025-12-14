# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Project overview
This is a single-module Gradle project for an Asteroids-style learning game built with the FXGL game engine.

Entry point:
- `src/main/java/com/example/asteroidsfxgl/MainApp.java` (`com.example.asteroidsfxgl.MainApp`), which extends `GameApplication`.

Build system:
- Gradle wrapper (`./gradlew`) using the `application` and `java` plugins.
- Code formatting is enforced via Spotless with Google Java Format.

## Common commands
All commands assume you are in the repo root.

### Build / run
- Run the game:
  - `./gradlew run`
- Build (compiles + runs checks):
  - `./gradlew clean build`
- Compile only:
  - `./gradlew classes`

### Formatting (Spotless)
- Auto-format Java sources:
  - `./gradlew spotlessApply`
- Check formatting in CI-style mode:
  - `./gradlew spotlessCheck`

### Tests
There is currently no `src/test` tree, but Gradle’s standard test task is available.

- Run all tests:
  - `./gradlew test`
- Run a single test class:
  - `./gradlew test --tests 'com.example.asteroidsfxgl.SomeTest'`
- Run a single test method:
  - `./gradlew test --tests 'com.example.asteroidsfxgl.SomeTest.someMethod'`

## Architecture notes (FXGL “big picture”)
FXGL’s `GameApplication` lifecycle methods are the main architectural backbone. In `MainApp.java`:
- `initSettings(GameSettings)`: window title/size and other global settings.
- `initGameVars(Map<String, Object>)`: initializes world/game variables (e.g. `pixelsMoved`). These are exposed via `FXGL.getWorldProperties()`.
- `initUI()`: constructs UI nodes and binds them to world properties (example: a `Text` node bound to `pixelsMoved`).
- `initGame()`: creates and attaches entities (currently a single `player` entity built via `FXGL.entityBuilder()` with a `Polygon` view).
- `initInput()`: defines input bindings via `FXGL.getInput().addAction(new UserAction(...), KeyCode.…)` and mutates entities / game vars.

As this project grows (see `README.md` “Next Steps”), expect the codebase to expand around:
- Entity types (often an enum) + an `EntityFactory` with `@Spawns` methods.
- Components for movement / wrapping / shooting (logic typically lives in `Component#onUpdate`).
- Collision handlers registered in `initPhysics()`.
- Game-state variables + HUD nodes in `initUI()`.

## Key implementation details to be aware of
- Java toolchain: `build.gradle` configures `java.toolchain.languageVersion = 25`. If you see JDK mismatches locally, prefer aligning with the toolchain (or let Gradle provision it).
- FXGL dependency source: FXGL is pulled from JitPack (`com.github.almasb:fxgl`). If resolution fails or you suspect a stale cache, try `./gradlew --refresh-dependencies run`.
- JavaFX dependencies: JavaFX modules are pulled as Gradle dependencies with an OS/arch classifier chosen at build time (mac vs mac-aarch64 vs win vs linux). If changing JavaFX modules/versions, keep the platform-classifier logic consistent.
- Assets/resources: FXGL assets typically live under `src/main/resources` (commonly `src/main/resources/assets/...`). This repo’s `src/main/resources` is currently empty.
