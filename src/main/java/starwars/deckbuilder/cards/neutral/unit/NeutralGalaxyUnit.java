package starwars.deckbuilder.cards.neutral.unit;

import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.cards.common.models.Unit;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class NeutralGalaxyUnit extends Unit {
    protected NeutralGalaxyUnit(int id, int cost, int attack, int resources, int force,
                                String title, List<Trait> traits, boolean isUnique, Game game) {
        super(id, cost, attack, resources, force, Faction.neutral, title, traits, isUnique, null,
                CardLocation.GalaxyDeck, game.getGalaxyDeck(), game);
    }
}
