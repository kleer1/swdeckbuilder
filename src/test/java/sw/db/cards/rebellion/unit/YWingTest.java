package sw.db.cards.rebellion.unit;

import sw.db.cards.playablecard.HasAbilityCardTest;
import sw.db.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class YWingTest extends RebelTargetableCardTest implements HasAbilityCardTest {

    @Override
    public void verifyAbility() {
        assertThat(card.getLocation(), equalTo(CardLocation.Exile));
        assertThat(getPlayer().getOpponent().getCurrentBase().getCurrentDamage(), equalTo(2));
    }

    @Override
    public void assertReward() {
        assertGameState(game.getEmpire(),0, 1);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }

    @Override
    public int getId() {
        return 50;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),2, 0);
        assertNoForceChange();
    }
}