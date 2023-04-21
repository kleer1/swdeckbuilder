package starwars.deckbuilder.cards.empire.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.base.HasAbilityTest;
import starwars.deckbuilder.cards.base.HasOnRevealTest;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.cards.empire.ship.StarDestroyer;
import starwars.deckbuilder.cards.rebellion.ship.MonCalamariCruiser;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.CardLocation;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class DeathStarTest extends EmpireAvailableBaseTest implements HasOnRevealTest, HasAbilityTest {

    @Override
    public int getId() {
        return 121;
    }

    @Override
    public void preChooseBaseSetup() {
        emptyGalaxyRow();
    }

    @Override
    public void assertAfterChooseBase() {
        assertThat(base.abilityActive(), equalTo(false));
        getPlayer().addResources(4);
        assertThat(base.abilityActive(), equalTo(false));
        PlayableCard monCal = moveToInPlay(MonCalamariCruiser.class, getPlayer().getOpponent()).get(0);
        assertThat(base.abilityActive(), equalTo(true));
        monCal.moveToGalaxyRow();
        assertThat(base.abilityActive(), equalTo(true));
        getPlayer().addResources(-1);
        assertThat(base.abilityActive(), equalTo(false));
    }

    @Override
    public void setupAbility() {
        emptyGalaxyRow();
        moveToInPlay(MonCalamariCruiser.class, getPlayer().getOpponent());
        getPlayer().addResources(4);
    }

    @Override
    public void verifyAbility() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.FireWhenReady));

        PlayableCard monCal = getPlayer().getOpponent().getShipsInPlay().get(0);
        game.applyAction(monCal.getId());

        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(monCal.getLocation(), equalTo(CardLocation.getDiscard(getPlayer().getOpponent().getFaction())));
        assertThat(getPlayer().getResources(), equalTo(0));
    }

    @Test
    void testAttackEmpireCenterRow() {
        emptyGalaxyRow();
        moveToGalaxyRow(StarDestroyer.class);
        getPlayer().addResources(5);
        useCardAbility(game, base);

        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.FireWhenReady));
        PlayableCard ssd = game.getGalaxyRow().get(0);
        game.applyAction(ssd.getId());

        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(ssd.getLocation(), equalTo(CardLocation.GalaxyDiscard));
        assertThat(getPlayer().getResources(), equalTo(1));
    }
}