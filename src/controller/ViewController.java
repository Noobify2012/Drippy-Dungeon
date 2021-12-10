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
import random.RandomNumberGenerator;
import view.BuildStructure;
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
  private RandomNumberGenerator rand;


  public ViewController(Dungeon startDungeon, IDungeonView view) {
    if (startDungeon == null || view == null) {
      throw new IllegalArgumentException("the model or view cannot be null");
    }
    this.startDungeon = startDungeon;
    this.currentSeed = startDungeon.getSeed();
    this.view = view;
    this.builder = null;
    this.actionEnum = ActionEnum.NONE;
    this.direction = Direction.NONE;
    this.pickup = PickupEnum.NONE;
    this.distance = 0;
    this.currDungeon = null;
    this.rand = new RandomNumberGenerator(0);
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


    return newBuild;
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println(e.getActionCommand());
    switch (e.getActionCommand()) {
      case "North Button":
        System.out.println("North Button");
        if (direction == Direction.NONE) {
          this.direction = Direction.NORTH;
        } else {
          this.direction = Direction.NONE;
        }
        view.resetFocus();
        //this is where you attempt to build the dungeon
        break;
      case "South Button":
        System.out.println("South Button");
        if (direction == Direction.NONE) {
          this.direction = Direction.SOUTH;
        } else {
          this.direction = Direction.NONE;
        }
        view.resetFocus();

        //this is where you attempt to build the dungeon
        break;

      case "East Button":
        System.out.println("East Button");
        if (direction == Direction.NONE) {
          this.direction = Direction.EAST;
        } else {
          this.direction = Direction.NONE;
        }
        view.resetFocus();

        //this is where you attempt to build the dungeon
        break;

      case "West Button":
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

      case "Move Button":
        while (!this.currDungeon.isGameOver()) {
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

          this.actionEnum = ActionEnum.NONE;
          this.pickup = PickupEnum.NONE;
          this.direction = Direction.NONE;
          view.resetFocus();
        }
        //this is where you attempt to build the dungeon
        break;

      case "Shoot Button":
        System.out.println("Shoot Button Pressed");
        view.resetFocus();
        while (!this.currDungeon.isGameOver()) {
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
        }
        break;

      case "Pickup Button":
        System.out.println("Pickup Button");
        while (!this.currDungeon.isGameOver()) {
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
        }
        //this is where you attempt to build the dungeon
        break;

      case "Build Button":
        System.out.println("Build Button in controller");
        view.resetFocus();
        BuildStructure newDungeon = view.getBuilder();
        System.out.println("Builder wraps: " + newDungeon.getWraps());
        System.out.println("Builder rows: " + newDungeon.getRows());
        System.out.println("Builder cols: " + newDungeon.getCols());
        System.out.println("Builder inter: " + newDungeon.getInter());
        System.out.println("Builder treas: " + newDungeon.getTreas());
        System.out.println("Builder diff: " + newDungeon.getDiff());

        try {
          Player player = new PlayerImpl();
          Dungeon freshDungeon = new DungeonImpl(newDungeon.getWraps(), newDungeon.getRows(),
                  newDungeon.getCols(), newDungeon.getInter(), newDungeon.getTreas(), player,
                  newDungeon.getDiff(), rand.getRandomNumber(0,200));
          this.startString = freshDungeon.getDungeon();
          view.setModel(freshDungeon);
          this.startDungeon = freshDungeon;
          this.builder = null;
          this.actionEnum = ActionEnum.NONE;
          this.direction = Direction.NONE;
          this.pickup = PickupEnum.NONE;
          this.distance = 0;
          this.currDungeon = freshDungeon;
          System.out.println("new start String: " + this.startString);
          this.startUpdate = freshDungeon.getStatusUpdater();
          //view.resetPanel(freshDungeon);
//          IDungeonView viewNew = new DungeonViewImpl(dungeon);
          //this.playGame(dungeon, viewNew);
          view.updateStatus(this.startString);
          view.getUpdater(startDungeon.getStatusUpdater());
          view.makeVisible();
          view.resetFocus();
          view.refresh();


        } catch (IllegalArgumentException iae) {

        } catch (IllegalStateException ise) {

        }
        //dungeon =
        //this is where you attempt to build the dungeon
        break;

      case "Quit Game":
        System.out.println("quit game");
        this.startDungeon.quitGame();
        System.exit(0);
        break;

      case "Restart New Dungeon":
        System.out.println("Build new Dungeon in controller");
        try {
          Player player = new PlayerImpl();
          Dungeon freshDungeon = new DungeonImpl(this.currDungeon.getWrapping(),
                  this.currDungeon.getGameBoardRows(), this.currDungeon.getGameBoardCols(),
                  this.currDungeon.getInterConnect(), this.currDungeon.getTreasurePer(), player,
                  this.currDungeon.getDifficulty(), rand.getRandomNumber(0, 200));

          this.startString = freshDungeon.getDungeon();
          view.setModel(freshDungeon);
          this.startDungeon = freshDungeon;
          this.builder = null;
          this.actionEnum = ActionEnum.NONE;
          this.direction = Direction.NONE;
          this.pickup = PickupEnum.NONE;
          this.distance = 0;
          this.currDungeon = freshDungeon;
          System.out.println("new start String: " + this.startString);
          this.startUpdate = freshDungeon.getStatusUpdater();
          //view.resetPanel(freshDungeon);
//          IDungeonView viewNew = new DungeonViewImpl(dungeon);
          //this.playGame(dungeon, viewNew);
          view.updateStatus(this.startString);
          view.getUpdater(startDungeon.getStatusUpdater());
          view.makeVisible();
          view.resetFocus();
          view.refresh();


        } catch (IllegalArgumentException iae) {

        } catch (IllegalStateException ise) {

        }
        break;

      case "Restart Same Dungeon":
        System.out.println("restart dungeon in the controller");
        try {
          Player player = new PlayerImpl();
          Dungeon freshDungeon = new DungeonImpl(this.currDungeon.getWrapping(), this.currDungeon.getGameBoardRows(),
                  this.currDungeon.getGameBoardCols(), this.currDungeon.getInterConnect(), this.currDungeon.getTreasurePer(), player,
                  this.currDungeon.getDifficulty(), this.currDungeon.getSeed());
          this.startString = freshDungeon.getDungeon();
          view.setModel(freshDungeon);
          this.startDungeon = freshDungeon;
          this.builder = null;
          this.actionEnum = ActionEnum.NONE;
          this.direction = Direction.NONE;
          this.pickup = PickupEnum.NONE;
          this.distance = 0;
          this.currDungeon = freshDungeon;
          System.out.println("new start String: " + this.startString);
          this.startUpdate = freshDungeon.getStatusUpdater();
          //view.resetPanel(freshDungeon);
//          IDungeonView viewNew = new DungeonViewImpl(dungeon);
          //this.playGame(dungeon, viewNew);
          view.updateStatus(this.startString);
          view.getUpdater(startDungeon.getStatusUpdater());
          view.makeVisible();
          view.resetFocus();
          view.refresh();


        } catch (IllegalArgumentException iae) {

        } catch (IllegalStateException ise) {

        }
        break;

      case "menuQuit":
        System.out.println("quit menu option");
        break;
    }

    if (e.getSource().equals("Restart")) {
      System.out.println("caught the reset bug");
    }

//

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
    //this method is not used because there are no behaviours dependent on key releases
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

  /**
   * Handle an action in a single cell of the board, such as to make a move.
   *
   * @param row the row of the clicked cell
   * @param col the column of the clicked cell
   */
  @Override
  public void handleCellClick(int row, int col) {
    System.out.println("Player clicked here: " + row + " " + col);
  }


  void setModel(Dungeon newDungeon) {
    //
    this.view.refresh();
    this.view.makeVisible();
  }
}
