package sw.db.cards.empire.unit;

import sw.db.cards.common.models.Faction;
import sw.db.cards.playablecard.IsBountyHunterCardTest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class BobaFettTest extends EmpireTargetableCardTest implements IsBountyHunterCardTest {
    private static final int ID = 29;

    @Override
    public void assertReward() {
        assertThat(game.getRebel().getResources(), equalTo(3));
        assertForceIncreasedBy(Faction.rebellion, 2);
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),5, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }

    @Override
    public void verifyPreBounty() {
        assertThat(getPlayer().getHand(), hasSize(5));
    }

    @Override
    public void verifyBountyHunterReward() {
        assertThat(getPlayer().getHand(), hasSize(6));
    }
}