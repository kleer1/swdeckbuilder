package starwars.deckbuilder.cards.playablecard;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.game.Game;

import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public interface HasAbilityCardTest extends BasePlayableCard {

    @Test
    default void testUseCardAbility() {
        Game game = getGame();
        PlayableCard card = getCard();
        setupAbility();
        useCardAbility(game, card);
        verifyAbility();
    }

    default void useCardAbility(Game game, PlayableCard card) {
        card.moveToInPlay();
        game.applyAction(card.getId());
        assertThat(card.abilityActive(), equalTo(false));
    }

    default void setupAbility() {

    }

    void verifyAbility();
}
