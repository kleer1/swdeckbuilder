package starwars.deckbuilder.cards.neutral.ship;

import starwars.deckbuilder.cards.common.models.CapitalShip;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class NeutralGalaxyShip extends CapitalShip {
    protected NeutralGalaxyShip(int id, int cost, int attack, int resources, String title, Game game, int hitPoints) {
        super(id, cost, attack, resources, 0, Faction.neutral, title, List.of(), false, null,
                CardLocation.GalaxyDeck, game.getGalaxyDeck(), game, hitPoints);
    }
}
