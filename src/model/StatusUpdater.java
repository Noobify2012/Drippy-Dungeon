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

  public StatusUpdater() {
    this.location = "";
    this.rubyCount = 0;
    this.diamondCount = 0;
    this.sapphireCount = 0;
    this.arrowCount = 0;
    this.smell = 0;
    this.directionList = null;
    this.caveTreasure = null;
  }

  void setLocation(String loc) {
    this.location = loc;
  }

  void setRubyCount(int count) {
    this.rubyCount = count;
  }

  void setDiamondCount(int count) {
    this.diamondCount = count;
  }

  void setSapphireCount(int count) {this.sapphireCount = count;}

  void setArrowCount(int count) {this.arrowCount = count;}

  void setSmell(int count) {this.smell = count;}

  void setDirectionList(List<Direction> directionList) {
    this.directionList = directionList;
  }

  void setCaveTreasure(List<Treasure> caveTreasure) {
    this.caveTreasure = caveTreasure;
  }


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
}
