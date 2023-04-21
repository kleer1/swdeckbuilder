package starwars.deckbuilder.cards.neutral.unit;

import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.game.StaticEffect;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class TwilekSmugglerTest extends NeutralPlayableCardTest implements HasAbilityCardTest {

    @Override
    public void verifyAbility() {
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects(), hasItem(StaticEffect.BuyNextToTopOfDeck));
        assertThat(game.getPendingActions(), hasSize(0));
    }

    @Override
    public int getId() {
        return 98;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 3);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}