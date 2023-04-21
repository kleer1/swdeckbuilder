package starwars.deckbuilder.cards.rebellion;

import starwars.deckbuilder.cards.playablecard.PlayableCardTest;
import starwars.deckbuilder.game.Player;

public abstract class RebelPlayableCardTest extends PlayableCardTest {

    @Override
    public Player getPlayer() {
        return game.getRebel();
    }
}
