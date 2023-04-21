package sw.db.cards.neutral.unit;

import org.junit.jupiter.api.Test;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.playablecard.HasAbilityCardTest;
import sw.db.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
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