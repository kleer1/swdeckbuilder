package starwars.deckbuilder.cards.empire.unit;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.CardLocation;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;


class MoffJerjerrodTest extends EmpireTargetableCardTest implements HasAbilityCardTest {

    @Override
    public void setupAbility() {
        PlayableCard card1 = (PlayableCard) game.getCardMap().get(29);
        card1.moveToGalaxyRow();
        PlayableCard card2 = (PlayableCard) game.getCardMap().get(30);
        card2.moveToTopOfGalaxyDeck();
        getPlayer().addForce(1);
    }

    @Override
    public void verifyAbility() {
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.empire), equalTo(1));
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.SwapTopCardOfDeck));

        game.applyAction(29);
        PlayableCard card1 = (PlayableCard) game.getCardMap().get(29);
        assertThat(card1.getLocation(), equalTo(CardLocation.GalaxyDeck));
        assertThat(card1.getCardList(), equalTo(game.getGalaxyDeck()));
        assertThat(card1, equalTo(game.getGalaxyDeck().get(0)));

        PlayableCard card2 = (PlayableCard) game.getCardMap().get(30);
        assertThat(card2.getLocation(), equalTo(CardLocation.GalaxyRow));
        assertThat(card2.getCardList(), equalTo(game.getGalaxyRow()));
        assertThat(game.getGalaxyRow(), hasItem(card2));

        assertThat(game.getPendingActions(), hasSize(0));
    }

    @Override
    public void assertReward() {
        assertForceIncreasedBy(Faction.rebellion, 3);
    }

    @Override
    public int getId() {
        return 28;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),2, 2);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }

    @Test
    void testAbilityWithoutForce() {
        useCardAbility(game, card);
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.empire), equalTo(1));
        assertThat(game.getPendingActions(), hasSize(0));
    }
}