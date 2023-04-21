package starwars.deckbuilder.cards.rebellion.ship;

import starwars.deckbuilder.cards.rebellion.RebelPlayableCardTest;

class MonCalamariCruiserTest extends RebelPlayableCardTest {

    @Override
    public int getId() {
        return 78;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),3, 0);
        assertNoForceChange();
    }
}