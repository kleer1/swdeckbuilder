package starwars.deckbuilder.cards.neutral.ship;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.neutral.unit.NeutralPlayableCardTest;
import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.ActionSpace;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class NebulonBFrigateTest extends NeutralPlayableCardTest implements HasAbilityCardTest {

    @Override
    public void setupAbility() {
        getPlayer().getCurrentBase().addDamage(4);
        assertThat(getPlayer().getCurrentBase().getCurrentDamage(), equalTo(4));
    }

    @Override
    public void verifyAbility() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ChooseResourceOrRepair));
        game.applyAction(ActionSpace.ChooseRepair.getMinRange());
        assertThat(getPlayer().getCurrentBase().getCurrentDamage(), equalTo(1));
        assertNoForceChange();
    }

    @Override
    public int getId() {
        return 118;
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
        assertThat(getPlayer().getCurrentBase().getCurrentDamage(), equalTo(4));
        assertGameState(getPlayer(), 0, 3);
        assertNoForceChange();
    }

    @Test
    void testActionWithoutDamagedBase() {
        useCardAbility(game, card);
        assertGameState(getPlayer(), 0, 3);
    }
}