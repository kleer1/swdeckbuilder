package sw.db.cards.neutral.ship;

import sw.db.cards.common.models.CapitalShip;
import sw.db.cards.common.models.Faction;
import sw.db.game.CardLocation;
import sw.db.game.Game;

import java.util.List;

public class NeutralGalaxyShip extends CapitalShip {
    protected NeutralGalaxyShip(int id, int cost, int attack, int resources, String title, Game game, int hitPoints) {
        super(id, cost, attack, resources, 0, Faction.neutral, title, List.of(), false, null,
                CardLocation.GalaxyDeck, game.getGalaxyDeck(), game, hitPoints);
    }
}
