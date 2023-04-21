package sw.db.cards.rebellion.base;

import sw.db.cards.base.AvailableBaseCardTest;
import sw.db.game.Player;

public abstract class RebelAvailableBaseTest extends AvailableBaseCardTest {
    @Override
    public Player getPlayer() {
        return getGame().getRebel();
    }
}
