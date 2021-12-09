package model;

import java.util.List;

public interface Updater {

  /**Gets the current location of the player from the updater.
   *
   * @return String of whether the player is in a Cave or Tunnel.
   */
  String getLocation();

  /**Gets the current number of rubies the player has from the updater.
   *
   * @return The integer value of the number of rubies the player has.
   */
  int getRubyCount();

  /**Gets the current number of diamonds the player has from the updater.
   *
   * @return The integer value of the number of diamonds the player has.
   */
  int getDiamondCount();

  /**Gets the current number of sapphires the player has from the updater.
   *
   * @return The integer value of the number of sapphires the player has.
   */
  int getSapphireCount();

  /**Gets the current number of arrows the player has from the updater.
   *
   * @return The integer value of the number of arrows the player has.
   */
  int getArrowCount();

  /**Gets the current smell if there is any from the player's location from the updater.
   *
   * @return The integer value of the smell at the player's location.
   */
  int getSmell();

  /**Gets the current possible directions of travel from the player's location from the updater.
   *
   * @return The list of directions the player can travel from their current location.
   */
  List<Direction> getDirectionList();

  /**Gets the treasure from the player's location from the updater.
   *
   * @return The list of treasure items at the player's location.
   */
  List<Treasure> getCaveTreasure();

  /**Gets the number of arrows at the players current location from the updater.
   *
   * @return The number of arrows at the player's location as an integer.
   */
  int getCaveArrows();

  /**Gets the result of a player shooting an arrow.
   *
   * @return A string with the result of a shot.
   */
  String getShotString();

  /**Gets the result of a player encountering a monster.
   *
   * @return A string with the result of the encounter.
   */
  String getMonsterEncounter();

  /**Gets the result of a player encountering the thief lucky the leprechaun.
   *
   * @return A string with the result of the encounter.
   */
  String getLuckyEncounter();

  /**Gets the result of a player encountering a the sides and falling into the pit of death.
   *
   * @return A string with the result of the encounter.
   */
  String getPitFall();

  /**Gets the result of a player picking up items.
   *
   * @return A string with the result of the pickup.
   */
  String getPickUpString();
}
