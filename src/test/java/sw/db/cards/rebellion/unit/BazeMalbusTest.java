package sw.db.cards.rebellion.unit;

import sw.db.cards.common.models.Faction;

class BazeMalbusTest  extends RebelTargetableCardTest {

    @Override
    public void assertReward() {
        assertForceIncreasedBy(Faction.empire, 1);
    }

    @Override
    public int getId() {
        return 65;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),2, 0);
        assertNoForceChange();

        game.getEmpire().getCurrentBase().addDamage(game.getEmpire().getCurrentBase().getHitPoints());
        game.getEmpire().getAvailableBases().get(0).makeCurrentBase();

        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),3, 0);
        assertNoForceChange();

        game.getEmpire().getCurrentBase().addDamage(game.getEmpire().getCurrentBase().getHitPoints());
        game.getEmpire().getAvailableBases().get(0).makeCurrentBase();

        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),4, 0);
        assertNoForceChange();

        game.getEmpire().getCurrentBase().addDamage(game.getEmpire().getCurrentBase().getHitPoints());
        game.getEmpire().getAvailableBases().get(0).makeCurrentBase();

        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),5, 0);
        assertNoForceChange();
    }
}