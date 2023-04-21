package sw.db.cards.rebellion.unit;

import sw.db.cards.common.models.Faction;

class ChirrutImweTest extends RebelTargetableCardTest {

    @Override
    public void assertReward() {
        assertForceIncreasedBy(Faction.empire,2);
    }

    @Override
    public int getId() {
        return 66;
    }


    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),2, 0);
        assertForceIncreasedBy(Faction.rebellion, 2);

        getPlayer().getOpponent().addForce(2);
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),0, 0);
    }
}