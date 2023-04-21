package sw.db.cards.playablecard;

import org.junit.jupiter.api.Test;
import sw.db.cards.common.models.PlayableCard;
import sw.db.game.Game;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

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
