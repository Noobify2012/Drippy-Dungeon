package view;

import java.awt.*;

import javax.swing.*;

import model.Cave;
import model.ReadOnlyDungeon;

class StatusPanel extends JPanel {
  private final ReadOnlyDungeon model;

  public StatusPanel(ReadOnlyDungeon model) {
    this.model = model;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    Cave[][] board = model.getGameBoard();
    g2d.setFont(new Font("Ubuntu", Font.BOLD, 30));
    g2d.drawString("This is my status panel", 100, 50);
  }
}
