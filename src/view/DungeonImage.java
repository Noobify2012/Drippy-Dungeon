package view;

import java.awt.image.BufferedImage;

/**
 * Data structure used to keep a dungeon image with its associated location data. This is then
 * stored in an array list to keep track of what dungeons have been visited so that they are
 * displayed to the user.
 */
public class DungeonImage {
  private BufferedImage cave;
  private int x;
  private int y;

  /**Constructor for a dungeon image.
   *
   * @param cave a buffered image of the cave that has been visited.
   * @param x the x coordinate of the cave.
   * @param y the y coordinate of the cave
   */
  public DungeonImage(BufferedImage cave, int x, int y) {
    this.cave = cave;
    this.x = x;
    this.y = y;
  }

  BufferedImage getCave() {
    BufferedImage temp = this.cave;
    return temp;
  }

  int getX() {
    int temp = this.x;
    return temp;
  }

  int getY() {
    int temp = this.y;
    return temp;
  }
}
