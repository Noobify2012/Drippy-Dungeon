package model;

import java.util.List;

public class StatusUpdater implements Updater {
  private String location;
  private int rubyCount;
  private int diamondCount;
  private int sapphireCount;
  private int arrowCount;
  private int smell;
  private List<Direction> directionList;
  private List<Treasure> caveTreasure;
  private int arrowListSize;
  private String monsterEncounter;
  private String luckyEncounter;
  private String pitFall;
  private String shotString;
  private String pickupString;

  public StatusUpdater() {
    this.location = "";
    this.rubyCount = 0;
    this.diamondCount = 0;
    this.sapphireCount = 0;
    this.arrowCount = 0;
    this.smell = 0;
    this.directionList = null;
    this.caveTreasure = null;
    this.arrowListSize = 0;
    this.shotString = "";
    this.pickupString = "";
  }

  void setPickupString(String pickup) {
    this.pickupString = "";
    this.pickupString = pickup;
  }

  void setMonsterEncounter(String encounter) {
    this.monsterEncounter = "";
    this.monsterEncounter = encounter;
  }

  void setLuckyEncounter(String encounter) {
    this.luckyEncounter = "";
    this.luckyEncounter = encounter;
  }

  void setPitFall(String fall) {
    this.pitFall = "";
    this.pitFall = fall;
  }

  void setLocation(String loc) {
    this.location = loc;
  }

  void setRubyCount(int count) {
    this.rubyCount = rubyCount +  count;
  }

  void setDiamondCount(int count) {
    this.diamondCount = diamondCount + count;
  }

  void setSapphireCount(int count) {this.sapphireCount = sapphireCount +  count;}

  void setArrowCount(int count) {this.arrowCount = arrowCount + count;}

  void setSmell(int count) {this.smell = count;}

  void setDirectionList(List<Direction> directionList) {
    this.directionList = directionList;
  }

  void setCaveTreasure(List<Treasure> caveTreasure) {
    this.caveTreasure = caveTreasure;
  }

  void setArrowList(int arrowList) {
    this.arrowListSize = arrowList;
  }

  void setShotString(String shotString) { this.shotString = shotString;}


  /**
   * Gets the current location of the player from the updater.
   *
   * @return String of whether the player is in a Cave or Tunnel.
   */
  @Override
  public String getLocation() {
    String tempString = this.location;
    return tempString;
  }

  /**
   * Gets the current number of rubies the player has from the updater.
   *
   * @return The integer value of the number of rubies the player has.
   */
  @Override
  public int getRubyCount() {
    int temp = rubyCount;
    return temp;
  }

  /**
   * Gets the current number of diamonds the player has from the updater.
   *
   * @return The integer value of the number of diamonds the player has.
   */
  @Override
  public int getDiamondCount() {
    int temp = diamondCount;
    return temp;
  }

  /**
   * Gets the current number of sapphires the player has from the updater.
   *
   * @return The integer value of the number of sapphires the player has.
   */
  @Override
  public int getSapphireCount() {
    int temp = sapphireCount;
    return temp;
  }

  /**
   * Gets the current number of arrows the player has from the updater.
   *
   * @return The integer value of the number of arrows the player has.
   */
  @Override
  public int getArrowCount() {
    int temp = arrowCount;
    return temp;
  }

  /**
   * Gets the current smell if there is any from the player's location from the updater.
   *
   * @return The integer value of the smell at the player's location.
   */
  @Override
  public int getSmell() {
    int temp = smell;
    return smell;
  }

  /**
   * Gets the current possible directions of travel from the player's location from the updater.
   *
   * @return The list of possible directions at the player's location.
   */
  @Override
  public List<Direction> getDirectionList() {
    List<Direction> temp = this.directionList;
    return temp;
  }

  /**
   * Gets the current treasure from the player's location from the updater.
   *
   * @return The list of treasure at the player's location.
   */
  @Override
  public List<Treasure> getCaveTreasure() {
    List<Treasure> temp = this.caveTreasure;
    return temp;
  }

  /**
   * Gets the number of arrows at the players current location from the updater.
   *
   * @return The number of arrows at the player's location as an integer.
   */
  @Override
  public int getCaveArrows() {
    int temp = this.arrowListSize;
    return temp;
  }

  /**
   * Gets the result of a player shooting an arrow.
   *
   * @return A string with the result of a shot.
   */
  @Override
  public String getShotString() {
    String temp = this.shotString;
    return temp;
  }

  /**
   * Gets the result of a player encountering a monster.
   *
   * @return A string with the result of the encounter.
   */
  @Override
  public String getMonsterEncounter() {
    String temp = this.monsterEncounter;
    return temp;
  }

  /**
   * Gets the result of a player encountering the thief lucky the leprechaun.
   *
   * @return A string with the result of the encounter.
   */
  @Override
  public String getLuckyEncounter() {
    String temp = this.luckyEncounter;
    return temp;
  }

  /**
   * Gets the result of a player encountering the sides and falling into the pit of death.
   *
   * @return A string with the result of the encounter.
   */
  @Override
  public String getPitFall() {
    String temp = this.pitFall;
    return temp;
  }

  /**
   * Gets the result of a player picking up items.
   *
   * @return A string with the result of the pickup.
   */
  @Override
  public String getPickUpString() {
    String temp = this.pickupString;
    return temp;
  }
}
