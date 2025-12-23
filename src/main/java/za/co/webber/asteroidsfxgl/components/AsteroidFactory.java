package za.co.webber.asteroidsfxgl.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import za.co.webber.asteroidsfxgl.EntityType;

public class AsteroidFactory implements EntityFactory {

  private static Polygon createLargeAsteroidShape() {
    Polygon p =
        new Polygon(
            -26.0, -10.0, -20.0, -22.0, -8.0, -28.0, 6.0, -26.0, 18.0, -20.0, 28.0, -8.0, 26.0, 4.0,
            26.0, 16.0, 16.0, 24.0, 4.0, 26.0, -8.0, 24.0, -16.0, 18.0, -22.0, 10.0, -30.0, 2.0,
            -28.0, -6.0, -24.0, -14.0);
    p.setFill(Color.TRANSPARENT);
    p.setStroke(Color.WHITE);
    p.setStrokeWidth(2);
    return p;
  }

  @Spawns("asteroid")
  public Entity newAsteroid(SpawnData data) {
    Polygon rock = createLargeAsteroidShape();

    return FXGL.entityBuilder(data)
        .type(EntityType.ASTEROID)
        // Use multiple convex hit boxes to approximate the concave asteroid outline
        // (concave single polygons can cause missed contacts in collision systems that expect convex shapes)
        .view(rock)
        // Top-Right convex section
        .bbox(
            new com.almasb.fxgl.physics.HitBox(
                "asteroidTR",
                com.almasb.fxgl.physics.BoundingShape.polygon(
                    new javafx.geometry.Point2D(6.0, -26.0),
                    new javafx.geometry.Point2D(18.0, -20.0),
                    new javafx.geometry.Point2D(28.0, -8.0),
                    new javafx.geometry.Point2D(26.0, 4.0),
                    new javafx.geometry.Point2D(26.0, 16.0),
                    new javafx.geometry.Point2D(16.0, 24.0),
                    new javafx.geometry.Point2D(4.0, 26.0)
                )))
        // Top-Left convex section
        .bbox(
            new com.almasb.fxgl.physics.HitBox(
                "asteroidTL",
                com.almasb.fxgl.physics.BoundingShape.polygon(
                    new javafx.geometry.Point2D(-8.0, -28.0),
                    new javafx.geometry.Point2D(-20.0, -22.0),
                    new javafx.geometry.Point2D(-26.0, -10.0),
                    new javafx.geometry.Point2D(-28.0, -6.0),
                    new javafx.geometry.Point2D(-30.0, 2.0),
                    new javafx.geometry.Point2D(-22.0, 10.0),
                    new javafx.geometry.Point2D(-16.0, 18.0),
                    new javafx.geometry.Point2D(-8.0, 24.0)
                )))
        // Bottom convex section (bridges the lower arc)
        .bbox(
            new com.almasb.fxgl.physics.HitBox(
                "asteroidBottom",
                com.almasb.fxgl.physics.BoundingShape.polygon(
                    new javafx.geometry.Point2D(-16.0, 18.0),
                    new javafx.geometry.Point2D(-8.0, 24.0),
                    new javafx.geometry.Point2D(4.0, 26.0),
                    new javafx.geometry.Point2D(16.0, 24.0),
                    new javafx.geometry.Point2D(26.0, 16.0),
                    new javafx.geometry.Point2D(26.0, 4.0),
                    new javafx.geometry.Point2D(18.0, -20.0),
                    new javafx.geometry.Point2D(6.0, -26.0),
                    new javafx.geometry.Point2D(-8.0, -28.0),
                    new javafx.geometry.Point2D(-20.0, -22.0)
                )))
        .with(new CollidableComponent(true))
        .with(new AsteroidComponent())
        .build();
  }
}
