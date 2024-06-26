package sw.db.cards.empire.unit.starter;

import sw.db.cards.empire.EmpirePlayableCardTest;

class ImperialShuttleTest extends EmpirePlayableCardTest {

    private static final int ID = 0;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 1);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}