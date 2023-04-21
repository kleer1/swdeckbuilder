package starwars.deckbuilder.cards.empire.unit;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.cards.empire.ship.StarDestroyer;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class TieFighterTest extends EmpireTargetableCardTest implements HasAbilityCardTest {

    @Override
    public void setupAbility() {
        moveToInPlay(StarDestroyer.class, getPlayer());
    }

    @Override
    public void verifyAbility() {
        assertThat(getPlayer().getHand(), hasSize(6));
    }

    @Override
    public void assertReward() {
        assertThat(game.getRebel().getResources(), equalTo(1));
    }

    @Override
    public int getId() {
        return 11;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),2, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }

    @Test
    void testAbilityWithNoShip() {
        useCardAbility(game, card);
        assertThat(getPlayer().getHand(), hasSize(5));
    }
}