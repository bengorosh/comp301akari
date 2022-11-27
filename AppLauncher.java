package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays; 

import static com.comp301.a09akari.SamplePuzzles.*;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    AlternateMvcController controller = new ControllerImpl();

    PuzzleView pView = new PuzzleView(controller);
    ControlView cView = new ControlView(controller);
    MessageView mView = new MessageView(controller);
    Pane layout = new VBox();
    layout.getChildren().addAll(mView.render(), cView.render(), pView.render());
    layout.getStyleClass().add("layout");
    Scene scene = new Scene(layout, 600, 400);
    scene.getStylesheets().add("main.css");
    stage.setScene(scene);
    stage.show();

    stage.setTitle("Akari Light Up");
  }
}
