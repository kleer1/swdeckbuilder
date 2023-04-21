package starwars.deckbuilder.cards.empire;

import starwars.deckbuilder.cards.playablecard.PlayableCardTest;
import starwars.deckbuilder.game.Player;

public abstract class EmpirePlayableCardTest extends PlayableCardTest {
    @Override
    public Player getPlayer() {
        return game.getEmpire();
    }
}
