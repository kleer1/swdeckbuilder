package sw.db.cards.empire.unit.starter;

import sw.db.cards.empire.EmpirePlayableCardTest;

class StormtrooperTest extends EmpirePlayableCardTest {

    private static final int ID = 7;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),2, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}