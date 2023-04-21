package sw.db.cards.neutral.unit;

import org.junit.jupiter.api.Test;
import sw.db.cards.common.models.Faction;
import sw.db.game.Action;
import sw.db.game.ActionSpace;
import sw.db.game.PendingAction;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class LobotTest extends NeutralPlayableCardTest {
    private static final int ID = 106;

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
        assertGameState(game.getEmpire(),2, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
        assertThat(game.getPendingActions(), hasSize(0));
    }

    @Test
    void testChooseResource() {
        game.applyAction(ID);
        game.applyAction(ActionSpace.ChooseStatResource.getMinRange());
        assertGameState(game.getEmpire(),0, 2);
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
        assertForceIncreasedBy(Faction.empire, 2);
        assertThat(game.getPendingActions(), hasSize(0));
    }
}