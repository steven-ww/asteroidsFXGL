package za.co.webber.asteroidsfxgl.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class BulletFactory {

  public static Entity spawnBullet(Point2D position, double rotation, Point2D shipVelocity) {

    // Direction from rotation
    Point2D direction =
        new Point2D(
            Math.cos(Math.toRadians(rotation - 90)), Math.sin(Math.toRadians(rotation - 90)));

    // Bullet shape (small line)
    Line bulletView = new Line(0, 0, 0, -4);
    bulletView.setStroke(Color.WHITE);
    bulletView.setStrokeWidth(2);

    return FXGL.entityBuilder()
        .at(position)
        .view(bulletView)
        .with(new BulletComponent(direction, shipVelocity))
        .collidable()
        .build();
  }
}
