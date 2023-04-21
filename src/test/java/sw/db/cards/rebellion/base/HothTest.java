package sw.db.cards.rebellion.base;

import sw.db.cards.base.HasAtStartOfTurnTest;
import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.empire.unit.DeathTrooper;
import sw.db.cards.empire.unit.TieInterceptor;
import sw.db.cards.rebellion.ship.RebelTransport;
import sw.db.game.ActionSpace;
import sw.db.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
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