package sw.db.cards.rebellion.ship;

import org.junit.jupiter.api.Test;
import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.empire.ship.StarDestroyer;
import sw.db.cards.playablecard.HasAbilityCardTest;
import sw.db.cards.rebellion.RebelPlayableCardTest;
import sw.db.game.Action;
import sw.db.game.ActionSpace;
import sw.db.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class HammerheadCorvetteTest extends RebelPlayableCardTest implements HasAbilityCardTest {

    private PlayableCard empShip;

    @Override
    public void setupAbility() {
        empShip = moveToInPlay(StarDestroyer.class, getPlayer().getOpponent()).get(0);
    }

    @Override
    public void verifyAbility() {
        assertThat(card.getLocation(), equalTo(CardLocation.Exile));
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.HammerHeadAway));

        game.applyAction(empShip.getId());

        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(empShip.getLocation(), equalTo(CardLocation.getDiscard(getPlayer().getOpponent().getFaction())));
    }

    @Test
    void testDestroyFromGalaxyRow() {
        empShip = moveToGalaxyRow(StarDestroyer.class).get(0);

        useCardAbility(game, card);

        assertThat(card.getLocation(), equalTo(CardLocation.Exile));
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.HammerHeadAway));

        game.applyAction(empShip.getId());

        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(empShip.getLocation(), equalTo(CardLocation.GalaxyDiscard));
    }

    @Override
    public int getId() {
        return 76;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),0, 2);
        assertNoForceChange();

        game.applyAction(ActionSpace.PassTurn.getMinRange());

        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();

        game.applyAction(ActionSpace.PassTurn.getMinRange());

        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),0, 2);
        assertNoForceChange();
    }
}