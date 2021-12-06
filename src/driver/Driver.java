package driver;

import controller.ConsoleController;
import java.io.InputStreamReader;
import java.util.Scanner;

import controller.Controller;
import controller.ViewController;
import model.Dungeon;
import model.DungeonImpl;
import model.Player;
import model.PlayerImpl;
import view.DungeonViewImpl;
import view.IDungeonView;


/**
 * Driver that acts as the controller for the Dungeon project. This just needs to be run.
 */
public class Driver {

  /**This is the main for the Dungeon model.
   *
   * @param args this takes in string arguments.
   */
  public static void main(String[] args) {

    //TODO - if command line args run in command line mode, if non-run in view
    //TODO - build view interface
    //TODO - import pictures and figure out how to display
    //TODO - build basic display with jpanel
    //TODO - build scroll bars
    //TODO - build restart and quit options
    //TODO - display basic dungeon
    //TODO - build uncovering functionality
    //TODO - build dungeon detail panel
    //TODO - provide get player description option
    //TODO - build shoot functionality using a key and direction
    //TODO - provide clear indication of results of actions
    if (args.length < 6) {
      Player throwAwayPlayer = new PlayerImpl();
      Dungeon defaultDungeon = new DungeonImpl(false, 4, 3,0,
              50, throwAwayPlayer, 1, 0);
      IDungeonView throwAwayView = new DungeonViewImpl(defaultDungeon);
      Controller viewController = new ViewController(defaultDungeon, throwAwayView);
      viewController.buildDungeon();

    } else if (args.length > 6) {
      throw new IllegalArgumentException("must have only 6 arguments in the format");
    } else if (args.length == 6) {
      if (!validBool(args[0])) {
        throw new IllegalArgumentException("the first argument must be a boolean");
      } else if (!validateInput(args[1])) {
        throw new IllegalArgumentException("the second argument must be a positive integer");
      } else if (!(validateInput(args[2]))) {
        throw new IllegalArgumentException("the third argument must be a positive integer");
      } else if (!(validateInput(args[3]))) {
        throw new IllegalArgumentException("the fourth argument must be a positive integer");
      } else if (!(validateInput(args[4]))) {
        throw new IllegalArgumentException("the fifth argument must be a positive integer");
      } else if (!(validateInput(args[5]))) {
        throw new IllegalArgumentException("the sixth argument must be a positive integer");
      }

      boolean wraps = Boolean.parseBoolean(args[0]);
      int row = Integer.parseInt(args[1]);
      int col = Integer.parseInt(args[2]);
      int inter = Integer.parseInt(args[3]);
      int treas = Integer.parseInt(args[4]);
      int dif = Integer.parseInt(args[5]);

      Scanner in = new Scanner((System.in));

      Player player = new PlayerImpl();
      try {
        Dungeon test = new DungeonImpl(wraps, row, col, inter, treas, player,
                dif, 1);
        String dungeonBuilder = test.getDungeon();
        System.out.println(dungeonBuilder + "\n");
        Readable inputs = new InputStreamReader(System.in);
        Appendable output = System.out;
        IDungeonView throwAway = new DungeonViewImpl(test);
        new ConsoleController(inputs, output).playGame((Dungeon) test, throwAway);
      } catch (IllegalArgumentException iae) {
        System.out.println(iae.getMessage() + "\n");
      } catch (IllegalStateException ise) {
        System.out.println(ise.getMessage() + "\n");
      }
    }
  }

  private static boolean validBool(String next) {
    return next.equalsIgnoreCase("false") || next.equalsIgnoreCase("true");
  }

  private static boolean validateInput(String next) {
    try {
      Integer.parseInt(next);
      return true;
    } catch (NumberFormatException nfe) {
      System.out.println("Not a valid number: " + next + "\n");
      return false;
    }
  }

  private void quitGame(Dungeon d) {
    String quitString = d.quitGame();
    System.out.println(quitString + "\n");

  }
}


