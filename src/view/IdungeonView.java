package view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import model.ReadOnlyDungeon;
import model.Updater;

/**
 * The interface for the view of the dungeon graphical adventure game.
 */
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

  /**
   * This is the method called when a player wants to build a new dungeon with new parameters. It
   * spawns a JDialogue box with 2 checkboxes for either wrapping or non, and formatted JTextFields
   * for entering the number of rows, columns, interconnectivity, treasure percentage, and
   * difficulty.
   */
  void getDungeon();

  /**
   *This resets the focus after a button press in the view.
   */
  void resetFocus();

  /** This is used for passing the status string from the model to the view. This is the same string
   * that is used for status updated in the text version of the game.
   *
   * @param status the status string from the model to be displayed below the status updater in the
   *               view.
   */
  void updateStatus(String status);

  /**Updates the status of the player to be displayed in the status panel.
   *
   * @param statusUpdate an updater object with the information
   */
  void getUpdater(Updater statusUpdate);

  /**Gets the parameters for the controller for the new dungeon to be played whenever a game is
   * restarted or a new dungeon is requested.
   *
   * @return a DungeonBuilder object which contains all the necessary parameters for attempting
   *         to construct the new dungeon.
   */
  BuildStructure getBuilder();

  /**
   * @param model
   */
  void resetPanel(ReadOnlyDungeon model);

  /**This changes the model in the view when a player either restarts a dungeon or builds a new one.
   *
   * @param dungeon takes in the new dungeon that is going to be presented to the player to play.
   */
  void setModel(ReadOnlyDungeon dungeon);


}
