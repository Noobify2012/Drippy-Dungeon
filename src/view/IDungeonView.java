package view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.*;

import controller.Controller;
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

  /**
   * This is to force the view to have a method to set up the keyboard. The name
   * has been chosen deliberately. This is the same method signature to add a key
   * listener in Java Swing.
   *
   * <p>Thus our Swing-based implementation of this interface will already have such
   * a method.
   *
   * @param listener the listener to add
   */
  void addKeyListener(KeyListener listener);

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
  void addActionListener(ActionListener listener);

  void updateStatus(String status);

  void getUpdater(Updater statusUpdate);

}
