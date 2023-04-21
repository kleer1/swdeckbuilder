package sw.db.cards.empire.ship;

import sw.db.cards.empire.EmpirePlayableCardTest;
import sw.db.cards.empire.unit.TieBomber;
import sw.db.cards.empire.unit.TieFighter;
import sw.db.game.ActionSpace;

class ImperialCarrierTest extends EmpirePlayableCardTest {

    @Override
    public int getId() {
        return 36;
    }

    @Override
    protected void prePlaySetup() {
        moveToInPlay(TieFighter.class, getPlayer(), 2);
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),6, 3);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();

        game.applyAction(ActionSpace.PassTurn.getMinRange());
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();

        game.applyAction(ActionSpace.PassTurn.getMinRange());
        assertGameState(game.getEmpire(),0, 3);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();

        moveToInPlay(TieBomber.class, getPlayer(), 2);
        assertGameState(game.getEmpire(),6, 3);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();

        card.moveToDiscard();
        assertGameState(game.getEmpire(),4, 3);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}