package starwars.deckbuilder.cards.rebellion.unit;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class ChewbaccaTest extends RebelTargetableCardTest implements HasAbilityCardTest {

    @Override
    public void setupAbility() {
        moveToInPlay(HanSolo.class, getPlayer());
    }

    @Override
    public void verifyAbility() {
        assertThat(getPlayer().getHand(), hasSize(6));
    }

    @Override
    public void assertReward() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),0, 0);
        assertForceIncreasedBy(Faction.empire, 3);
    }

    @Override
    public int getId() {
        return 68;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),5, 0);
        assertNoForceChange();
    }

    @Test
    void testNoUniqueInPlay() {
        moveToInPlay(BWing.class, getPlayer());
        useCardAbility(game, getCard());
        assertThat(getPlayer().getHand(), hasSize(5));
    }
}