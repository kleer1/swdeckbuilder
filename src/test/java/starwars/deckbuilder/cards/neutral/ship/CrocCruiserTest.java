package starwars.deckbuilder.cards.neutral.ship;

import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.cards.neutral.unit.NeutralPlayableCardTest;
import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class CrocCruiserTest extends NeutralPlayableCardTest implements HasAbilityCardTest {

    @Override
    public void setupAbility() {
        getPlayer().getCurrentBase().addDamage(4);
    }

    @Override
    public void verifyAbility() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.DiscardFromHand));
        assertThat(getPlayer().getCurrentBase().getCurrentDamage(), equalTo(4));

        PlayableCard card1 = getPlayer().getHand().get(0);
        game.applyAction(card1.getId());

        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(getPlayer().getCurrentBase().getCurrentDamage(), equalTo(1));
        assertThat(card1.getLocation(), equalTo(CardLocation.EmpireDiscard));
    }

    @Override
    public int getId() {
        return 113;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 1);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}