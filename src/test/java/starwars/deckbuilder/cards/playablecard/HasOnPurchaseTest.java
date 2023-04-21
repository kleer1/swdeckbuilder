package starwars.deckbuilder.cards.playablecard;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.game.Game;

public interface HasOnPurchaseTest extends BasePlayableCard {

    @Test
    default void testOnPurchase() {
        beforePurchase();
        purchase();
        verifyAfterPurchase();
    }

    default void beforePurchase() {

    }

    default void purchase() {
        getCard().moveToGalaxyRow();
        getPlayer().addResources(getCard().getCost());
        getGame().applyAction(getCard().getId());
    }

    void verifyAfterPurchase();
}
