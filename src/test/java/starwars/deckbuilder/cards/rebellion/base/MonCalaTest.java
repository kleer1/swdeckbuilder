package starwars.deckbuilder.cards.rebellion.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.base.HasOnRevealTest;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.StaticEffect;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class MonCalaTest extends RebelAvailableBaseTest implements HasOnRevealTest {

    @Override
    public int getId() {
        return 132;
    }

    @Override
    public void assertAfterChooseBase() {
        assertThat(game.getStaticEffects(), hasSize(2));
        assertThat(game.getStaticEffects(), hasItems(
                StaticEffect.NextFactionOrNeutralPurchaseIsFree,
                StaticEffect.BuyNextToHand));
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.PurchaseCard));

        PlayableCard card = game.getGalaxyRow().get(0);
        PlayableCard rebel = (PlayableCard) game.getCardMap().get(REBEL_GALAXY_CARD);
        if (rebel.getLocation() != CardLocation.GalaxyRow) {
            card.moveToTopOfGalaxyDeck();
            rebel.moveToGalaxyRow();
        }
        assertThat(game.getGalaxyRow(), hasSize(6));

        game.applyAction(rebel.getId());
        assertThat(rebel.getLocation(), equalTo(CardLocation.getHand(getPlayer().getFaction())));
        assertThat(game.getStaticEffects(), hasSize(0));
        assertThat(game.getPendingActions(), hasSize(0));
    }

    @Test
    void testPurchaseOfNeutral() {
        chooseBase();

        assertThat(game.getStaticEffects(), hasSize(2));
        assertThat(game.getStaticEffects(), hasItems(
                StaticEffect.NextFactionOrNeutralPurchaseIsFree,
                StaticEffect.BuyNextToHand));
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.PurchaseCard));

        PlayableCard card = game.getGalaxyRow().get(0);
        PlayableCard neutral = (PlayableCard) game.getCardMap().get(NEUTRAL_GALAXY_CARD);
        if (neutral.getLocation() != CardLocation.GalaxyRow) {
            card.moveToTopOfGalaxyDeck();
            neutral.moveToGalaxyRow();
        }

        assertThat(game.getGalaxyRow(), hasSize(6));

        game.applyAction(neutral.getId());

        assertThat(neutral.getLocation(), equalTo(CardLocation.getHand(getPlayer().getFaction())));
        assertThat(game.getStaticEffects(), hasSize(0));
        assertThat(game.getPendingActions(), hasSize(0));
    }
}