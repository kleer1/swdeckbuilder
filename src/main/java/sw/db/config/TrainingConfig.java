package sw.db.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sw.db.service.TrainingService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class TrainingConfig {

    @Bean
    public TrainingService trainingService(ExecutorService executorService) {
        TrainingService service = new TrainingService();
        service.setExecutorService(executorService);
        return service;
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(10);
    }
}
