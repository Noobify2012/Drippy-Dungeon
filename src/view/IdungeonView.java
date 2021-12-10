package view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import javax.swing.*;
import controller.Controller;
import controller.ViewController;
import model.Dungeon;
import model.ReadOnlyDungeon;
import model.Updater;

public interface IdungeonView {

  void setListeners(ActionListener clicks, KeyListener keys);

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  void getDungeon();

  void resetFocus();

  void updateStatus(String status);

  void getUpdater(Updater statusUpdate);

  BuildStructure getBuilder();

  void resetPanel(ReadOnlyDungeon model);

  void setModel(ReadOnlyDungeon dungeon);


}
