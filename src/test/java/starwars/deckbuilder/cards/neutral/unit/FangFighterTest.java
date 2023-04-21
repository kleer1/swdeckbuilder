package starwars.deckbuilder.cards.neutral.unit;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.cards.playablecard.HasOnPurchaseTest;
import starwars.deckbuilder.game.CardLocation;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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