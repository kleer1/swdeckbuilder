package sw.db.cards.rebellion.unit.starter;

import org.junit.jupiter.api.Test;
import sw.db.cards.common.models.Faction;
import sw.db.cards.rebellion.RebelPlayableCardTest;
import sw.db.game.Action;
import sw.db.game.ActionSpace;
import sw.db.game.PendingAction;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class TempleGuardianTest extends RebelPlayableCardTest {

    private static final int ID = 49;
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
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),1, 0);
        assertNoForceChange();
        assertThat(game.getPendingActions(), hasSize(0));
    }

    @Test
    void testChooseResource() {
        game.applyAction(ID);
        game.applyAction(ActionSpace.ChooseStatResource.getMinRange());
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),0, 1);
        assertNoForceChange();
        assertThat(game.getPendingActions(), hasSize(0));
    }

    @Test
    void testChooseForce() {
        game.applyAction(ID);
        game.applyAction(ActionSpace.ChooseStatForce.getMinRange());
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),0, 0);
        assertForceIncreasedBy(Faction.rebellion, 1);
        assertThat(game.getPendingActions(), hasSize(0));
    }
}