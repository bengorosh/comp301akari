package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  // the Model for akari should encapsulate the following concepts:
  // 1. A PuzzleLibrary instance which stores available akari puzzles for the user to solve
  // 2. A way to identify which puzzle is currently active; maybe index into the puzzle library
  // 3. The locations of all lamps which have been added by the user to the active puzzle (lamp
  // locations are not stored in the Puzzle object since they are not part of the puzzle itself, but
  // are instead marked in by the user)
  // 4. An algorithm for determining whether the active puzzle has been solved
  // 5. A List<ModelObserver> of active model observers in support of the observer design pattern
  private final PuzzleLibrary library; // 1
  private final List<ModelObserver> observers; // 5
  private final List<MyEntry> lamps; // 3
  private int index = 0; // 2
  private boolean simulation;

  public ModelImpl(PuzzleLibrary library) {
    this.library = library;
    observers = new ArrayList<ModelObserver>();
    lamps = new ArrayList<MyEntry>();
    simulation = false;
  }

  @Override
  public void addLamp(int r, int c) {
    if (c >= this.getActivePuzzle().getWidth()
        || r >= this.getActivePuzzle().getHeight()
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException();
    } else if (this.getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    MyEntry a = new MyEntry(r, c);
    for (MyEntry lamp : lamps) {
      if (lamp.equals(a)) {
        return;
      }
    }
    lamps.add(a);
    if (!simulation) {
      notifyObservers(this);
    }
  }

  @Override
  public void removeLamp(int r, int c) {
    if (c >= this.getActivePuzzle().getWidth()
        || r >= this.getActivePuzzle().getHeight()
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    MyEntry b = new MyEntry(r, c);
    if (this.getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    boolean flag = false;
    for (MyEntry lamp : lamps) {
      if (lamp.equals(b)) {
        flag = true;
        break;
      }
    }
    if (flag) {
      lamps.remove(b);
    }
    if (!simulation) {
      notifyObservers(this);
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    if (c >= this.getActivePuzzle().getWidth()
        || r >= this.getActivePuzzle().getHeight()
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (isLamp(r, c)) {
      return true;
    }
    if (this.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
      int a = r;
      while (a < this.getActivePuzzle().getHeight() && a >= 0) {
        if (getActivePuzzle().getCellType(a, c) == CellType.CORRIDOR) {
          if (isLamp(a, c))
            return true; // up until the first wall/clue, if there's a lamp up this col return true
        } else {
          break; // if a is not a corridor then we're reached the "end" of this corridor
        }
        ++a;
      }
      a = r;
      while (a >= 0 && a <= this.getActivePuzzle().getHeight()) {
        if (getActivePuzzle().getCellType(a, c) == CellType.CORRIDOR) {
          if (isLamp(a, c)) return true;
        } else {
          break; // if a is not a corridor then we're reached the "end" of this corridor
        }
        a--;
      }
      int b = c;
      while (b < this.getActivePuzzle().getWidth()) {
        if (getActivePuzzle().getCellType(r, b) == CellType.CORRIDOR) {
          if (isLamp(r, b))
            return true; // up until the first wall/clue, if there's a lamp "right" through the row
        } else {
          break; // if b is not a corridor then we're reached the "end" of this corridor
        }
        ++b;
      }
      b = c;
      while (b >= 0 && b <= this.getActivePuzzle().getWidth()) {
        if (getActivePuzzle().getCellType(r, b) == CellType.CORRIDOR) {
          if (isLamp(r, b))
            return true; // up until the first wall/clue, if there's a lamp "left" through the row
        } else {
          break; // if b is not a corridor then we're reached the "end" of this corridor
        }
        --b;
      }
    } else {
      throw new IllegalArgumentException();
    }
    return false;
  }

  @Override
  public boolean isLamp(int r, int c) { // CORRECT
    if (c >= this.getActivePuzzle().getWidth()
        || r >= this.getActivePuzzle().getHeight()
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (this.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
      MyEntry d = new MyEntry(r, c);
      for (MyEntry lamp : lamps) {
        if (lamp.equals(d)) {
          return true;
        }
      }
      return false;
    }
    throw new IllegalArgumentException();
  }

  @Override
  public boolean isLampIllegal(int r, int c) { // CORRECT
    if (c >= this.getActivePuzzle().getWidth()
        || r >= this.getActivePuzzle().getHeight()
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (!isLamp(r, c)) {
      throw new IllegalArgumentException();
    }
    this.simulation = true;
    removeLamp(r, c);
    if (isLit(r, c)) { // remove the lamp and check if the square is still lit
      addLamp(r, c); // add the lamp back
      this.simulation = false;
      return true; // yes, this lamp was illegal
    }
    addLamp(r, c);
    this.simulation = false;
    return false; // the square was not lit, but still add the lamp back
  }

  @Override
  public Puzzle getActivePuzzle() {
    return library.getPuzzle(index);
  }

  @Override
  public int getActivePuzzleIndex() {
    return index;
  }

  @Override
  public void setActivePuzzleIndex(int in) {
    if (in > library.size() - 1 || in < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (in != index) {
      this.resetPuzzle();
    }
    index = in;
    if (!simulation) {
      notifyObservers(this);
    }
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    lamps.clear();
    if (!simulation) {
      notifyObservers(this);
    }
  }

  @Override
  public boolean isSolved() {
    boolean x = false;
    for (int c = 0; c < this.getActivePuzzle().getWidth(); c++) {
      for (int r = 0; r < this.getActivePuzzle().getHeight(); r++) {
        if (this.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
          if (isLamp(r, c)) {
            x = x || isLampIllegal(r, c);
          } else {
            x = x || !(this.isLit(r, c));
          }
        } else if (this.getActivePuzzle().getCellType(r, c) == CellType.CLUE) {
          x = x || !this.isClueSatisfied(r, c);
        }
      }
    }
    return !x;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (c >= this.getActivePuzzle().getWidth()
        || r >= this.getActivePuzzle().getHeight()
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException();
    } else if (this.getActivePuzzle().getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    int a = 0;
    if (c < this.getActivePuzzle().getWidth() - 1) {
      if (isLamp(r, c + 1)) {
        a++;
      }
    }
    if (c > 0) {
      if (isLamp(r, c - 1)) {
        a++;
      }
    }
    if (r < this.getActivePuzzle().getHeight() - 1) {
      if (isLamp(r + 1, c)) {
        a++;
      }
    }
    if (r > 0) {
      if (isLamp(r - 1, c)) {
        a++;
      }
    }
    return a == this.getActivePuzzle().getClue(r, c);
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }

  public void notifyObservers(Model model) {
    for (ModelObserver m : observers) {
      m.update(this);
    }
  }

  class MyEntry {
    int row;
    int col;

    public MyEntry(int row, int col) {
      this.row = row;
      this.col = col;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) return true;
      if (!(o instanceof MyEntry)) return false;
      MyEntry other = (MyEntry) o;
      return other.col == this.col && other.row == this.row;
    }
  }
}
