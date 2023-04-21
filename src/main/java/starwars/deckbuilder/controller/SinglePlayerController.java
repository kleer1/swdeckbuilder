package starwars.deckbuilder.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/1p")
public class SinglePlayerController {

    @PostMapping("/train")
    public void train() {
    }
}
