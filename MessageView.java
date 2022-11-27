package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class MessageView implements FXComponent, ModelObserver {
  private AlternateMvcController controller;
  private AnchorPane aPane;
  public MessageView(AlternateMvcController controller) {
    this.controller=controller;
    controller.registerObserver(this);
    this.aPane = new AnchorPane();
  }
  //public void callRender(){
  //  if(controller.isSolved()){
  //    render();
  //  }
  //}

  // show the "success" message when the user successfully finishes the puzzle

  @Override
  public Parent render() {
    aPane.getChildren().clear();

    if(controller.isSolved()) {
      aPane.getChildren().add(new TextField("Puzzle SOLVED!"));
    }

    return aPane;
  }

  @Override
  public void update(Model model){
    render();
  }

}
