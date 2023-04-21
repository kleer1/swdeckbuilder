package sw.db.cards.empire.unit;

import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.playablecard.HasAbilityCardTest;
import sw.db.game.Action;
import sw.db.game.CardLocation;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class AtStTest extends EmpireTargetableCardTest implements HasAbilityCardTest {
    private static final int ID = 22;
    private static final int REBEL_CARD_ID_1 = 40;
    private static final int REBEL_CARD_ID_2 = 41;


    @Override
    public void assertReward() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ExileCard));

        PlayableCard card1 = (PlayableCard) game.getCardMap().get(REBEL_CARD_ID_1);
        card1.moveToDiscard();
        PlayableCard card2 = (PlayableCard) game.getCardMap().get(REBEL_CARD_ID_2);
        card2.moveToHand();

        game.applyAction(REBEL_CARD_ID_1);
        assertThat(card1.getLocation(), equalTo(CardLocation.Exile));
        assertThat(card1.getOwner(), is(nullValue()));
        assertThat(card1.getCardList(), equalTo(game.getExiledCards()));
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ExileCard));

        game.applyAction(REBEL_CARD_ID_2);
        assertThat(card2.getLocation(), equalTo(CardLocation.Exile));
        assertThat(card2.getOwner(), is(nullValue()));
        assertThat(card2.getCardList(), equalTo(game.getExiledCards()));
        assertThat(game.getPendingActions(), hasSize(0));
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),4, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }

    @Override
    public void setupAbility() {
        PlayableCard luke = (PlayableCard) game.getCardMap().get(LUKE_ID);
        luke.moveToGalaxyRow();
    }

    @Override
    public void verifyAbility() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.DiscardCardFromCenter));
        game.applyAction(LUKE_ID);
        PlayableCard luke = (PlayableCard) game.getCardMap().get(LUKE_ID);
        assertThat(luke.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(luke.getOwner(), is(nullValue()));
        assertThat(luke.getCardList(), equalTo(game.getGalaxyDiscard()));
    }
}