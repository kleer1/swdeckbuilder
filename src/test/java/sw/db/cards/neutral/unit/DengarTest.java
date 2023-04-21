package sw.db.cards.neutral.unit;

import sw.db.cards.playablecard.IsBountyHunterCardTest;

class DengarTest extends NeutralPlayableCardTest implements IsBountyHunterCardTest {

    @Override
    public void verifyBountyHunterReward() {
        // 2 for dengar, 1 for tie fighter
        assertGameState(getPlayer(), 0, 3);
    }

    @Override
    public int getId() {
        return 108;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),4, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}