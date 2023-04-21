package sw.db.cards.empire.ship;

import sw.db.cards.common.models.HasAtStartOfTurn;
import sw.db.game.Game;
import sw.db.game.StaticEffect;

public class ImperialCarrier extends EmpireGalaxyShip implements HasAtStartOfTurn {
    public ImperialCarrier(int id, Game game) {
        super(id, 5, 0, 3, 0, "Imperial Carrier", game, 5);
    }

    @Override
    public void applyAtStartOfTurn() {
        game.getStaticEffects().add(StaticEffect.CarrierEffect);
    }

    @Override
    public void moveToInPlay() {
        super.moveToInPlay();
        game.getStaticEffects().add(StaticEffect.CarrierEffect);
    }

    @Override
    public void moveToDiscard() {
        super.moveToDiscard();
        game.getStaticEffects().remove(StaticEffect.CarrierEffect);
    }
}
