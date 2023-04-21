package starwars.deckbuilder.cards.rebellion.base;

import starwars.deckbuilder.cards.base.HasAtStartOfTurnTest;
import starwars.deckbuilder.cards.base.HasOnRevealTest;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.cards.neutral.unit.Z95Headhunter;
import starwars.deckbuilder.cards.rebellion.unit.YWing;
import starwars.deckbuilder.game.ActionSpace;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.StaticEffect;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class JedhaTest extends RebelAvailableBaseTest implements HasOnRevealTest, HasAtStartOfTurnTest {

    @Override
    public int getId() {
        return 136;
    }

    @Override
    public void assertAfterStartOfTurn() {
        assertThat(game.getStaticEffects(), hasSize(2));
        assertThat(game.getStaticEffects(), hasItems(StaticEffect.ExileNextNeutralPurchase,
                StaticEffect.BuyNextNeutralToHand));

        PlayableCard z95 = moveToGalaxyRow(Z95Headhunter.class).get(0);
        getPlayer().addResources(1);

        game.applyAction(z95.getId());

        assertThat(getPlayer().getResources(), equalTo(0));
        assertThat(game.getStaticEffects(), hasSize(0));
        assertThat(z95.getLocation(), equalTo(CardLocation.RebelHand));

        game.applyAction(ActionSpace.PassTurn.getMinRange());

        assertThat(z95.getLocation(), equalTo(CardLocation.Exile));
    }

    @Override
    public void assertAfterChooseBase() {
        assertThat(game.getStaticEffects(), hasSize(2));
        assertThat(game.getStaticEffects(), hasItems(StaticEffect.ExileNextNeutralPurchase,
                StaticEffect.BuyNextNeutralToHand));

        PlayableCard ywing = moveToGalaxyRow(YWing.class).get(0);

        getPlayer().addResources(1);

        game.applyAction(ywing.getId());

        assertThat(getPlayer().getResources(), equalTo(0));
        assertThat(ywing.getLocation(), equalTo(CardLocation.RebelDiscard));
        assertThat(game.getStaticEffects(), hasSize(2));
        assertThat(game.getStaticEffects(), hasItems(StaticEffect.ExileNextNeutralPurchase,
                StaticEffect.BuyNextNeutralToHand));

        PlayableCard z95 = moveToGalaxyRow(Z95Headhunter.class).get(0);
        getPlayer().addResources(1);

        game.applyAction(z95.getId());

        assertThat(getPlayer().getResources(), equalTo(0));
        assertThat(game.getStaticEffects(), hasSize(0));
        assertThat(z95.getLocation(), equalTo(CardLocation.RebelHand));

        game.applyAction(ActionSpace.PassTurn.getMinRange());

        assertThat(z95.getLocation(), equalTo(CardLocation.Exile));
    }
}