package sw.db.cards.neutral.unit;

import org.junit.jupiter.api.Test;
import sw.db.cards.empire.ship.StarDestroyer;
import sw.db.cards.playablecard.HasAbilityCardTest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class Z95HeadhunterTest extends NeutralPlayableCardTest implements HasAbilityCardTest {

    @Override
    public void setupAbility() {
        moveToInPlay(StarDestroyer.class, getPlayer().getOpponent());
    }

    @Override
    public void verifyAbility() {
        assertThat(getPlayer().getHand(), hasSize(6));
    }

    @Test
    void testNoCapitalShip() {
        useCardAbility(game, card);
        assertThat(getPlayer().getHand(), hasSize(5));
    }

    @Override
    public int getId() {
        return 90;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),2, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}