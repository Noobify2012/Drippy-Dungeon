package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.Cave;
import model.Direction;
import model.Edge;
import model.ReadOnlyDungeon;
import model.StatusUpdater;
import model.Treasure;
import model.TreasureImpl;
import model.Updater;

class BoardPanel extends JPanel {

  private ReadOnlyDungeon model;
  private Updater statusUpdater;
  private BufferedImage currentImage;
  private List<DungeonImage> dungeonList;

  public BoardPanel(ReadOnlyDungeon model) {
    this.model = model;
    this.statusUpdater = new StatusUpdater();
    this.dungeonList = new ArrayList<>();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    Cave[][] board = model.getGameBoard();
    int rows = model.getGameBoardRows();
    int col = model.getGameBoardCols();
    List<Edge> edgeList = model.getFinalEdgeList();
    Cave currentLocation = model.getPlayerLocation();
    Path pathBase = null;
    Path emPath = null;
//    g.drawImage()
    BufferedImage currentLoc = null;
    BufferedImage finalImage = null;
    String directoryPath = "/res/dungeon-images/";

    ///Users/Owner/Documents/CS5010/Project5_Graphical_Adventure_Game/src/view/emerald.png

    try {
      pathBase = Path.of(new File(".").getCanonicalPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
    String pathToAppend = "/res/dungeon-images/emerald.png";
    //get cave
    if (statusUpdater.getDirectionList() != null && !model.getPlayerLocation().getPitStatus()) {
      String cavePath = getCavePath(statusUpdater.getDirectionList());
      System.out.println("Cave extension to put out: " + cavePath);
      System.out.println("full path: " + pathBase + directoryPath + cavePath);
      try {
        currentLoc = ImageIO.read(new File(pathBase + directoryPath + cavePath));
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      try {
        currentLoc = ImageIO.read(new File(pathBase + directoryPath + "blank.png"));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    finalImage = currentLoc;

    //check for treasure and add if necessary
    if (currentLocation.getTreasureList().size() != 0) {
      for (int i = 0; i < currentLocation.getTreasureList().size(); i++) {
        if (currentLocation.getTreasureList().get(i).getName().equalsIgnoreCase("Ruby")) {
          try {
            finalImage = overlay2D(finalImage, pathBase + directoryPath + "ruby.png", 0, 0);
          } catch (IOException e) {
            e.printStackTrace();
          }
          //add ruby to image offset is -2
        } else if (currentLocation.getTreasureList().get(i).getName().equalsIgnoreCase("Diamond")) {
          try {
            finalImage = overlay2D(finalImage, pathBase + directoryPath + "diamond.png", 5, 35);
          } catch (IOException e) {
            e.printStackTrace();
          }
          //add diamond to image offset is 2
        } else if (currentLocation.getTreasureList().get(i).getName().equalsIgnoreCase("Sapphire")) {
          //add sapphire/emerald offset is 0
          try {
            finalImage = overlay2D(finalImage, pathBase + directoryPath + "emerald.png", 35, 30);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }

    if (currentLocation != null) {
      if (currentLocation.getArrowListSize() > 0) {
        try {
          finalImage = overlay2D(finalImage, pathBase + directoryPath + "arrow-white.png", 10, 10);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    if (model.checkSmell() == 1) {
      try {
        finalImage = overlayItems(finalImage, pathBase + directoryPath + "stench01.png", 0);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else if (model.checkSmell() >= 2) {
      try {
        finalImage = overlayItems(finalImage, pathBase + directoryPath + "stench02.png", 0);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    if (model.getPlayerLocation().getMonsterListSize() > 0) {
      try {
        finalImage = overlayItems(finalImage, pathBase + directoryPath + "otyugh.png", 0);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    //leprechaun image citation: https://pixers.us/posters/leprechaun-30230709
    if (model.getPlayerLocation().getLuckyListSize() > 0) {
      try {
        finalImage = overlayItems(finalImage, pathBase + directoryPath + "lucky.png", 0);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    //rock image citation: https://www.google.com/search?q=falling+rock&tbm=isch&chips=q:falling
    // +rock,g_1:clipart:sRh-rYy3tFI%3D&hl=en&sa=X&ved=2ahUKEwiapsrT4NT0AhW5BlkFHWb9DogQ4lYoAHoECAEQEQ&biw=2543&bih=1278#imgrc=hj0HpQPDYGvjMM
    if (model.getPitProx()) {
      try {
        finalImage = overlayItems(finalImage, pathBase + directoryPath + "rocks.png", 0);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    int x = (currentLocation.getColumn() * 100);
    int y = (currentLocation.getRow() * 100);

    if (finalImage != null) {
      DungeonImage temp = new DungeonImage(finalImage, x, y);
      this.dungeonList.add(temp);
      for (int i = 0; i < this.dungeonList.size(); i++) {
        g2d.drawImage(this.dungeonList.get(i).getCave(), this.dungeonList.get(i).getX(), this.dungeonList.get(i).getY(), null);
      }
    }
    g2d.setFont(new Font("Ubuntu", Font.BOLD, 50));
  }


  private BufferedImage overlayItems(BufferedImage starting, String fpath, int offset) throws IOException {
    BufferedImage overlay = ImageIO.read(new File(fpath));
    int w = Math.max(starting.getWidth(), overlay.getWidth());
    int h = Math.max(starting.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(overlay, offset, offset, null);
    return combined;
  }

  private BufferedImage overlay2D(BufferedImage starting, String fpath, int xOffset, int yOffset) throws IOException {
    BufferedImage overlay = ImageIO.read(new File(fpath));
    int w = Math.max(starting.getWidth(), overlay.getWidth());
    int h = Math.max(starting.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(overlay, xOffset, yOffset, null);
    return combined;
  }

  private BufferedImage overlayBoardTiles(BufferedImage starting, BufferedImage adding, int xOffset, int yOffset) throws IOException {
    BufferedImage overlay = adding;
    int w = Math.max(starting.getWidth(), overlay.getWidth());
    int h = Math.max(starting.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(overlay, xOffset, yOffset, null);
    return combined;
  }

  private String getCavePath(List<Direction> directions) {
    String finalPath = "";
    if (directions.size() == 4) {
      finalPath = "color-cells/NSEW.png";
    } else if (directions.size() == 3) {
      if (directions.contains(Direction.NORTH) && directions.contains(Direction.EAST)
              && directions.contains(Direction.SOUTH)) {
        finalPath = "color-cells/NSE.png";
      } else if (directions.contains(Direction.NORTH) && directions.contains(Direction.EAST)
              && directions.contains(Direction.WEST)) {
        finalPath = "color-cells/NEW.png";
      } else if (directions.contains(Direction.NORTH) && directions.contains(Direction.SOUTH)
              && directions.contains(Direction.WEST)) {
        finalPath = "color-cells/NSW.png";
      } else if (directions.contains(Direction.SOUTH) && directions.contains(Direction.EAST)
              && directions.contains(Direction.WEST)) {
        finalPath = "color-cells/SEW.png";
      }
    } else if (directions.size() == 2) {
      System.out.println(directions);
      if (directions.contains(Direction.EAST) && directions.contains(Direction.WEST)) {
        finalPath = "color-cells/EW.png";
      } else if (directions.contains(Direction.NORTH) && directions.contains(Direction.EAST)) {
        finalPath = "color-cells/NE.png";
      } else if (directions.contains(Direction.NORTH) && directions.contains(Direction.SOUTH)) {
        finalPath = "color-cells/NS.png";
      } else if (directions.contains(Direction.NORTH) && directions.contains(Direction.WEST)) {
        finalPath = "color-cells/NW.png";
      } else if (directions.contains(Direction.SOUTH) && directions.contains(Direction.EAST)) {
        finalPath = "color-cells/SE.png";
      } else if (directions.contains(Direction.SOUTH) && directions.contains(Direction.WEST)) {
        finalPath = "color-cells/SW.png";
      }
    } else if (directions.size() == 1) {
      if (directions.contains(Direction.EAST)) {
        finalPath = "color-cells/E.png";
      } else if (directions.contains(Direction.NORTH)) {
        finalPath = "color-cells/N.png";
      } else if (directions.contains(Direction.SOUTH)) {
        finalPath = "color-cells/S.png";
      } else if (directions.contains(Direction.WEST)) {
        finalPath = "color-cells/W.png";
      }
    }
    return finalPath;
  }

  void getStatusUpdater(Updater statusUpdate) {
    this.statusUpdater = statusUpdate;
  }

  void clearBoard() {
    this.dungeonList = new ArrayList<>();
//    this.currentImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
  }

  void setModel(ReadOnlyDungeon model) {
      if (model == null) {
        throw new IllegalArgumentException("Model can't be null");
      }
      this.statusUpdater = null;
      this.model = model;
      this.dungeonList = new ArrayList<>();
      this.invalidate();
      this.revalidate();
      this.repaint();
      this.setVisible(true);
    }
  }

