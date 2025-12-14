package com.example.asteroidsfxgl;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import java.util.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class MainApp extends GameApplication {

  private Entity player;

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
    //    FXGL.getGameScene().setBackgroundColor(Color.BLACK);
  }

  @Override
  protected void initGame() {
    //    player =
    //        FXGL.entityBuilder().at(300, 300).view(new Rectangle(25, 25,
    // Color.BLUE)).buildAndAttach();

    player =
        FXGL.entityBuilder()
            .at(640, 360)
            .view(
                new Polygon(
                    0.0, -10.0, // top point
                    7.5, 10.0, // bottom right
                    -7.5, 10.0 // bottom left
                    ))
            .with(new CollidableComponent(true))
            .buildAndAttach();
  }

  @Override
  protected void initInput() {
    Input input = FXGL.getInput();

    input.addAction(
        new UserAction("Move Right") {
          @Override
          protected void onAction() {
            player.translateX(5); // move right 5 pixels
            FXGL.inc("pixelsMoved", +5);
          }
        },
        KeyCode.D);

    input.addAction(
        new UserAction("Move Left") {
          @Override
          protected void onAction() {
            player.translateX(-5); // move left 5 pixels
            FXGL.inc("pixelsMoved", +5);
          }
        },
        KeyCode.A);

    input.addAction(
        new UserAction("Turn Left") {
          @Override
          protected void onAction() {
            player.rotateBy(2.5); // move left 5 pixels
            FXGL.inc("pixelsMoved", +5);
          }
        },
        KeyCode.E);

    input.addAction(
        new UserAction("Turn Right") {
          @Override
          protected void onAction() {
            player.rotateBy(-2.5); // move left 5 pixels
            FXGL.inc("pixelsMoved", +5);
          }
        },
        KeyCode.Q);

    input.addAction(
        new UserAction("Move Down") {
          @Override
          protected void onAction() {
            player.translateY(5); // move down 5 pixels
            FXGL.inc("pixelsMoved", +5);
          }
        },
        KeyCode.S);

    input.addAction(
        new UserAction("Move Up") {
          @Override
          protected void onAction() {
            player.translateY(-5); // move up 5 pixels
            FXGL.inc("pixelsMoved", +5);
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
