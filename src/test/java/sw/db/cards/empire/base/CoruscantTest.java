package sw.db.cards.empire.base;

import org.junit.jupiter.api.Test;
import sw.db.cards.base.HasAtStartOfTurnTest;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.PlayableCard;
import sw.db.game.Action;
import sw.db.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class CoruscantTest extends EmpireAvailableBaseTest implements HasAtStartOfTurnTest {

    @Override
    public int getId() {
        return 129;
    }

    @Override
    public void assertAfterStartOfTurn() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.GalacticRule));
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.empire), equalTo(2));
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.rebellion), equalTo(0));

        PlayableCard card1 = game.getGalaxyDeck().get(0);
        PlayableCard card2 = game.getGalaxyDeck().get(1);

        game.applyAction(card1.getId());
        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(card1.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(game.getGalaxyDeck().get(0), equalTo(card2));
     }

    @Test
    void testPickOtherCard() {
        triggerStartOfTurn();
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.GalacticRule));
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.empire), equalTo(2));
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.rebellion), equalTo(0));

        PlayableCard card1 = game.getGalaxyDeck().get(0);
        PlayableCard card2 = game.getGalaxyDeck().get(1);

        game.applyAction(card2.getId());
        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(card2.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(game.getGalaxyDeck().get(0), equalTo(card1));
    }
}