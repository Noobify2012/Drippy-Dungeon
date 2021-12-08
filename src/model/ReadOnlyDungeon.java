package model;

import java.awt.event.ActionListener;
import java.util.List;

import model.Cave;
import model.Edge;

/**
 * The interface for our view class.
 */
public interface ReadOnlyDungeon {


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

  /**Gets the seed of the dungeon, used for restarting the dungeon.
   *
   * @return the seed of the dungeon as an integer.
   */
  int getSeed();

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

  /**Gets an abbreviated status update not as a string but as a custom data structure.
   *
   * @return the status update as a status updater object, which is used for the status pane.
   */
  Updater getStatusUpdater();

  /**Gets the percentage of treasure that is in the dungeon
   *
   * @return An integer of the percentage of treasure.
   */
  int getTreasurePer();

  /**Gets the difficulty of the dungeon.
   *
   * @return An integer expressing the dungeon's difficulty.
   */
  int getDifficulty();

  /**Gets the boolean if a dungeon wraps or not.
   *
   * @return A boolean where true means the dungeon wraps and false means it doesn't.
   */
  boolean getWraps();

  /**Gets the interconnectivity level of a dungeon.
   *
   * @return The interconnectivity expressed as an integer.
   */
  int getInterConnect();

  /**Gets the current location of the player.
   *
   * @return A copy of the cave where the player is currently located.
   */
  Cave getPlayerLocation();

  /**Returns the smell of the cave based on its proxemity to a monster.
   *
   * @return the smell expressed as an int. If the int is 1 a monster is 2 spaces away. If the
   *          smell is 2 or greater a monster is either one spot away or there are multiple
   *          monsters within 2 spots.
   */
  int checkSmell();
}
