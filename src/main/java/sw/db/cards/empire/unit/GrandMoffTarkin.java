package sw.db.cards.empire.unit;

import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.HasAbility;
import sw.db.cards.common.models.Trait;
import sw.db.game.Action;
import sw.db.game.Game;
import sw.db.game.PendingAction;
import sw.db.game.StaticEffect;

import java.util.List;

public class GrandMoffTarkin extends EmpireGalaxyUnit implements HasAbility {
    public GrandMoffTarkin(int id, Game game) {
        super(id, 6, 2, 2, 2, "Grand Moff Tarkin", List.of(Trait.officer), true, game);
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && game.getGalaxyRow().stream().anyMatch(pc -> pc.getFaction() == Faction.empire);
    }

    @Override
    public void applyAbility() {
        super.applyAbility();
        game.getPendingActions().add(PendingAction.of(Action.PurchaseCard));
        game.getStaticEffects().add(StaticEffect.NextFactionPurchaseIsFree);
        game.getStaticEffects().add(StaticEffect.ExileNextFactionPurchase);
        game.getStaticEffects().add(StaticEffect.BuyNextToHand);
    }

    @Override
    public int getTargetValue() {
        return 6;
    }

    @Override
    public void applyReward() {
        game.getRebel().addResources(3);
        game.getRebel().addForce(3);
    }
}
