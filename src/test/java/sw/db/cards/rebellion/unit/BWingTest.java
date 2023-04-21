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

class BWingTest extends RebelTargetableCardTest implements HasAbilityCardTest {

    @Override
    public void verifyAbility() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.BWingDiscard));
        assertThat(game.getCurrentPlayersAction(), equalTo(Faction.empire));

        PlayableCard card1 = getPlayer().getOpponent().getHand().get(0);
        game.applyAction(card1.getId());

        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(game.getCurrentPlayersAction(), equalTo(Faction.rebellion));
        assertThat(card1.getLocation(), equalTo(CardLocation.getDiscard(getPlayer().getOpponent().getFaction())));

        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),5, 0);
        assertNoForceChange();
    }

    @Override
    public void assertReward() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ExileCard));

        PlayableCard card1 = getPlayer().getOpponent().getHand().get(0);
        card1.moveToDiscard();
        PlayableCard card2 = getPlayer().getOpponent().getHand().get(0);

        game.applyAction(card1.getId());
        assertThat(card1.getLocation(), equalTo(CardLocation.Exile));
        assertThat(card1.getOwner(), is(nullValue()));
        assertThat(card1.getCardList(), equalTo(game.getExiledCards()));
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ExileCard));

        game.applyAction(card2.getId());
        assertThat(card2.getLocation(), equalTo(CardLocation.Exile));
        assertThat(card2.getOwner(), is(nullValue()));
        assertThat(card2.getCardList(), equalTo(game.getExiledCards()));
        assertThat(game.getPendingActions(), hasSize(0));
    }

    @Override
    public int getId() {
        return 63;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),5, 0);
        assertNoForceChange();
    }

    @Test
    void testNoDiscard() {
        useCardAbility(game, getCard());

        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.BWingDiscard));
        assertThat(game.getCurrentPlayersAction(), equalTo(Faction.empire));

        game.applyAction(ActionSpace.DeclineAction.getMinRange());

        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(game.getCurrentPlayersAction(), equalTo(Faction.rebellion));
        assertThat(getPlayer().getOpponent().getHand(), hasSize(5));

        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),7, 0);
        assertNoForceChange();
    }
}