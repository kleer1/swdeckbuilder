package sw.db.cards.empire.unit;

import org.junit.jupiter.api.Test;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.playablecard.HasAbilityCardTest;
import sw.db.game.Action;
import sw.db.game.CardLocation;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class ScoutTrooperTest extends EmpireTargetableCardTest implements HasAbilityCardTest {

    private static final int REBEL_CARD_ID = 40;
    @Override
    public void setupAbility() {
        PlayableCard card1 = (PlayableCard) game.getCardMap().get(28);
        card1.moveToTopOfGalaxyDeck();
    }

    @Override
    public void verifyAbility() {
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.empire), equalTo(1));
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.rebellion), equalTo(1));
        assertForceIncreasedBy(Faction.empire, 1);
    }

    @Override
    public void assertReward() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ExileCard));

        PlayableCard card1 = (PlayableCard) game.getCardMap().get(REBEL_CARD_ID);
        card1.moveToDiscard();

        game.applyAction(REBEL_CARD_ID);
        assertThat(card1.getLocation(), equalTo(CardLocation.Exile));
        assertThat(card1.getOwner(), is(nullValue()));
        assertThat(card1.getCardList(), equalTo(game.getExiledCards()));
        assertThat(game.getPendingActions(), hasSize(0));
    }

    @Override
    public int getId() {
        return 17;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 2);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }

    @Test
    void testRebelCardOnTop() {
        PlayableCard card1 = (PlayableCard) game.getCardMap().get(50);
        card1.moveToTopOfGalaxyDeck();
        useCardAbility(game, card);
        assertThat(card1.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.empire), equalTo(0));
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.rebellion), equalTo(0));
        assertNoForceChange();
    }

    @Test
    void testNeutralCardOnTop() {
        PlayableCard card1 = (PlayableCard) game.getCardMap().get(90);
        card1.moveToTopOfGalaxyDeck();
        useCardAbility(game, card);
        assertThat(card1.getLocation(), equalTo(CardLocation.GalaxyDeck));
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.empire), equalTo(1));
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.rebellion), equalTo(1));
        assertNoForceChange();
    }
}