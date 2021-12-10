package model;

/**
 * The thief interface which represents a character that steals treasure from the player when they
 * run into them.
 */
public interface Thief {

  /**Steals a random percentage of the players treasure when they encounter the Leprechaun.
   *
   * @param player The player is passed to the leprechaun in order to allow them to steal the
   *               treasure from the player.
   * @param treasureNum The number of pieces of treasure to be taken from the player by Lucky the
   *                    Leprechaun.
   */
  String stealTreasure(Player player, int treasureNum);
}
