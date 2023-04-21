package starwars.deckbuilder.cards.empire.ship;

import starwars.deckbuilder.cards.empire.EmpirePlayableCardTest;
import starwars.deckbuilder.game.ActionSpace;

class StarDestroyerTest extends EmpirePlayableCardTest {

    @Override
    public int getId() {
        return 39;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),4, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();

        game.applyAction(ActionSpace.PassTurn.getMinRange());
        assertGameState(game.getEmpire(),4, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();

        game.applyAction(ActionSpace.PassTurn.getMinRange());
        assertGameState(game.getEmpire(),4, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}