package view;

import java.awt.image.BufferedImage;

public class DungeonImage {
  private BufferedImage cave;
  private int x;
  private int y;

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
