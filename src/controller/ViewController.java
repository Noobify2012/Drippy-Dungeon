package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import model.Direction;
import model.Dungeon;
import model.DungeonImpl;
import model.Player;
import model.PlayerImpl;
import model.ReadOnlyDungeon;
import model.Updater;
import view.DungeonBuilder;
import view.DungeonBuilderImpl;
import view.DungeonViewImpl;
import view.IDungeonView;

public class ViewController implements VController, ActionListener, KeyListener {
  private int currentSeed;
  private DungeonBuilder builder;
  private Dungeon startDungeon;
  private Dungeon currDungeon;
  private IDungeonView view;
  private String startString;
  private Updater startUpdate;
  private Enum<ActionEnum> actionEnum;
  private Direction direction;
  private Enum<PickupEnum> pickup;
  private int distance;


  public ViewController(Dungeon startDungeon, IDungeonView view) {
    this.startDungeon = startDungeon;
    this.currentSeed = startDungeon.getSeed();
    this.builder = null;
    this.view = view;
    this.actionEnum = ActionEnum.NONE;
    this.direction = Direction.NONE;
    this.pickup = PickupEnum.NONE;
    this.distance = 0;
    this.currDungeon = null;
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
    this.currDungeon = this.startDungeon;
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
      if (actionEnum == ActionEnum.MOVE) {
        if (direction != Direction.NONE) {
          try {
            //String element = scan.next();
            System.out.println("hit the move try");
            String moveString = dungeon.movePlayer(direction);
            view.updateStatus(moveString);
            view.getUpdater(dungeon.getStatusUpdater());
            view.makeVisible();
            view.refresh();
            view.resetFocus();
              System.out.println(moveString + "\n");
          } catch (IllegalArgumentException iae) {
              System.out.println(iae.getMessage() + "\n");
            }
          }
        }
      }
//    String moveString = dungeon.movePlayer(direction);
//    view.updateStatus(moveString);
//    view.getUpdater(dungeon.getStatusUpdater());
//    view.makeVisible();
//    view.refresh();
//    view.resetFocus();

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
        if (direction == Direction.NONE) {
          this.direction = Direction.NORTH;
        } else {
          this.direction = Direction.NONE;
        }
        view.resetFocus();
        //this is where you attempt to build the dungeon
        break;
      case "South Button" :
        System.out.println("South Button");
        if (direction == Direction.NONE) {
          this.direction = Direction.SOUTH;
        } else {
          this.direction = Direction.NONE;
        }
        view.resetFocus();

        //this is where you attempt to build the dungeon
        break;

      case "East Button" :
        System.out.println("East Button");
        if (direction == Direction.NONE) {
          this.direction = Direction.EAST;
        } else {
          this.direction = Direction.NONE;
        }
        view.resetFocus();

        //this is where you attempt to build the dungeon
        break;

      case "West Button" :
        System.out.println("West Button");
        view.resetFocus();
        if (direction == Direction.NONE) {
          this.direction = Direction.WEST;
        } else {
          this.direction = Direction.NONE;
        }
        view.resetFocus();

        //this is where you attempt to build the dungeon
        break;

      case "Move Button" :
        if (actionEnum == ActionEnum.NONE) {
          this.actionEnum = ActionEnum.MOVE;
          try {
            //String element = scan.next();
            System.out.println("Move Button Pressed");
            System.out.println("hit the move try");
            String moveString = currDungeon.movePlayer(direction);
            this.direction = Direction.NONE;
            this.actionEnum = ActionEnum.NONE;
            System.out.println(moveString);
            view.updateStatus(moveString);
            view.getUpdater(currDungeon.getStatusUpdater());
            view.makeVisible();
            view.refresh();
            view.resetFocus();
            System.out.println(moveString + "\n");
          } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage() + "\n");
            this.actionEnum = ActionEnum.NONE;
            this.direction = Direction.NONE;
            this.pickup = PickupEnum.NONE;
          }
        }
        //String temp = currDungeon.movePlayer(direction);
        //System.out.println(temp);

        this.actionEnum = ActionEnum.NONE;
        this.pickup = PickupEnum.NONE;
        this.direction = Direction.NONE;
        view.resetFocus();


        //this is where you attempt to build the dungeon
        break;

      case "Shoot Button" :
        System.out.println("Shoot Button Pressed");
        view.resetFocus();
        if (actionEnum == ActionEnum.NONE) {
          this.actionEnum = ActionEnum.SHOOT;
          try {
            //String element = scan.next();
            System.out.println("hit the shoot try");
            String shootString = currDungeon.shootArrow(distance, direction);
            this.direction = Direction.NONE;
            this.actionEnum = ActionEnum.NONE;
            System.out.println("first shoot string print" + shootString);
            view.updateStatus(shootString);
            view.getUpdater(currDungeon.getStatusUpdater());
            System.out.println(shootString + "\n");
            view.makeVisible();
            view.refresh();
            view.resetFocus();
          } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage() + "\n");
            this.actionEnum = ActionEnum.NONE;
            this.direction = Direction.NONE;
            this.pickup = PickupEnum.NONE;
          }
        }
        this.actionEnum = ActionEnum.NONE;
        this.pickup = PickupEnum.NONE;
        this.direction = Direction.NONE;
        view.resetFocus();
        break;

      case "Pickup Button" :
        System.out.println("Pickup Button");
        if (actionEnum == ActionEnum.NONE) {
          this.actionEnum = ActionEnum.PICKUP;
          try {
            //String element = scan.next();
            System.out.println("p key Pressed");
            System.out.println("hit the pickup p key try");
            int temp = 0;
            System.out.println("value of pickup enum: " + this.pickup);
            if (pickup == PickupEnum.TREASURE) {
              //temp already set
            } else if (pickup == PickupEnum.ARROW) {
              temp = 1;
            } else {
              temp = 2;
            }
            String pickupString = currDungeon.pickUpItem(temp);
            this.pickup = PickupEnum.NONE;
            this.actionEnum = ActionEnum.NONE;
            System.out.println(pickupString);
            view.updateStatus(pickupString);
            view.getUpdater(currDungeon.getStatusUpdater());
            view.makeVisible();
            view.refresh();
            view.resetFocus();
          } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage() + "\n");
            this.actionEnum = ActionEnum.NONE;
            this.direction = Direction.NONE;
            this.pickup = PickupEnum.NONE;
          }
        }
        this.actionEnum = ActionEnum.NONE;
        this.pickup = PickupEnum.NONE;
        this.direction = Direction.NONE;
        view.resetFocus();

        //this is where you attempt to build the dungeon
        break;

      case "Build Button" :
        System.out.println("Build Button");
        view.resetFocus();
        //dungeon =
        //this is where you attempt to build the dungeon
        break;

      case "Quit Game" :
        System.out.println("quit game");
        this.startDungeon.quitGame();
        System.exit(0);
        break;

      case "Build New" :
        System.out.println("Build new Dungeon");
        break;

      case "Restart Dungeon" :
        System.out.println("restart dungeon");
        //do something
        break;

      case "menuQuit" :
        System.out.println("quit menu option");
        break;
    }

    if (e.getActionCommand().equals("Restart")) {
      System.out.println("caught the reset bug");
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
    int keyCode = e.getKeyCode();
    switch( keyCode ) {
      case KeyEvent.VK_UP:
        System.out.println("up arrow case");
        if (direction == Direction.NONE) {
          this.direction = Direction.NORTH;
        } else {
          this.direction = Direction.NONE;
          this.actionEnum = ActionEnum.NONE;
          this.pickup = PickupEnum.NONE;
        }
        // handle up
        break;
      case KeyEvent.VK_DOWN:
        System.out.println("down arrow case");
        if (direction == Direction.NONE) {
          this.direction = Direction.SOUTH;
        } else {
          this.direction = Direction.NONE;
          this.actionEnum = ActionEnum.NONE;
          this.pickup = PickupEnum.NONE;
        }
        // handle down
        break;
      case KeyEvent.VK_LEFT:
        System.out.println("left arrow case");
        if (direction == Direction.NONE) {
          this.direction = Direction.WEST;
        } else {
          this.direction = Direction.NONE;
          this.actionEnum = ActionEnum.NONE;
          this.pickup = PickupEnum.NONE;
        }
        // handle left
        break;
      case KeyEvent.VK_RIGHT :
        System.out.println("right arrow case");
        if (direction == Direction.NONE) {
          this.direction = Direction.EAST;
        } else {
          this.direction = Direction.NONE;
          this.actionEnum = ActionEnum.NONE;
          this.pickup = PickupEnum.NONE;
        }
        // handle right
        break;
    }
    if (e.getKeyChar() == 'm') {
      System.out.println("m means move");
      if (actionEnum == ActionEnum.NONE) {
        this.actionEnum = ActionEnum.MOVE;
        try {
          //String element = scan.next();
          System.out.println("Move Button Pressed");
          System.out.println("hit the move try");
          String moveString = currDungeon.movePlayer(direction);
          this.direction = Direction.NONE;
          this.actionEnum = ActionEnum.NONE;
          System.out.println(moveString);
          view.updateStatus(moveString);
          view.getUpdater(currDungeon.getStatusUpdater());
          view.makeVisible();
          view.refresh();
          view.resetFocus();
          System.out.println(moveString + "\n");
        } catch (IllegalArgumentException iae) {
          System.out.println(iae.getMessage() + "\n");
          this.actionEnum = ActionEnum.NONE;
          this.direction = Direction.NONE;
          this.pickup = PickupEnum.NONE;
        }
      }
      //String temp = currDungeon.movePlayer(direction);
      //System.out.println(temp);

      this.actionEnum = ActionEnum.NONE;
      this.pickup = PickupEnum.NONE;
      this.direction = Direction.NONE;
      view.resetFocus();
    } else if (e.getKeyChar() == 's') {
      System.out.println("s means shoot");
      if (actionEnum == ActionEnum.NONE) {
        this.actionEnum = ActionEnum.SHOOT;
        try {
          //String element = scan.next();
          System.out.println("hit the shoot try");
          String shootString = currDungeon.shootArrow(distance, direction);
          this.direction = Direction.NONE;
          this.actionEnum = ActionEnum.NONE;
          System.out.println("first shoot string print" + shootString);
          view.updateStatus(shootString);
          view.getUpdater(currDungeon.getStatusUpdater());
          System.out.println(shootString + "\n");
          view.makeVisible();
          view.refresh();
          view.resetFocus();
        } catch (IllegalArgumentException iae) {
          System.out.println(iae.getMessage() + "\n");
          this.actionEnum = ActionEnum.NONE;
          this.direction = Direction.NONE;
          this.pickup = PickupEnum.NONE;
        }
      }
      this.actionEnum = ActionEnum.NONE;
      this.pickup = PickupEnum.NONE;
      this.direction = Direction.NONE;
      view.resetFocus();
    } else if (e.getKeyChar() == 'p') {
      System.out.println("p means pickup");
      if (actionEnum == ActionEnum.NONE) {
        this.actionEnum = ActionEnum.PICKUP;
        try {
          //String element = scan.next();
          System.out.println("p key Pressed");
          System.out.println("hit the pickup p key try");
          int temp = 0;
          System.out.println("value of pickup enum: " + this.pickup);
          if (pickup == PickupEnum.TREASURE) {
            //temp already set
          } else if (pickup == PickupEnum.ARROW) {
            temp = 1;
          } else {
            temp = 2;
          }
          String pickupString = currDungeon.pickUpItem(temp);
          this.pickup = PickupEnum.NONE;
          this.actionEnum = ActionEnum.NONE;
          System.out.println(pickupString);
          view.updateStatus(pickupString);
          view.getUpdater(currDungeon.getStatusUpdater());
          view.makeVisible();
          view.refresh();
          view.resetFocus();
        } catch (IllegalArgumentException iae) {
          System.out.println(iae.getMessage() + "\n");
          this.actionEnum = ActionEnum.NONE;
          this.direction = Direction.NONE;
          this.pickup = PickupEnum.NONE;
        }
      }
      this.actionEnum = ActionEnum.NONE;
      this.pickup = PickupEnum.NONE;
      this.direction = Direction.NONE;
      view.resetFocus();
    } else if (e.getKeyChar() == 'a') {
      System.out.println("a means arrows");
      if (pickup == PickupEnum.NONE) {
        this.pickup = PickupEnum.ARROW;
      } else {
        this.direction = Direction.NONE;
        this.actionEnum = ActionEnum.NONE;
        this.pickup = PickupEnum.NONE;
      }
    } else if (e.getKeyChar() == 't') {
      System.out.println("t means treasure");
      if (pickup == PickupEnum.NONE) {
        this.pickup = PickupEnum.TREASURE;
      } else {
        this.direction = Direction.NONE;
        this.actionEnum = ActionEnum.NONE;
        this.pickup = PickupEnum.NONE;
      }
    } else if (e.getKeyChar() == 'b') {
      System.out.println("b means both");
      if (pickup == PickupEnum.NONE) {
        this.pickup = PickupEnum.BOTH;
      } else {
        this.direction = Direction.NONE;
        this.actionEnum = ActionEnum.NONE;
        this.pickup = PickupEnum.NONE;
      }
    } else if (e.getKeyChar() == 'q') {
      this.startDungeon.quitGame();
      System.exit(0);
    } else if (e.getKeyChar() == '1') {
      System.out.println("1");
      if (this.distance == 0) {
        this.distance = 1;
        System.out.println("value of distance is: " + distance);
      }
    } else if (e.getKeyChar() == '2') {
      System.out.println("2");
      if (this.distance == 0) {
        this.distance = 2;
        System.out.println("value of distance is: " + distance);
      }
    } else if (e.getKeyChar() == '3') {
      System.out.println("3");
      if (this.distance == 0) {
        this.distance = 3;
        System.out.println("value of distance is: " + distance);
      }
    } else if (e.getKeyChar() == '4') {
      System.out.println("4");
      if (this.distance == 0) {
        this.distance = 4;
        System.out.println("value of distance is: " + distance);
      }
    } else if (e.getKeyCode() == 38) {
      System.out.println("Up arrow");
    } else if (e.getKeyChar() == KeyEvent.VK_DOWN) {
      System.out.println("Down arrow");
    } else if (e.getKeyChar() == KeyEvent.VK_LEFT) {
      System.out.println("Left arrow");
    } else if (e.getKeyChar() == KeyEvent.VK_RIGHT) {
      System.out.println("Right arrow");
    }

  }

  class movePlayer implements Runnable {
    public void run() {
      try {
        //String element = scan.next();
        System.out.println("hit the move try");
        String moveString = currDungeon.movePlayer(direction);
        view.updateStatus(moveString);
        view.getUpdater(currDungeon.getStatusUpdater());

        System.out.println(moveString + "\n");
      } catch (IllegalArgumentException iae) {
        System.out.println(iae.getMessage() + "\n");
      }
      view.makeVisible();
      view.refresh();
      view.resetFocus();
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

    int keyCode = e.getKeyCode();
    switch( keyCode ) {
      case KeyEvent.VK_UP:
        System.out.println("up arrow case");
        if (direction == Direction.NONE) {
          this.direction = Direction.NORTH;
        }
        // handle up
        break;
      case KeyEvent.VK_DOWN:
        System.out.println("down arrow case");
        if (direction == Direction.NONE) {
          this.direction = Direction.SOUTH;
        }
        // handle down
        break;
      case KeyEvent.VK_LEFT:
        System.out.println("left arrow case");
        if (direction == Direction.NONE) {
          this.direction = Direction.WEST;
        }
        // handle left
        break;
      case KeyEvent.VK_RIGHT :
        System.out.println("right arrow case");
        if (direction == Direction.NONE) {
          this.direction = Direction.EAST;
        }
        // handle right
        break;
    }
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

  @Override
  public void restartDungeon() {
    int curDungeonRows = this.currDungeon.getGameBoardRows();
    int curDungeonCols = this.currDungeon.getGameBoardCols();
    int curDungeonTreas = this.currDungeon.getTreasurePer();
    int curDungeonInt = this.currDungeon.getInterConnect();
    int curDungeonDif = this.currDungeon.getDifficulty();
    boolean curDungeonWraps = this.currDungeon.getWraps();
    int curDungeonSeed = this.currDungeon.getSeed();
    Player newPlayer = new PlayerImpl();
    Dungeon newDungeon = new DungeonImpl(curDungeonWraps, curDungeonRows, curDungeonCols,
            curDungeonInt, curDungeonTreas, newPlayer, curDungeonDif, curDungeonSeed);


    this.startString = newDungeon.getDungeon();
    this.startUpdate = newDungeon.getStatusUpdater();
    view.updateStatus(this.startString);
    view.getUpdater(newDungeon.getStatusUpdater());
    this.view.makeVisible();
    this.view.refresh();
    this.view.resetFocus();
    this.currDungeon = newDungeon;
    this.playGame(newDungeon, this.view);
  }



//  public void setModel(ReadOnlyDungeon newDungeon) {
//    //
//    this.view.refresh();
//    this.view.makeVisible();
//  }
}
