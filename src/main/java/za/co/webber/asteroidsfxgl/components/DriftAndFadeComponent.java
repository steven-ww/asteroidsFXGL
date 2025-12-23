package za.co.webber.asteroidsfxgl.components;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.scene.Node;

public class DriftAndFadeComponent extends Component {

  private final Point2D velocity; // px/s in world space
  private final double spin; // deg/s
  private final double life; // seconds
  private double elapsed = 0.0;

  public DriftAndFadeComponent(Point2D velocity, double spinDegPerSec, double lifeSeconds) {
    this.velocity = velocity;
    this.spin = spinDegPerSec;
    this.life = lifeSeconds;
  }

  @Override
  public void onUpdate(double tpf) {
    // move and rotate fragment
    entity.translate(velocity.getX() * tpf, velocity.getY() * tpf);
    entity.rotateBy(spin * tpf);

    // fade out over lifetime
    elapsed += tpf;
    double alpha = 1.0 - Math.min(1.0, elapsed / life);
    for (Node n : entity.getViewComponent().getChildren()) {
      n.setOpacity(alpha);
    }

    if (elapsed >= life) {
      entity.removeFromWorld();
    }
  }
}
