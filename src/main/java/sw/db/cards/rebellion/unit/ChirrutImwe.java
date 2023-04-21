package sw.db.cards.rebellion.unit;

import sw.db.cards.common.models.Trait;
import sw.db.game.Game;

import java.util.List;

public class ChirrutImwe extends RebelGalaxyUnit {
    public ChirrutImwe(int id, Game game) {
        super(id, 3, 0, 0, 2, "Chirrut Imwe", List.of(Trait.trooper), true, game);
    }

    @Override
    public int getTargetValue() {
        return 3;
    }

    @Override
    public void applyReward() {
        game.getEmpire().addForce(2);
    }

    @Override
    public int getAttack() {
        return super.getAttack() + (owner != null && owner.isForceWithPlayer() ? 2 : 0);
    }
}
