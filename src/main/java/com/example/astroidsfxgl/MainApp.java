package com.example.astroidsfxgl;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.paint.Color;

public class MainApp extends GameApplication {

  @Override
  protected void initSettings(GameSettings settings) {
    settings.setTitle("Astroids FXGL");
    settings.setWidth(1280);
    settings.setHeight(720);
  }

  @Override
  protected void initGame() {
    // Match the original astroids game appearance: solid black background
    FXGL.getGameScene().setBackgroundColor(Color.BLACK);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
