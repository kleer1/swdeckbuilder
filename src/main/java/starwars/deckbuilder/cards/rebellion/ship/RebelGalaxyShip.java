package starwars.deckbuilder.cards.rebellion.ship;

import starwars.deckbuilder.cards.common.models.CapitalShip;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class RebelGalaxyShip extends CapitalShip {
    protected RebelGalaxyShip(int id, int cost, int attack, int resources, int force, String title, Game game, int hitPoints) {
        super(id, cost, attack, resources, force, Faction.rebellion, title, List.of(), false, null,
                CardLocation.GalaxyDeck, game.getGalaxyDeck(), game, hitPoints);
    }
}
