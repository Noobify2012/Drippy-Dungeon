package view;

public class DungeonBuilderImpl implements DungeonBuilder {
  private boolean wrapping;
  private int row;
  private int col;
  private int inter;
  private int treasure;
  private int difficulty;

  public DungeonBuilderImpl(boolean wrapping, int row, int col, int inter, int treasure,
                            int difficulty) {
    this.wrapping = wrapping;
    this.row = row;
    this.col = col;
    this.inter = inter;
    this.treasure = treasure;
    this.difficulty = difficulty;
    if (row < 1) {
      throw new IllegalArgumentException("Must have at least 1 row");
    }

    if (col < 1) {
      throw new IllegalArgumentException("Must have at least 1 col");
    }

    if (inter < 0) {
      throw new IllegalArgumentException("Can not have interconnectivity less than 0");
    }

    if (treasure < 0 || treasure > 100) {
      throw new IllegalArgumentException("Cannot have treasure less than 0 or great than 100.");
    }

    if (difficulty < 1) {
      throw new IllegalArgumentException("Must have difficulty of 1 or more");
    }
  }

  /**
   * Getter for the value of wrapping that the user inputs.
   *
   * @return A boolean where true means the new dungeon should wrap and false should not.
   */
  @Override
  public boolean getWrapping() {
    boolean wrapper = this.wrapping;
    return wrapper;
  }

  /**
   * Getter for number of rows of the new dungeon.
   *
   * @return An integer for the number of rows that should be in the new dungeon.
   */
  @Override
  public int getRow() {
    int rows = this.row;
    return rows;
  }

  /**
   * Getter for number of columns of the new dungeon.
   *
   * @return An integer for the number of columns that should be in the new dungeon.
   */
  @Override
  public int getCol() {
    int cols = this.col;
    return cols;
  }

  /**
   * Getter for number of Otyughs in the new dungeon.
   *
   * @return An integer for the number of Otyughs that should be in the new dungeon.
   */
  @Override
  public int getDifficulty() {
    int diff = this.difficulty;
    return diff;
  }

  /**
   * Getter for percentage of caves that should have treasure and caves and tunnels that should
   * have arrows in the new dungeon.
   *
   * @return An integer between 0 and 100 for the percentage of arrows and treasure in the new
   *         dungeon.
   */
  @Override
  public int getTreas() {
    int treas = this.treasure;
    return treas;
  }

  /**
   * Getter for level of interconnectivity in the new dungeon.
   *
   * @return An integer for the level of interconnectivity that should be in the new dungeon.
   */
  @Override
  public int getInter() {
    int intercon = this.inter;
    return intercon;
  }
}
