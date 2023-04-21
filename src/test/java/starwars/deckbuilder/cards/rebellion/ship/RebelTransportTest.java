package starwars.deckbuilder.cards.rebellion.ship;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.cards.rebellion.RebelPlayableCardTest;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.ActionSpace;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class RebelTransportTest extends RebelPlayableCardTest implements HasAbilityCardTest {

    @Override
    public void setupAbility() {
        getPlayer().getCurrentBase().addDamage(3);
        assertThat(getPlayer().getCurrentBase().getCurrentDamage(), equalTo(3));
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
        return 74;
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
        assertThat(getPlayer().getCurrentBase().getCurrentDamage(), equalTo(3));
        assertGameState(getPlayer(), 0, 1);
        assertNoForceChange();
    }

    @Test
    void testActionWithoutDamagedBase() {
        useCardAbility(game, card);
        assertGameState(getPlayer(), 0, 1);
    }
}