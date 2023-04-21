package sw.db.cards.neutral.unit;

import org.junit.jupiter.api.Test;
import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.playablecard.HasOnPurchaseTest;
import sw.db.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class FangFighterTest extends NeutralPlayableCardTest implements HasOnPurchaseTest {

    private PlayableCard topCard;
    @Override
    public void beforePurchase() {
        topCard = getPlayer().getDeck().get(0);
        getPlayer().addForce(1);
    }

    @Override
    public void verifyAfterPurchase() {
        assertThat(card.getLocation(), equalTo(CardLocation.EmpireHand));
        assertThat(topCard.getLocation(), equalTo(CardLocation.EmpireHand));
        assertThat(getPlayer().getHand(), hasSize(7));
    }

    @Test
    void testForceIsNotWithYou() {
        purchase();
        assertThat(card.getLocation(), equalTo(CardLocation.EmpireHand));
        assertThat(getPlayer().getHand(), hasSize(6));
    }

    @Override
    public int getId() {
        return 100;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),3, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}