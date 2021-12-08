package test;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import model.Cave;
import model.Direction;
import model.Dungeon;
import model.DungeonImpl;
import model.Player;
import model.PlayerImpl;
import model.StatusUpdater;
import model.Updater;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This is the tests for all Dungeon functionality and construction.
 */
public class DungeonImplTest {
  private Dungeon test;
  private Player player;
  private ArrayList edgeList;



  @Test
  public void getGameBoardRows() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 5,5,0,20, player, 1, 0);
    test.getDungeon();
    assertEquals(5, test.getGameBoardRows());
  }

  @Test
  public void getGameBoardCols() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 5,8,0,20, player, 1, 1);
    test.getDungeon();
    assertEquals(8, test.getGameBoardCols());
  }

  @Test
  public void getFinalEdgeList() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 5,5,0,20, player, 1, 0);
    test.getDungeon();
    assertTrue(test.getFinalEdgeList().size() >= 24);
  }

  @Test
  public void getFinalEdgeListMax() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 5,5,16,20, player, 1, 1);
    test.getDungeon();
    assertEquals(40,test.getFinalEdgeList().size());
  }

  @Test (expected = IllegalArgumentException.class)
  public void dungeonConstructorNotNodesTest() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 2,2,16,20, player, 1, 1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void dungeonConstructorNotEnoughRowsTest() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 0,5,16,20, player, 1, 1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void dungeonConstructorNotEnoughColsTest() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 5,0,16,20, player, 1, 1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void dungeonConstructorNegTreasureTest() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 5,5,1,-20, player, 1, 1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void dungeonConstructorTooMuchTreasureTest() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 5,5,1,200, player, 1, 0);  }

  @Test (expected = IllegalArgumentException.class)
  public void dungeonConstructorNegInterconTest() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 5,5,-1,20, player, 1, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void dungeonConstructorNonwrapTooMuchIntercon() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 5,5,40,20, player, 1, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void dungeonConstructorWrapTooMuchIntercon() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 5,5,50,20, player, 1, 0);
  }

  @Test
  public void getGameBoard() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 5,5,0,20, player, 1, 0);
    test.getDungeon();
    assertEquals(5,test.getGameBoard().length);
  }

  @Test
  public void getTeasureTest() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,50, player, 1, 0);
    test.getDungeon();
    Cave[][] testboard = test.getGameBoard();
    int treasureInt = 0;
    for (int r = 0; r < 4; r++) {
      for (int c = 0; c < 3; c++) {
        if (!testboard[r][c].getTreasureList().isEmpty()) {
          treasureInt++;
        }
      }
    }
    assertEquals(3, treasureInt);
  }

  @Test
  public void getZeroTunnelTreasureTest() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 5,5,0,20, player, 1, 0);
    test.getDungeon();
    Cave[][] testboard = test.getGameBoard();
    int treasureInt = 0;
    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        if (testboard[r][c].getNeighbors().size() == 2
                && testboard[r][c].getTreasureList().size() > 0) {
          treasureInt++;
        }
      }
    }
    assertEquals(0, treasureInt);
  }

  @Test
  public void getDungeon() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,20, player,
            1, 0);
    String createString = test.getDungeon();
    String testString =
            "\n" +
            "The player is currently in a Tunnel and has nothing in their treasure bag. \n" +
            "They can go SOUTH NORTH , there are 3 arrows remaining in their quiver, and there is "
            + "no treasure in this cave and no arrows in this cave.\n";
    assertEquals(testString, createString);
  }

  @Test
  public void isGameOver() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,20, player,
            1, 0);
    test.getDungeon();
    boolean testBool = test.isGameOver();
    assertEquals(false, testBool);
  }

  @Test
  public void movePlayer() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,20, player,
            1, 0);
    test.getDungeon();
    String moveString = test.movePlayer(Direction.NORTH);
    String result = "\n" +
            "\n" +
            "\n" +
            "The player is currently in a Tunnel and has nothing in their treasure bag. \n" +
            "They can go EAST SOUTH , there are 3 arrows remaining in their quiver, and there is no treasure in this cave and no arrows in this cave.\n" +
            "\n";
    assertEquals(result, moveString);
  }

  @Test
  public void getWrapping() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,20, player,
            1, 0);
    test.getDungeon();
    boolean wraps = test.getWrapping();
    assertEquals(false, wraps);

  }

  @Test
  public void shootArrow() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,20, player,
            1, 0);
    test.getDungeon();
    String firstShot = test.shootArrow(1,Direction.NORTH);
    String secondShot = test.shootArrow(1,Direction.NORTH);
    String howlString = "\n" +
            "Fired an arrow 1 spaces NORTH\n" +
            "\n" +
            "The player has 2 arrows remaining.\n" +
            "A great howl echos through the dungeon.\n";

    String deathString = "\n" +
            "Fired an arrow 1 spaces NORTH\n" +
            "\n" +
            "The player has 1 arrows remaining.\n" +
            "A great howl echos through the dungeon and a loud crash as the monster falls over "
            + "dead.\n";
    assertEquals(howlString,firstShot);
    assertEquals(deathString, secondShot);
  }

  @Test
  public void missShot() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,20, player,
            1, 0);
    test.getDungeon();
    String firstShot = test.shootArrow(2,Direction.NORTH);
    String missString = "\n" +
            "Fired an arrow 2 spaces NORTH\n" +
            "\n" +
            "The player has 2 arrows remaining.\n" +
            "Zing! The arrow bounced off a wall.\n";
    assertEquals(missString,firstShot);
  }

  @Test
  public void pickUpItem() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,50, player,
            1, 0);
    test.getDungeon();
    String pickUp = test.pickUpItem(1);
    String pickUpString = "\n" +
            "The player picked up an arrow.\n" +
            "The player is currently in a Tunnel and has nothing in their treasure bag. \n" +
            "They can go SOUTH NORTH , there are 4 arrows remaining in their quiver, and there is"
            + " no treasure in this cave and no arrows in this cave.\n";
    assertEquals(pickUpString, pickUp);
  }

  @Test
  public void quitGame() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,50, player,
            1, 0);
    test.getDungeon();
    String quitString = test.quitGame();
    String finalString = "Game quit! Thank You for Playing Dungeon Adventure.\n" +
            "The player is currently in a Tunnel and has nothing in their treasure bag. \n" +
            "They can go SOUTH NORTH , there are 3 arrows remaining in their quiver, and there is "
            + "no treasure in this cave and an arrow in this cave.\n";
    assertEquals(finalString, quitString);
  }

  @Test
  public void getArrowTest() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,50, player, 1, 0);
    test.getDungeon();
    Cave[][] testboard = test.getGameBoard();
    int arrowInt = 0;
    for (int r = 0; r < 4; r++) {
      for (int c = 0; c < 3; c++) {
        if (testboard[r][c].getArrowListSize() != 0) {
          arrowInt++;
        }
      }
    }
    assertEquals(6, arrowInt);
  }

  @Test
  public void getMonsterTest() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,50, player, 1, 1);
    test.getDungeon();
    Cave[][] testboard = test.getGameBoard();
    int monsterInt = 0;
    for (int r = 0; r < 4; r++) {
      for (int c = 0; c < 3; c++) {
        if (testboard[r][c].getMonsterListSize() != 0) {
          monsterInt++;
        }
      }
    }
    assertEquals(1, monsterInt);
  }

  @Test
  public void getMultiMonsterTest() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,50, player, 2, 0);
    test.getDungeon();
    Cave[][] testboard = test.getGameBoard();
    int monsterInt = 0;
    for (int r = 0; r < 4; r++) {
      for (int c = 0; c < 3; c++) {
        if (testboard[r][c].getMonsterListSize() != 0) {
          monsterInt++;
        }
      }
    }
    assertEquals(2, monsterInt);
  }

  @Test (expected = IllegalArgumentException.class)
  public void notEnoughMonstersTest() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,20, player, 0, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void tooManyMonstersTest() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,20, player, 5, 0);
    test.getDungeon();
  }

  @Test
  public void smellCheck() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,20, player,
            1, 0);
    test.getDungeon();
    String moveString = test.movePlayer(Direction.NORTH);
    String result = "\n" +
            "\n" +
            "\n" +
            "The player is currently in a Tunnel and has nothing in their treasure bag. \n" +
            "They can go EAST SOUTH , there are 3 arrows remaining in their quiver, and there is no treasure in this cave and no arrows in this cave.\n" +
            "\n";
    assertEquals(result, moveString);
    test.movePlayer(Direction.EAST);
    String smellStringOne = test.movePlayer(Direction.EAST);
    String smellResultOne = "\n" +
            "\n" +
            "\n" +
            "The player is currently in a Tunnel and has nothing in their treasure bag. \n" +
            "They can go SOUTH WEST , there are 3 arrows remaining in their quiver, and there is no treasure in this cave and no arrows in this cave.\n" +
            "\n" +
            "The player smells something faint but awful.\n" +
            "\n";
    assertEquals(smellResultOne, smellStringOne);
    String smellStringTwo = test.movePlayer(Direction.SOUTH);
    String smellResultTwo = "\n" +
            "\n" +
            "\n" +
            "The player is currently in a Tunnel and has nothing in their treasure bag. \n" +
            "They can go WEST NORTH , there are 3 arrows remaining in their quiver, and there is no treasure in this cave and no arrows in this cave.\n" +
            "\n" +
            "The player smells something awful and strong.\n" +
            "\n";
    assertEquals(smellResultTwo, smellStringTwo);
  }


  @Test
  public void deadMonsterSmellCheck() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,20, player,
            1, 0);
    test.getDungeon();
    String moveString = test.movePlayer(Direction.NORTH);
    String result = "\n" +
            "\n" +
            "\n" +
            "The player is currently in a Tunnel and has nothing in their treasure bag. \n" +
            "They can go EAST SOUTH , there are 3 arrows remaining in their quiver, and there is no treasure in this cave and no arrows in this cave.\n" +
            "\n";
    assertEquals(result, moveString);
    test.movePlayer(Direction.EAST);
    String smellStringOne = test.movePlayer(Direction.EAST);
    String smellResultOne = "\n" +
            "\n" +
            "\n" +
            "The player is currently in a Tunnel and has nothing in their treasure bag. \n" +
            "They can go SOUTH WEST , there are 3 arrows remaining in their quiver, and there is no treasure in this cave and no arrows in this cave.\n" +
            "\n" +
            "The player smells something faint but awful.\n" +
            "\n";
    assertEquals(smellResultOne, smellStringOne);
    test.shootArrow(1, Direction.SOUTH);
    test.shootArrow(1, Direction.SOUTH);
    String smellStringTwo = test.movePlayer(Direction.SOUTH);
    String smellResultTwo = "\n" +
            "\n" +
            "\n" +
            "The player is currently in a Tunnel and has nothing in their treasure bag. \n" +
            "They can go WEST NORTH , there are 1 arrows remaining in their quiver, and there is no treasure in this cave and no arrows in this cave.\n" +
            "\n";
    assertEquals(smellResultTwo, smellStringTwo);
  }


  @Test
  public void checkSmell() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,50, player, 1, 0);
    test.getDungeon();
  }

  @Test
  public void getPitProx() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,50, player, 1, 0);
    test.getDungeon();
    String rockfall = test.movePlayer(Direction.NORTH);
    boolean notGameOver = test.isGameOver();
    String pitDeath = test.movePlayer(Direction.EAST);
    boolean gameOver = test.isGameOver();
    String expected1 = "\n" +
            "\n" +
            "The player hears rocks falling into the abyss.\n" +
            "The player is currently in a Tunnel and has nothing in their treasure bag. \n" +
            "They can go EAST SOUTH , there are 3 arrows remaining in their quiver, and there is no treasure in this cave and an arrow in this cave.\n" +
            "\n";

    String expected2 = "\n" +
            "\n" +
            "The player falls to their death!\n" +
            "Game Over, you have died.\n";

    assertEquals(expected1, rockfall);
    assertEquals(expected2, pitDeath);
    assertEquals(notGameOver, false);
    assertEquals(gameOver, true);
  }

  @Test
  public void testGetWrapping() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,50, player, 1, 0);
    test.getDungeon();
    boolean testWrap = test.getWrapping();
    assertEquals(testWrap, false);

    Player player1 = new PlayerImpl();
    Dungeon test1 = new DungeonImpl(true, 4,3,0,50, player, 1, 0);
    test1.getDungeon();
    boolean testWrap1 = test1.getWrapping();
    assertEquals(testWrap1, true);
  }

  @Test
  public void getSeed() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,50, player, 1, 0);
    test.getDungeon();
    int temp = test.getSeed();
    assertEquals(temp, 0);
  }

  @Test
  public void getStatusUpdater() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,50, player, 1, 0);
    test.getDungeon();
    Updater temp = test.getStatusUpdater();
    Updater temp2 = new StatusUpdater();
    String loc = "a Tunnel";
    List<Direction> dir = new ArrayList<>();
    dir.add(Direction.SOUTH);
    dir.add(Direction.NORTH);
    assertEquals(temp.getLocation(), loc);
    assertEquals(temp.getDirectionList(), dir);
    assertEquals(temp.getArrowCount(), 3);
    assertEquals(temp.getCaveTreasure().size(), 0);
    assertEquals(temp.getCaveArrows(), 1);
    assertEquals(temp.getDiamondCount(), 0);
    assertEquals(temp.getRubyCount(), 0);
    assertEquals(temp.getSapphireCount(), 0);
    assertEquals(temp.getSmell(), 0);
  }

  @Test
  public void getTreasurePer() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,50, player, 1, 0);
    test.getDungeon();
    int temp = test.getTreasurePer();
    assertEquals(temp, 50);
  }

  @Test
  public void getDifficulty() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,50, player, 1, 0);
    test.getDungeon();
    int temp = test.getDifficulty();
    assertEquals(temp, 1);
  }


  @Test
  public void getInterConnect() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,50, player, 1, 0);
    test.getDungeon();
    int temp = test.getInterConnect();
    assertEquals(temp, 0);
  }

  @Test
  public void getPlayerLocation() {
    Player player = new PlayerImpl();
    Dungeon test = new DungeonImpl(false, 4,3,0,50, player, 1, 0);
    test.getDungeon();
    Cave temp = test.getPlayerLocation();
    int tempX = temp.getColumn();
    int tempy = temp.getRow();
    assertEquals(tempX, 0);
    assertEquals(tempy, 1);
    test.movePlayer(Direction.NORTH);
    Cave temp1 = test.getPlayerLocation();
    int tempX1 = temp1.getColumn();
    int tempY1 = temp1.getRow();
    assertEquals(tempX1, 0);
    assertEquals(tempX1, 0);
  }
}