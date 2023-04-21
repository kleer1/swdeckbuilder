package starwars.deckbuilder.cards.empire.unit;

import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.CardLocation;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class TieBomberTest extends EmpireTargetableCardTest implements HasAbilityCardTest {

    private int rowCard = -1;

    @Override
    public void setupAbility() {
        rowCard = game.getGalaxyRow().get(0).getId();
    }

    @Override
    public void verifyAbility() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.DiscardCardFromCenter));
        assertThat(game.getGalaxyRow(), hasSize(6));
        game.applyAction(rowCard);
        PlayableCard card1 = (PlayableCard) game.getCardMap().get(rowCard);
        assertThat(card1.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(game.getGalaxyRow(), hasSize(6));
    }

    @Override
    public void assertReward() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ExileCard));

        PlayableCard card1 = (PlayableCard) game.getCardMap().get(40);
        card1.moveToDiscard();

        game.applyAction(40);
        assertThat(card1.getLocation(), equalTo(CardLocation.Exile));
        assertThat(card1.getOwner(), is(nullValue()));
        assertThat(card1.getCardList(), equalTo(game.getExiledCards()));
        assertThat(game.getPendingActions(), hasSize(0));
    }

    @Override
    public int getId() {
        return 16;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),2, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}