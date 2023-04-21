package starwars.deckbuilder.cards.rebellion.unit;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.cards.empire.unit.DarthVader;
import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.StaticEffect;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class MillenniumFalconTest extends RebelTargetableCardTest implements HasAbilityCardTest {

    private PlayableCard unique;

    @Override
    public void setupAbility() {
        unique = moveToInPlay(HanSolo.class, getPlayer()).get(0);
        unique.moveToDiscard();
    }

    @Override
    public void verifyAbility() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ReturnCardToHand));

        game.applyAction(unique.getId());
        assertThat(unique.getLocation(), equalTo(CardLocation.RebelHand));
    }

    @Test
    void testReturnNonUnique() {
        unique = moveToInPlay(HanSolo.class, getPlayer()).get(0);
        unique.moveToDiscard();
        PlayableCard nonUnique = moveToInPlay(BWing.class, getPlayer()).get(0);
        nonUnique.moveToDiscard();

        useCardAbility(game, card);
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ReturnCardToHand));

        game.applyAction(nonUnique.getId());
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ReturnCardToHand));
        assertThat(nonUnique.getLocation(), equalTo(CardLocation.RebelDiscard));
    }

    @Override
    public void assertReward() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.PurchaseCard));
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects().get(0), equalTo(StaticEffect.NextFactionPurchaseIsFree));

        // check that I can buy for free
        assertThat(game.getEmpire().getResources(), equalTo(0));

        PlayableCard darth = moveToGalaxyRow(DarthVader.class).get(0);

        // Try to buy
        game.applyAction(darth.getId());

        assertThat(darth.getLocation(), equalTo(CardLocation.EmpireDiscard));
        assertThat(darth.getOwner(), equalTo(game.getEmpire()));
        assertThat(darth.getCardList(), equalTo(game.getEmpire().getDiscard()));
        assertThat(game.getEmpire().getResources(), equalTo(0));
    }

    @Override
    public int getId() {
        return 72;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),5, 2);
        assertNoForceChange();
    }
}