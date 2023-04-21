package sw.db.network;

import lombok.extern.slf4j.Slf4j;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;
import sw.db.cards.common.models.Faction;
import sw.db.game.ActionSpace;
import sw.db.game.Game;

@Slf4j
public class SinglePlayerEnvironment implements MDP<GameState, Integer, DiscreteSpace> {
    private final DiscreteSpace actionSpace;
    private final Game game;

    public SinglePlayerEnvironment(Game game) {
        this.game = game;
        this.actionSpace = new MaskedDiscreteSpace(ActionSpace.SIZE, game);
    }

    @Override
    public ObservationSpace<GameState> getObservationSpace() {
        return new GameObservationSpace();
    }

    @Override
    public DiscreteSpace getActionSpace() {
        return actionSpace;
    }

    @Override
    public GameState reset() {
        game.initialize();
        return game.getGameState();
    }

    @Override
    public void close() {

    }

    @Override
    public StepReply<GameState> step(Integer integer) {
        Faction faction = game.getCurrentPlayersAction();
        game.applyAction(integer);

        double reward = game.getReward();
        final GameState nextState = game.getGameState();
        return new StepReply<>(
                nextState,
                reward,
                isDone(),
                "Player" + faction
        );
    }

    @Override
    public boolean isDone() {
        return game.isGameComplete();
    }

    @Override
    public MDP<GameState, Integer, DiscreteSpace> newInstance() {
        game.initialize();
        return new SinglePlayerEnvironment(game);
    }
}
