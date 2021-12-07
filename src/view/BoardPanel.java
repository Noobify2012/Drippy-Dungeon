package view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.Cave;
import model.Edge;
import model.ReadOnlyDungeon;

import static com.sun.tools.doclint.Entity.image;

class BoardPanel extends JPanel {

  private final ReadOnlyDungeon model;

  public BoardPanel(ReadOnlyDungeon model) {
    this.model = model;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    Cave[][] board = model.getGameBoard();
    int rows = model.getGameBoardRows();
    int col = model.getGameBoardCols();
    List<Edge> edgeList = model.getFinalEdgeList();

    //System.out.println(System.getProperty("user.dir"));
    try {
      System.out.println(new File(".").getCanonicalPath());
      Path pathBase = Path.of(new File(".").getCanonicalPath() + "\\dungeon-images\\blank.png");
      System.out.println("Path to blank" + pathBase.toString());
      Path pathToPics = Path.of(new File(".").getCanonicalPath());
      Path pathRelative = pathToPics.relativize(pathBase);
      System.out.println("relative path: " + pathRelative);
    } catch (IOException e) {
      e.printStackTrace();
    }

//    Path p = Paths.get(System.getProperty(("dungeon-images")));
//    System.out.println("This is path p: " + p.toString());
//    try {
//      overlay(null, "dungeon-images/bw-cells/E.png", 100);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }

    for (int r = 0; r < rows ; r++) {
      for (int c = 0; c < col; c ++) {
        //g2d.drawImage("dungeon-image/bw-cells/e.png", (board[r][c].getRow() * 100) + 100, (board[r][c].getColumn() * 100) + 100, null);
      }
    }
    g2d.setFont(new Font("Ubuntu", Font.BOLD, 50));
    g2d.drawLine(150, 50, 150, 350);
    g2d.drawLine(250, 50, 250, 350);
    g2d.drawLine(50, 150, 350, 150);
    g2d.drawLine(50, 250, 350, 250);

    g2d.drawLine(50, 400, 400, 400);

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

  private BufferedImage overlay(BufferedImage starting, String fpath, int offset) throws IOException {
    BufferedImage overlay = ImageIO.read(new File(fpath));
    int w = Math.max(starting.getWidth(), overlay.getWidth());
    int h = Math.max(starting.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(overlay, offset, offset, null);
    return combined;
  }
}
