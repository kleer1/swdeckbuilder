package starwars.deckbuilder;

import starwars.deckbuilder.network.SinglePlayerTraining;

public class TrainingProgram {

    public static void main(String[] args) {
        SinglePlayerTraining gym1 = new SinglePlayerTraining();
        gym1.train();
    }
}
