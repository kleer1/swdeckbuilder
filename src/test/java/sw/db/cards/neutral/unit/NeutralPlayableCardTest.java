package sw.db.cards.neutral.unit;

import sw.db.cards.playablecard.PlayableCardTest;
import sw.db.game.Player;

public abstract class NeutralPlayableCardTest extends PlayableCardTest {
    // testing all neutrals as empire
    @Override
    public Player getPlayer() {
        return game.getEmpire();
    }
}
