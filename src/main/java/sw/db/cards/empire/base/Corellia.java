package sw.db.cards.empire.base;

import sw.db.cards.common.models.Base;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.HasOnReveal;
import sw.db.game.*;

public class Corellia extends Base implements HasOnReveal {
    public Corellia(int id, Game game) {
        super(id, Faction.empire, "Corellia", CardLocation.EmpireAvailableBases, game.getEmpire().getAvailableBases(),
                game, game.getEmpire(),10);
    }

    @Override
    public void applyOnReveal() {
        game.getPendingActions().add(PendingAction.of(Action.PurchaseCard));
        game.getStaticEffects().add(StaticEffect.NextFactionOrNeutralPurchaseIsFree);
        game.getStaticEffects().add(StaticEffect.BuyNextToHand);
    }
}
