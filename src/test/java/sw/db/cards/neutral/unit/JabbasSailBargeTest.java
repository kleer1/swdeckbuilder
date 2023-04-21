package sw.db.cards.neutral.unit;

import org.junit.jupiter.api.Test;
import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.empire.unit.BobaFett;
import sw.db.cards.empire.unit.starter.Stormtrooper;
import sw.db.cards.playablecard.HasAbilityCardTest;
import sw.db.game.Action;
import sw.db.game.CardLocation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.junit.MatcherAssert.assertThat;

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