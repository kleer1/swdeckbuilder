package starwars.deckbuilder.cards.empire.base;

import starwars.deckbuilder.cards.base.HasAtStartOfTurnTest;
import starwars.deckbuilder.cards.base.HasOnRevealTest;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.cards.empire.unit.AtAt;
import starwars.deckbuilder.cards.empire.unit.BobaFett;
import starwars.deckbuilder.cards.empire.unit.starter.Stormtrooper;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.StaticEffect;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class EndorTest extends EmpireAvailableBaseTest implements HasOnRevealTest, HasAtStartOfTurnTest {

    @Override
    public void assertAfterChooseBase() {
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects().get(0), equalTo(StaticEffect.EndorBonus));

        PlayableCard stormtrooper = moveToInPlay(Stormtrooper.class, getPlayer()).get(0);
        assertThat(getPlayer().getAvailableAttack(), equalTo(3));
        stormtrooper.moveToDiscard();

        moveToInPlay(AtAt.class, getPlayer()).get(0);
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