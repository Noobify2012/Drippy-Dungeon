package view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.*;

import controller.Controller;

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

}
