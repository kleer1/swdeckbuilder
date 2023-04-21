package starwars.deckbuilder.cards.neutral.unit;

import starwars.deckbuilder.cards.playablecard.PlayableCardTest;
import starwars.deckbuilder.game.Player;

public abstract class NeutralPlayableCardTest extends PlayableCardTest {
    // testing all neutrals as empire
    @Override
    public Player getPlayer() {
        return game.getEmpire();
    }
}
