package sw.db.cards.rebellion.unit;

import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.IsTargetable;
import sw.db.cards.common.models.Trait;
import sw.db.cards.common.models.Unit;
import sw.db.game.CardLocation;
import sw.db.game.Game;

import java.util.List;

public abstract class RebelGalaxyUnit extends Unit implements IsTargetable {
    protected RebelGalaxyUnit(int id, int cost, int attack, int resources, int force, String title, List<Trait> traits, boolean isUnique, Game game) {
        super(id, cost, attack, resources, force, Faction.rebellion, title, traits, isUnique, null,
                CardLocation.GalaxyDeck, game.getGalaxyDeck(), game);
    }
}
