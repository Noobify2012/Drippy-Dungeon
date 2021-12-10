package view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.Cave;
import model.Direction;
import model.ReadOnlyDungeon;
import model.Treasure;
import model.Updater;

class StatusPanel extends JPanel {
  private ReadOnlyDungeon model;
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
  private String monsterEncounter;
  private String luckyEncounter;
  private String pitFall;
  private String shotString;
  private String pickupString;


  public StatusPanel(ReadOnlyDungeon model) {
    this.model = model;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    Cave[][] board = model.getGameBoard();
    g2d.setFont(new Font("Ubuntu", Font.BOLD, 25));

    //TODO - fix too long of a status panel.
    //g2d.drawString(statusString, 100, 250);

    if (statusString != null) {
      g2d.drawString(statusString, 100, 250);
    }
    //draw images for status
    Path pathBase = null;
    Path emPath = null;
    BufferedImage emerald = null;
    BufferedImage ruby = null;
    BufferedImage diamond = null;
    BufferedImage arrow = null;
    //get base path
    try {
      pathBase = Path.of(new File(".").getCanonicalPath());
    } catch (IOException e) {
      //don't print out any errors
    }
    //get ruby with base path
    String directoryPath = "/res/dungeon-images/";

    try {
      arrow = ImageIO.read(new File(pathBase + directoryPath + "arrow-black.png"));
    } catch (IOException e) {
      //don't print out any errors
    }
    g2d.drawImage(arrow, 50, 58, null);

    try {
      ruby = ImageIO.read(new File(pathBase + directoryPath + "ruby.png"));
    } catch (IOException e) {
      //don't print out any errors
    }
    g2d.drawImage(ruby, 75, 70, null);

    try {
      diamond = ImageIO.read(new File(pathBase + directoryPath + "diamond.png"));
    } catch (IOException e) {
      //don't print out any errors
    }
    g2d.drawImage(diamond, 75, 95, null);
    //build sapphire/emerald
    try {
      emerald = ImageIO.read(new File(pathBase + directoryPath + "emerald.png"));
    } catch (IOException e) {
      //don't print out any errors
    }

    //draw emerald/sapphire
    g2d.drawImage(emerald, 75, 120, null);

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
        g2d.drawString("Treasure in this space " + getTreasureString(this.caveTreasure), 100, 170);
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
      if (model.getPlayerLocation().getMonsterListSize() > 0) {
        g2d.drawString(this.monsterEncounter, 100, 275);
      }
      if (model.getPitProx()) {
        g2d.drawString(this.pitFall, 100, 300);
      }
      g2d.drawString(this.pickupString, 100, 325);
      g2d.drawString(this.shotString, 100, 350);
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
    this.shotString = statusUpdate.getShotString();
    this.pickupString = statusUpdate.getPickUpString();
    this.monsterEncounter = statusUpdate.getMonsterEncounter();
    this.pitFall = statusUpdate.getPitFall();
  }

  private BufferedImage overlay(BufferedImage starting, String fpath, int offset) throws
          IOException {
    BufferedImage overlay = ImageIO.read(new File(fpath));
    int w = Math.max(starting.getWidth(), overlay.getWidth());
    int h = Math.max(starting.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(overlay, offset, offset, null);
    return combined;
  }

  void setModel(ReadOnlyDungeon model) {
    if (model == null) {
      throw new IllegalArgumentException("Model can't be null");
    }
    this.statusUpdater = null;
    this.model = model;
    this.statusString = "";
    this.locationString = "";
    this.arrowCount = 0;
    this.rubyCount = 0;
    this.sapphireCount = 0;
    this.diamondCount = 0;
    this.smellCount = 0;
    this.caveArrows = 0;
    this.directionList = new ArrayList<>();
    this.caveTreasure = new ArrayList<>();
    this.monsterEncounter = "";
    this.luckyEncounter = "";
    this.pitFall = "";
    this.shotString = "";
    this.pickupString = "";
    this.invalidate();
    this.revalidate();
    this.repaint();
    this.setVisible(true);
  }

  private String getTreasureString(List<Treasure> treasureList) {
    int rubyInt = 0;
    int diamondInt = 0;
    int sapphireInt = 0;
    for (int t = 0; t < treasureList.size(); t++) {
      if (treasureList.get(t).getName().equalsIgnoreCase("Ruby")) {
        rubyInt++;
      } else if (treasureList.get(t).getName().equalsIgnoreCase("Diamond")) {
        diamondInt++;
      } else if (treasureList.get(t).getName().equalsIgnoreCase("Sapphire")) {
        sapphireInt++;
      }
    }
    String treasureString2 = rubyInt + " rubies, " + diamondInt + " diamonds, "
            + sapphireInt + " sapphires.";
    return treasureString2;
  }
}
