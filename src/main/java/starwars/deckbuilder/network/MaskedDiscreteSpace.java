package starwars.deckbuilder.network;

import org.deeplearning4j.rl4j.space.DiscreteSpace;
import starwars.deckbuilder.game.Game;

import java.util.Set;

public class MaskedDiscreteSpace extends DiscreteSpace {
    private final Game game;
    public MaskedDiscreteSpace(int size, Game game) {
        super(size);
        this.game = game;
    }

    @Override
    public Integer randomAction() {
        Set<Integer> availableAction = game.getAvailableActions();
        int action;
        do {
            action = super.randomAction();
        } while (!availableAction.contains(action));
        return action;
    }
}
