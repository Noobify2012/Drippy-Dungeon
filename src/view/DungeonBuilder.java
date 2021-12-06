package view;

/**
 * Data structure for building new dungeons in the view.
 */
public interface DungeonBuilder {

  /**Getter for the value of wrapping that the user inputs.
   *
   * @return A boolean where true means the new dungeon should wrap and false should not.
   */
  boolean getWrapping();

  /**Getter for number of rows of the new dungeon.
   *
   * @return An integer for the number of rows that should be in the new dungeon.
   */
  int getRow();

  /**Getter for number of columns of the new dungeon.
   *
   * @return An integer for the number of columns that should be in the new dungeon.
   */
  int getCol();

  /**Getter for number of Otyughs in the new dungeon.
   *
   * @return An integer for the number of Otyughs that should be in the new dungeon.
   */
  int getDifficulty();

  /**Getter for percentage of caves that should have treasure and caves and tunnels that should
   * have arrows in the new dungeon.
   *
   * @return An integer between 0 and 100 for the percentage of arrows and treasure in the new
   *         dungeon.
   */
  int getTreas();

  /**Getter for level of interconnectivity in the new dungeon.
   *
   * @return An integer for the level of interconnectivity that should be in the new dungeon.
   */
  int getInter();


}
