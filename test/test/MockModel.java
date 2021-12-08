package test;

import java.util.List;

import model.Cave;
import model.Direction;
import model.Edge;
import model.Dungeon;
import model.Updater;

/**
 * This is the mock model for mocking the controller.
 */
public class MockModel implements Dungeon {


  private StringBuilder log;

  /** The constructor for the mockmodel which uses the log and uCode or unique code for testing the
   *  controller.
   *
   * @param log For recording which methods were called to test the controller.
   * @param uCode the unique code returned at the end of the method to compare with the out at the
   *              end.
   */
  public MockModel(StringBuilder log, int uCode) {
    this.log = log;
  }

  /**
   * A helper to get the number of rows in the game board.
   *
   * @return the number of rows in the game board as an integer.
   */
  @Override
  public int getGameBoardRows() {
    return 0;
  }

  /**
   * A helper to get the number of columns in the game board.
   *
   * @return the number of columns in the game board as an integer.
   */
  @Override
  public int getGameBoardCols() {
    return 0;
  }

  /**
   * A helper to set up the dungeon and run the player through their predesignated path.
   */
  @Override
  public String getDungeon() {
    log.append("Got the dungeon\n");
    return "123";
  }

  /**
   * A getter method that returns the list of final edges after Kruskal's has been run.
   *
   * @return an arraylist of the edges which were selected to act as the paths in the dungeon.
   */
  @Override
  public List<Edge> getFinalEdgeList() {
    return null;
  }

  /**
   * A getter that returns a copy of the game board data.
   *
   * @return a deep copy of the game board.
   */
  @Override
  public Cave[][] getGameBoard() {
    return new Cave[0][];
  }

  int iter = 0;

  @Override
  public boolean isGameOver() {
    boolean retBool = false;
    iter++;
    if (iter > 7) {
      retBool = true;
    }
    return retBool;
  }

  @Override
  public String movePlayer(Direction direction) {
    log.append("Input: move " + direction + "\n");
    return "movePlayer";
  }

  @Override
  public boolean getWrapping() {
    return false;
  }

  @Override
  public String shootArrow(int distance, Direction direction) {
    log.append("Input: shoot " + distance + " " + direction + "\n");
    return "shootArrow";
  }

  @Override
  public String pickUpItem(int option) {
    log.append("Input: pickup " + option + "\n");
    return "pickUpItem";
  }

  @Override
  public String quitGame() {
    return "quitGame";
  }

  @Override
  public int getSeed() {
    return 0;
  }

  /**
   * Gets an abbreviated status update not as a string but as a custom data structure.
   *
   * @return the status update as a status updater object, which is used for the status pane.
   */
  @Override
  public Updater getStatusUpdater() {
    return null;
  }

  /**
   * Gets the percentage of treasure that is in the dungeon
   *
   * @return An integer of the percentage of treasure.
   */
  @Override
  public int getTreasurePer() {
    return 0;
  }

  /**
   * Gets the difficulty of the dungeon.
   *
   * @return An integer expressing the dungeon's difficulty.
   */
  @Override
  public int getDifficulty() {
    return 0;
  }

  /**
   * Gets the boolean if a dungeon wraps or not.
   *
   * @return A boolean where true means the dungeon wraps and false means it doesn't.
   */
  @Override
  public boolean getWraps() {
    return false;
  }

  /**
   * Gets the interconnectivity level of a dungeon.
   *
   * @return The interconnectivity expressed as an integer.
   */
  @Override
  public int getInterConnect() {
    return 0;
  }

  /**
   * Gets the current location of the player.
   *
   * @return A copy of the cave where the player is currently located.
   */
  @Override
  public Cave getPlayerLocation() {
    return null;
  }

  /**
   * Returns the smell of the cave based on its proxemity to a monster.
   *
   * @return the smell expressed as an int. If the int is 1 a monster is 2 spaces away. If the
   * smell is 2 or greater a monster is either one spot away or there are multiple
   * monsters within 2 spots.
   */
  @Override
  public int checkSmell() {
    return 0;
  }


}
