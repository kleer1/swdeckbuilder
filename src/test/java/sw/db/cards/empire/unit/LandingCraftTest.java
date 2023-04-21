package sw.db.cards.empire.unit;

import org.junit.jupiter.api.Test;
import sw.db.cards.playablecard.HasAbilityCardTest;
import sw.db.game.Action;
import sw.db.game.ActionSpace;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class LandingCraftTest extends EmpireTargetableCardTest implements HasAbilityCardTest {

    @Override
    public void setupAbility() {
        getPlayer().getCurrentBase().addDamage(1);
        assertThat(getPlayer().getCurrentBase().getCurrentDamage(), equalTo(1));
    }

    @Override
    public void verifyAbility() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ChooseResourceOrRepair));
        game.applyAction(ActionSpace.ChooseRepair.getMinRange());
        assertThat(getPlayer().getCurrentBase().getCurrentDamage(), equalTo(0));
        assertNoForceChange();
    }

    @Override
    public void assertReward() {
        assertGameState(game.getRebel(),0, 4);
        assertGameState(game.getEmpire(),0, 0);
        assertNoForceChange();
    }

    @Override
    public int getId() {
        return 23;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }

    @Test
    void testChooseResourceWithDamagedBase() {
        setupAbility();
        useCardAbility(game, card);
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ChooseResourceOrRepair));
        game.applyAction(ActionSpace.ChooseResource.getMinRange());
        assertThat(getPlayer().getCurrentBase().getCurrentDamage(), equalTo(1));
        assertGameState(getPlayer(), 0, 4);
        assertNoForceChange();
    }

    @Test
    void testActionWithoutDamagedBase() {
        useCardAbility(game, card);
        assertGameState(getPlayer(), 0, 4);
    }
}