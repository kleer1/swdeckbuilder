package sw.db.network;

import lombok.extern.slf4j.Slf4j;
import org.deeplearning4j.rl4j.learning.configuration.QLearningConfiguration;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.deeplearning4j.rl4j.network.dqn.DQNFactoryStdDense;
import sw.db.game.Game;

import java.io.IOException;

@Slf4j
public class SinglePlayerTraining {

    private static final String PATH_PREFIX = "/temp/network/";

    private final SinglePlayerEnvironment environment;
    private final QLearningDiscreteDense<GameState> agent;

    public SinglePlayerTraining() {
        Game game = new Game();
        environment = new SinglePlayerEnvironment(game);
        DQNFactoryStdDense netConf = NetworkUtils.buildDQNFactory();
        QLearningConfiguration qlConf = NetworkUtils.buildConfig();
        agent = NetworkUtils.buildPlayerAgent(environment, netConf, qlConf);
    }

    public void train() {
        final String randomNetworkName = PATH_PREFIX + "singlePlayer-" + System.currentTimeMillis() + ".zip";
        log.info("Starting agent training");
        agent.train();
        environment.close();
        try {
            agent.getNeuralNet().save(randomNetworkName);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
