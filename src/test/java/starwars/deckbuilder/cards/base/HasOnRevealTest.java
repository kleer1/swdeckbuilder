package starwars.deckbuilder.cards.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.ActionSpace;
import starwars.deckbuilder.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public interface HasOnRevealTest extends AvailableBaseCard {

    default void preChooseBaseSetup() {
        // can be overridden for extra setup options
    }

    @Test
    default void testChooseBase() {
        preChooseBaseSetup();
        chooseBase();
        assertAfterChooseBase();
    }

    void assertAfterChooseBase();

    default void chooseBase() {
        getGame().applyAction(ActionSpace.PassTurn.getMinRange());
        assertThat(getGame().getPendingActions(), hasSize(1));
        assertThat(getGame().getPendingActions().get(0).getAction(), equalTo(Action.ChooseNextBase));
        getGame().applyAction(getId());
        assertThat(getPlayer().getCurrentBase(), equalTo(getBase()));
        assertThat(getBase().getLocation(), equalTo(CardLocation.getCurrentBase(getPlayer().getFaction())));
    }
}
