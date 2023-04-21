package starwars.deckbuilder.cards.base;

import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.BaseTest;
import starwars.deckbuilder.cards.common.models.Base;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.game.ActionSpace;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.Player;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public abstract class AvailableBaseCardTest extends BaseTest implements AvailableBaseCard {
    @Getter
    protected Base base;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.initialize();
        Player player = getPlayer();
        Faction faction = player.getFaction();
        base = (Base) game.getCardMap().get(getId());
        game.getEmpire().addForce(3);
        // start as the opposite faction so passing turn prompts a base choice
        if (faction == Faction.empire) {
            game.applyAction(ActionSpace.PassTurn.getMinRange());
        }
        player.setCurrentBase(null);
    }

    @Test
    void testSetup() {
        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(game.getStaticEffects(), hasSize(0));
        assertThat(getPlayer().getResources(), equalTo(0));
        assertThat(getPlayer().getOpponent().getResources(), equalTo(0));
        assertThat(getPlayer().getCurrentBase(), is(nullValue()));
        assertThat(game.getForceBalance().getPosition(), equalTo(3));
    }
}
