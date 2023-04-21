package sw.db.service;

import lombok.Data;
import sw.db.network.SinglePlayerTraining;
import sw.db.network.TwoPlayerTraining;

import java.util.concurrent.ExecutorService;

@Data
public class TrainingService {

    private ExecutorService executorService;

    public void submitSinglePlayerTraining() {
        executorService.submit(() -> {
            SinglePlayerTraining training = new SinglePlayerTraining();
            training.train();
        });
    }

    public void submitTwoPlayerTraining() {
        executorService.submit(() -> {
            TwoPlayerTraining training = new TwoPlayerTraining();
            try {
                training.train();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
