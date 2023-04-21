package starwars.deckbuilder.cards.rebellion.base;

import starwars.deckbuilder.cards.base.StartingBaseTest;
import starwars.deckbuilder.game.Player;

class DantooineTest extends StartingBaseTest {

    @Override
    public int getId() {
        return 130;
    }

    @Override
    public Player getPlayer() {
        return game.getRebel();
    }
}