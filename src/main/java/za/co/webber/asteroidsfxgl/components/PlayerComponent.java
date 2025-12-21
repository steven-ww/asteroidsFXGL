package za.co.webber.asteroidsfxgl.components;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.component.Component;
import javafx.scene.shape.Polyline;

public class PlayerComponent extends Component {

  private final Vec2 velocity = new Vec2(0, 0);
  private final Polyline thrustFlame;

  public PlayerComponent(Polyline thrustFlame) {
    this.thrustFlame = thrustFlame;
    this.thrustFlame.setVisible(false);
  }

  public void turnLeft() {
    entity.rotateBy(2.5);
  }

  public void turnRight() {
    entity.rotateBy(-2.5);
  }

  public void thrustOn() {
    Vec2 thrust = Vec2.fromAngle(entity.getRotation() - 90).mulLocal(0.10);
    velocity.set(velocity.add(thrust));
    thrustFlame.setVisible(true);
  }

  public void thrustOff() {
    thrustFlame.setVisible(false);
  }

  @Override
  public void onUpdate(double tpf) {
    entity.translate(velocity);
  }
}
