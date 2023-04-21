package sw.db.cards.rebellion.base;

import sw.db.cards.common.models.Base;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.HasOnReveal;
import sw.db.game.*;

public class MonCala extends Base implements HasOnReveal {

    public MonCala(int id, Game game) {
        super(id, Faction.rebellion, "Mon Cala", CardLocation.RebelAvailableBases, game.getRebel().getAvailableBases(),
                game, game.getRebel(), 10);
    }

    @Override
    public void applyOnReveal() {
        game.getPendingActions().add(PendingAction.of(Action.PurchaseCard));
        game.getStaticEffects().add(StaticEffect.NextFactionOrNeutralPurchaseIsFree);
        game.getStaticEffects().add(StaticEffect.BuyNextToHand);
    }
}
