package sw.db.cards.rebellion.base;

import sw.db.cards.base.HasAtStartOfTurnTest;
import sw.db.cards.base.HasOnRevealTest;
import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.rebellion.unit.RebelCommando;
import sw.db.cards.rebellion.unit.Snowspeeder;
import sw.db.game.StaticEffect;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class Yavin4Test extends RebelAvailableBaseTest implements HasAtStartOfTurnTest, HasOnRevealTest {

    @Override
    public int getId() {
        return 139;
    }

    @Override
    public void assertAfterChooseBase() {
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects().get(0), equalTo(StaticEffect.Yavin4Effect));
    }

    @Override
    public void assertAfterStartOfTurn() {
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects().get(0), equalTo(StaticEffect.Yavin4Effect));

        assertThat(getPlayer().getOpponent().getCurrentBase().getCurrentDamage(), equalTo(0));

        // set up rebel commando random discard
        getPlayer().addForce(1);
        PlayableCard rc = moveToInPlay(RebelCommando.class, getPlayer()).get(0);
        game.applyAction(rc.getId());

        assertThat(getPlayer().getOpponent().getHand(), hasSize(4));
        assertThat(getPlayer().getOpponent().getCurrentBase().getCurrentDamage(), equalTo(2));

        // do it again with snow speeder to test both paths
        PlayableCard snow = moveToInPlay(Snowspeeder.class, getPlayer()).get(0);
        game.applyAction(snow.getId());

        PlayableCard card1 = getPlayer().getOpponent().getHand().get(0);
        game.applyAction(card1.getId());

        assertThat(getPlayer().getOpponent().getHand(), hasSize(3));
        assertThat(getPlayer().getOpponent().getCurrentBase().getCurrentDamage(), equalTo(4));
    }
}