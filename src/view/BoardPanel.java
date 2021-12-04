package view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;

import model.Cave;
import model.ReadOnlyDungeon;

class BoardPanel extends JPanel {

  private final ReadOnlyDungeon model;
  private JMenuBar menuBar;
  private JMenu menu;
  private JMenuItem menuQuit, buildDungeon, restartDungeon;

  public BoardPanel(ReadOnlyDungeon model) {
    this.model = model;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    Cave[][] board = model.getGameBoard();
    g2d.setFont(new Font("Ubuntu", Font.BOLD, 50));
    g2d.drawLine(150, 50, 150, 350);
    g2d.drawLine(250, 50, 250, 350);
    g2d.drawLine(50, 150, 350, 150);
    g2d.drawLine(50, 250, 350, 250);

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
}
