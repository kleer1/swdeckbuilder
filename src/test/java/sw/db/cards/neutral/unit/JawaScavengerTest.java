package sw.db.cards.neutral.unit;

import org.junit.jupiter.api.Test;
import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.playablecard.HasAbilityCardTest;
import sw.db.game.Action;
import sw.db.game.CardLocation;
import sw.db.game.StaticEffect;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class JawaScavengerTest extends NeutralPlayableCardTest implements HasAbilityCardTest {

    private PlayableCard neutral;
    private PlayableCard empire;
    private PlayableCard rebel;
    private PlayableCard empire2;
    int startingResources = 2;

    @Override
    public void setupAbility() {
        neutral = (PlayableCard) game.getCardMap().get(NEUTRAL_GALAXY_CARD);
        neutral.moveToGalaxyDiscard();
        empire = (PlayableCard) game.getCardMap().get(EMPIRE_GALAXY_CARD);
        empire.moveToGalaxyDiscard();
        rebel = (PlayableCard) game.getCardMap().get(REBEL_GALAXY_CARD);
        rebel.moveToGalaxyDiscard();
        empire2 = (PlayableCard) game.getCardMap().get(EMPIRE_GALAXY_CARD + 1);
        empire2.moveToGalaxyRow();
    }

    @Override
    public void verifyAbility() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.PurchaseCard));
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects().get(0), equalTo(StaticEffect.PurchaseFromDiscard));

        // buy empire card
        game.applyAction(empire.getId());

        assertThat(empire.getLocation(), equalTo(CardLocation.EmpireDiscard));
        assertThat(getPlayer().getResources(), equalTo(startingResources - empire.getCost()));
        assertThat(rebel.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(neutral.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(empire2.getLocation(), equalTo(CardLocation.GalaxyRow));
        assertThat(game.getStaticEffects(), hasSize(0));
        assertThat(game.getPendingActions(), hasSize(0));
    }

    @Test
    void testPurchaseNeutralCard() {
        setupAbility();
        useCardAbility(game, card);

        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.PurchaseCard));
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects().get(0), equalTo(StaticEffect.PurchaseFromDiscard));

        // buy neutral card
        game.applyAction(neutral.getId());

        assertThat(neutral.getLocation(), equalTo(CardLocation.EmpireDiscard));
        assertThat(getPlayer().getResources(), equalTo(startingResources - neutral.getCost()));
        assertThat(rebel.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(empire.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(empire2.getLocation(), equalTo(CardLocation.GalaxyRow));
        assertThat(game.getStaticEffects(), hasSize(0));
        assertThat(game.getPendingActions(), hasSize(0));
    }

    @Test
    void testCantBuyRebel() {
        setupAbility();
        useCardAbility(game, card);

        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.PurchaseCard));
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects().get(0), equalTo(StaticEffect.PurchaseFromDiscard));

        // buy neutral card
        game.applyAction(rebel.getId());

        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.PurchaseCard));
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects().get(0), equalTo(StaticEffect.PurchaseFromDiscard));

        assertThat(neutral.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(rebel.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(empire.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(empire2.getLocation(), equalTo(CardLocation.GalaxyRow));
    }

    @Test
    void testCantBuyFromCenter() {
        setupAbility();
        useCardAbility(game, card);

        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.PurchaseCard));
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects().get(0), equalTo(StaticEffect.PurchaseFromDiscard));

        // buy center card
        game.applyAction(empire2.getId());

        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.PurchaseCard));
        assertThat(game.getStaticEffects(), hasSize(1));
        assertThat(game.getStaticEffects().get(0), equalTo(StaticEffect.PurchaseFromDiscard));

        assertThat(neutral.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(rebel.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(empire.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(empire2.getLocation(), equalTo(CardLocation.GalaxyRow));
    }

    @Override
    public int getId() {
        return 92;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 2);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }

    @Test
    void testNotActive() {
        rebel = (PlayableCard) game.getCardMap().get(REBEL_GALAXY_CARD);
        rebel.moveToGalaxyDiscard();
        useCardAbility(game, card);
        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(game.getStaticEffects(), hasSize(0));
    }
}