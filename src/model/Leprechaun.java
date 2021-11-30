package model;

import java.util.ArrayList;
import java.util.List;

public class Leprechaun implements Thief {
  private List<Treasure> stolenTreasure;

  public Leprechaun() {
    this.stolenTreasure = new ArrayList<>();
  }

  /**
   * Steals a random percentage of the players treasure when they encounter the Leprechaun.
   *
   * @param player   The player is passed to the leprechaun in order to allow them to steal the
   *                 treasure from the player.
   * @param treasureNum The number of pieces of treasure to be taken from the player by Lucky the
   *    *               Leprechaun.
   */
  @Override
  public String stealTreasure(Player player, int treasureNum) {
    String stealString = "";
    if (treasureNum < 0 || treasureNum > player.getTreasureList().size()) {
      throw new IllegalArgumentException("You cannot take less than 0 items or more than the" +
              " player has");
    }
    if (player.getTreasureList().size() == 0) {
      stealString = "Lucky the Leprechaun wasn't so lucky because our player is broke";
    } else {
      for (int l = 0; l < treasureNum; l ++) {
        stolenTreasure.add(player.getTreasureList().get(l));
        stealString = "Lucky the Leprechaun got lucky and stole "
                + getTreasureString(stolenTreasure) + " from our player and disappears into thin " +
                "air never to be seen again!";
      }

    }
    return stealString;
  }

  private String getTreasureString(List<Treasure> treasureList) {
    int rubyInt = 0;
    int diamondInt = 0;
    int sapphireInt = 0;
    for (int t = 0; t < treasureList.size(); t++) {
      if (treasureList.get(t).getName().equalsIgnoreCase("Ruby")) {
        rubyInt++;
      } else if (treasureList.get(t).getName().equalsIgnoreCase("Diamond")) {
        diamondInt++;
      } else if (treasureList.get(t).getName().equalsIgnoreCase("Sapphire")) {
        sapphireInt++;
      }
    }
    String treasureString2 = rubyInt + " rubies, " + diamondInt + " diamonds, "
            + sapphireInt + " sapphires";
    return treasureString2;
  }

  int getStolenTreasureSize() {
    return this.stolenTreasure.size();
  }
}
