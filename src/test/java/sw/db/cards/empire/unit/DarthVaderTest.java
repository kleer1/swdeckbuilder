package sw.db.cards.empire.unit;

import sw.db.cards.common.models.Faction;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class DarthVaderTest extends EmpireTargetableCardTest {

    @Override
    public void preAttackSetup() {
        game.getEmpire().addForce(100);
    }

    @Override
    public void assertReward() {
        assertThat(game.getRebel().getResources(), equalTo(4));
        assertThat(game.getForceBalance().getPosition(), equalTo(4));
    }

    @Override
    public int getId() {
        return 32;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),10, 0);
        assertGameState(game.getRebel(),0, 0);
        assertForceIncreasedBy(Faction.empire, 2);

        game.getRebel().addForce(100);
        assertGameState(game.getEmpire(),6, 0);
    }
}