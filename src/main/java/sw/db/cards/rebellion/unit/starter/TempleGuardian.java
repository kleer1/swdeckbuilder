package sw.db.cards.rebellion.unit.starter;

import sw.db.cards.common.TempleInquisitor;
import sw.db.cards.common.models.Faction;
import sw.db.game.CardLocation;
import sw.db.game.Game;

import java.util.List;

public class TempleGuardian extends TempleInquisitor {

    public TempleGuardian(int id, Game game) {
        super(id, Faction.rebellion, "Temple Guardian", List.of(), game.getRebel(),
                CardLocation.RebelDeck, game.getRebel().getDeck(),  game);
    }
}
