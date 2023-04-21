package starwars.deckbuilder.cards.rebellion.unit.starter;

import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.Trait;
import starwars.deckbuilder.cards.common.models.Unit;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;

import java.util.List;

public class AllianceShuttle extends Unit {

    public AllianceShuttle(int id, Game game) {
        super(id, 0, 0, 1, 0, Faction.rebellion, "Alliance Shuttle", List.of(Trait.transport),
                false, game.getRebel(), CardLocation.RebelDeck, game.getRebel().getDeck(), game);
    }
}
