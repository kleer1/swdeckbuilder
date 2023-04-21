package starwars.deckbuilder.cards.neutral.unit;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.game.CardLocation;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class OuterRimPilotTest extends NeutralPlayableCardTest implements HasAbilityCardTest {

    @Override
    public int getId() {
        return 80;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 2);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }

    @Override
    public void verifyAbility() {
        assertThat(card.getLocation(), equalTo(CardLocation.Exile));
        assertForceIncreasedBy(Faction.empire, 1);
    }

    @Test
    void testCanBePurchased() {
        getPlayer().addResources(3);
        PlayableCard orp = game.getOuterRimPilots().get(0);

        game.applyAction(orp.getId());

        assertThat(orp.getLocation(), equalTo(CardLocation.EmpireDiscard));
        assertThat(getPlayer().getResources(), equalTo(1));
    }
}