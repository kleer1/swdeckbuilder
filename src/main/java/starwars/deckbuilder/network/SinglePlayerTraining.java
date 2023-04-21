package starwars.deckbuilder.network;

import lombok.extern.slf4j.Slf4j;
import org.deeplearning4j.rl4j.learning.configuration.QLearningConfiguration;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.deeplearning4j.rl4j.network.dqn.DQNFactoryStdDense;
import starwars.deckbuilder.game.Game;

import java.io.IOException;

@Slf4j
public class SinglePlayerTraining {

    private final Game game;
    private final SinglePlayerEnvironment environment;
    private final QLearningDiscreteDense<GameState> agent;

    public SinglePlayerTraining() {
        game = new Game();
        environment = new SinglePlayerEnvironment(game);
        DQNFactoryStdDense netConf = NetworkUtils.buildDQNFactory();
        QLearningConfiguration qlConf = NetworkUtils.buildConfig();
        agent = NetworkUtils.buildPlayerAgent(environment, netConf, qlConf);
    }

    public void train() {
        Runnable thread = new Thread(() -> {
            final String randomNetworkName = "singlePlayer-" + System.currentTimeMillis() + ".zip";
            log.info("Startingagent training");
            agent.train();
            environment.close();
            try {
                agent.getNeuralNet().save(randomNetworkName);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        });
        thread.run();
    }
}
