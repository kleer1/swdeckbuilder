package starwars.deckbuilder.cards.empire.unit;

import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.ActionSpace;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.StaticEffect;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class GrandMoffTarkinTest extends EmpireTargetableCardTest implements HasAbilityCardTest {

    @Override
    public void setupAbility() {
        PlayableCard dv = (PlayableCard) game.getCardMap().get(32);
        dv.moveToGalaxyRow();
    }

    @Override
    public void verifyAbility() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.PurchaseCard));
        assertThat(game.getStaticEffects(), hasSize(3));
        assertThat(game.getStaticEffects(), hasItems(StaticEffect.NextFactionPurchaseIsFree,
                StaticEffect.ExileNextFactionPurchase,
                StaticEffect.BuyNextToHand));

        game.applyAction(32);
        PlayableCard dv = (PlayableCard) game.getCardMap().get(32);
        assertThat(dv.getLocation(), equalTo(CardLocation.EmpireHand));

        game.applyAction(ActionSpace.PassTurn.getMinRange());
        assertThat(dv.getLocation(), equalTo(CardLocation.Exile));
    }

    @Override
    public void assertReward() {
        assertGameState(game.getRebel(), 0, 3);
        assertForceIncreasedBy(Faction.rebellion, 3);
    }

    @Override
    public int getId() {
        return 31;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),2, 2);
        assertGameState(game.getRebel(),0, 0);
        assertForceIncreasedBy(Faction.empire, 2);
    }
}