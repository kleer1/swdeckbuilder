package sw.db.cards.neutral.unit;

import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.Trait;
import sw.db.cards.common.models.Unit;
import sw.db.game.CardLocation;
import sw.db.game.Game;

import java.util.List;

public class NeutralGalaxyUnit extends Unit {
    protected NeutralGalaxyUnit(int id, int cost, int attack, int resources, int force,
                                String title, List<Trait> traits, boolean isUnique, Game game) {
        super(id, cost, attack, resources, force, Faction.neutral, title, traits, isUnique, null,
                CardLocation.GalaxyDeck, game.getGalaxyDeck(), game);
    }
}
