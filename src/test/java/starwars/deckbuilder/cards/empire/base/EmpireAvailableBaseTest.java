package starwars.deckbuilder.cards.empire.base;

import starwars.deckbuilder.cards.base.AvailableBaseCardTest;
import starwars.deckbuilder.game.Player;

public abstract class EmpireAvailableBaseTest extends AvailableBaseCardTest {
    @Override
    public Player getPlayer() {
        return getGame().getEmpire();
    }
}
