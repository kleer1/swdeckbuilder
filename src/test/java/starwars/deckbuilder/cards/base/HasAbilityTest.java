package starwars.deckbuilder.cards.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.common.models.Base;
import starwars.deckbuilder.game.ActionSpace;
import starwars.deckbuilder.game.Game;

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
