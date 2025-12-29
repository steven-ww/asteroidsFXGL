package za.co.webber.asteroidsfxgl.components;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeLineCap;

public class PlayerComponent extends Component {

  private final Vec2 velocity = new Vec2(0, 0);
  private final Polyline thrustFlame;
  private boolean exploded = false;
  private boolean invincible = false;
  private double invincibilityTimer = 0;
  private static final double INVINCIBILITY_DURATION = 3.0; // 3 seconds like original

  public PlayerComponent(Polyline thrustFlame) {
    this.thrustFlame = thrustFlame;
    this.thrustFlame.setVisible(false);
  }

  public Point2D getNosePosition(double distance) {
    // Entity rotation origin is set to (0,0) in the factory (the ship nose),
    // so use the entity position (the rotation origin) + rotated local offset.
    Point2D localNose = new Point2D(0, -distance);
    double ang = Math.toRadians(entity.getRotation());
    Point2D rotated = rotate(localNose, ang);
    return entity.getPosition().add(rotated);
  }

  public Vec2 getVelocity() {
    return velocity;
  }

  public double getRotation() {
    return entity.getRotation();
  }

  public void turnLeft() {
    if (exploded) return;
    entity.rotateBy(2.5);
  }

  public void turnRight() {
    if (exploded) return;
    entity.rotateBy(-2.5);
  }

  public void thrustOn() {
    if (exploded) return;
    Vec2 thrust = Vec2.fromAngle(entity.getRotation() - 90).mulLocal(0.05);
    velocity.set(velocity.add(thrust));
    thrustFlame.setVisible(true);
  }

  public void thrustOff() {
    thrustFlame.setVisible(false);
  }

  @Override
  public void onUpdate(double tpf) {
    if (exploded) return;

    // Handle invincibility timer and blinking
    if (invincible) {
      invincibilityTimer += tpf;
      if (invincibilityTimer >= INVINCIBILITY_DURATION) {
        invincible = false;
        invincibilityTimer = 0;
        entity.getViewComponent().setOpacity(1.0);
      } else {
        // Blink effect during invincibility
        double blinkFreq = 8.0; // blinks per second
        entity
            .getViewComponent()
            .setOpacity((Math.sin(invincibilityTimer * blinkFreq * Math.PI * 2) > 0) ? 1.0 : 0.3);
      }
    }

    entity.translate(velocity);
    if (entity.getX() < 0) {
      entity.setX(1280);
    } else if (entity.getX() > 1280) {
      entity.setX(0);
    }

    if (entity.getY() < 0) {
      entity.setY(720);
    } else if (entity.getY() > 720) {
      entity.setY(0);
    }
  }

  public boolean isInvincible() {
    return invincible;
  }

  public void explode() {
    if (exploded) return;
    exploded = true;

    // stop thrust visual
    thrustFlame.setVisible(false);
    // disable further collisions for the player entity
    try {
      com.almasb.fxgl.entity.components.CollidableComponent cc =
          entity.getComponent(com.almasb.fxgl.entity.components.CollidableComponent.class);
      cc.setValue(false);
    } catch (Exception ignored) {
    }

    // Remove current ship visuals
    entity.getViewComponent().clearChildren();

    // Build three line fragments matching the original A-shape
    javafx.scene.shape.Line left = new javafx.scene.shape.Line(0, -12, -8, 10);
    javafx.scene.shape.Line right = new javafx.scene.shape.Line(0, -12, 8, 10);
    javafx.scene.shape.Line bar = new javafx.scene.shape.Line(-7, 7, 7, 7);
    left.setStroke(javafx.scene.paint.Color.WHITE);
    right.setStroke(javafx.scene.paint.Color.WHITE);
    bar.setStroke(javafx.scene.paint.Color.WHITE);
    left.setStrokeWidth(2);
    right.setStrokeWidth(2);
    bar.setStrokeWidth(2);

    // Compute outward drift vectors in local space, then rotate to world space
    double rotDeg = entity.getRotation();
    double ang = Math.toRadians(rotDeg);
    javafx.geometry.Point2D vLeftLocal = new javafx.geometry.Point2D(-90, 0);
    javafx.geometry.Point2D vRightLocal = new javafx.geometry.Point2D(90, 0);
    javafx.geometry.Point2D vBarLocal = new javafx.geometry.Point2D(0, 90);

    javafx.geometry.Point2D vLeft = rotate(vLeftLocal, ang);
    javafx.geometry.Point2D vRight = rotate(vRightLocal, ang);
    javafx.geometry.Point2D vBar = rotate(vBarLocal, ang);

    // Spawn three fragment entities at the player's transform
    spawnFragment(left, vLeft, 40.0, 1.5);
    spawnFragment(right, vRight, -40.0, 1.5);
    spawnFragment(bar, vBar, 0.0, 1.5);
  }

  private static javafx.geometry.Point2D rotate(javafx.geometry.Point2D v, double ang) {
    double c = Math.cos(ang);
    double s = Math.sin(ang);
    return new javafx.geometry.Point2D(v.getX() * c - v.getY() * s, v.getX() * s + v.getY() * c);
  }

  private void spawnFragment(
      javafx.scene.Node view,
      javafx.geometry.Point2D velocity,
      double spinDegPerSec,
      double lifeSeconds) {
    com.almasb.fxgl.entity.Entity frag =
        com.almasb
            .fxgl
            .dsl
            .FXGL
            .entityBuilder()
            .type(za.co.webber.asteroidsfxgl.EntityType.PLAYER) // type not used for collisions here
            .at(entity.getX(), entity.getY())
            .view(view)
            .buildAndAttach();
    frag.setRotation(entity.getRotation());
    frag.addComponent(new DriftAndFadeComponent(velocity, spinDegPerSec, lifeSeconds));
  }

  public void respawn(double x, double y) {
    // Clear any explosion fragments
    entity.getViewComponent().clearChildren();

    // Recreate ship visuals
    Path ship =
        new Path(
            new MoveTo(0, -12), new LineTo(-8, 10),
            new MoveTo(0, -12), new LineTo(8, 10),
            new MoveTo(-7, 7), new LineTo(7, 7));
    ship.setFill(Color.TRANSPARENT);
    ship.setStroke(Color.WHITE);
    ship.setStrokeWidth(2);
    ship.setStrokeLineCap(StrokeLineCap.ROUND);

    entity.getViewComponent().addChild(ship);
    entity.getViewComponent().addChild(thrustFlame);

    // Reset position and velocity
    entity.setPosition(x, y);
    entity.setRotation(0);
    velocity.set(0, 0);

    // Reset state
    exploded = false;
    thrustFlame.setVisible(false);

    // Enable invincibility
    invincible = true;
    invincibilityTimer = 0;

    // Re-enable collisions (but they'll be ignored while invincible)
    try {
      com.almasb.fxgl.entity.components.CollidableComponent cc =
          entity.getComponent(com.almasb.fxgl.entity.components.CollidableComponent.class);
      cc.setValue(true);
    } catch (Exception ignored) {
    }
  }
}
