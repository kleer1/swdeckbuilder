package sw.db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sw.db.service.TrainingService;

@RestController
@RequestMapping("/train")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    @PostMapping("/one-player")
    public ResponseEntity<String> trainOnePlayer() {
        trainingService.submitSinglePlayerTraining();
        return ResponseEntity.ok("One Player training has started.");
    }

    @PostMapping("/two-player")
    public ResponseEntity<String> trainTwoPlayer() {
        trainingService.submitTwoPlayerTraining();
        return ResponseEntity.ok("Two Player training has started.");
    }
}
