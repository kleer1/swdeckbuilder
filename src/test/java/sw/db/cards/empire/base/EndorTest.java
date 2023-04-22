package sw.db.cards.empire.base;

import sw.db.cards.base.HasAtStartOfTurnTest;
import sw.db.cards.base.HasOnRevealTest;
import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.empire.unit.AtAt;
import sw.db.cards.empire.unit.BobaFett;
import sw.db.cards.empire.unit.starter.Stormtrooper;
import sw.db.game.StaticEffect;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class EndorTest extends EmpireAvailableBaseTest implements HasOnRevealTest, HasAtStartOfTurnTest {

    @Override
    public void assertAfterChooseBase() {
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects().get(0), equalTo(StaticEffect.EndorBonus));

        PlayableCard stormtrooper = moveToInPlay(Stormtrooper.class, getPlayer()).get(0);
        assertThat(getPlayer().getAvailableAttack(), equalTo(3));
        stormtrooper.moveToDiscard();

        moveToInPlay(AtAt.class, getPlayer());
        assertThat(getPlayer().getAvailableAttack(), equalTo(7));
        stormtrooper.moveToInPlay();
        assertThat(getPlayer().getAvailableAttack(), equalTo(10));
        moveToInPlay(BobaFett.class, getPlayer());
        assertThat(getPlayer().getAvailableAttack(), equalTo(15));
    }

    @Override
    public int getId() {
        return 128;
    }

    @Override
    public void assertAfterStartOfTurn() {
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects().get(0), equalTo(StaticEffect.EndorBonus));
    }
}