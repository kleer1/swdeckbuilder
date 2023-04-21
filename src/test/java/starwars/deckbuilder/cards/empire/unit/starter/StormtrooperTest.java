package starwars.deckbuilder.cards.empire.unit.starter;

import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.empire.EmpirePlayableCardTest;
import starwars.deckbuilder.game.CardLocation;

import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class StormtrooperTest extends EmpirePlayableCardTest {

    private static final int ID = 7;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),2, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}