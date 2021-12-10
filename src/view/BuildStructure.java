package view;

/**
 * This represents a data structure for building new dungeons in the graphical version of the
 * adventure game. This structure is passed from the view to controller after collecting user input
 * when they enter their specifications after clicking the build button in the build new dungeon
 * pop up.
 */
public interface BuildStructure {


  /**Getter that returns the value of the selected JCheckbox either wrapping or non-wrapping.
   *
   * @return A boolean, either false for non-wrapping or true for wrapping.
   */
  Boolean getWraps();

  /**Getter that returns the value of the user input for the number of rows.
   *
   * @return a positive none zero integer.
   */
  int getRows();

  /**Getter that returns the value of the user input for the number of columns.
   *
   * @return a positive none zero integer.
   */
  int getCols();

  /**Getter that returns the value of the user input for the number of monsters known as
   * difficulty.
   *
   * @return a positive none zero integer.
   */
  int getDiff();

  /**Getter that returns the value of the user input for the percentage of caves with treasure.
   *
   * @return an integer between 0 and 100.
   */
  int getTreas();

  /**Getter that returns the value of the user input for the level of interconnectivity.
   *
   * @return an integer that is greater than or equal to 0.
   */
  int getInter();
}
