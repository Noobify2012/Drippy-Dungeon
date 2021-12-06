package model;

import java.util.List;

//import view.ReadOnlyDungeon;

/**This represents a dungeon which is where the player navigates from a start point to an end point.
 *
 */
public interface Dungeon extends ReadOnlyDungeon {

  /**A helper to get the number of rows in the game board.
   *
   * @return the number of rows in the game board as an integer.
   */
  int getGameBoardRows();

  /**A helper to get the number of columns in the game board.
   *
   * @return the number of columns in the game board as an integer.
   */
  int getGameBoardCols();

  /** A helper to set up the dungeon and run the player through their predesignated path.
   *
   */
  String getDungeon();

  /**A getter method that returns the list of final edges after Kruskal's has been run.
   *
   * @return an arraylist of the edges which were selected to act as the paths in the dungeon.
   */
  List<Edge> getFinalEdgeList();

  /**A getter that returns a copy of the game board data.
   *
   * @return a deep copy of the game board.
   */
  Cave[][] getGameBoard();

  /**A check to see if the game termination criteria has been met.
   *
   * @return a boolean where true means indicates one of the game termination conditions has been
   *          met. Either the player has died, reached the final cave safely, or quit the game.
   */
  boolean isGameOver();


  /**Attempting to move a player given a direction. If it is not given a legal direction from the
   * direction enum it will throw an Illegal Argument Exception
   *
   * @param direction A direction from the direction Enum
   * @return a string documenting the players move if the move is legal.
   */
  String movePlayer(Direction direction);

  /**A getter that returns the boolean value of whether a dungeon is wrapping or not.
   *
   * @return a boolean, if false it is not a wrapping dungeon, else true means wrapping.
   */
  boolean getWrapping();

  /**A player shooting an arrow if they have any remaining.
   *
   * @param distance given as an integer, must be zero or greater.
   * @param direction given as an element of the direction enum.
   * @return a string indicating the result of the shot.
   */
  String shootArrow(int distance, Direction direction);

  /**A player attempting to pick up an item(treasure, arrow, or both.
   *
   * @param option an integer value of the item that would like to be attempted to be picked up. 0
   *               is for picking up arrows, 1 for picking up treasure, 2 for both arrows and treasure.
   * @return a string indicating the result of the player attempting to pick up.
   */
  String pickUpItem(int option);

  /**A player quiting the game. This method only works after the dungeon has been built and the
   * player has been dropped at the starting position.
   *
   * @return A String indicating that the game has been quit.
   */
  String quitGame();

  int getSeed();

}
