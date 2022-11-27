package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class PuzzleView
    implements FXComponent, ModelObserver { // display the clues and the game board inside a GridPanel; CORRECT
  private AlternateMvcController controller;
  private GridPane gameBoard;
  public PuzzleView(AlternateMvcController controller) {
    this.controller = controller;
    controller.registerObserver(this);
    this.gameBoard = new GridPane();
    gameBoard.setGridLinesVisible(true);
    gameBoard.getStyleClass().add("grid");
  }

    //this.gameBoard.getStyleClass().add("layout");
  @Override
  public Parent render() {
    gameBoard.getChildren().clear();
    for (int h = 0; h < controller.getActivePuzzle().getHeight(); h++) {
      for (int w = 0; w < controller.getActivePuzzle().getWidth(); w++) {
        /*Rectangle rec = new Rectangle();
        rec.setWidth(10);
        rec.setHeight(10);
        GridPane.setRowIndex(rec, h);
        GridPane.setColumnIndex(rec, w);
        gameBoard.getChildren().addAll(rec);*/
        if (controller.getActivePuzzle().getCellType(h, w) == CellType.CORRIDOR) {
          if (controller.isLamp(h, w)) {
            if (controller.isLampIllegal(h, w)) {
              gameBoard.add(makeTile("I"), h, w);
            } else {
              gameBoard.add(makeTile("L"), h, w);
            }
          }
          else if (controller.isLit(h, w)) {
            gameBoard.add(makeTile("B"), h, w);
          }
          else {
            gameBoard.add(makeTile("D"), h, w);
          }
        } else if (controller.getActivePuzzle().getCellType(h, w) == CellType.CLUE) {
          gameBoard.add(makeTile(controller.getActivePuzzle().getClue(h, w) + ""), h, w);
        } else { // if cell is a wall
          gameBoard.add(makeTile("W"), h, w);
        }
      }
    }
    return gameBoard;
  }

  @Override
  public void update(Model model){
    render();
  }

  private Label makeTile(String label) {
    Label tile;
    if (label.equals("W")) {
      tile = new Label(  label + " ");
    }else if(label.equals("L")){
      tile = new Label(  "     ");
    }else if(label.equals("I")){
      tile = new Label(  "     ");
    } else{
      tile = new Label(label + "  ");
    }
    tile.setStyle("layout");
    if(label.equals("L")){
      tile.setStyle("lamp");
      tile.getStyleClass().add("lamp");
    }else if(label.equals("D")){
      tile.getStyleClass().add("hall");
    }else if(label.equals("I")){
      tile.getStyleClass().add("illegalLamp");
    }else if(label.equals("B")){
      tile.getStyleClass().add("lit");
    }else if(label.equals("W")){
      tile.getStyleClass().add("wall");
    }else if(label.equals("1") || label.equals("2") || label.equals("3") || label.equals("4") || label.equals("0")){//cell is a clue
      //cell is a clue
      tile.getStyleClass().add("hall");
    }

    tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Node node = (Node) event.getSource();
        System.out.println(node.toString());

        int row = toIndex(GridPane.getRowIndex(node));
        int column = toIndex(GridPane.getColumnIndex(node));

        System.out.println(node.toString() + " " + row + " : " + column);

        controller.clickCell(column, row);
      }
    });
    // use lambda functions to set actions on the click - turn lamps on or off
    // set sizes of the labels
    // tile.getStyleClass().add("tile");
    // tile.getStyleClass().add("tile-" + num);
    return tile;
  }

  private static int toIndex(Integer value) {
    return value == null ? 0 : value;
  }

  /*@Override
    //set gaps, styleclass,
    int i;
    int j;
    for(i=0; i < controller.getActivePuzzle().getHeight(); i++){
      for(j = 0; j < controller.getActivePuzzle().getWidth(); j++){
        gameBoard.add(makeTile(j,i), j, i);
      }
    }
    //add gameboard to a pane, add button
  }*/
  //render makes a board depending on the state of the controller.
  // When the model changes, it calls render, which remakes it.

}
