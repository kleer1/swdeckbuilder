package sw.db.cards.empire;

import sw.db.cards.playablecard.PlayableCardTest;
import sw.db.game.Player;

public abstract class EmpirePlayableCardTest extends PlayableCardTest {
    @Override
    public Player getPlayer() {
        return game.getEmpire();
    }
}
