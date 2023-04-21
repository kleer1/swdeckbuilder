package sw.db.cards.empire.base;

import sw.db.cards.common.models.Base;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.HasAtStartOfTurn;
import sw.db.cards.common.models.HasOnReveal;
import sw.db.game.CardLocation;
import sw.db.game.Game;
import sw.db.game.StaticEffect;

public class OrdMantell extends Base implements HasOnReveal, HasAtStartOfTurn {

    public OrdMantell(int id, Game game) {
        super(id, Faction.empire, "Ord Mantell", CardLocation.EmpireAvailableBases, game.getEmpire().getAvailableBases(),
                game, game.getEmpire(), 14);
    }

    @Override
    public void applyAtStartOfTurn() {
        game.getStaticEffects().add(StaticEffect.CanBountyOneNeutral);
    }

    @Override
    public void applyOnReveal() {
        game.getStaticEffects().add(StaticEffect.CanBountyOneNeutral);
    }
}
