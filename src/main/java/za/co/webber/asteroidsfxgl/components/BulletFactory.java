package za.co.webber.asteroidsfxgl.components;

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

    com.almasb.fxgl.entity.Entity bullet =
        com.almasb
            .fxgl
            .dsl
            .FXGL
            .entityBuilder()
            .type(za.co.webber.asteroidsfxgl.EntityType.BULLET)
            .at(position)
            .viewWithBBox(bulletView)
            .with(new BulletComponent(direction, shipVelocity))
            .build();

    // Rotate the bullet to match the ship orientation so the visual points the same way.
    // If your bullet view points up by default, use shipRotation (no change).
    // If it points right or another direction, adjust by +/-90 accordingly, e.g. shipRotation - 90.
    bullet.setRotation(rotation);

    return bullet;
  }
}
