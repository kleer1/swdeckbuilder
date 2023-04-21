package sw.db.cards.base;

import org.junit.jupiter.api.Test;
import sw.db.game.ActionSpace;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public interface HasAtStartOfTurnTest extends AvailableBaseCard {

    @Test
    default void testAtStartOfTurn() {
        triggerStartOfTurn();
        assertAfterStartOfTurn();
    }

    default void triggerStartOfTurn() {
        getBase().makeCurrentBase();
        assertThat(getGame().getPendingActions(), hasSize(0));
        assertThat(getGame().getStaticEffects(), hasSize(0));
        getGame().applyAction(ActionSpace.PassTurn.getMinRange());
    }

    void assertAfterStartOfTurn();
}
