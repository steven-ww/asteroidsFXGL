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

  private static Polygon createAsteroidShape(double scale) {
    Polygon p =
        new Polygon(
            -26.0, -10.0, -20.0, -22.0, -8.0, -28.0, 6.0, -26.0, 18.0, -20.0, 28.0, -8.0, 26.0, 4.0,
            26.0, 16.0, 16.0, 24.0, 4.0, 26.0, -8.0, 24.0, -16.0, 18.0, -22.0, 10.0, -30.0, 2.0,
            -28.0, -6.0, -24.0, -14.0);
    p.setFill(Color.TRANSPARENT);
    p.setStroke(Color.WHITE);
    p.setStrokeWidth(2);

    p.setScaleX(scale);
    p.setScaleY(scale);

    return p;
  }

  private static Polygon createAsteroidForSize(AsteroidSize size) {
    return switch (size) {
      case LARGE -> createAsteroidShape(1.0);
      case MEDIUM -> createAsteroidShape(0.6);
      case SMALL -> createAsteroidShape(0.35);
    };
  }

  @Spawns("asteroid")
  public Entity newAsteroid(SpawnData data) {
    AsteroidSize size = data.hasKey("size") ? data.get("size") : AsteroidSize.LARGE;
    Polygon rock = createAsteroidForSize(size);

    return FXGL.entityBuilder(data)
        .type(EntityType.ASTEROID)
        .viewWithBBox(rock)
        .with(new CollidableComponent(true))
        .with(new AsteroidComponent(size))
        .build();
  }
}
