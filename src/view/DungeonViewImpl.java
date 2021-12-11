package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.text.NumberFormatter;
import model.ReadOnlyDungeon;
import model.Updater;

/**
 * The implementation of the view in the dungeon graphical game. The view has 4 parts, the board
 * panel which displays the dungeon as the player moves. The status panel which displays the players
 * current state and actions. The action panel which has three buttons for moving, shooting, or
 * picking up items. Finally, is the direction pad which allows the user to click on the 4 primary
 * direction buttons for moving and shooting.
 */
public class DungeonViewImpl extends JFrame implements IdungeonView {
  private BoardPanel boardPanel;
  private JMenuBar menuBar;
  private JMenu menu;
  private JMenuItem menuQuit;
  private JMenuItem buildDungeon;
  private JMenuItem restartDungeon;
  private JMenuItem restartNewDungeon;
  private ArrayList<String> buildList;
  private JButton northButton;
  private JButton southButton;
  private JButton eastButton;
  private JButton westButton;
  private JButton moveButton;
  private JButton shootButton;
  private JButton pickupButton;
  private JButton buildButton;
  private JButton applyButton;
  private JPanel directionPanel;
  private JPanel actionPanel;
  private StatusPanel statusPanel;
  private JScrollPane dungeonPanel;
  private ReadOnlyDungeon model;
  private BuildStructure newDungeon;
  private JScrollPane boardPane;
  private JScrollPane statusPane;


  /**The constructor for the dungeon view that takes in a read only model.
   *
   * @param model a read only model for displaying the current state of the dungeon and the player's
   *              status.
   */
  public DungeonViewImpl(ReadOnlyDungeon model) {
    super("Graphical Adventure Game");
    this.setSize(1000, 1000);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.model = model;
    boardPanel = new BoardPanel(model);
    boardPanel.setPreferredSize(new Dimension(1200, 1200));
    boardPane = new JScrollPane(boardPanel);
    boardPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    boardPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    this.add(boardPane, "Center");
    this.buildList = new ArrayList<String>();
    this.setJMenuBar(buildMenuBar());
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
    actionPanel = new JPanel(new GridLayout(3, 1));
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
    applyButton = new JButton("Apply");
    applyButton.setActionCommand("Apply Button");

    //build Status panel
    statusPanel = new StatusPanel(model);
    statusPanel.setPreferredSize(new Dimension(500, 400));
    statusPane = new JScrollPane(statusPanel);
    //statusPane.setPreferredSize(new Dimension(200, 100));
    statusPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    statusPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
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
    this.applyButton.addActionListener(clicks);
    this.restartNewDungeon.addActionListener(clicks);
    this.restartDungeon.addActionListener(clicks);
    this.buildDungeon.addActionListener(clicks);
    this.buildButton.addActionListener(clicks);
    this.menuQuit.addActionListener(clicks);
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
  public void getDungeon() {

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
    rowInt.setPreferredSize(new Dimension(20, 20));
    JFormattedTextField colInt = new JFormattedTextField(formatter);
    colInt.setPreferredSize(new Dimension(20, 20));
    JFormattedTextField interInt = new JFormattedTextField(zeroFormatter);
    interInt.setPreferredSize(new Dimension(20, 20));
    JFormattedTextField treasInt = new JFormattedTextField(zeroFormatter);
    treasInt.setPreferredSize(new Dimension(20, 20));
    JFormattedTextField diffInt = new JFormattedTextField(formatter);
    diffInt.setPreferredSize(new Dimension(20, 20));

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

    this.buildButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        boolean checkBool = false;
        int row;
        int col;
        int inter;
        int treas;
        int diff;
        if (wrapping.isSelected()) {
          checkBool = true;
          System.out.println(true);
        } else if (notWrapping.isSelected()) {
          checkBool = false;
          System.out.println(false);
        }
        row = Integer.parseInt(rowInt.getText());
        col = Integer.parseInt(colInt.getText());
        inter = Integer.parseInt(interInt.getText());
        treas = Integer.parseInt(treasInt.getText());
        diff = Integer.parseInt(diffInt.getText());
        newDungeon = new DungeonBuildStructureImpl(checkBool, row, col, inter, treas, diff);
        System.out.println(rowInt.getText());
        System.out.println(colInt.getText());
        System.out.println(interInt.getText());
        System.out.println(treasInt.getText());
        System.out.println(diffInt.getText());
        d.dispose();
      }
    });
    d.add(this.buildButton);
    d.setSize(800, 100);
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
    }
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
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

  @Override
  public BuildStructure getBuilder() {
    BuildStructure newBuilder = this.newDungeon;
    return newBuilder;
  }

  @Override
  public void resetPanel(ReadOnlyDungeon newModel) {
    removeBoard();
    removeStatus();
    System.out.println("row of the new model " + newModel.getPlayerLocation().getRow()
            + " column of new model " + newModel.getPlayerLocation().getColumn()
            + " possible Directions " + newModel.getPlayerLocation().getDirectionList());

    this.getContentPane().invalidate();
    this.getContentPane().revalidate();
    this.invalidate();
    this.revalidate();
    this.refresh();
    this.model = newModel;
    System.out.println("row of the model " + model.getPlayerLocation().getRow()
            + " column of new model " + model.getPlayerLocation().getColumn()
            + " possible Directions " + model.getPlayerLocation().getDirectionList());


    //build new and add boardpanel
    this.boardPanel = new BoardPanel(model);
    BoardPanel boardPanel = new BoardPanel(model);
    boardPanel.setPreferredSize(new Dimension(1200, 1200));
    boardPane = new JScrollPane(boardPanel);
    boardPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    boardPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    this.add(boardPane, "Center");

    //do the same for status panel
    statusPanel = new StatusPanel(model);
    statusPanel.setPreferredSize(new Dimension(500, 400));
    statusPane = new JScrollPane(statusPanel);
    statusPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    statusPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    this.add(statusPane, "South");
    this.refresh();
  }

  void removeBoard() {
    this.remove(boardPanel);
    this.remove(boardPane);
    this.refresh();
    this.repaint();
    boardPane.repaint();
    boardPanel.repaint();
  }

  void removeStatus() {
    this.remove(statusPanel);
    this.remove(statusPane);

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
    buildDungeon.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        getDungeon();
      }
    });
    menu.addSeparator();
    restartDungeon = new JMenuItem("Restart Same Dungeon");
    restartDungeon.setActionCommand("Restart Same Dungeon");
    menu.add(restartDungeon);

    restartNewDungeon = new JMenuItem("Restart New Dungeon");
    restartNewDungeon.setActionCommand("Restart New Dungeon");
    menu.add(restartNewDungeon);
    menu.addSeparator();
    menuQuit = new JMenuItem("Quit Game");
    menuQuit.setActionCommand("Quit Game");
    menu.add(menuQuit);
    //add menu to menu bar
    menuBar.add(menu);
    return menuBar;
  }

  @Override
  public void setModel(ReadOnlyDungeon dungeon) {
    if (dungeon == null) {
      throw new IllegalArgumentException("Model can't be null");
    }
    this.model = dungeon;
    this.boardPanel.setModel(this.model);
    this.statusPanel.setModel(this.model);
    this.getContentPane().invalidate();
    this.getContentPane().revalidate();
    this.invalidate();
    this.revalidate();
    this.repaint();
    this.refresh();
    this.setVisible(true);
    this.resetFocus();
  }


}
