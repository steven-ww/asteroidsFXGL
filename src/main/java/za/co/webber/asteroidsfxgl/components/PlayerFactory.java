package za.co.webber.asteroidsfxgl.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import za.co.webber.asteroidsfxgl.EntityType;

public class PlayerFactory implements EntityFactory {

  @Spawns("player")
  public Entity newPlayer(SpawnData data) {
    //    Polygon ship = new Polygon(0.0, -10.0, 7.5, 10.0, -7.5, 10.0);
    Path ship =
        new Path(
            new MoveTo(0, -12), new LineTo(-8, 10), // left leg
            new MoveTo(0, -12), new LineTo(8, 10), // right leg
            new MoveTo(-7, 7), new LineTo(7, 7) // crossbar
            );
    ship.setFill(Color.TRANSPARENT);
    ship.setStroke(Color.WHITE);
    ship.setStrokeWidth(2);

    Polyline flame = new Polyline(-3.0, 12.0, 0.0, 18.0, 3.0, 12.0);
    flame.setStroke(Color.WHITE);

    return FXGL.entityBuilder(data)
        .type(EntityType.PLAYER)
        .view(ship)
        .view(flame)
        .with(new CollidableComponent(true))
        .with(new PlayerComponent(flame))
        .build();
  }
}
