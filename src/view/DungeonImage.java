package view;

import java.awt.image.BufferedImage;

public class DungeonImage {
  private BufferedImage cave;
  private int xCoord;
  private int yCoord;

  public DungeonImage(BufferedImage cave, int x, int y) {
    this.cave = cave;
    this.xCoord = x;
    this.yCoord = y;
  }

  BufferedImage getCave() {
    BufferedImage temp = this.cave;
    return temp;
  }

  int getX() {
    int temp = this.xCoord;
    return temp;
  }

  int getY() {
    int temp = this.yCoord;
    return temp;
  }
}
