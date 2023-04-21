package starwars.deckbuilder.cards.empire.base;

import starwars.deckbuilder.cards.common.models.Base;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.HasOnReveal;
import starwars.deckbuilder.game.*;

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
