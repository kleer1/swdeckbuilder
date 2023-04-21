package sw.db.cards.playablecard;

import org.junit.jupiter.api.Test;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.PlayableCard;
import sw.db.game.ActionSpace;
import sw.db.game.Game;

public interface IsBountyHunterCardTest extends BasePlayableCard {

    @Test
    default void testBountyHunterReward() {
        Game game = getGame();
        PlayableCard card = getCard();
        card.moveToInPlay();
        PlayableCard target = null;
        if (game.getCurrentPlayersAction() == Faction.empire) {
            // add y-wing id = 50
            target = (PlayableCard) game.getCardMap().get(50);
        } else {
            // add tie fighter id = 10
            target = (PlayableCard) game.getCardMap().get(10);
        }
        target.moveToGalaxyRow();
        game.applyAction(target.getId());
        game.applyAction(card.getId());
        game.applyAction(ActionSpace.ConfirmAttackers.getMinRange());
        verifyBountyHunterReward();
    }

    default void verifyPreBounty() {

    }

    void verifyBountyHunterReward();
}
