package sw.db.cards.rebellion.unit;

import sw.db.cards.common.models.Trait;
import sw.db.game.Game;

import java.util.List;

public class BazeMalbus extends RebelGalaxyUnit {
    public BazeMalbus(int id, Game game) {
        super(id, 2, 2, 0, 0, "Baze Malbus", List.of(Trait.trooper), true, game);
    }

    @Override
    public int getTargetValue() {
        return 2;
    }

    @Override
    public void applyReward() {
        game.getEmpire().addForce(1);
    }

    @Override
    public int getAttack() {
        return super.getAttack() + game.getRebel().getDestroyedBases().size();

    }
}
