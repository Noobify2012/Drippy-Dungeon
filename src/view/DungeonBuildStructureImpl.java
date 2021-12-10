package view;

/**
 * A custom data structure that takes in the values entered to create a new dungeon in the graphical
 * version of the dungeon adventure game.
 */
public class DungeonBuildStructureImpl implements BuildStructure {
  private boolean wraps;
  private int rows;
  private int cols;
  private int interconnect;
  private int treasure;
  private int diff;

  /**The constructor for the dungeon builder data structure.
   *
   * @param wraps boolean where false is used to indicate a non-wrapping dungeon and true indicates
   *              wrapping.
   * @param rows a positive integer greater than 0 representing the number of rows to be built.
   * @param cols a positive integer greater than 0 representing the number of columns to be built.
   * @param interconnect an integer 0 or greater representing the level of interconnectivity to be
   *                    built.
   * @param treasure an integer betweeon 0 and 100 representing the percentage of caves to have
   *                 treasure.
   * @param diff a positive integer greater than 0 representing the number of monsters to be added
   *             to caves.
   */
  public DungeonBuildStructureImpl(boolean wraps, int rows, int cols, int interconnect,
                                   int treasure, int diff) {
    this.wraps = wraps;
    this.rows = rows;
    this.cols = cols;
    this.interconnect = interconnect;
    this.treasure = treasure;
    this.diff = diff;
    if (rows <= 0 || cols <= 0 || interconnect < 0 || treasure < 0 || diff < 1) {
      throw new IllegalArgumentException("illegal input from the jmenu");
    }
  }


  @Override
  public Boolean getWraps() {
    boolean temp = this.wraps;
    return temp;
  }

  @Override
  public int getRows() {
    int temp = this.rows;
    return temp;
  }

  @Override
  public int getCols() {
    int temp = this.cols;
    return temp;
  }

  @Override
  public int getDiff() {
    int temp = this.diff;
    return temp;
  }

  @Override
  public int getTreas() {
    int temp = this.treasure;
    return temp;
  }

  @Override
  public int getInter() {
    int temp = this.interconnect;
    return temp;
  }
}
