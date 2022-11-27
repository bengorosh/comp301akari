package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static com.comp301.a09akari.SamplePuzzles.*;

public class ControllerImpl implements AlternateMvcController {
  private Model model;

  public ControllerImpl() {
    Puzzle p1 = new PuzzleImpl(PUZZLE_01);
    Puzzle p2 = new PuzzleImpl(PUZZLE_02);
    Puzzle p3 = new PuzzleImpl(PUZZLE_03);
    Puzzle p4 = new PuzzleImpl(PUZZLE_04);
    Puzzle p5 = new PuzzleImpl(PUZZLE_05);
    ArrayList<Puzzle> puzzles = new ArrayList<Puzzle>(Arrays.asList(p1, p2, p3, p4, p5));
    PuzzleLibrary puzzleLibrary = new PuzzleLibraryImpl();
    for (Puzzle p : puzzles) {
      puzzleLibrary.addPuzzle(p);
    }
    model = new ModelImpl(puzzleLibrary); // create model instance
    model.setActivePuzzleIndex(0);
  }

  /*Most of the Controller methods will simply be delegated/forwarded
  directly to the encapsulated Model instance. However, a few methods
  like clickNextPuzzle(), clickPrevPuzzle(), and clickRandPuzzle() may
  require a few lines of code to correctly control the model.*/

  @Override
  public void clickNextPuzzle() {
    int i = this.model.getActivePuzzleIndex() + 1;
    if (i < this.model.getPuzzleLibrarySize()) {
      this.model.setActivePuzzleIndex(i);
    }
  }

  @Override
  public void clickPrevPuzzle() {
    int i = this.model.getActivePuzzleIndex() - 1;
    if (i >= 0) {
      this.model.setActivePuzzleIndex(i);
    }
  }

  @Override
  public void clickRandPuzzle() {
    int Min = 0;
    int Max = this.model.getPuzzleLibrarySize();
    int i = ThreadLocalRandom.current().nextInt(Min, Max);
    this.model.setActivePuzzleIndex(i);
  }

  @Override
  public void clickResetPuzzle() {
    this.model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    // maybe make a click variable for each cell to see how many times it's been clicked?
    // add 1 for each click; for each even click & 0 remove lamp; for each odd click ass lamp
    if(model.isLamp(r,c)){
      model.removeLamp(r,c);
    }else{
      model.addLamp(r,c);
    }
    // if clicked again, remove the lamp
  }

  @Override
  public boolean isLit(int r, int c) {
    return this.model.isLit(r, c);
  }

  @Override
  public boolean isLamp(int r, int c) {
    return this.model.isLamp(r, c);
  }

  @Override
  public boolean isLampIllegal(int r, int c) { return this.model.isLampIllegal(r, c); }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    return this.model.isClueSatisfied(r, c);
  }

  @Override
  public boolean isSolved() {
    return this.model.isSolved();
  }

  @Override
  public Puzzle getActivePuzzle() {
    return this.model.getActivePuzzle();
  }

  @Override
  public void registerObserver(ModelObserver observer) { model.addObserver(observer);}

}
