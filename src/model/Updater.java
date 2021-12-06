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
   * @return The integer value of the smell at the player's location.
   */
  List<Direction> getDirectionList();

  /**Gets the current possible directions of travel from the player's location from the updater.
   *
   * @return The integer value of the smell at the player's location.
   */
  List<Treasure> getCaveTreasure();
}
