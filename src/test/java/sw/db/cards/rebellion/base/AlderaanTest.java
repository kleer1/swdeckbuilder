package sw.db.cards.rebellion.base;

import sw.db.cards.base.HasOnRevealTest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class AlderaanTest extends RebelAvailableBaseTest implements HasOnRevealTest {

    @Override
    public int getId() {
        return 137;
    }

    @Override
    public void preChooseBaseSetup() {
        getPlayer().getOpponent().addForce(1);
        assertThat(game.getForceBalance().getPosition(), equalTo(2));
    }

    @Override
    public void assertAfterChooseBase() {
        assertThat(game.getForceBalance().getPosition(), equalTo(6));
    }
}