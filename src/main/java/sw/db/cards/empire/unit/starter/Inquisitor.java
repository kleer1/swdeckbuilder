package sw.db.cards.empire.unit.starter;

import sw.db.cards.common.TempleInquisitor;
import sw.db.cards.common.models.Faction;
import sw.db.game.CardLocation;
import sw.db.game.Game;

import java.util.List;

public class Inquisitor extends TempleInquisitor {

    public Inquisitor(int id, Game game) {
        super(id,  Faction.empire, "Inquisitor", List.of(),game.getEmpire(), CardLocation.EmpireDeck, game.getEmpire().getDeck(), game);
    }
}
