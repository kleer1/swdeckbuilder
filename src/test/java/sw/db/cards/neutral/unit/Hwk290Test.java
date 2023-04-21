package sw.db.cards.neutral.unit;

import sw.db.cards.playablecard.HasAbilityCardTest;
import sw.db.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class Hwk290Test extends NeutralPlayableCardTest implements HasAbilityCardTest {

    @Override
    public void setupAbility() {
        getPlayer().getCurrentBase().addDamage(5);
    }

    @Override
    public void verifyAbility() {
        assertThat(card.getLocation(), equalTo(CardLocation.Exile));
        assertThat(getPlayer().getCurrentBase().getCurrentDamage(), equalTo(1));
    }

    @Override
    public int getId() {
        return 102;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 4);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}