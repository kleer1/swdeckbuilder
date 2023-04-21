package starwars.deckbuilder.cards.empire.base;

import starwars.deckbuilder.cards.base.HasOnRevealTest;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class MustafarTest extends EmpireAvailableBaseTest implements HasOnRevealTest {

    @Override
    public int getId() {
        return 122;
    }

    @Override
    public void preChooseBaseSetup() {
        getPlayer().getOpponent().addForce(1);
        assertThat(game.getForceBalance().getPosition(), equalTo(4));
    }

    @Override
    public void assertAfterChooseBase() {
        assertThat(game.getForceBalance().getPosition(), equalTo(0));
    }
}