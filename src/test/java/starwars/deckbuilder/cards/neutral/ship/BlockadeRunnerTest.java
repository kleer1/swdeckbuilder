package starwars.deckbuilder.cards.neutral.ship;

import starwars.deckbuilder.cards.neutral.unit.NeutralPlayableCardTest;

class BlockadeRunnerTest extends NeutralPlayableCardTest {

    @Override
    public int getId() {
        return 115;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),1, 1);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}