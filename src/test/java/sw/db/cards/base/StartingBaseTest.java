package sw.db.cards.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sw.db.cards.BaseTest;
import sw.db.cards.HasId;
import sw.db.cards.HasPlayer;
import sw.db.cards.common.models.Base;
import sw.db.game.CardLocation;
import sw.db.game.Game;

import static org.hamcrest.Matchers.equalTo;
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
