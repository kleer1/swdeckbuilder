package starwars.deckbuilder.cards.rebellion.unit;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.CardLocation;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class RebelCommandoTest extends RebelTargetableCardTest implements HasAbilityCardTest {

    @Override
    public void setupAbility() {
        getPlayer().addForce(1);
    }

    @Override
    public void verifyAbility() {
        assertThat(game.getEmpire().getHand(), hasSize(4));
    }

    @Test
    void testForceNotWithYou() {
        useCardAbility(game, card);

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
        assertForceIncreasedBy(Faction.empire, 2);
    }

    @Override
    public int getId() {
        return 56;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),3, 0);
        assertNoForceChange();
    }
}