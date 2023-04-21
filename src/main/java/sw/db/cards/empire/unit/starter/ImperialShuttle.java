package sw.db.cards.empire.unit.starter;

import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.Trait;
import sw.db.cards.common.models.Unit;
import sw.db.game.CardLocation;
import sw.db.game.Game;

import java.util.List;

public class ImperialShuttle extends Unit {

    public ImperialShuttle(int id, Game game) {
        super(id, 0, 0, 1, 0, Faction.empire, "Imperial Shuttle", List.of(Trait.transport),
                false, game.getEmpire(), CardLocation.EmpireDeck, game.getEmpire().getDeck(), game);
    }
}
