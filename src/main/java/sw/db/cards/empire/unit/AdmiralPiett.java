package sw.db.cards.empire.unit;

import sw.db.cards.common.models.Trait;
import sw.db.game.Game;
import sw.db.game.StaticEffect;

import java.util.List;

public class AdmiralPiett extends EmpireGalaxyUnit {
    public AdmiralPiett(int id, Game game) {
        super(id, 2, 0, 2, 0, "Admiral Piett", List.of(Trait.officer), true, game);
    }

    @Override
    public void moveToInPlay() {
        super.moveToInPlay();
        game.getStaticEffects().add(StaticEffect.AdmiralPiettBonus);
    }

    @Override
    public void moveToDiscard() {
        super.moveToDiscard();
        game.getStaticEffects().remove(StaticEffect.AdmiralPiettBonus);
    }

    @Override
    public int getTargetValue() {
        return 2;
    }

    @Override
    public void applyReward() {
        game.getRebel().addForce(1);
    }
}
