package sw.db.cards.neutral.unit;

import sw.db.cards.common.models.Faction;
import sw.db.cards.playablecard.IsBountyHunterCardTest;

class BosskTest extends NeutralPlayableCardTest implements IsBountyHunterCardTest {

    @Override
    public void verifyBountyHunterReward() {
        assertForceIncreasedBy(Faction.empire, 1);
    }

    @Override
    public int getId() {
        return 107;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),3, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}