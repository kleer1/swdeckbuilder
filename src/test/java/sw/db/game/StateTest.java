package sw.db.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class StateTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.initialize();
    }

    @Test
    void testSize() {
        State state = new State(game.getCurrentPlayer());
        assertThat(state.buildInputs().length, equalTo(State.SIZE));
    }
}