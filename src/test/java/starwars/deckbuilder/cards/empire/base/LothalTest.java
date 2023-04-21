package starwars.deckbuilder.cards.empire.base;

import starwars.deckbuilder.cards.base.StartingBaseTest;
import starwars.deckbuilder.game.Player;

class LothalTest extends StartingBaseTest {

    @Override
    public Player getPlayer() {
        return game.getEmpire();
    }


    @Override
    public int getId() {
        return 120;
    }
}