package sw.db.cards.rebellion.ship;

import sw.db.cards.common.models.CapitalShip;
import sw.db.cards.common.models.Faction;
import sw.db.game.CardLocation;
import sw.db.game.Game;

import java.util.List;

public class RebelGalaxyShip extends CapitalShip {
    protected RebelGalaxyShip(int id, int cost, int attack, int resources, int force, String title, Game game, int hitPoints) {
        super(id, cost, attack, resources, force, Faction.rebellion, title, List.of(), false, null,
                CardLocation.GalaxyDeck, game.getGalaxyDeck(), game, hitPoints);
    }
}
