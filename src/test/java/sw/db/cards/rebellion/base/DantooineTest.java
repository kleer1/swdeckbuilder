package sw.db.cards.rebellion.base;

import sw.db.cards.base.StartingBaseTest;
import sw.db.game.Player;

class DantooineTest extends StartingBaseTest {

    @Override
    public int getId() {
        return 130;
    }

    @Override
    public Player getPlayer() {
        return game.getRebel();
    }
}