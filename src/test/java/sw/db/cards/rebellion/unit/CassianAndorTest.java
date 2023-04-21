package sw.db.cards.rebellion.unit;

import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.playablecard.IsBountyHunterCardTest;
import sw.db.game.Action;
import sw.db.game.ActionSpace;
import sw.db.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class CassianAndorTest extends RebelTargetableCardTest implements IsBountyHunterCardTest {

    @Override
    public void verifyBountyHunterReward() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.DiscardFromHand));
        assertThat(game.getCurrentPlayersAction(), equalTo(Faction.empire));

        // can't decline
        game.applyAction(ActionSpace.DeclineAction.getMinRange());
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.DiscardFromHand));
        assertThat(game.getCurrentPlayersAction(), equalTo(Faction.empire));

        PlayableCard card1 = getPlayer().getOpponent().getHand().get(0);
        game.applyAction(card1.getId());

        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(game.getCurrentPlayersAction(), equalTo(Faction.rebellion));
        assertThat(card1.getLocation(), equalTo(CardLocation.getDiscard(getPlayer().getOpponent().getFaction())));
    }

    @Override
    public void assertReward() {
        assertGameState(game.getEmpire(),0, 3);
        assertGameState(game.getRebel(),0, 0);
        assertForceIncreasedBy(Faction.empire, 2);
    }

    @Override
    public int getId() {
        return 70;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),5, 0);
        assertNoForceChange();
    }
}