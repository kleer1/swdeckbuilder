package sw.db.cards.empire.unit;

import org.junit.jupiter.api.Test;
import sw.db.cards.BaseTest;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.playablecard.HasAbilityCardTest;
import sw.db.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class TieInterceptorTest extends EmpireTargetableCardTest implements HasAbilityCardTest {

    @Override
    public void assertReward() {
        assertGameState(game.getRebel(),0, 3);
        assertNoForceChange();
    }

    @Override
    public int getId() {
        return 20;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),3, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }

    @Override
    public void setupAbility() {
        setUpAbility(BaseTest.EMPIRE_GALAXY_CARD);
    }

    @Override
    public void verifyAbility() {
        PlayableCard playableCard = (PlayableCard) game.getCardMap().get(BaseTest.EMPIRE_GALAXY_CARD);
        assertThat(playableCard.getLocation(), equalTo(CardLocation.GalaxyDeck));
        assertThat(playableCard, equalTo(game.getGalaxyDeck().get(0)));
        assertThat(getPlayer().getHand(), hasSize(6));
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.empire), equalTo(1));
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.rebellion), equalTo(1));
    }

    @Test
    void testRebelCardOnTop() {
        setUpAbility(BaseTest.REBEL_GALAXY_CARD);
        useCardAbility(game, card);
        PlayableCard playableCard = (PlayableCard) game.getCardMap().get(BaseTest.REBEL_GALAXY_CARD);
        assertThat(playableCard.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(getPlayer().getHand(), hasSize(5));
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.empire), equalTo(0));
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.rebellion), equalTo(0));
    }

    @Test
    void testNeutralCardOnTop() {
        setUpAbility(BaseTest.NEUTRAL_GALAXY_CARD);
        useCardAbility(game, card);
        PlayableCard playableCard = (PlayableCard) game.getCardMap().get(BaseTest.NEUTRAL_GALAXY_CARD);
        assertThat(playableCard.getLocation(), equalTo(CardLocation.GalaxyDeck));
        assertThat(playableCard, equalTo(game.getGalaxyDeck().get(0)));
        assertThat(getPlayer().getHand(), hasSize(5));
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.empire), equalTo(1));
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.rebellion), equalTo(1));
    }

    private void setUpAbility(int id) {
        PlayableCard playableCard = (PlayableCard) game.getCardMap().get(id);
        playableCard.moveToTopOfGalaxyDeck();
    }
}