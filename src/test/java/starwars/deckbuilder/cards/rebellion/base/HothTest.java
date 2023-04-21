package starwars.deckbuilder.cards.rebellion.base;

import starwars.deckbuilder.cards.base.HasAtStartOfTurnTest;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.cards.empire.unit.DeathTrooper;
import starwars.deckbuilder.cards.empire.unit.TieInterceptor;
import starwars.deckbuilder.cards.rebellion.ship.RebelTransport;
import starwars.deckbuilder.game.ActionSpace;
import starwars.deckbuilder.game.CardLocation;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class HothTest extends RebelAvailableBaseTest implements HasAtStartOfTurnTest {

    @Override
    public int getId() {
        return 131;
    }

    @Override
    public void assertAfterStartOfTurn() {
        PlayableCard rebelTransport = moveToInPlay(RebelTransport.class, getPlayer()).get(0);
        game.applyAction(ActionSpace.PassTurn.getMinRange());

        PlayableCard deathTrooper = moveToInPlay(DeathTrooper.class, getPlayer().getOpponent()).get(0);
        game.applyAction(base.getId());
        game.applyAction(deathTrooper.getId());
        game.applyAction(ActionSpace.ConfirmAttackers.getMinRange());

        assertThat(rebelTransport.getLocation(), equalTo(CardLocation.RebelDiscard));
        assertThat(base.getCurrentDamage(), equalTo(0));

        PlayableCard intercept = moveToInPlay(TieInterceptor.class, getPlayer().getOpponent()).get(0);
        game.applyAction(base.getId());
        game.applyAction(intercept.getId());
        game.applyAction(ActionSpace.ConfirmAttackers.getMinRange());

        assertThat(base.getCurrentDamage(), equalTo(2));

        game.applyAction(ActionSpace.PassTurn.getMinRange());
        game.applyAction(ActionSpace.PassTurn.getMinRange());

        deathTrooper.moveToInPlay();
        intercept.moveToInPlay();

        game.applyAction(base.getId());
        game.applyAction(deathTrooper.getId());
        game.applyAction(ActionSpace.ConfirmAttackers.getMinRange());

        assertThat(base.getCurrentDamage(), equalTo(3));

        game.applyAction(base.getId());
        game.applyAction(intercept.getId());
        game.applyAction(ActionSpace.ConfirmAttackers.getMinRange());

        assertThat(base.getCurrentDamage(), equalTo(6));
    }
}