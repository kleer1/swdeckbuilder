package starwars.deckbuilder.cards.empire.unit;

import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.cards.common.models.Trait;

import java.util.List;

public class DeathTrooper extends EmpireGalaxyUnit {

    public DeathTrooper(int id, Game game) {
        super(id, 3, 3, 0, 0, "Death Trooper", List.of(Trait.trooper), false, game);
    }

    @Override
    public int getAttack() {
        if (owner != null && owner.isForceWithPlayer()) {
            return attack + 2;
        }
        return attack;
    }

    @Override
    public int getTargetValue() {
        return 3;
    }

    @Override
    public void applyReward() {
        game.getForceBalance().lightSideGainForce(2);
    }
}
