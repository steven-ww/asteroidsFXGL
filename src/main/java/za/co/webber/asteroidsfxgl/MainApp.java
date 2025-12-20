package za.co.webber.asteroidsfxgl;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import java.util.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class MainApp extends GameApplication {

  private Entity player;
  private Vec2 velocity = new Vec2(0, 0);

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

    Polygon ship =
        new Polygon(
            0.0, -10.0, // top point
            7.5, 10.0, // bottom right
            -7.5, 10.0 // bottom left
            );
    ship.setFill(javafx.scene.paint.Color.TRANSPARENT); // no fill
    ship.setStroke(javafx.scene.paint.Color.WHITE); // outline color
    ship.setStrokeWidth(2);

    player =
        FXGL.entityBuilder()
            .at(640, 360)
            .view(ship)
            .with(new CollidableComponent(true))
            .buildAndAttach();
  }

  @Override
  protected void initInput() {
    Input input = FXGL.getInput();

    input.addAction(
        new UserAction("Turn Left") {
          @Override
          protected void onAction() {
            player.rotateBy(2.5); // move left 5 pixels
          }
        },
        KeyCode.D);

    input.addAction(
        new UserAction("Turn Right") {
          @Override
          protected void onAction() {
            player.rotateBy(-2.5); // move left 5 pixels
          }
        },
        KeyCode.A);

    input.addAction(
        new UserAction("Thrust") {
          @Override
          protected void onAction() {

            Vec2 thrust = Vec2.fromAngle(player.getRotation() - 90).mulLocal(0.15);
            velocity = velocity.add(thrust);
          }
        },
        KeyCode.W);
  }

  @Override
  protected void initGameVars(Map<String, Object> vars) {
    vars.put("pixelsMoved", 0);
  }

  @Override
  public void onUpdate(double tpf) {
    player.translate(velocity);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
