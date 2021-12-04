package controller;

import model.Dungeon;
import model.DungeonImpl;
import model.Player;
import model.PlayerImpl;
import view.DungeonViewImpl;
import view.IDungeonView;

public class ViewController implements Controller {

  @Override
  public void buildDungeon() {
    Player player = new PlayerImpl();
    Dungeon defaultDungeon = new DungeonImpl(false, 4, 3, 0, 50 ,
            player, 1, 1);
    IDungeonView view = new DungeonViewImpl(defaultDungeon);

    view.makeVisible();
    view.refresh();
  }

  @Override
  public void playGame(Dungeon dungeon, IDungeonView view) {
    if (dungeon == null) {
      throw new IllegalArgumentException("The model cannot be null");
    }
    if (view == null) {
      throw new IllegalArgumentException("The view cannot be null");
    }


  }
}
