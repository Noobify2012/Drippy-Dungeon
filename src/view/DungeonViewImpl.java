package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.*;

import model.ReadOnlyDungeon;

public class DungeonViewImpl extends JFrame implements IDungeonView, ActionListener {
  private BoardPanel boardPanel;
  private JMenuBar menuBar;
  private JMenu menu;
  private JMenuItem menuQuit, buildDungeon, restartDungeon;

  public DungeonViewImpl(ReadOnlyDungeon model) {
    //super("Graphical Adventure Game");
    this.setSize(1000,1000);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    //this.setJMenuBar();
    JFrame frame = new JFrame("not Graphical Adventure Game");
    //add(buildMenuBar());
    boardPanel = new BoardPanel(model);
    //add(boardPanel);
    frame.setJMenuBar(buildMenuBar());
    frame.setSize(500, 500);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  @Override
  public void setListeners(ActionListener clicks, KeyListener keys) {

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

  JMenuBar buildMenuBar() {
    //initialize JMenuBar
    menuBar = new JMenuBar();
    //Build first JMenu
    menu = new JMenu("Menu");
    menu.getAccessibleContext();

    //build jMenuItem
    buildDungeon = new JMenuItem("Build New Dungeon");
    buildDungeon.addActionListener(this::actionPerformed);
    //add menu item to the menu
    menu.add(buildDungeon);
    menu.addSeparator();
    restartDungeon = new JMenuItem("Restart Dungeon");
    menu.add(restartDungeon);
    menu.addSeparator();
    menuQuit = new JMenuItem("Quit Game");
    menu.add(menuQuit);

    //add menu to menu bar
    menuBar.add(menu);
    return menuBar;
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equalsIgnoreCase("Build New Dungeon")) {
      //build new dungeon
      JDialog d = new JDialog(this, "Build Menu");
      JLabel l = new JLabel("This is the box for building a new dungeon");
      d.add(l);
      JCheckBox notWrapping = new JCheckBox("Non-Wrapping", true);
      JCheckBox wrapping = new JCheckBox("Wrapping", true);
      ButtonGroup checkBoxGroup = new ButtonGroup();
      checkBoxGroup.add(notWrapping);
      checkBoxGroup.add(wrapping);
      d.add(notWrapping);
      d.add(wrapping);


      JButton b = new JButton("Build");
      //b.addActionListener();
      d.setSize(500,100);
      d.setVisible(true);
      //System.out.print("Hit the set vis");
    } else if (e.getActionCommand().equals(restartDungeon)) {
      //restart dungeon
    } else if (e.getActionCommand().equals(menuQuit)) {
      //quit game
    }
    String s = e.getActionCommand();

    System.out.println(s + " selected");
  }

  JTextField addTextField() {
    JTextField field = new JFormattedTextField("something");
    return field;
  }
}
