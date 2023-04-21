package starwars.deckbuilder.cards.empire.base;

import starwars.deckbuilder.cards.base.HasAtStartOfTurnTest;
import starwars.deckbuilder.cards.base.HasOnRevealTest;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.StaticEffect;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class KafreneTest extends EmpireAvailableBaseTest implements HasOnRevealTest, HasAtStartOfTurnTest {

    @Override
    public int getId() {
        return 125;
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
        PlayableCard neutral = (PlayableCard) game.getCardMap().get(NEUTRAL_GALAXY_CARD);
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