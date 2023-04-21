package sw.db.cards.rebellion.unit.starter;

import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.Trait;
import sw.db.cards.common.models.Unit;
import sw.db.game.CardLocation;
import sw.db.game.Game;

import java.util.List;

public class RebelTrooper extends Unit {

    public RebelTrooper(int id, Game game) {
        super(id, 0, 2, 0, 0, Faction.rebellion, "Rebel Trooper", List.of(Trait.trooper),
                false, game.getRebel(), CardLocation.RebelDeck, game.getRebel().getDeck(), game);
    }
}
