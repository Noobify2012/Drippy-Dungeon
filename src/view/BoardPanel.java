package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import static com.sun.tools.doclint.Entity.image;
import static com.sun.tools.doclint.Entity.nu;

class BoardPanel extends JPanel {

  private final ReadOnlyDungeon model;
  private BufferedImage image1;
  private BufferedImage image2;
  private BufferedImage combinedImage;
  private Updater statusUpdater;
  private BufferedImage currentImage;

  public BoardPanel(ReadOnlyDungeon model) {
    this.model = model;
    this.statusUpdater = new StatusUpdater();
    this.currentImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
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
    BufferedImage emeraldNull = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
    BufferedImage emerald = null;
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
      for (int i = 0; i < currentLocation.getTreasureList().size(); i ++) {
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

    if(currentLocation != null) {
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



    int x = (currentLocation.getColumn() * 100) + 50;
    int y = (currentLocation.getRow() * 100) + 50;
    //TODO - find way to reveal board area.
    try {
      this.currentImage = overlayBoardTiles(currentImage, finalImage, 0, 0);
    } catch (IOException e) {
      e.printStackTrace();
    }
    g2d.drawImage(currentImage, 0, 0 , null);
    g2d.drawImage(finalImage, x, y, null);



    //g2d.drawImage(emerald, 100, 100, null);
//    try {
//      emPath = Path.of(new File(".").getCanonicalPath() + "dungeon-images/emerald.png");
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//
//
//    try {
//      //emerald = ImageIO.read(new File (emPath.toString()));
//      emerald = ImageIO.read(getClass().getResource("emerald.png"));
//
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
    g2d.setFont(new Font("Ubuntu", Font.BOLD, 50));
    //g2d.drawImage(emerald, 100, 100, null);
    //Image wall = new ImageIcon(GamePanel.class.getResource("wall.png")).getImage();
    //g2d.drawImage(wall, x, y, this);

    //System.out.println(System.getProperty("user.dir"));
//    try {
//      System.out.println(new File(".").getCanonicalPath());
//      pathBase = Path.of(new File(".").getCanonicalPath() + "/res/dungeon-images/blank.png");
//      emPath = Path.of(new File(".").getCanonicalPath() + "/res/dungeon-images/emerald.png");
//      System.out.println("Path to blank" + pathBase.toString());
//      Path pathToPics = Path.of(new File(".").getCanonicalPath());
//      Path pathRelative = pathToPics.relativize(pathBase);
//      System.out.println("relative path: " + pathRelative);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }

//    Path p = Paths.get(System.getProperty(("dungeon-images")));
//    System.out.println("This is path p: " + p.toString());
//    try {
//      overlay(null, "dungeon-images/bw-cells/E.png", 100);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }



    //got this from here : https://www.ryisnow.online/2021/04/java-code-sample-combine-multiple.html
//    try {
//      // VERSION 1
//      //image1 = ImageIO.read(getClass().getClassLoader().getResource(pathBase.toString()));
//      //image2 = ImageIO.read(getClass().getClassLoader().getResource(emPath.toString()));
//
//      combinedImage = new BufferedImage(800,600, BufferedImage.TYPE_INT_ARGB);
//
//      Graphics2D g2 = combinedImage.createGraphics();
//
//      g2.drawImage(image1, 0, 0, null);
//      g2.drawImage(image2, 0, 0, null);
//      // VERSION 1 END
//
//      // VERSION 2
////			image1 = ImageIO.read(getClass().getClassLoader().getResource("11C.png"));
////			image2 = ImageIO.read(getClass().getClassLoader().getResource("12C.png"));
////
////			int width = image1.getWidth() + image2.getWidth();
////			int height = Math.max(image1.getHeight(), image2.getHeight());
////
////			combinedImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
////
////			Graphics2D g = combinedImage.createGraphics();
////
////			g.drawImage(image1, 0, 0, null);
////			g.drawImage(image2, image1.getWidth(), 0, null);
//      // VERSION 2 END
//
//      g.dispose();
//
//      JLabel label = new JLabel();
//      this.add(label);
//      label.setIcon(new ImageIcon(combinedImage));
//
//      // Export the combined image to desktop
//      try {
//        ImageIO.write(combinedImage, "PNG", new File("C:\\Users\\User\\Desktop\\combinedImage.png"));
//      } catch(IOException e) {
//
//      }
//
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//    } catch (IOException e) {
//      e.printStackTrace();
//    }

//    //g2d.drawString("X", 280, 310);
//
//    //good 1st col = 80 for x
//
//
//    for (int r = 0; r < 3; r++) {
//      for (int c = 0; c < 3; c++) {
//        if (board[r][c] != null) {
//          int drawX = 0;
//          int drawY = 0;
//          if (r == 0) {
//            drawY = 110;
//          } else if ( r == 1) {
//            drawY = 210;
//          } else if (r == 2) {
//            drawY = 310;
//          }
//          if (c == 0) {
//            drawX = 80;
//          } else if (c == 1) {
//            drawX = 180;
//          } else if (c == 2) {
//            drawX = 280;
//          }
//          g2d.drawString(board[r][c].toString(), drawX, drawY);
//        }
//      }
//    }
//    //g2d.setFont(???);
//    // iterate over board, draw X and O accordingly
//    if (model.isGameOver()) {
//      if (model.getWinner() == null) {
//        g2d.drawString("It is a tie.", 50, 50);
//      } else {
//        g2d.drawString(model.getWinner() + " is the winner!", 50 , 50);
//      }
//    } else {
//      g2d.drawString("It is " + model.getTurn().toString() + "'s turn.", 50, 50);
//
//    }

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
}
