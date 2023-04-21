package sw.db.cards.rebellion.base;

import org.hamcrest.Matchers;
import sw.db.cards.base.HasAtStartOfTurnTest;
import sw.db.cards.base.HasOnRevealTest;
import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.neutral.unit.Z95Headhunter;
import sw.db.cards.rebellion.unit.YWing;
import sw.db.game.ActionSpace;
import sw.db.game.CardLocation;
import sw.db.game.StaticEffect;

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
        assertThat(game.getStaticEffects(), Matchers.hasItems(StaticEffect.ExileNextNeutralPurchase,
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