package sw.db.cards.rebellion.unit;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.playablecard.HasAbilityCardTest;
import sw.db.game.Action;
import sw.db.game.CardLocation;
import sw.db.game.StaticEffect;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class PrincessLeiaTest extends RebelTargetableCardTest implements HasAbilityCardTest {

    private PlayableCard luke;
    @Override
    public void setupAbility() {
        luke = moveToGalaxyRow(LukeSkywalker.class).get(0);
    }

    @Override
    public void verifyAbility() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.PurchaseCard));
        assertThat(game.getStaticEffects(), hasSize(2));
        assertThat(game.getStaticEffects(), Matchers.hasItems(StaticEffect.NextFactionPurchaseIsFree, StaticEffect.BuyNextToTopOfDeck));

        game.applyAction(luke.getId());

        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(game.getStaticEffects(), hasSize(0));
        assertThat(luke.getLocation(), equalTo(CardLocation.RebelDeck));
        assertThat(getPlayer().getDeck().get(0), equalTo(luke));
    }

    @Test
    void testForceNotWithYou() {
        luke = moveToGalaxyRow(LukeSkywalker.class).get(0);
        getPlayer().getOpponent().addForce(100);
        useCardAbility(game, card);

        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.PurchaseCard));
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects().get(0), equalTo(StaticEffect.NextFactionPurchaseIsFree));

        game.applyAction(luke.getId());

        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(game.getStaticEffects(), hasSize(0));
        assertThat(luke.getLocation(), equalTo(CardLocation.RebelDiscard));
    }

    @Override
    public void assertReward() {
        assertGameState(game.getEmpire(),0, 3);
        assertGameState(game.getRebel(),0, 0);
        assertForceIncreasedBy(Faction.empire, 3);
    }

    @Override
    public int getId() {
        return 71;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),2, 2);
        assertForceIncreasedBy(Faction.rebellion, 2);
    }
}