package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private final int[][] board;
  //    first dimension is rows, second is cols
  //    0 indicates the cell is a clue cell with value "0"
  //    1 indicates the cell is a clue cell with value "1"
  //    2 indicates the cell is a clue cell with value "2"
  //    3 indicates the cell is a clue cell with value "3"
  //    4 indicates the cell is a clue cell with value "4"
  //    5 indicates the cell is a wall cell
  //    6 indicates the cell is a corridor cell
  public PuzzleImpl(int[][] board) {
    this.board = board;
  }

  @Override
  public int getWidth() {
    return board[0].length;
  }

  @Override
  public int getHeight() {
    return board.length;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (c >= this.getWidth() || r >= this.getHeight() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    int x = board[r][c];
    if (x >= 0 && x <= 4) {
      return CellType.CLUE;
    } else if (x == 5) {
      return CellType.WALL;
    } else if (x == 6) {
      return CellType.CORRIDOR;
    }
    return null;
  }

  @Override
  public int getClue(int r, int c) {
    if (c >= this.getWidth() || r >= this.getHeight() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    int x = board[r][c];
    if (x >= 0 && x <= 4) {
      return x;
    }
    throw new IllegalArgumentException();
  }
}
