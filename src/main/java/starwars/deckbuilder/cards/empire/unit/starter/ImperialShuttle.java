package starwars.deckbuilder.cards.empire.unit.starter;

import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.cards.common.models.Unit;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class ImperialShuttle extends Unit {

    public ImperialShuttle(int id, Game game) {
        super(id, 0, 0, 1, 0, Faction.empire, "Imperial Shuttle", List.of(Trait.transport),
                false, game.getEmpire(), CardLocation.EmpireDeck, game.getEmpire().getDeck(), game);
    }
}
