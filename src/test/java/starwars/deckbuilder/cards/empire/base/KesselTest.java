package starwars.deckbuilder.cards.empire.base;

import starwars.deckbuilder.cards.base.HasOnRevealTest;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.CardLocation;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class KesselTest extends EmpireAvailableBaseTest implements HasOnRevealTest {

    @Override
    public int getId() {
        return 124;
    }

    @Override
    public void assertAfterChooseBase() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ExileCard));

        PlayableCard card1 = getPlayer().getHand().get(0);
        PlayableCard card2 = getPlayer().getHand().get(1);
        PlayableCard card3 = getPlayer().getDiscard().get(0);

        game.applyAction(card1.getId());
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ExileCard));
        assertThat(card1.getLocation(), equalTo(CardLocation.Exile));
        assertThat(card2.getLocation(), equalTo(CardLocation.getHand(getPlayer().getFaction())));
        assertThat(card3.getLocation(), equalTo(CardLocation.getDiscard(getPlayer().getFaction())));

        game.applyAction(card2.getId());
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ExileCard));
        assertThat(card1.getLocation(), equalTo(CardLocation.Exile));
        assertThat(card2.getLocation(), equalTo(CardLocation.Exile));
        assertThat(card3.getLocation(), equalTo(CardLocation.getDiscard(getPlayer().getFaction())));

        game.applyAction(card3.getId());
        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(card1.getLocation(), equalTo(CardLocation.Exile));
        assertThat(card2.getLocation(), equalTo(CardLocation.Exile));
        assertThat(card3.getLocation(), equalTo(CardLocation.Exile));
    }
}