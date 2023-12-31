package model;

import java.util.List;

/**
 * This represents a location which is made up of a x and y location. Currently, it doesn't
 * have any public methods.
 */
public interface Location {

  /**Gets the players treasure list.
   *
   * @return a deep copy array list of all the treasure that the cave has.
   */
  List<Treasure> getTreasureList();

  /**Gets the list of neighbors for the current cave.
   *
   * @return an ArrayList of the neighbors of the current cave.
   */
  List<Integer> getNeighbors();

  /**Gets the size of the arrow list in a given cave.
   *
   * @return the size of the arrow list as an integer.
   */
  int getArrowListSize();

  /**Gets the size of the monster list in a given cave.
   *
   * @return returns the size of the monster list as an integer. Possible values are 0 and 1.
   */
  int getMonsterListSize();

  boolean getPitStatus();

  List<Direction> getDirectionList();

  int getRow();

  int getColumn();

  int getLuckyListSize();

}
