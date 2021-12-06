package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import model.Dungeon;
import model.DungeonImpl;
import model.Player;
import model.PlayerImpl;
import model.Updater;
import view.DungeonBuilder;
import view.DungeonBuilderImpl;
import view.DungeonViewImpl;
import view.IDungeonView;

public class ViewController implements Controller, ActionListener, KeyListener {
  private int currentSeed;
  private DungeonBuilder builder;
  private Dungeon startDungeon;
  private IDungeonView view;
  private String startString;
  private Updater startUpdate;

  public ViewController(Dungeon startDungeon, IDungeonView view) {
    this.startDungeon = startDungeon;
    this.currentSeed = 0;
    this.builder = null;
    this.view = view;
  }

  @Override
  public void buildDungeon() {
    //build default dungeon and player
    //DungeonBuilder dungeonBuilder = getDungeon();
//    System.out.println(dungeonBuilder.getWrapping());
//    System.out.println(dungeonBuilder.getRow());
//    System.out.println(dungeonBuilder.getCol());
//    System.out.println(dungeonBuilder.getInter());
//    System.out.println(dungeonBuilder.getTreas());
//    System.out.println(dungeonBuilder.getDifficulty());
//    Player player = new PlayerImpl();
//    Dungeon defaultDungeon = new DungeonImpl(false, 4, 3, 0, 50 ,
//            player, 1, currentSeed);
//    defaultDungeon.getDungeon();
    //DungeonBuilder newDungeon = getDungeon();
    //build view with default dungeon
//    IDungeonView view = new DungeonViewImpl(defaultDungeon);
//    Dungeon newDungeon = view.getDungeon();
    //this.currentSeed = defaultDungeon.getSeed();
    this.startString = startDungeon.getDungeon();
    this.startUpdate = startDungeon.getStatusUpdater();
    view.updateStatus(this.startString);
    view.getUpdater(startDungeon.getStatusUpdater());
    this.view.makeVisible();
    this.view.refresh();
    this.view.resetFocus();
    playGame(this.startDungeon, this.view);

  }

  @Override
  public void playGame(Dungeon dungeon, IDungeonView view) {
    if (dungeon == null) {
      throw new IllegalArgumentException("The model cannot be null");
    }
    if (view == null) {
      throw new IllegalArgumentException("The view cannot be null");
    }

    this.view.refresh();

    view.setListeners(this, this);
    view.resetFocus();
    view.makeVisible();
    view.refresh();

    while(!dungeon.isGameOver()) {
      break;

    }

  }
  private void waitForDungeon(Dungeon d) {
    while (d == null) {
      //do nothing
    }
  }

  private void quitGame(Dungeon d) {
      String quitString = d.quitGame();

  }

  private DungeonBuilder getDungeon() {
    List<String> buildList = new ArrayList<>();
    DungeonBuilder newBuild = null;
    JDialog d = new JDialog(new JDialog(), "Build Menu 1");
    JLabel l = new JLabel("Build a new dungeon");
    d.setLayout(new FlowLayout());
    d.add(l);
    //citaton for JCheckBoxes and Groups:
    //https://www.tutorialspoint.com/how-to-select-one-item-at-a-time-from-jcheckbox-in-java
    JCheckBox notWrapping = new JCheckBox("Non-Wrapping", true);
    JCheckBox wrapping = new JCheckBox("Wrapping", true);
    ButtonGroup checkBoxGroup = new ButtonGroup();
    checkBoxGroup.add(notWrapping);
    checkBoxGroup.add(wrapping);

    JLabel rowLabel = new JLabel("Rows");
    JLabel colLabel = new JLabel("Columns");
    JLabel interLabel = new JLabel("Interconnectivity");
    JLabel treasLabel = new JLabel("Treasure");
    JLabel diffLabel = new JLabel("Difficulty");

    //citation for field formatters:
    //https://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers
    NumberFormat format = NumberFormat.getIntegerInstance();
    NumberFormatter formatter = new NumberFormatter(format);
    NumberFormatter zeroFormatter = new NumberFormatter(format);
    formatter.setValueClass(Integer.class);
    formatter.setMinimum(1);
    formatter.setMaximum(100);
    formatter.setAllowsInvalid(false);

    zeroFormatter.setMinimum(0);
    zeroFormatter.setMaximum(100);
    zeroFormatter.setAllowsInvalid(false);

    JFormattedTextField rowInt = new JFormattedTextField(formatter);
    rowInt.setPreferredSize(new Dimension(20,20));
    JFormattedTextField colInt = new JFormattedTextField(formatter);
    colInt.setPreferredSize(new Dimension(20,20));
    JFormattedTextField interInt = new JFormattedTextField(zeroFormatter);
    interInt.setPreferredSize(new Dimension(20,20));
    JFormattedTextField treasInt = new JFormattedTextField(zeroFormatter);
    treasInt.setPreferredSize(new Dimension(20,20));
    JFormattedTextField diffInt = new JFormattedTextField(formatter);
    diffInt.setPreferredSize(new Dimension(20,20));

    //int row = Integer.parseInt(rowInt.getText());
    //System.out.println("rows: " + row);

    //citation for putting the Jlabel with the field:
    // https://stackoverflow.com/questions/41314951/how-can-i-use-jtextfield-and-jlabel-together
    rowLabel.setLabelFor(rowInt);
    colLabel.setLabelFor(colInt);
    interLabel.setLabelFor(interInt);
    treasLabel.setLabelFor(treasInt);
    diffLabel.setLabelFor(diffInt);

    d.add(notWrapping);
    d.add(wrapping);
    d.add(rowLabel);
    d.add(rowInt);
    d.add(colLabel);
    d.add(colInt);
    d.add(interLabel);
    d.add(interInt);
    d.add(treasLabel);
    d.add(treasInt);
    d.add(diffLabel);
    d.add(diffInt);

    JButton b = new JButton("Builder");
    d.add(b);
    d.setSize(800,100);
    d.setVisible(true);
    b.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (wrapping.isSelected()) {
          buildList.add(0, "true");
        } else {
          buildList.add(0, "false");
        }
        try {
          int row = Integer.parseInt(interInt.getText());
          buildList.add(1, rowInt.getText());
        } catch (NumberFormatException nfe) {
          System.out.println("didn't get an int for row");
        }
        try {
          int col = Integer.parseInt(colInt.getText());
          buildList.add(2, colInt.getText());

        } catch (NumberFormatException nfe) {
          System.out.println("didn't get an int for col");
        }
        try {
          int inter = Integer.parseInt(interInt.getText());
          buildList.add(3, interInt.getText());

        } catch (NumberFormatException nfe) {
          System.out.println("didn't get an int for inter");
        }
        try {
          int treas = Integer.parseInt(treasInt.getText());
          buildList.add(4, treasInt.getText());

        } catch (NumberFormatException nfe) {
          System.out.println("didn't get an int for treas");
        }
        try {
          int diff = Integer.parseInt(diffInt.getText());
          buildList.add(5, diffInt.getText());

        } catch (NumberFormatException nfe) {
          System.out.println("didn't get an int for treas");
        }


        //newBuild = new DungeonBuilderImpl(wrap, row, col, inter, treas, diff);

        d.dispose();
      }
    });

    if (buildList.size() != 0) {
      boolean wrap = Boolean.getBoolean(buildList.get(0));
      int row = Integer.parseInt(buildList.get(1));
      int col = Integer.parseInt(buildList.get(2));
      int inter = Integer.parseInt(buildList.get(3));
      int treas = Integer.parseInt(buildList.get(4));
      int diff = Integer.parseInt(buildList.get(5));
      newBuild = new DungeonBuilderImpl(wrap, row, col, inter, treas, diff);
      System.out.println(newBuild);
      for (int i = 0; i < buildList.size(); i++) {
        System.out.println(buildList.get(i));
      }
    }



    return newBuild;
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "North Button" :
        System.out.println("North Button");
        view.resetFocus();
        //this is where you attempt to build the dungeon
        break;
      case "South Button" :
        System.out.println("South Button");
        view.resetFocus();

        //this is where you attempt to build the dungeon
        break;

      case "East Button" :
        System.out.println("East Button");
        view.resetFocus();

        //this is where you attempt to build the dungeon
        break;

      case "West Button" :
        System.out.println("West Button");
        view.resetFocus();

        //this is where you attempt to build the dungeon
        break;

      case "Move Button" :
        System.out.println("Move Button");
        view.resetFocus();

        //this is where you attempt to build the dungeon
        break;

      case "Shoot Button" :
        System.out.println("Shoot Button");
        view.resetFocus();

        //this is where you attempt to build the dungeon
        break;

      case "Pickup Button" :
        System.out.println("Pickup Button");
        view.resetFocus();

        //this is where you attempt to build the dungeon
        break;

      case "Build Button" :
        System.out.println("Build Button");
        view.resetFocus();

        //this is where you attempt to build the dungeon
        break;
    }


  }

  /**
   * Invoked when a key has been typed.
   * See the class description for {@link KeyEvent} for a definition of
   * a key typed event.
   *
   * @param e the event to be processed
   */
  @Override
  public void keyTyped(KeyEvent e) {
    if (e.getKeyChar() == 'm') {
      System.out.println("m means move");
    } else if (e.getKeyChar() == 's') {
      System.out.println("s means shoot");
    } else if (e.getKeyChar() == 'p') {
      System.out.println("p means pickup");
    } else if (e.getKeyChar() == 'a') {
      System.out.println("a means arrows");
    } else if (e.getKeyChar() == 't') {
      System.out.println("t means treasure");
    } else if (e.getKeyChar() == 'b') {
      System.out.println("b means both");
    } else if (e.getKeyChar() == '1') {
      System.out.println("1");
    } else if (e.getKeyChar() == '2') {
      System.out.println("2");
    } else if (e.getKeyChar() == '3') {
      System.out.println("3");
    } else if (e.getKeyChar() == '4') {
      System.out.println("4");
    }

  }

  /**
   * Invoked when a key has been pressed.
   * See the class description for {@link KeyEvent} for a definition of
   * a key pressed event.
   *
   * @param e the event to be processed
   */
  @Override
  public void keyPressed(KeyEvent e) {
//    if (e.getKeyChar() == 'm') {
//      System.out.print("m means move");
//    } else if (e.getKeyChar() == 's') {
//      System.out.print("s means shoot");
//    } else if (e.getKeyChar() == 'p') {
//      System.out.print("p means pickup");
//    } else if (e.getKeyChar() == 'a') {
//      System.out.print("a means arrows");
//    } else if (e.getKeyChar() == 't') {
//      System.out.print("t means treasure");
//    } else if (e.getKeyChar() == 'b') {
//      System.out.print("b means both");
//    } else if (e.getKeyChar() == '1') {
//      System.out.print("1");
//    } else if (e.getKeyChar() == '2') {
//      System.out.print("2");
//    } else if (e.getKeyChar() == '3') {
//      System.out.print("3");
//    } else if (e.getKeyChar() == '4') {
//      System.out.print("4");
//    }

  }

  /**
   * Invoked when a key has been released.
   * See the class description for {@link KeyEvent} for a definition of
   * a key released event.
   *
   * @param e the event to be processed
   */
  @Override
  public void keyReleased(KeyEvent e) {
//    if (e.getKeyChar() == 'm') {
//      System.out.print("m means move");
//    } else if (e.getKeyChar() == 's') {
//      System.out.print("s means shoot");
//    } else if (e.getKeyChar() == 'p') {
//      System.out.print("p means pickup");
//    } else if (e.getKeyChar() == 'a') {
//      System.out.print("a means arrows");
//    } else if (e.getKeyChar() == 't') {
//      System.out.print("t means treasure");
//    } else if (e.getKeyChar() == 'b') {
//      System.out.print("b means both");
//    } else if (e.getKeyChar() == '1') {
//      System.out.print("1");
//    } else if (e.getKeyChar() == '2') {
//      System.out.print("2");
//    } else if (e.getKeyChar() == '3') {
//      System.out.print("3");
//    } else if (e.getKeyChar() == '4') {
//      System.out.print("4");
//    }

  }
}
