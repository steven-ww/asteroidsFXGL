package za.co.webber.asteroidsfxgl.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import java.util.concurrent.ThreadLocalRandom;
import javafx.geometry.Point2D;

public class AsteroidComponent extends Component {

  private Point2D velocity; // pixels per second
  private double spin; // degrees per second

  @Override
  public void onAdded() {
    double w = FXGL.getAppWidth();
    double h = FXGL.getAppHeight();
    double cx = w / 2.0;
    double cy = h / 2.0;

    double dx = cx - entity.getX();
    double dy = cy - entity.getY();
    Point2D dirToCenter = new Point2D(dx, dy).normalize();

    double angleJitter = rnd(-25, 25); // degrees
    Point2D jittered = rotate(dirToCenter, Math.toRadians(angleJitter));

    double speed = rnd(60, 120);
    velocity = jittered.multiply(speed);

    spin = rnd(-40, 40);
  }

  @Override
  public void onUpdate(double tpf) {
    entity.translate(velocity.getX() * tpf, velocity.getY() * tpf);
    entity.rotateBy(spin * tpf);
    wrapAround();
  }

  private void wrapAround() {
    double w = FXGL.getAppWidth();
    double h = FXGL.getAppHeight();
    double x = entity.getX();
    double y = entity.getY();
    double m = 36; // margin

    if (x < -m) entity.setX(w + m);
    else if (x > w + m) entity.setX(-m);

    if (y < -m) entity.setY(h + m);
    else if (y > h + m) entity.setY(-m);
  }

  private static double rnd(double min, double max) {
    return ThreadLocalRandom.current().nextDouble(min, max);
  }

  private static Point2D rotate(Point2D v, double angleRad) {
    double cos = Math.cos(angleRad);
    double sin = Math.sin(angleRad);
    return new Point2D(v.getX() * cos - v.getY() * sin, v.getX() * sin + v.getY() * cos);
  }
}
