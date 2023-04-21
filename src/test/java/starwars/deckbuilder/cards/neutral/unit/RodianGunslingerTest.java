package starwars.deckbuilder.cards.neutral.unit;

import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.cards.rebellion.unit.UWing;
import starwars.deckbuilder.game.ActionSpace;
import starwars.deckbuilder.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class RodianGunslingerTest extends NeutralPlayableCardTest {

    @Override
    public int getId() {
        return 94;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),2, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();

        PlayableCard uwing = moveToGalaxyRow(UWing.class).get(0);

        game.applyAction(uwing.getId());
        assertGameState(game.getEmpire(),4, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();

        game.applyAction(card.getId());
        game.applyAction(ActionSpace.ConfirmAttackers.getMinRange());

        assertThat(uwing.getLocation(), equalTo(CardLocation.GalaxyDiscard));

        game.applyAction(ActionSpace.PassTurn.getMinRange());
        game.applyAction(ActionSpace.PassTurn.getMinRange());

        card.moveToInPlay();

        assertGameState(game.getEmpire(),2, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();

        game.applyAction(getPlayer().getOpponent().getCurrentBase().getId());
        assertGameState(game.getEmpire(),2, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}