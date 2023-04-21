package sw.db.cards.empire.base;

import sw.db.cards.base.AvailableBaseCardTest;
import sw.db.game.Player;

public abstract class EmpireAvailableBaseTest extends AvailableBaseCardTest {
    @Override
    public Player getPlayer() {
        return getGame().getEmpire();
    }
}
