package sw.db.cards.rebellion.unit;

import sw.db.cards.playablecard.HasAbilityCardTest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class UWingTest extends RebelTargetableCardTest implements HasAbilityCardTest {

    @Override
    public void setupAbility() {
        getPlayer().addForce(1);
        getPlayer().getCurrentBase().addDamage(5);
    }

    @Override
    public void verifyAbility() {
        assertThat(getPlayer().getCurrentBase().getCurrentDamage(), equalTo(2));
    }

    @Override
    public void assertReward() {
        assertGameState(game.getEmpire(),0, 4);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }

    @Override
    public int getId() {
        return 61;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),0, 3);
        assertNoForceChange();
    }
}