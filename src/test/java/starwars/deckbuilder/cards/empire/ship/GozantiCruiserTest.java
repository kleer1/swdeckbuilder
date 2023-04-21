package starwars.deckbuilder.cards.empire.ship;

import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.cards.common.models.Card;
import starwars.deckbuilder.cards.empire.EmpirePlayableCardTest;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.ActionSpace;
import starwars.deckbuilder.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class GozantiCruiserTest extends EmpirePlayableCardTest implements HasAbilityCardTest {

    int handCard = -1;
    int deckCard = -1;
    @Override
    public void setupAbility() {
        handCard = getPlayer().getHand().get(0).getId();
        deckCard = getPlayer().getDeck().get(0).getId();
    }

    @Override
    public void verifyAbility() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.DiscardFromHand));

        game.applyAction(handCard);
        Card card1 = game.getCardMap().get(handCard);
        assertThat(card1.getLocation(), equalTo(CardLocation.EmpireDiscard));

        Card card2 = game.getCardMap().get(deckCard);
        assertThat(card2.getLocation(), equalTo(CardLocation.EmpireHand));

        assertThat(getPlayer().getHand(), hasSize(5));
    }

    @Override
    public int getId() {
        return 33;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 2);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();

        game.applyAction(ActionSpace.PassTurn.getMinRange());
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();

        game.applyAction(ActionSpace.PassTurn.getMinRange());
        assertGameState(game.getEmpire(),0, 2);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}