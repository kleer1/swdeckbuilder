package sw.db.cards.playablecard;

import org.junit.jupiter.api.Test;
import sw.db.cards.common.models.Card;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.empire.ship.StarDestroyer;
import sw.db.cards.rebellion.unit.BWing;
import sw.db.game.ActionSpace;
import sw.db.game.Game;

public interface IsTargetableCardTest extends BasePlayableCard {

    @Test
    default void testAttackedInCenterRow() {
        Game game = getGame();
        PlayableCard card = getCard();
        // move card to galaxy row so it can be targeted
        card.moveToGalaxyRow();

        // Pass turn so opponent can attack
        game.applyAction(ActionSpace.PassTurn.getMinRange());
        Faction faction = game.getCurrentPlayersTurn();

        if (faction == Faction.rebellion) {
            // for rebels, two b-wings will kill anything in center
            moveToInPlay(BWing.class, game.getRebel(), 2);
        } else if (faction == Faction.empire) {
            // for empire, two ssds will do the same
            moveToInPlay(StarDestroyer.class, game.getEmpire(), 2);
        }

        // choose card to attack
        game.applyAction(card.getId());
        // chose the attackers
        for (Card attacker : faction == Faction.rebellion ? game.getRebel().getUnitsInPlay() : game.getEmpire().getShipsInPlay()) {
            game.applyAction(attacker.getId());
        }
        preAttackSetup();
        // chose to confirm attackers
        game.applyAction(ActionSpace.ConfirmAttackers.getMinRange());
        assertReward();
    }

    void assertReward();

    default void preAttackSetup() {

    }
}
