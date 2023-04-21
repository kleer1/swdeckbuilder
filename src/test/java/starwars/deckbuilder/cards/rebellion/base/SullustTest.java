package starwars.deckbuilder.cards.rebellion.base;

import starwars.deckbuilder.cards.base.HasAtStartOfTurnTest;
import starwars.deckbuilder.cards.base.HasOnRevealTest;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.cards.rebellion.unit.LukeSkywalker;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.StaticEffect;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class SullustTest extends RebelAvailableBaseTest implements HasOnRevealTest, HasAtStartOfTurnTest {

    @Override
    public int getId() {
        return 134;
    }

    @Override
    public void assertAfterStartOfTurn() {
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects(), hasItem(StaticEffect.BuyNextToTopOfDeck));

        PlayableCard luke = moveToGalaxyRow(LukeSkywalker.class).get(0);
        getPlayer().addResources(9);

        game.applyAction(luke.getId());

        assertThat(game.getStaticEffects(), hasSize(0));
        assertThat(luke.getLocation(), equalTo(CardLocation.RebelDeck));
        assertThat(getPlayer().getDeck().get(0), equalTo(luke));
        assertThat(getPlayer().getResources(), equalTo(1));
    }

    @Override
    public void assertAfterChooseBase() {
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects(), hasItem(StaticEffect.BuyNextToTopOfDeck));
    }
}