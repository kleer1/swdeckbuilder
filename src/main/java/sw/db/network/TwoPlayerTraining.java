package sw.db.network;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.FastDateFormat;
import org.deeplearning4j.rl4j.learning.configuration.QLearningConfiguration;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.deeplearning4j.rl4j.network.dqn.DQNFactoryStdDense;
import sw.db.cards.common.models.Faction;
import sw.db.game.Game;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

@Slf4j
public class TwoPlayerTraining {

    private static final FastDateFormat FAST_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    private final String imperialName;
    private final String rebelName;
    private final Game game;
    private final TwoPlayerEnvironment imperialEnvironment;
    private final TwoPlayerEnvironment rebelEnvironment;
    private final QLearningDiscreteDense<GameState> imperialAgent;
    private final QLearningDiscreteDense<GameState> rebelAgent;

    public TwoPlayerTraining() {
        game = new Game();
        imperialEnvironment = new TwoPlayerEnvironment(game, Faction.empire);
        rebelEnvironment = new TwoPlayerEnvironment(game, Faction.rebellion);

        DQNFactoryStdDense netConf = NetworkUtils.buildDQNFactory();
        QLearningConfiguration qlConf = NetworkUtils.buildConfig();
        imperialAgent = NetworkUtils.buildPlayerAgent(imperialEnvironment, netConf, qlConf);
        rebelAgent = NetworkUtils.buildPlayerAgent(rebelEnvironment, netConf, qlConf);

        imperialName = "imperial-" + FAST_DATE_FORMAT.format(new Date()) + ".zip";
        rebelName = "rebel-" + FAST_DATE_FORMAT.format(new Date()) + ".zip";

    }

    public void train() throws InterruptedException {
        Runnable imperialThread = new Thread(() -> {
            log.info("Starting " + imperialName + " thread. It is our turn: " +
                    game.isFactionsAction(imperialEnvironment.getFaction()));
            imperialAgent.train();
            imperialEnvironment.close();
            try {
                imperialAgent.getNeuralNet().save(imperialName);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        });
        Runnable rebelThread = new Thread(() -> {
            log.info("Starting " + rebelName + " thread. It is our turn: " + game.isFactionsAction(rebelEnvironment.getFaction()));
            rebelAgent.train();
            rebelEnvironment.close();
            try {
                rebelAgent.getNeuralNet().save(rebelName);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        });
        CompletionService<Boolean> completionService =
                new ExecutorCompletionService<>( Executors.newFixedThreadPool(2));

        completionService.submit(imperialThread, true);
        completionService.submit(rebelThread, true);

        int finished = 0;
        while (finished < 2) {
            completionService.take();
            finished++;
        }
    }


}
