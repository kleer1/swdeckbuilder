package starwars.deckbuilder.cards.neutral.unit;

import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class Hwk290Test extends NeutralPlayableCardTest implements HasAbilityCardTest {

    @Override
    public void setupAbility() {
        getPlayer().getCurrentBase().addDamage(5);
    }

    @Override
    public void verifyAbility() {
        assertThat(card.getLocation(), equalTo(CardLocation.Exile));
        assertThat(getPlayer().getCurrentBase().getCurrentDamage(), equalTo(1));
    }

    @Override
    public int getId() {
        return 102;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 4);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}