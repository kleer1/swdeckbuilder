package sw.db.cards.empire.unit;

import sw.db.cards.common.models.Faction;

class DeathTrooperTest extends EmpireTargetableCardTest {

    @Override
    public void assertReward() {
        assertForceIncreasedBy(Faction.rebellion, 2);
    }

    @Override
    public int getId() {
        return 14;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),3, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();

        game.getEmpire().addForce(1);
        assertGameState(game.getEmpire(),5, 0);
    }
}