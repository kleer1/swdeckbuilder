package starwars.deckbuilder.cards.empire.base;

import starwars.deckbuilder.cards.base.HasAtStartOfTurnTest;
import starwars.deckbuilder.cards.base.HasOnRevealTest;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.cards.empire.unit.starter.Stormtrooper;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.ActionSpace;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.StaticEffect;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class OrdMantellTest extends EmpireAvailableBaseTest implements HasOnRevealTest, HasAtStartOfTurnTest {

    @Override
    public void assertAfterChooseBase() {
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects().get(0), equalTo(StaticEffect.CanBountyOneNeutral));

        // Confirm we can attack a neutral card
        PlayableCard neutral = (PlayableCard) game.getCardMap().get(NEUTRAL_GALAXY_CARD);
        neutral.moveToGalaxyRow();

        PlayableCard stormtrooper = moveToInPlay(Stormtrooper.class, getPlayer()).get(0);

        game.applyAction(ActionSpace.AttackNeutralCard.getMinRange());
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.AttackCenterRow));

        game.applyAction(neutral.getId());
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.SelectAttacker));

        game.applyAction(stormtrooper.getId());
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.SelectAttacker));

        game.applyAction(ActionSpace.ConfirmAttackers.getMinRange());
        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(game.getStaticEffects(), hasSize(0));
        assertThat(neutral.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(getPlayer().getResources(), equalTo(neutral.getCost()));
    }

    @Override
    public int getId() {
        return 127;
    }

    @Override
    public void assertAfterStartOfTurn() {
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects().get(0), equalTo(StaticEffect.CanBountyOneNeutral));
    }
}