package starwars.deckbuilder.cards.empire.ship;

import starwars.deckbuilder.cards.common.models.CapitalShip;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class EmpireGalaxyShip extends CapitalShip {
    protected EmpireGalaxyShip(int id, int cost, int attack, int resources, int force, String title, Game game, int hitPoints) {
        super(id, cost, attack, resources, force, Faction.empire, title, List.of(), false, null,
                CardLocation.GalaxyDeck, game.getGalaxyDeck(), game, hitPoints);
    }
}
