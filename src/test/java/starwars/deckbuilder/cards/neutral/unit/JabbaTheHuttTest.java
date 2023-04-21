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

class JabbaTheHuttTest extends NeutralPlayableCardTest implements HasAbilityCardTest {

    @Override
    public void setupAbility() {
        getPlayer().getOpponent().addForce(2);
    }

    @Override
    public void verifyAbility() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.JabbaExile));

        PlayableCard card1 = getPlayer().getHand().get(0);
        PlayableCard card2 = getPlayer().getDeck().get(0);

        game.applyAction(card1.getId());

        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(getPlayer().getHand(), hasSize(5));
        assertThat(card1.getLocation(), equalTo(CardLocation.Exile));
        assertThat(card2.getLocation(), equalTo(CardLocation.EmpireHand));
    }

    @Test
    void testForceIsWithYou() {
        useCardAbility(game, card);

        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.JabbaExile));

        PlayableCard card1 = getPlayer().getHand().get(0);
        PlayableCard card2 = getPlayer().getDeck().get(0);
        PlayableCard card3 = getPlayer().getDeck().get(1);

        game.applyAction(card1.getId());

        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(getPlayer().getHand(), hasSize(6));
        assertThat(card1.getLocation(), equalTo(CardLocation.Exile));
        assertThat(card2.getLocation(), equalTo(CardLocation.EmpireHand));
        assertThat(card3.getLocation(), equalTo(CardLocation.EmpireHand));
    }

    @Override
    public int getId() {
        return 112;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),2, 2);
        assertGameState(game.getRebel(),0, 0);
        assertForceIncreasedBy(Faction.empire, 2);
    }
}