package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import controller.ViewController;
import model.Dungeon;
import model.DungeonImpl;
import model.Player;
import model.PlayerImpl;
import model.ReadOnlyDungeon;
import model.Updater;

public class DungeonViewImpl extends JFrame implements IDungeonView {
  private BoardPanel boardPanel;
  private JMenuBar menuBar;
  private JMenu menu;
  private JMenuItem menuQuit, buildDungeon, restartDungeon;
  private ArrayList<String> buildList;
  private JButton northButton;
  private JButton southButton;
  private JButton eastButton;
  private JButton westButton;
  private JButton moveButton;
  private JButton shootButton;
  private JButton pickupButton;
  private JButton buildButton;
  private JPanel directionPanel;
  private JPanel actionPanel;
  private StatusPanel statusPanel;
  private JScrollPane dungeonPanel;
  private ReadOnlyDungeon model;



  public DungeonViewImpl(ReadOnlyDungeon model) {
    super("Graphical Adventure Game");
    this.setSize(1000,1000);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.model = model;
    //this.setLayout(new FlowLayout());
    //this.setLayout(new GridLayout());
    //this.setJMenuBar();
    //JFrame frame = new JFrame("not Graphical Adventure Game");
    //JPanel dungeonPanel = new JPanel();
    //frame.setLayout(new BorderLayout());
    //frame.add(buildMenuBar());
    boardPanel = new BoardPanel(model);
    boardPanel.setPreferredSize(new Dimension(1200, 1200));
    JScrollPane boardPane = new JScrollPane(boardPanel);
    //boardPane.setPreferredSize(new Dimension((model.getGameBoardCols() * 100) + 400, (model.getGameBoardRows() * 100) + 400));
    boardPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    boardPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    this.add(boardPane, "Center");
//    JScrollPane scrollPane = new JScrollPane(dungeonPanel);
    //scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    //scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    //add(boardPanel);
    this.buildList = new ArrayList<String>();
    this.setJMenuBar(buildMenuBar());
    //this.setSize(1000, 1000);
    this.setVisible(true);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    directionPanel = new JPanel();
    directionPanel.setLayout(new BorderLayout());
    northButton = new JButton("North");
    northButton.setActionCommand("North Button");
    directionPanel.add(northButton, "North");
    southButton = new JButton("South");
    southButton.setActionCommand("South Button");
    directionPanel.add(southButton, "South");
    eastButton = new JButton("East");
    eastButton.setActionCommand("East Button");
    directionPanel.add(eastButton, "East");
    westButton = new JButton("West");
    westButton.setActionCommand("West Button");
    directionPanel.add(westButton, "West");
    this.add(directionPanel, "East");

    //action panel build out
    actionPanel = new JPanel(new GridLayout(3,1));
    moveButton = new JButton("Move");
    moveButton.setActionCommand("Move Button");
    moveButton.setMnemonic(KeyEvent.VK_M);
    actionPanel.add(moveButton);

    shootButton = new JButton("Shoot");
    shootButton.setActionCommand("Shoot Button");
    shootButton.setMnemonic(KeyEvent.VK_S);
    actionPanel.add(shootButton);
    pickupButton = new JButton("Pick up");
    pickupButton.setActionCommand("Pickup Button");
    pickupButton.setMnemonic(KeyEvent.VK_P);
    actionPanel.add(pickupButton);
    this.add(actionPanel, "North");
    buildButton = new JButton("Build");
    buildButton.setActionCommand("Build Button");

    //build Status panel
    statusPanel = new StatusPanel(model);
    statusPanel.setPreferredSize(new Dimension(300,300));
    JScrollPane statusPane = new JScrollPane(statusPanel);
    //statusPane.setPreferredSize(new Dimension(200, 100));
    statusPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    statusPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//    JScrollPane statusPane = new JScrollPane(statusPanel);
//    statusPane.setPreferredSize(new Dimension(300,300));
//    statusPane.add(statusPanel);
    this.add(statusPane, "South");
    this.refresh();
    //this.repaint();





  }

  @Override
  public void setListeners(ActionListener clicks, KeyListener keys) {
    this.addKeyListener(keys);
    this.northButton.addActionListener(clicks);
    this.southButton.addActionListener(clicks);
    this.eastButton.addActionListener(clicks);
    this.westButton.addActionListener(clicks);
    this.moveButton.addActionListener(clicks);
    this.shootButton.addActionListener(clicks);
    this.pickupButton.addActionListener(clicks);
  }

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  @Override
  public void refresh() {
    repaint();
  }

  /**
   * Make the view visible to start the game session.
   */
  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public Dungeon getDungeon() {

    JDialog d = new JDialog(this, "Build Menu");
    JLabel l = new JLabel("Build a new dungeon");
    d.setLayout(new FlowLayout());
    d.add(l);
    //citatons for JCheckBoxes and Groups:
    //https://www.tutorialspoint.com/how-to-select-one-item-at-a-time-from-jcheckbox-in-java
    //https://mathbits.com/JavaBitsNotebook/GUIs/CheckBox.html
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

    d.add(this.buildButton);
    d.dispose();
//    buildButton.addActionListener(new ActionListener() {
//      @Override
//      public void actionPerformed(ActionEvent e) {
//        if (wrapping.isSelected()) {
//          buildList.add(0, "true");
//        } else {
//          buildList.add(0, "false");
//        }
//        try {
//          int row = Integer.parseInt(interInt.getText());
//          buildList.add(1, rowInt.getText());
//        } catch (NumberFormatException nfe) {
//          System.out.println("didn't get an int for row");
//        }
//        try {
//          int col = Integer.parseInt(colInt.getText());
//          buildList.add(2, colInt.getText());
//
//        } catch (NumberFormatException nfe) {
//          System.out.println("didn't get an int for col");
//        }
//        try {
//          int inter = Integer.parseInt(interInt.getText());
//          buildList.add(3, interInt.getText());
//
//        } catch (NumberFormatException nfe) {
//          System.out.println("didn't get an int for inter");
//        }
//        try {
//          int treas = Integer.parseInt(treasInt.getText());
//          buildList.add(4, treasInt.getText());
//
//        } catch (NumberFormatException nfe) {
//          System.out.println("didn't get an int for treas");
//        }
//        try {
//          int diff = Integer.parseInt(diffInt.getText());
//          buildList.add(5, diffInt.getText());
//
//        } catch (NumberFormatException nfe) {
//          System.out.println("didn't get an int for treas");
//        }
//
//        d.dispose();
//      }
//    });
    d.setSize(800,100);
    d.setVisible(true);

    if (buildList.size() != 0) {
      boolean wrap = Boolean.getBoolean(buildList.get(0));
      int row = Integer.parseInt(buildList.get(1));
      int col = Integer.parseInt(buildList.get(2));
      int inter = Integer.parseInt(buildList.get(3));
      int treas = Integer.parseInt(buildList.get(4));
      int diff = Integer.parseInt(buildList.get(5));
      for (int i = 0; i < buildList.size(); i++) {
        System.out.println(buildList.get(i));

      }

      try {
        Player player = new PlayerImpl();
        Dungeon newDungeon = new DungeonImpl(wrap, row, col, inter, treas, player, diff, 0);
        return newDungeon;
      } catch (IllegalArgumentException iae) {

      } catch (IllegalStateException ise) {

      }
    }
    return null;
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  /**
   * This is to force the view to have a method to set up the buttons. The name
   * has been chosen deliberately. This is the same method signature to add an
   * action listener in Java Swing.
   *
   * <p>Thus our Swing-based implementation of this interface will already have such
   * a method.
   *
   * @param listener the listener to add
   */
  @Override
  public void addActionListener(ActionListener listener) {
    buildButton.addActionListener(listener);
    northButton.addActionListener(listener);
    southButton.addActionListener(listener);
    eastButton.addActionListener(listener);
    westButton.addActionListener(listener);
    moveButton.addActionListener(listener);
    shootButton.addActionListener(listener);
    pickupButton.addActionListener(listener);
    buildDungeon.addActionListener(listener);
    menuQuit.addActionListener(listener);
    restartDungeon.addActionListener(listener);
  }

  @Override
  public void updateStatus(String status) {
    statusPanel.getStatus(status);
  }

  @Override
  public void getUpdater(Updater statusUpdate) {
    statusPanel.getStatusUpdater(statusUpdate);
    boardPanel.getStatusUpdater(statusUpdate);
  }


  JMenuBar buildMenuBar() {
    //initialize JMenuBar
    menuBar = new JMenuBar();
    //Build first JMenu
    menu = new JMenu("Menu");
    menu.getAccessibleContext();

    //build jMenuItem
    buildDungeon = new JMenuItem("Build New Dungeon");
    //add menu item to the menu
    menu.add(buildDungeon);
    buildDungeon.setActionCommand("Build New");
//    buildDungeon.addActionListener(new ActionListener() {
//      @Override
//      public void actionPerformed(ActionEvent e) {
//        System.out.println("build new dungeon selected");
//      }
//    });
    menu.addSeparator();
    restartDungeon = new JMenuItem("Restart Dungeon");
    menu.add(restartDungeon);
    restartDungeon.setActionCommand("Restart Dungeon");
    restartDungeon.addActionListener(new ActionListener() {

      //TODO - figure out how to call restart dungeon in the view controller
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("restart selected");

      }
    });
    menu.addSeparator();
    menuQuit = new JMenuItem("Quit Game");
    menuQuit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });
    menuQuit.setActionCommand("Quit Game");
    menu.add(menuQuit);

    //add menu to menu bar
    menuBar.add(menu);
    return menuBar;
  }

  public void setModel(ReadOnlyDungeon dungeon) {
    if (dungeon == null) {
      throw new IllegalArgumentException("Model can't be null");
    }
    this.model = dungeon;
    this.refresh();
    this.setVisible(true);
    this.resetFocus();
  }

}
