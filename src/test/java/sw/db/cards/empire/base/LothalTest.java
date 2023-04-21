package sw.db.cards.empire.base;

import sw.db.cards.base.StartingBaseTest;
import sw.db.game.Player;

class LothalTest extends StartingBaseTest {

    @Override
    public Player getPlayer() {
        return game.getEmpire();
    }


    @Override
    public int getId() {
        return 120;
    }
}