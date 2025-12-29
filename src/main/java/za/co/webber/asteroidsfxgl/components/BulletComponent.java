package za.co.webber.asteroidsfxgl.components;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

public class BulletComponent extends Component {

  private static final double SPEED = 500;
  private static final double LIFETIME = 1.2;

  private double life = 0;

  private Point2D velocity;

  public BulletComponent(Point2D direction, Point2D shipVelocity) {
    // Bullet moves forward + inherits ship movement
    this.velocity = direction.normalize().multiply(SPEED).add(shipVelocity);
  }

  @Override
  public void onUpdate(double tpf) {
    entity.translate(velocity.multiply(tpf));

    life += tpf;
    if (life > LIFETIME) {
      entity.removeFromWorld();
    }
  }
}
