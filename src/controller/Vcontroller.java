package controller;

public interface Vcontroller extends Controller {

  /**
   * Restarts the current dungeon.
   */
  void restartDungeon();

  /**
   * Handle an action in a single cell of the board, such as to make a move.
   *
   * @param row the row of the clicked cell
   * @param col the column of the clicked cell
   */
  void handleCellClick(int row, int col);


}
