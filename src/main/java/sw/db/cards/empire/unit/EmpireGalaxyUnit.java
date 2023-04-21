package sw.db.cards.empire.unit;

import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.IsTargetable;
import sw.db.cards.common.models.Trait;
import sw.db.cards.common.models.Unit;
import sw.db.game.CardLocation;
import sw.db.game.Game;
import sw.db.game.StaticEffect;

import java.util.List;

public abstract class EmpireGalaxyUnit extends Unit implements IsTargetable {
    protected EmpireGalaxyUnit(int id, int cost, int attack, int resources, int force, String title, List<Trait> traits, boolean isUnique, Game game) {
        super(id, cost, attack, resources, force, Faction.empire, title, traits, isUnique, null, CardLocation.GalaxyDeck, game.getGalaxyDeck(), game);
    }

    @Override
    public int getAttack() {
        int atk = super.getAttack();
        if (traits.contains(Trait.fighter)) {
            atk += game.getStaticEffects().stream().filter(se -> se == StaticEffect.CarrierEffect).count();
        }
        return atk;
    }
}
