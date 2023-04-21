package sw.db.cards.rebellion.unit.starter;

import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.Trait;
import sw.db.cards.common.models.Unit;
import sw.db.game.CardLocation;
import sw.db.game.Game;

import java.util.List;

public class AllianceShuttle extends Unit {

    public AllianceShuttle(int id, Game game) {
        super(id, 0, 0, 1, 0, Faction.rebellion, "Alliance Shuttle", List.of(Trait.transport),
                false, game.getRebel(), CardLocation.RebelDeck, game.getRebel().getDeck(), game);
    }
}
