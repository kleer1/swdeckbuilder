package sw.db.cards.base;

import org.junit.jupiter.api.Test;
import sw.db.cards.common.models.Base;
import sw.db.game.ActionSpace;
import sw.db.game.Game;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public interface HasAbilityTest extends AvailableBaseCard {

    @Test
    default void testUseCardAbility() {
        Game game = getGame();
        Base card = getBase();
        setupAbility();
        useCardAbility(game, card);
        verifyAbility();
    }

    default void useCardAbility(Game game, Base card) {
        card.makeCurrentBase();
        getGame().applyAction(ActionSpace.PassTurn.getMinRange());
        game.applyAction(card.getId());
        assertThat(getBase().abilityActive(), equalTo(false));
    }

    default void setupAbility() {

    }

    void verifyAbility();
}
