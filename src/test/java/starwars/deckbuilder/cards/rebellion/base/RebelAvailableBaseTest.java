package starwars.deckbuilder.cards.rebellion.base;

import starwars.deckbuilder.cards.base.AvailableBaseCardTest;
import starwars.deckbuilder.game.Player;

public abstract class RebelAvailableBaseTest extends AvailableBaseCardTest {
    @Override
    public Player getPlayer() {
        return getGame().getRebel();
    }
}
