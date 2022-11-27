package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class View {
  private final AlternateMvcController controller;

  public View(AlternateMvcController controller) {
    this.controller = controller;
  }

  public Parent render() {
    VBox layout = new VBox();

    ControlView controls_view = new ControlView(controller);
    MessageView playlist_view = new MessageView(controller);
    PuzzleView add_song_view = new PuzzleView(controller);

    layout.getChildren().add(controls_view.render());
    layout.getChildren().add(playlist_view.render());
    layout.getChildren().add(add_song_view.render());

    return layout;
  }
}
