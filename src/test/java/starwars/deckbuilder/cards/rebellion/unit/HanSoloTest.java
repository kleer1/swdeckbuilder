package starwars.deckbuilder.cards.rebellion.unit;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class HanSoloTest extends RebelTargetableCardTest implements HasAbilityCardTest {

    @Override
    public void setupAbility() {
        moveToInPlay(MillenniumFalcon.class, getPlayer());
    }

    @Override
    public void verifyAbility() {
        assertThat(getPlayer().getHand(), hasSize(7));
    }

    @Override
    public void assertReward() {
       assertGameState(game.getEmpire(), 0, 3);
       assertForceIncreasedBy(Faction.empire, 2);
    }

    @Override
    public int getId() {
        return 69;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),3, 2);
        assertNoForceChange();
    }

    @Test
    void testNoMillFalcon() {
        useCardAbility(game, getCard());
        assertThat(getPlayer().getHand(), hasSize(6));
    }
}