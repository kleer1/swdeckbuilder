package starwars.deckbuilder.cards.rebellion.unit;

import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class XWingTest extends RebelTargetableCardTest implements HasAbilityCardTest {

    @Override
    public void setupAbility() {
        getPlayer().addForce(1);
    }

    @Override
    public void verifyAbility() {
        assertThat(getPlayer().getHand(), hasSize(6));
    }

    @Override
    public void assertReward() {
        assertGameState(game.getEmpire(),0, 3);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }

    @Override
    public int getId() {
        return 58;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),3, 0);
        assertNoForceChange();
    }
}