package sw.db.cards.rebellion.base;

import sw.db.cards.BaseTest;
import sw.db.cards.base.HasAtStartOfTurnTest;
import sw.db.cards.base.HasOnRevealTest;
import sw.db.cards.common.models.PlayableCard;
import sw.db.game.CardLocation;
import sw.db.game.StaticEffect;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class BespinTest extends RebelAvailableBaseTest implements HasAtStartOfTurnTest, HasOnRevealTest {

    @Override
    public int getId() {
        return 135;
    }

    @Override
    public void assertAfterStartOfTurn() {
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects().get(0), equalTo(StaticEffect.DrawOnFirstNeutralCard));
    }

    @Override
    public void assertAfterChooseBase() {
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects().get(0), equalTo(StaticEffect.DrawOnFirstNeutralCard));

        // play a neutral card
        PlayableCard neutral = (PlayableCard) game.getCardMap().get(BaseTest.NEUTRAL_GALAXY_CARD);
        neutral.buyToHand(getPlayer());
        getPlayer().drawCards(1);
        assertThat(getPlayer().getHand(), hasSize(7));

        PlayableCard topCard = getPlayer().getDeck().get(0);

        game.applyAction(neutral.getId());
        assertThat(getPlayer().getHand(), hasSize(7));
        assertThat(topCard.getLocation(), equalTo(CardLocation.getHand(getPlayer().getFaction())));
        assertThat(neutral.getLocation(), equalTo(CardLocation.getUnitsInPlay(getPlayer().getFaction())));
        assertThat(game.getStaticEffects(), hasSize(0));
    }
}