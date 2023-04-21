package sw.db.cards.rebellion.base;

import org.hamcrest.Matchers;
import sw.db.cards.base.HasAtStartOfTurnTest;
import sw.db.cards.base.HasOnRevealTest;
import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.rebellion.unit.LukeSkywalker;
import sw.db.game.CardLocation;
import sw.db.game.StaticEffect;

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
        assertThat(game.getStaticEffects(), Matchers.hasItem(StaticEffect.BuyNextToTopOfDeck));

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