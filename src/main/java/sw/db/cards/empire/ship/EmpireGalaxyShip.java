package sw.db.cards.empire.ship;

import sw.db.cards.common.models.CapitalShip;
import sw.db.cards.common.models.Faction;
import sw.db.game.CardLocation;
import sw.db.game.Game;

import java.util.List;

public class EmpireGalaxyShip extends CapitalShip {
    protected EmpireGalaxyShip(int id, int cost, int attack, int resources, int force, String title, Game game, int hitPoints) {
        super(id, cost, attack, resources, force, Faction.empire, title, List.of(), false, null,
                CardLocation.GalaxyDeck, game.getGalaxyDeck(), game, hitPoints);
    }
}
