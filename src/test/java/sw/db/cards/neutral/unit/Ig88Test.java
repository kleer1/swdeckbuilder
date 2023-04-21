package sw.db.cards.neutral.unit;

import sw.db.cards.playablecard.IsBountyHunterCardTest;
import sw.db.game.Action;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class Ig88Test extends NeutralPlayableCardTest implements IsBountyHunterCardTest {
    @Override
    public void verifyBountyHunterReward() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ExileCard));
    }

    @Override
    public int getId() {
        return 109;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),5, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}