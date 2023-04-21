package sw.db.cards.empire.unit.starter;

import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.Trait;
import sw.db.cards.common.models.Unit;
import sw.db.game.CardLocation;
import sw.db.game.Game;

import java.util.List;

public class Stormtrooper extends Unit {

    public Stormtrooper(int id, Game game) {
        super(id, 0, 2, 0, 0, Faction.empire, "Stormtrooper", List.of(Trait.trooper),
                false, game.getEmpire(), CardLocation.EmpireDeck, game.getEmpire().getDeck(), game);
    }
}
