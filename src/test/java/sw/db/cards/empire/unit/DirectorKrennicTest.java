package sw.db.cards.empire.unit;

import org.junit.jupiter.api.Test;
import sw.db.cards.common.models.Base;
import sw.db.cards.common.models.Faction;
import sw.db.cards.playablecard.HasAbilityCardTest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class DirectorKrennicTest extends EmpireTargetableCardTest implements HasAbilityCardTest {

    private static final int DEATH_STAR_ID = 121;

    @Override
    public void setupAbility() {
        getPlayer().setCurrentBase(null);
        Base ds = (Base) game.getCardMap().get(DEATH_STAR_ID);
        ds.makeCurrentBase();
    }
    @Override
    public void verifyAbility() {
        assertThat(getPlayer().getHand(), hasSize(7));
    }

    @Override
    public void assertReward() {
        assertThat(game.getRebel().getResources(), equalTo(3));
        assertForceIncreasedBy(Faction.rebellion, 2);
    }

    @Override
    public int getId() {
        return 30;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),3, 2);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }

    @Test
    void testAbilityWithoutDeathStar() {
        useCardAbility(game, card);
        assertThat(getPlayer().getHand(), hasSize(6));
    }
}