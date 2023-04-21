package sw.db.cards.neutral.unit;

import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.playablecard.HasOnPurchaseTest;
import sw.db.game.Action;
import sw.db.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class QuarrenMercenaryTest extends NeutralPlayableCardTest implements HasOnPurchaseTest {

    @Override
    public void beforePurchase() {
        getPlayer().addForce(1);
    }

    @Override
    public void verifyAfterPurchase() {
        assertThat(card.getLocation(), equalTo(CardLocation.EmpireDiscard));

        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ExileCard));

        PlayableCard card1 = game.getEmpire().getHand().get(0);
        game.applyAction(card1.getId());

        assertThat(card1.getLocation(), equalTo(CardLocation.Exile));
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ExileCard));

        card1 = game.getEmpire().getHand().get(0);
        game.applyAction(card1.getId());

        assertThat(card1.getLocation(), equalTo(CardLocation.Exile));
        assertThat(game.getPendingActions(), hasSize(0));
    }

    @Override
    public int getId() {
        return 104;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),4, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}