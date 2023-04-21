package starwars.deckbuilder.cards.rebellion.unit.starter;

import starwars.deckbuilder.cards.rebellion.RebelPlayableCardTest;

class RebelTrooperTest extends RebelPlayableCardTest {

    private static final int ID = 47;
    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),2, 0);
        assertNoForceChange();
    }
}