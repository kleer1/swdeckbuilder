package sw.db.network;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;
import sw.db.cards.common.models.Faction;
import sw.db.game.ActionSpace;
import sw.db.game.Game;

@Slf4j
@Getter
public class TwoPlayerEnvironment implements MDP<GameState, Integer, DiscreteSpace> {

    private final DiscreteSpace actionSpace;
    private final Game game;
    private final Faction faction;

    public TwoPlayerEnvironment(Game game, Faction faction) {
        this.game = game;
        this.faction = faction;
        this.actionSpace = new MaskedDiscreteSpace(ActionSpace.SIZE, game);
    }

    @Override
    public ObservationSpace<GameState> getObservationSpace() {
        return new GameObservationSpace();
    }

    @Override
    public DiscreteSpace getActionSpace() {
        // Return the action space for the game
        return actionSpace;
    }

    @Override
    public GameState reset() {
        while (!game.isGameOver()) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        game.initialize(faction);
        while (!game.isGameReady()) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (faction == Faction.rebellion) {
            while (!game.isFactionsAction(faction)) {

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return game.getGameState();
    }

    @Override
    public void close() {

    }

    @Override
    public StepReply<GameState> step(final Integer integer) {
        log.info("Player " + faction + " chose action " + integer);
        game.applyAction(integer);

        double reward = game.getReward();

        // check if our turn is done.
        while (!game.isFactionsAction(faction)) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (isDone()) {
            int i = 1 + 1;
        }
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
        while (!game.isGameOver()) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        game.initialize(faction);
        while (!game.isGameReady()) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (faction == Faction.rebellion) {
            while (!game.isFactionsAction(faction)) {

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return new TwoPlayerEnvironment(game, faction);
    }
}
