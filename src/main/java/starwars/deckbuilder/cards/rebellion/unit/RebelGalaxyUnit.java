package starwars.deckbuilder.cards.rebellion.unit;

import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.IsTargetable;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.cards.common.models.Unit;

import java.util.List;

public abstract class RebelGalaxyUnit extends Unit implements IsTargetable {
    protected RebelGalaxyUnit(int id, int cost, int attack, int resources, int force, String title, List<Trait> traits, boolean isUnique, Game game) {
        super(id, cost, attack, resources, force, Faction.rebellion, title, traits, isUnique, null,
                CardLocation.GalaxyDeck, game.getGalaxyDeck(), game);
    }
}
