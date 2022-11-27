package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControlView implements FXComponent, ModelObserver /*does it implement FXcomponrnt?*/{
  private Button nextButton;
  private Button prevButton;
  private Button randButton;
  private Button resetButton;
  private HBox buttonContainer;
  private AlternateMvcController controller;

  public ControlView(AlternateMvcController controller) {
    this.nextButton = new Button();
    nextButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        controller.clickNextPuzzle();
      }
    });
    this.prevButton = new Button();
    prevButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        controller.clickPrevPuzzle();
      }
    });
    this.randButton = new Button();
    randButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        controller.clickRandPuzzle();
      }
    });
    this.resetButton = new Button();
    resetButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        controller.clickResetPuzzle();
      }
    });
    this.buttonContainer = new HBox();
    this.controller = controller;
    controller.registerObserver(this);

  }
  // display the puzzle controls, including buttons, to move through the puzzle library

  @Override
  public Parent render() {
    buttonContainer.getChildren().clear();
    buttonContainer.getChildren().addAll(prevButton, nextButton, randButton, resetButton);
    nextButton.setText("Next Puzzle");
    prevButton.setText("Previous Puzzle");
    randButton.setText("Random Puzzle");
    resetButton.setText("Reset Puzzle");
    return buttonContainer;
  }

  @Override
  public void update(Model model){
    render();
  }
}
