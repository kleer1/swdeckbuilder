package sw.db.cards.rebellion.unit.starter;

import sw.db.cards.rebellion.RebelPlayableCardTest;

class AllianceShuttleTest extends RebelPlayableCardTest {
    private static final int ID = 40;
    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),0, 1);
        assertNoForceChange();
    }
}