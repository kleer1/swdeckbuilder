package starwars.deckbuilder.network;

import org.deeplearning4j.rl4j.space.ObservationSpace;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import starwars.deckbuilder.game.State;

import java.util.List;
import java.util.stream.Stream;

public class GameObservationSpace implements ObservationSpace<GameState> {
    private static final double[] LOWS = createValueArray(0.0);
    private static final double[] HIGHS = createValueArray(1.0);

    @Override
    public String getName() {
        return "GameObservationSpace";
    }

    @Override
    public int[] getShape() {
        return new int[]{ 1, State.SIZE};
    }

    @Override
    public INDArray getLow() {
        return Nd4j.create(LOWS);
    }

    @Override
    public INDArray getHigh() {
        return Nd4j.create(HIGHS);
    }

    private static double[] createValueArray(final double value) {
        final double[] values = new double[State.SIZE];
        for (int i = 0; i < State.SIZE; i++) {
            values[i] = value;
        }
        return values;
    }
}
