package view;

public class DungeonBuildStructureImpl implements BuildStructure {
  private boolean wraps;
  private int rows;
  private int cols;
  private int interconnect;
  private int treasure;
  private int diff;

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
