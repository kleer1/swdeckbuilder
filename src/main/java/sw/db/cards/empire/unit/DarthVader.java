package sw.db.cards.empire.unit;

import sw.db.cards.common.models.Trait;
import sw.db.game.Game;

import java.util.List;

public class DarthVader extends EmpireGalaxyUnit {
    public DarthVader(int id, Game game) {
        super(id, 8, 6, 0, 2, "Darth Vader", List.of(Trait.sith), true, game);
    }

    @Override
    public int getTargetValue() {
        return 8;
    }

    @Override
    public void applyReward() {
        game.getRebel().addResources(4);
        game.getRebel().addForce(4);
    }

    @Override
    public int getAttack() {
        int atk = super.getAttack();
        if (owner != null && owner.isForceWithPlayer()) {
            atk += 4;
        }
        return atk;
    }
}
