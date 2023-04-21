package sw.db.cards.playablecard;

import org.junit.jupiter.api.Test;

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
