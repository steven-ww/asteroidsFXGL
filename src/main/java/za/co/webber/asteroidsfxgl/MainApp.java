package za.co.webber.asteroidsfxgl;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import java.util.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import za.co.webber.asteroidsfxgl.components.AsteroidFactory;
import za.co.webber.asteroidsfxgl.components.PlayerComponent;
import za.co.webber.asteroidsfxgl.components.PlayerFactory;

public class MainApp extends GameApplication {

  private PlayerComponent playerComp;

  @Override
  protected void initSettings(GameSettings settings) {
    settings.setTitle("Astroids FXGL");
    settings.setWidth(1280);
    settings.setHeight(720);
  }

  @Override
  protected void initUI() {
    Text textPixels = new Text();
    textPixels.setTranslateX(50); // x = 50
    textPixels.setTranslateY(100); // y = 100
    textPixels.textProperty().bind(FXGL.getWorldProperties().intProperty("pixelsMoved").asString());

    FXGL.getGameScene().addUINode(textPixels); // add to the scene graph
    FXGL.getGameScene().setBackgroundColor(Color.BLACK);
  }

  @Override
  protected void initGame() {
    FXGL.getGameWorld().addEntityFactory(new PlayerFactory());
    FXGL.getGameWorld().addEntityFactory(new AsteroidFactory());
    Entity player = FXGL.spawn("player", 640, 360); // 640 360
    playerComp = player.getComponent(PlayerComponent.class);

    // spawn a large asteroid off-screen drifting inward
    spawnLargeAsteroidOffscreen();
  }

  @Override
  protected void initPhysics() {
    FXGL.getPhysicsWorld()
        .addCollisionHandler(
            new CollisionHandler(EntityType.PLAYER, EntityType.ASTEROID) {
              @Override
              protected void onCollisionBegin(Entity player, Entity asteroid) {
                // Visual feedback to confirm collision detection
                FXGL.getGameScene().setBackgroundColor(Color.DARKRED);
                FXGL.getNotificationService().pushNotification("Ship hit!");
                // Trigger ship explosion animation
                PlayerComponent pc = player.getComponentOptional(PlayerComponent.class).orElse(null);
                if (pc != null) {
                  pc.explode();
                }
              }

              @Override
              protected void onCollisionEnd(Entity player, Entity asteroid) {
                // Restore normal background when no longer colliding
                FXGL.getGameScene().setBackgroundColor(Color.BLACK);
              }
            });
  }

  @Override
  protected void initInput() {
    Input input = FXGL.getInput();

    input.addAction(
        new UserAction("Turn Left") {
          @Override
          protected void onAction() {
            playerComp.turnLeft();
          }
        },
        KeyCode.D);

    input.addAction(
        new UserAction("Turn Right") {
          @Override
          protected void onAction() {
            playerComp.turnRight();
          }
        },
        KeyCode.A);

    input.addAction(
        new UserAction("Thrust") {
          @Override
          protected void onAction() {
            playerComp.thrustOn();
          }

          protected void onActionEnd() {
            playerComp.thrustOff();
          }
        },
        KeyCode.W);
  }

  @Override
  protected void initGameVars(Map<String, Object> vars) {
    vars.put("pixelsMoved", 0);
  }

  private void spawnLargeAsteroidOffscreen() {
    double w = FXGL.getAppWidth();
    double h = FXGL.getAppHeight();
    double margin = 40; // spawn just beyond the edge

    int edge = (int) (Math.random() * 4); // 0=left,1=right,2=top,3=bottom
    double x;
    double y;
    switch (edge) {
      case 0: // left
        x = -margin;
        y = Math.random() * h;
        break;
      case 1: // right
        x = w + margin;
        y = Math.random() * h;
        break;
      case 2: // top
        x = Math.random() * w;
        y = -margin;
        break;
      default: // bottom
        x = Math.random() * w;
        y = h + margin;
        break;
    }

    FXGL.spawn("asteroid", x, y);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
