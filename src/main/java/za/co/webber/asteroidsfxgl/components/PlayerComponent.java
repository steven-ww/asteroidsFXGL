package za.co.webber.asteroidsfxgl.components;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.component.Component;
import javafx.scene.shape.Polyline;

public class PlayerComponent extends Component {

  private final Vec2 velocity = new Vec2(0, 0);
  private final Polyline thrustFlame;
  private boolean exploded = false;

  public PlayerComponent(Polyline thrustFlame) {
    this.thrustFlame = thrustFlame;
    this.thrustFlame.setVisible(false);
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
}
