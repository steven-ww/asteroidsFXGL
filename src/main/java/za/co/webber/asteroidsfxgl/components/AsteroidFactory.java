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
        .viewWithBBox(rock)
        .with(new CollidableComponent(true))
        .with(new AsteroidComponent())
        .build();
  }
}
