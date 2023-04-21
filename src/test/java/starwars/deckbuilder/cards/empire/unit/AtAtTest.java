package starwars.deckbuilder.cards.empire.unit;

import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.StaticEffect;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class AtAtTest extends EmpireTargetableCardTest implements HasAbilityCardTest {

    private static final int ID = 25;
    private static final int TROOPER_ID = 7;

    @Override
    public void assertReward() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.PurchaseCard));
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects().get(0), equalTo(StaticEffect.NextFactionPurchaseIsFree));

        // check that I can buy for free
        assertThat(game.getRebel().getResources(), equalTo(0));

        // Move Luke to buy row
        PlayableCard luke = (PlayableCard) game.getCardMap().get(LUKE_ID);
        luke.moveToGalaxyRow();

        // Try to buy
        game.applyAction(LUKE_ID);

        assertThat(luke.getLocation(), equalTo(CardLocation.RebelDiscard));
        assertThat(luke.getOwner(), equalTo(game.getRebel()));
        assertThat(luke.getCardList(), equalTo(game.getRebel().getDiscard()));
        assertThat(game.getRebel().getResources(), equalTo(0));
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),6, 0);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }

    @Override
    public void setupAbility() {
        PlayableCard stormtooper = (PlayableCard) game.getCardMap().get(TROOPER_ID);
        stormtooper.moveToDiscard();
    }

    @Override
    public void verifyAbility() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ReturnCardToHand));
        game.applyAction(TROOPER_ID);
        PlayableCard stormtooper = (PlayableCard) game.getCardMap().get(TROOPER_ID);
        assertThat(stormtooper.getLocation(), equalTo(CardLocation.EmpireHand));
    }
}