package starwars.deckbuilder.network;

import org.deeplearning4j.rl4j.learning.configuration.QLearningConfiguration;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.network.configuration.DQNDenseNetworkConfiguration;
import org.deeplearning4j.rl4j.network.dqn.DQNFactoryStdDense;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.nd4j.linalg.learning.config.Adam;

public class NetworkUtils {
    public static QLearningDiscreteDense<GameState> buildPlayerAgent(MDP<GameState, Integer, DiscreteSpace> environment,
                                                                     DQNFactoryStdDense netConf,
                                                                     QLearningConfiguration qlConf) {
        return  new QLearningDiscreteDense<>(
                environment,
                netConf,
                qlConf
        );
    }

    public static QLearningConfiguration buildConfig() {
        return QLearningConfiguration.builder()
                .seed(System.currentTimeMillis())
                .maxEpochStep(600)
                .maxStep(500000)
                .expRepMaxSize(500000)
                .batchSize(128)
                .targetDqnUpdateFreq(500)
                .updateStart(600)
                .rewardFactor(0.95)
                .gamma(0.99)
                .errorClamp(1.0)
                .minEpsilon(0.1f)
                .epsilonNbStep(1000)
                .doubleDQN(true)
                .build();
    }

    public static DQNFactoryStdDense buildDQNFactory() {
        final DQNDenseNetworkConfiguration build = DQNDenseNetworkConfiguration.builder()
                .l2(0.001)
                .updater(new Adam(0.001))
                .numHiddenNodes(750)
                .numLayers(2)
                .build();

        return new DQNFactoryStdDense(build);
    }
}
