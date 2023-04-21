package starwars.deckbuilder.cards.rebellion.unit;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.game.Action;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class JynErsoTest extends RebelTargetableCardTest implements HasAbilityCardTest {

    @Override
    public void setupAbility() {
        getPlayer().addForce(1);
    }

    @Override
    public void verifyAbility() {
        assertThat(game.canSeeOpponentsHand(), equalTo(true));
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.JynErsoTopDeck));

        PlayableCard card1 = getPlayer().getOpponent().getHand().get(0);
        game.applyAction(card1.getId());

        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(card1, equalTo(game.getEmpire().getDeck().get(0)));
    }

    @Override
    public void assertReward() {
        assertForceIncreasedBy(Faction.empire, 3);
    }

    @Override
    public int getId() {
        return 67;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),4, 0);
        assertNoForceChange();
    }

    @Test
    void testNotForceWithYou() {
        useCardAbility(game, card);
        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(game.canSeeOpponentsHand(), equalTo(true));
    }
}