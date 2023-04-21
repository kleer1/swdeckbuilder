package sw.db.cards.empire.base;

import sw.db.cards.common.models.Base;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.HasAtStartOfTurn;
import sw.db.cards.common.models.HasOnReveal;
import sw.db.game.CardLocation;
import sw.db.game.Game;
import sw.db.game.StaticEffect;

public class Endor extends Base implements HasAtStartOfTurn, HasOnReveal {

    public Endor(int id, Game game) {
        super(id, Faction.empire, "Endor", CardLocation.EmpireAvailableBases, game.getEmpire().getAvailableBases(),
                game, game.getEmpire(), 16);
    }

    @Override
    public void applyAtStartOfTurn() {
        game.getStaticEffects().add(StaticEffect.EndorBonus);
    }

    @Override
    public void applyOnReveal() {
        game.getStaticEffects().add(StaticEffect.EndorBonus);
    }
}
