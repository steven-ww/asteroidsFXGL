package za.co.webber.asteroidsfxgl;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import java.util.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    Entity player = FXGL.spawn("player", 640, 360);
    playerComp = player.getComponent(PlayerComponent.class);
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

  public static void main(String[] args) {
    launch(args);
  }
}
