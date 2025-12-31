package za.co.webber.asteroidsfxgl.components;

/**
 * Logical size categories for asteroids. Used by the factory and component to choose shape, speed,
 * and screen-wrapping behavior.
 */
public enum AsteroidSize {
  LARGE(30.0),
  MEDIUM(18.0),
  SMALL(11.0);

  private final double radius;

  AsteroidSize(double radius) {
    this.radius = radius;
  }

  public double getRadius() {
    return radius;
  }
}
