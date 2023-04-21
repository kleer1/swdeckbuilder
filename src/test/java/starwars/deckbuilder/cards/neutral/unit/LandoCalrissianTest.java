package starwars.deckbuilder.cards.neutral.unit;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class LandoCalrissianTest extends NeutralPlayableCardTest implements HasAbilityCardTest {

    @Override
    public void setupAbility() {
        getPlayer().addForce(1);
    }

    @Override
    public void verifyAbility() {
        assertThat(getPlayer().getHand(), hasSize(6));
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.DiscardFromHand));
        assertThat(game.getCurrentPlayersAction(), equalTo(Faction.rebellion));

        PlayableCard card1 = game.getRebel().getHand().get(0);

        game.applyAction(card1.getId());

        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(card1.getLocation(), equalTo(CardLocation.RebelDiscard));
        assertThat(game.getCurrentPlayersAction(), equalTo(Faction.empire));
    }

    @Test
    void testForceIsNotWithYou() {
        PlayableCard card1 = getPlayer().getDeck().get(0);

        useCardAbility(game, card);
        assertThat(getPlayer().getHand(), hasSize(6));
        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(card1.getLocation(), equalTo(CardLocation.EmpireHand));
    }

    @Override
    public int getId() {
        return 110;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),3, 3);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}