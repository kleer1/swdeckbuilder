package sw.db.cards.rebellion.unit;

import org.junit.jupiter.api.Test;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.playablecard.HasAbilityCardTest;
import sw.db.game.Action;
import sw.db.game.ActionSpace;
import sw.db.game.CardLocation;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class DurosSpyTest extends RebelTargetableCardTest implements HasAbilityCardTest {

    @Override
    public void verifyAbility() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.DurosDiscard));
        assertThat(game.getCurrentPlayersAction(), equalTo(Faction.empire));

        PlayableCard card1 = getPlayer().getOpponent().getHand().get(0);
        game.applyAction(card1.getId());

        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(game.getCurrentPlayersAction(), equalTo(Faction.rebellion));
        assertThat(card1.getLocation(), equalTo(CardLocation.getDiscard(getPlayer().getOpponent().getFaction())));

        assertNoForceChange();
    }

    @Override
    public void assertReward() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ExileCard));

        PlayableCard card1 = getPlayer().getOpponent().getHand().get(0);

        game.applyAction(card1.getId());
        assertThat(card1.getLocation(), equalTo(CardLocation.Exile));
        assertThat(card1.getOwner(), is(nullValue()));
        assertThat(card1.getCardList(), equalTo(game.getExiledCards()));
        assertThat(game.getPendingActions(), hasSize(0));
    }

    @Override
    public int getId() {
        return 54;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),0, 2);
        assertNoForceChange();
    }

    @Test
    void testNoDiscard() {
        useCardAbility(game, getCard());

        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.DurosDiscard));
        assertThat(game.getCurrentPlayersAction(), equalTo(Faction.empire));

        game.applyAction(ActionSpace.DeclineAction.getMinRange());

        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(game.getCurrentPlayersAction(), equalTo(Faction.rebellion));
        assertThat(getPlayer().getOpponent().getHand(), hasSize(5));

        assertForceIncreasedBy(Faction.rebellion, 1);
    }
}