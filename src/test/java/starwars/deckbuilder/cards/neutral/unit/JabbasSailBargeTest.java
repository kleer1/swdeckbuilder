package starwars.deckbuilder.cards.neutral.unit;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.cards.empire.unit.BobaFett;
import starwars.deckbuilder.cards.empire.unit.starter.Stormtrooper;
import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JabbasSailBargeTest extends NeutralPlayableCardTest implements HasAbilityCardTest {

    private PlayableCard bounty;
    private PlayableCard nonBounty;

    @Override
    public void setupAbility() {
        bounty = moveToInPlay(BobaFett.class, getPlayer()).get(0);
        bounty.moveToDiscard();
        nonBounty = moveToInPlay(Stormtrooper.class, getPlayer()).get(0);
        nonBounty.moveToDiscard();
    }

    @Override
    public void verifyAbility() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ReturnCardToHand));

        game.applyAction(bounty.getId());

        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(bounty.getLocation(), equalTo(CardLocation.EmpireHand));
        assertThat(nonBounty.getLocation(), equalTo(CardLocation.EmpireDiscard));
    }

    @Test
    void testSelectNonBounty() {
        setupAbility();
        useCardAbility(game, card);

        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ReturnCardToHand));

        game.applyAction(nonBounty.getId());

        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.ReturnCardToHand));
        assertThat(bounty.getLocation(), equalTo(CardLocation.EmpireDiscard));
        assertThat(nonBounty.getLocation(), equalTo(CardLocation.EmpireDiscard));
    }

    @Override
    public int getId() {
        return 111;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),4, 3);
        assertGameState(game.getRebel(),0, 0);
        assertNoForceChange();
    }
}