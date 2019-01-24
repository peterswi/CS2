// file: Player*.java
// authors: Erin Corcoran & Will Peters
// date: April 29, 2018
//
// purpose: A client which makes moves in dots & boxes.


package players.player1;

import players.*;
import board.*;
import util.*;


import java.util.*;
import javafx.scene.paint.Color;

public class Player18 implements Player {

    private DBG dbg;

    public Player18() {
        dbg = new DBG(DBG.PLAYERS, "Player1");
    }

    public Line makePlay(Board board, Line oppPlay, long timeRemaining) {

      if (board.gameOver()) return null;

      int numRows = numberOfRows(board);

      System.out.println("Number of Rows is: " + numRows);

      // if there are any squares with 3 sides filled, we will fill in the fourth side
      Set<Square> threeSidesSquares = board.squaresWithMarkedSides(3);

      if (!threeSidesSquares.isEmpty()) {
        // scan open sides, see if box next to it has > 1 side filled
        Line openLine = null;
        for (Square square : threeSidesSquares) {
          int row = square.getRow();
          int col = square.getCol();
          Set<Line> openSide = square.openLines(); // will return the set of open sides
          openLine = openSide.iterator().next();
          if (checkThreeSideNeighbor(row, col, openLine, board, numRows)) return openLine;
        }
      return openLine;
      }

      Set<Square> noSidesSquares = board.squaresWithMarkedSides(0);
      Set<Square> oneSideSquares = board.squaresWithMarkedSides(1);

      if ((!noSidesSquares.isEmpty()) || (!oneSideSquares.isEmpty())) {

        Line horizontalLine = scanHorizontals(board, noSidesSquares, oneSideSquares);

        if (horizontalLine != null) return horizontalLine;
        else {
        // if there are squares with < 2 sides marked, but the horizontal line rule isn't best play
        if (!noSidesSquares.isEmpty()) return chooseRandomLine(noSidesSquares, board);
        else return chooseRandomLine(oneSideSquares, board);
      }
    }
      else {
        // this means that we only have squares with two sides left
        Set<Square> squaresWithTwoSides = board.squaresWithMarkedSides(2);
        return chooseRandomLine(squaresWithTwoSides, board);
      }
    }

    // this function checks the neighboring boxes to determine which square with 3 sides is the best
    // to close off (how we can get 2 squares out of 1 move)
    private static boolean checkThreeSideNeighbor(int row, int col, Line openLine, Board board, int numRows) {

      if (openLine.getSide().toString().equals("NORTH")) {
        if (row-1 < 0) return false;
        int newRow = row-1;
        Square newSquare = board.getSquare(newRow, col);
        if (newSquare.hasNMarkedSides(2) || newSquare.hasNMarkedSides(3)) return true;
      }
      else if (openLine.getSide().toString().equals("SOUTH")) {
        if (row+1 >= numRows) return false;
        int newRow = row+1;
        Square newSquare = board.getSquare(newRow, col);
        if (newSquare.hasNMarkedSides(2) || newSquare.hasNMarkedSides(3)) return true;
      }
      else if (openLine.getSide().toString().equals("EAST")) {
        if (col+1 >= numRows) return false;
        int newCol = col+1;
        Square newSquare = board.getSquare(row, newCol);
        if (newSquare.hasNMarkedSides(2) || newSquare.hasNMarkedSides(3)) return true;
      }
      else if (openLine.getSide().toString().equals("WEST")) {
        if (col-1 < 0) return false;
        int newCol = col-1;
        Square newSquare = board.getSquare(row, newCol);
        if (newSquare.hasNMarkedSides(2) || newSquare.hasNMarkedSides(3)) return true;
      }
    return false;
    }

  private static int numberOfRows(Board board) {
    int counter = 0;
    Square[][] arrayBoard = board.toArray();
    for (Square[] square : arrayBoard) {
      counter++;
    }
  return counter;
  }

  // scans the horizontals and fills in the north or south side on all boxes with 0 or 1 side filled in
  // and where northern neighbor has <2 sides filled
  private static Line scanHorizontals(Board board, Set<Square> noSidesSquares, Set<Square> oneSideSquares) {
    int numRows = numberOfRows(board);
    for (int r = 0; r < numRows; r++) {
      for (int c = 0; c < numRows; c++) {
        Square currentSquare = board.getSquare(r, c);
        if (noSidesSquares.contains(currentSquare) || (oneSideSquares.contains(currentSquare))) {
        Set<Line> squareLines = currentSquare.openLines();
        if (r == numRows-1 ) { // means we are in the last row
          for (Line currentLine : squareLines) {
            if (currentLine.getSide().toString().equals("NORTH")) {
              if (checkNorthNeighbor(r, c, board)) return currentLine;
            }
            else if (currentLine.getSide().toString().equals("SOUTH")) return currentLine;
            }
        }
        else {
          for (Line currentLine : squareLines) {
            if (currentLine.getSide().toString().equals("NORTH")){
              if (checkNorthNeighbor(r, c, board)) return currentLine; } }
            }
        }
      }
    }
    return null;
  }

  // function that tells us if the northern neighbor has 0 or 1 sides marked
  // returing true tells us that there are only 0 or 1 sides marked
  private static boolean checkNorthNeighbor(int row, int col, Board board) {
    Square option = board.getSquare(row, col);
    if (row == 0) return true;
    Square north = board.getSquare(row-1, col);
    if (!north.hasNMarkedSides(2) && !north.hasNMarkedSides(3)) {
      return true;
    }
    else return false;
  }

  private Line chooseRandomLine(Set<Square> candidates, Board board) {
      List<Square> shuffledCandidates = new ArrayList<Square>(candidates);
      Collections.shuffle(shuffledCandidates);
      for (Square square : shuffledCandidates) {
          Iterator<Line> openLines = square.openLines().iterator();
          while (openLines.hasNext()) {
              Line line = openLines.next();
              return line;
            }
        }
      return null;
      }


  public String teamName() { return "2Chainz"; }
  public String teamMembers() { return "Will Peters & Erin Corcoran"; }
  public Color getSquareColor() { return Util.PLAYER1_COLOR; }
  public Color getLineColor() { return Util.PLAYER1_LINE_COLOR; }
  public int getId() { return 18; }
  public String toString() { return teamName(); }


}
