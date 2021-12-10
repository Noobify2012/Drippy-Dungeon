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
import model.Direction;
import model.Dungeon;
import model.DungeonImpl;
import model.Player;
import model.PlayerImpl;
import model.Updater;
import random.RandomNumberGenerator;
import view.BuildStructure;
import view.DungeonBuilder;
import view.IdungeonView;

public class ViewController implements vController, ActionListener, KeyListener {
  private int currentSeed;
  private DungeonBuilder builder;
  private Dungeon startDungeon;
  private Dungeon currDungeon;
  private IdungeonView view;
  private String startString;
  private Updater startUpdate;
  private Enum<ActionEnum> actionEnum;
  private Direction direction;
  private Enum<PickupEnum> pickup;
  private int distance;
  private RandomNumberGenerator rand;


  public ViewController(Dungeon startDungeon, IdungeonView view) {
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
  public void playGame(Dungeon dungeon, IdungeonView view) {
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
    rowInt.setPreferredSize(new Dimension(20, 20));
    JFormattedTextField colInt = new JFormattedTextField(formatter);
    colInt.setPreferredSize(new Dimension(20, 20));
    JFormattedTextField interInt = new JFormattedTextField(zeroFormatter);
    interInt.setPreferredSize(new Dimension(20, 20));
    JFormattedTextField treasInt = new JFormattedTextField(zeroFormatter);
    treasInt.setPreferredSize(new Dimension(20, 20));
    JFormattedTextField diffInt = new JFormattedTextField(formatter);
    diffInt.setPreferredSize(new Dimension(20, 20));

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
    d.setSize(800, 100);
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
    switch (e.getActionCommand()) {
      case "North Button":
        if (direction == Direction.NONE) {
          this.direction = Direction.NORTH;
        } else {
          this.direction = Direction.NONE;
        }
        view.resetFocus();
        break;
      case "South Button":
        if (direction == Direction.NONE) {
          this.direction = Direction.SOUTH;
        } else {
          this.direction = Direction.NONE;
        }
        view.resetFocus();
        break;

      case "East Button":
        if (direction == Direction.NONE) {
          this.direction = Direction.EAST;
        } else {
          this.direction = Direction.NONE;
        }
        view.resetFocus();
        break;

      case "West Button":
        view.resetFocus();
        if (direction == Direction.NONE) {
          this.direction = Direction.WEST;
        } else {
          this.direction = Direction.NONE;
        }
        view.resetFocus();
        break;

      case "Move Button":
        if (actionEnum == ActionEnum.NONE) {
          this.actionEnum = ActionEnum.MOVE;
          try {
            if (!this.currDungeon.isGameOver()) {
              String moveString = currDungeon.movePlayer(direction);
              view.updateStatus(moveString);
              view.getUpdater(currDungeon.getStatusUpdater());
            }
            this.direction = Direction.NONE;
            this.actionEnum = ActionEnum.NONE;
            view.makeVisible();
            view.refresh();
            view.resetFocus();
          } catch (IllegalArgumentException iae) {
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

      case "Shoot Button":
        view.resetFocus();
        if (actionEnum == ActionEnum.NONE) {
          this.actionEnum = ActionEnum.SHOOT;
          try {
            //String element = scan.next();
            if (!this.currDungeon.isGameOver()) {
              String shootString = currDungeon.shootArrow(distance, direction);
              view.updateStatus(shootString);
              view.getUpdater(currDungeon.getStatusUpdater());
            }
            this.direction = Direction.NONE;
            this.actionEnum = ActionEnum.NONE;
            view.makeVisible();
            view.refresh();
            view.resetFocus();
          } catch (IllegalArgumentException iae) {
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

      case "Pickup Button":
        while (!this.currDungeon.isGameOver()) {
          if (actionEnum == ActionEnum.NONE) {
            this.actionEnum = ActionEnum.PICKUP;
            try {
              int temp = 0;
              if (pickup == PickupEnum.TREASURE) {
                //do nothing temp already set
              } else if (pickup == PickupEnum.ARROW) {
                temp = 1;
              } else {
                temp = 2;
              }
              if (!this.currDungeon.isGameOver()) {
                String pickupString = currDungeon.pickUpItem(temp);
                view.updateStatus(pickupString);
                view.getUpdater(currDungeon.getStatusUpdater());
              }
              this.pickup = PickupEnum.NONE;
              this.actionEnum = ActionEnum.NONE;
              view.makeVisible();
              view.refresh();
              view.resetFocus();
            } catch (IllegalArgumentException iae) {
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

      case "Build Button":
        view.resetFocus();
        BuildStructure newDungeon = view.getBuilder();
        try {
          Player player = new PlayerImpl();
          Dungeon freshDungeon = new DungeonImpl(newDungeon.getWraps(), newDungeon.getRows(),
                  newDungeon.getCols(), newDungeon.getInter(), newDungeon.getTreas(), player,
                  newDungeon.getDiff(), rand.getRandomNumber(0, 200));
          this.startString = freshDungeon.getDungeon();
          view.setModel(freshDungeon);
          this.startDungeon = freshDungeon;
          this.builder = null;
          this.actionEnum = ActionEnum.NONE;
          this.direction = Direction.NONE;
          this.pickup = PickupEnum.NONE;
          this.distance = 0;
          this.currDungeon = freshDungeon;
          this.startUpdate = freshDungeon.getStatusUpdater();
          view.updateStatus(this.startString);
          view.getUpdater(startDungeon.getStatusUpdater());
          view.makeVisible();
          view.resetFocus();
          view.refresh();
        } catch (IllegalArgumentException iae) {
          //do nothing should already be caught
        } catch (IllegalStateException ise) {
          //do nothing should already be caught
        }
        break;

      case "Quit Game":
        this.startDungeon.quitGame();
        System.exit(0);
        break;

      case "Restart New Dungeon":
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
          this.startUpdate = freshDungeon.getStatusUpdater();
          view.updateStatus(this.startString);
          view.getUpdater(startDungeon.getStatusUpdater());
          view.makeVisible();
          view.resetFocus();
          view.refresh();


        } catch (IllegalArgumentException iae) {
          //do nothing should already be caught
        } catch (IllegalStateException ise) {
          //do nothing should already be caught
        }
        break;

      case "Restart Same Dungeon":
        try {
          Player player = new PlayerImpl();
          Dungeon freshDungeon = new DungeonImpl(this.currDungeon.getWrapping(),
                  this.currDungeon.getGameBoardRows(), this.currDungeon.getGameBoardCols(),
                  this.currDungeon.getInterConnect(), this.currDungeon.getTreasurePer(), player,
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
          this.startUpdate = freshDungeon.getStatusUpdater();
          view.updateStatus(this.startString);
          view.getUpdater(startDungeon.getStatusUpdater());
          view.makeVisible();
          view.resetFocus();
          view.refresh();
        } catch (IllegalArgumentException iae) {
          //do nothing should already be caught
        } catch (IllegalStateException ise) {
          //do nothing should already be caught
        }
        break;
      default: throw new IllegalArgumentException("No such button or key");
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
    switch (keyCode) {
      case KeyEvent.VK_UP:
        if (direction == Direction.NONE) {
          this.direction = Direction.NORTH;
        } else {
          this.direction = Direction.NONE;
          this.actionEnum = ActionEnum.NONE;
          this.pickup = PickupEnum.NONE;
        }
        break;
      case KeyEvent.VK_DOWN:
        if (direction == Direction.NONE) {
          this.direction = Direction.SOUTH;
        } else {
          this.direction = Direction.NONE;
          this.actionEnum = ActionEnum.NONE;
          this.pickup = PickupEnum.NONE;
        }
        break;
      case KeyEvent.VK_LEFT:
        if (direction == Direction.NONE) {
          this.direction = Direction.WEST;
        } else {
          this.direction = Direction.NONE;
          this.actionEnum = ActionEnum.NONE;
          this.pickup = PickupEnum.NONE;
        }
        break;
      case KeyEvent.VK_RIGHT:
        if (direction == Direction.NONE) {
          this.direction = Direction.EAST;
        } else {
          this.direction = Direction.NONE;
          this.actionEnum = ActionEnum.NONE;
          this.pickup = PickupEnum.NONE;
        }
        break;
      default: break;
    }
    if (e.getKeyChar() == 'm') {
      if (actionEnum == ActionEnum.NONE) {
        this.actionEnum = ActionEnum.MOVE;
        try {
          if (!this.currDungeon.isGameOver()) {
            String moveString = currDungeon.movePlayer(direction);
            view.updateStatus(moveString);
            view.getUpdater(currDungeon.getStatusUpdater());
          }
          this.direction = Direction.NONE;
          this.actionEnum = ActionEnum.NONE;
          view.makeVisible();
          view.refresh();
          view.resetFocus();
        } catch (IllegalArgumentException iae) {
          this.actionEnum = ActionEnum.NONE;
          this.direction = Direction.NONE;
          this.pickup = PickupEnum.NONE;
        }
      }

      this.actionEnum = ActionEnum.NONE;
      this.pickup = PickupEnum.NONE;
      this.direction = Direction.NONE;
      view.resetFocus();
    } else if (e.getKeyChar() == 's') {
      if (actionEnum == ActionEnum.NONE) {
        this.actionEnum = ActionEnum.SHOOT;
        try {
          if (!this.currDungeon.isGameOver()) {
            String shootString = currDungeon.shootArrow(distance, direction);
            view.updateStatus(shootString);
            view.getUpdater(currDungeon.getStatusUpdater());
          }
          this.direction = Direction.NONE;
          this.actionEnum = ActionEnum.NONE;
          view.makeVisible();
          view.refresh();
          view.resetFocus();
        } catch (IllegalArgumentException iae) {
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
      if (actionEnum == ActionEnum.NONE) {
        this.actionEnum = ActionEnum.PICKUP;
        try {
          int temp = 0;
          if (pickup == PickupEnum.TREASURE) {
            //temp already set
          } else if (pickup == PickupEnum.ARROW) {
            temp = 1;
          } else {
            temp = 2;
          }
          if (!this.currDungeon.isGameOver()) {
            String pickupString = currDungeon.pickUpItem(temp);
            view.updateStatus(pickupString);
            view.getUpdater(currDungeon.getStatusUpdater());
          }
          this.pickup = PickupEnum.NONE;
          this.actionEnum = ActionEnum.NONE;
          view.makeVisible();
          view.refresh();
          view.resetFocus();
        } catch (IllegalArgumentException iae) {
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
      if (pickup == PickupEnum.NONE) {
        this.pickup = PickupEnum.ARROW;
      } else {
        this.direction = Direction.NONE;
        this.actionEnum = ActionEnum.NONE;
        this.pickup = PickupEnum.NONE;
      }
    } else if (e.getKeyChar() == 't') {
      if (pickup == PickupEnum.NONE) {
        this.pickup = PickupEnum.TREASURE;
      } else {
        this.direction = Direction.NONE;
        this.actionEnum = ActionEnum.NONE;
        this.pickup = PickupEnum.NONE;
      }
    } else if (e.getKeyChar() == 'b') {
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
      if (this.distance == 0) {
        this.distance = 1;
      }
    } else if (e.getKeyChar() == '2') {
      if (this.distance == 0) {
        this.distance = 2;
      }
    } else if (e.getKeyChar() == '3') {
      if (this.distance == 0) {
        this.distance = 3;
      }
    } else if (e.getKeyChar() == '4') {
      if (this.distance == 0) {
        this.distance = 4;
      }
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
    switch (keyCode) {
      case KeyEvent.VK_UP:
        if (direction == Direction.NONE) {
          this.direction = Direction.NORTH;
        }
        break;
      case KeyEvent.VK_DOWN:
        if (direction == Direction.NONE) {
          this.direction = Direction.SOUTH;
        }
        break;
      case KeyEvent.VK_LEFT:
        if (direction == Direction.NONE) {
          this.direction = Direction.WEST;
        }
        break;
      case KeyEvent.VK_RIGHT:
        if (direction == Direction.NONE) {
          this.direction = Direction.EAST;
        }
        break;
      default: break;
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
}
