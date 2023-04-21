package starwars.deckbuilder.cards.empire.unit;

import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.PlayableCard;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class GeneralVeersTest extends EmpireTargetableCardTest implements HasAbilityCardTest {

    private static final int VEHICLE_ID = 22;
    @Override
    public void setupAbility() {
        PlayableCard vehicle = (PlayableCard) game.getCardMap().get(VEHICLE_ID);
        vehicle.buyToHand(getPlayer());
        vehicle.moveToInPlay();
    }

    @Override
    public void verifyAbility() {
        assertThat(getPlayer().getHand(), hasSize(6));
    }

    @Override
    public void assertReward() {
        assertForceIncreasedBy(Faction.rebellion, 3);
    }

    @Override
    public int getId() {
        return 27;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),4, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();

    }
}