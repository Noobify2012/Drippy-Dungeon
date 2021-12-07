package view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import model.Cave;
import model.Direction;
import model.ReadOnlyDungeon;
import model.Treasure;
import model.Updater;

class StatusPanel extends JPanel {
  private final ReadOnlyDungeon model;
  private String statusString;
  private String locationString;
  private int arrowCount;
  private int rubyCount;
  private int sapphireCount;
  private int diamondCount;
  private int smellCount;
  private int caveArrows;
  private List<Direction> directionList;
  private List<Treasure> caveTreasure;
  private Updater statusUpdater;


  public StatusPanel(ReadOnlyDungeon model) {
    this.model = model;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    Cave[][] board = model.getGameBoard();
    g2d.setFont(new Font("Ubuntu", Font.BOLD, 25));
    if (statusString != null && statusString.length() <= 65) {
      g2d.drawString(statusString, 100, 250);
    } else if (statusString != null && statusString.length() > 65) {

    }
    if (statusUpdater != null) {
      if (model.isGameOver()) {
        g2d.drawString("GAME OVER", 100, 10);
      }
      g2d.drawString("Player is in " + this.locationString, 100, 20);
      g2d.drawString("Player can go " + this.directionList, 100, 45);
      g2d.drawString("Arrows Remaining " + this.arrowCount, 100, 70);
      g2d.drawString("Rubies Collected " + this.rubyCount, 100, 95);
      g2d.drawString("Diamonds Collected " + this.diamondCount, 100, 120);
      g2d.drawString("Sapphires Collected " + this.sapphireCount, 100, 145);
      if (this.caveTreasure.size() == 0) {
        g2d.drawString("No treasure here.", 100, 170);
      } else {
        g2d.drawString("Treasure in this space" + this.caveTreasure, 100, 170);
      }
      if (this.caveArrows == 0) {
        g2d.drawString("No arrows here.", 100, 195);
      } else {
        g2d.drawString("1 Arrow in this cave", 100, 195);
      }
      if (this.smellCount == 1) {
        g2d.drawString("The player smells something fait but awful", 100, 220);
      } else if (this.smellCount >= 2) {
        g2d.drawString("The player smells something pungent and awful", 100, 220);
      }
    }
  }

  void getStatus(String statusString) {
    this.statusString = statusString;
  }

  void getStatusUpdater(Updater statusUpdate) {
    this.statusUpdater = statusUpdate;
    this.locationString = statusUpdate.getLocation();
    this.arrowCount = statusUpdate.getArrowCount();
    this.rubyCount = statusUpdate.getRubyCount();
    this.sapphireCount = statusUpdate.getSapphireCount();
    this.diamondCount = statusUpdate.getDiamondCount();
    this.smellCount = statusUpdate.getSmell();
    this.directionList = statusUpdate.getDirectionList();
    this.caveTreasure = statusUpdate.getCaveTreasure();
    this.caveArrows = statusUpdate.getCaveArrows();
  }
}
