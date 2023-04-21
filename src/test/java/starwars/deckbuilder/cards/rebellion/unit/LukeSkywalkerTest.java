package starwars.deckbuilder.cards.rebellion.unit;

import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.cards.empire.ship.StarDestroyer;
import starwars.deckbuilder.cards.playablecard.HasAbilityCardTest;
import starwars.deckbuilder.game.Action;
import starwars.deckbuilder.game.CardLocation;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class LukeSkywalkerTest extends RebelTargetableCardTest implements HasAbilityCardTest {

    @Override
    public void setupAbility() {
        getPlayer().addForce(1);
        moveToInPlay(StarDestroyer.class, getPlayer().getOpponent());
    }

    @Override
    public void verifyAbility() {
        assertThat(game.getPendingActions(), hasSize(1));
        assertThat(game.getPendingActions().get(0).getAction(), equalTo(Action.LukeDestroyShip));

        PlayableCard card1 = getPlayer().getOpponent().getShipsInPlay().get(0);
        game.applyAction(card1.getId());

        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(card1.getLocation(), equalTo(CardLocation.getDiscard(getPlayer().getOpponent().getFaction())));
    }

    @Override
    public void preAttackSetup() {
        getPlayer().addForce(1);
    }

    @Override
    public void assertReward() {
        assertGameState(game.getEmpire(),0, 4);
        assertGameState(game.getRebel(),0, 0);
        assertForceIncreasedBy(Faction.empire, 4);
    }

    @Override
    public int getId() {
        return 73;
    }

    @Override
    public void assertAfterPlay() {
        assertGameState(game.getEmpire(),0, 0);
        assertGameState(game.getRebel(),6, 0);
        assertForceIncreasedBy(Faction.rebellion, 2);
    }
}