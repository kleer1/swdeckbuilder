package sw.db.cards.rebellion;

import sw.db.cards.playablecard.PlayableCardTest;
import sw.db.game.Player;

public abstract class RebelPlayableCardTest extends PlayableCardTest {

    @Override
    public Player getPlayer() {
        return game.getRebel();
    }
}
