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
}
