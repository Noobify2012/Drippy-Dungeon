package view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.*;

import controller.Controller;
import controller.ViewController;
import model.Dungeon;
import model.Updater;

public interface IDungeonView {

  void setListeners(ActionListener clicks, KeyListener keys);

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  Dungeon getDungeon();

  void resetFocus();

  void updateStatus(String status);

  void getUpdater(Updater statusUpdate);

  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   */
  void addClickListener(ViewController listener);

}
