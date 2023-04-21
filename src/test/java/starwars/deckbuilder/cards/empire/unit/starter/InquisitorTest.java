package starwars.deckbuilder.cards.empire.unit.starter;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.empire.EmpirePlayableCardTest;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.ActionSpace;
import starwars.deckbuilder.game.PendingAction;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class InquisitorTest extends EmpirePlayableCardTest {

    private static final int ID = 9;
    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();

        assertThat(game.getPendingActions(), hasSize(1));
        PendingAction pendingAction = game.getPendingActions().get(0);
        assertThat(pendingAction.getAction(), equalTo(Action.ChooseStatBoost));
    }

    @Test
    void testChooseAttack() {
        game.applyAction(ID);
        game.applyAction(ActionSpace.ChooseStatAttack.getMinRange());
        assertGameState(game.getEmpire(),1, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
        assertThat(game.getPendingActions(), hasSize(0));
    }

    @Test
    void testChooseResource() {
        game.applyAction(ID);
        game.applyAction(ActionSpace.ChooseStatResource.getMinRange());
        assertGameState(game.getEmpire(),0, 1);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
        assertThat(game.getPendingActions(), hasSize(0));
    }

    @Test
    void testChooseForce() {
        game.applyAction(ID);
        game.applyAction(ActionSpace.ChooseStatForce.getMinRange());
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),0, 0);
        assertForceIncreasedBy(Faction.empire, 1);
        assertThat(game.getPendingActions(), hasSize(0));
    }
}