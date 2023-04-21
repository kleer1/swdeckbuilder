package starwars.deckbuilder.cards.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.BaseTest;
import starwars.deckbuilder.cards.HasId;
import starwars.deckbuilder.cards.HasPlayer;
import starwars.deckbuilder.cards.common.models.Base;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public abstract class StartingBaseTest extends BaseTest implements HasId, HasPlayer {

    protected Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.initialize();
    }

    @Test
    void testStartingBase() {
        Base base = (Base) game.getCardMap().get(getId());
        assertThat(base.getLocation(), equalTo(CardLocation.getCurrentBase(getPlayer().getFaction())));
        assertThat(getPlayer().getCurrentBase(), equalTo(base));
        assertThat(base.getHitPoints(), equalTo(8));
    }
}
