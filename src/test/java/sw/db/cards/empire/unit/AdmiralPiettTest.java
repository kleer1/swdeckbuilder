package sw.db.cards.empire.unit;

import sw.db.cards.common.models.Faction;
import sw.db.cards.empire.ship.StarDestroyer;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;


class AdmiralPiettTest extends EmpireTargetableCardTest {

    private static final int ID = 26;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),10, 2);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();

        card.moveToDiscard();
        assertThat(game.getEmpire().getAvailableAttack(), equalTo(8));
    }

    @Override
    protected void prePlaySetup() {
        // get two star destroyers in play
        moveToInPlay(StarDestroyer.class, game.getEmpire(), 2);

        assertThat(game.getEmpire().getAvailableAttack(), equalTo(8));
    }

    @Override
    public void assertReward() {
        assertForceIncreasedBy(Faction.rebellion, 1);
    }
}