package sw.db.cards.playablecard;

import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sw.db.cards.BaseTest;
import sw.db.cards.common.models.CapitalShip;
import sw.db.cards.common.models.Faction;
import sw.db.cards.common.models.PlayableCard;
import sw.db.cards.common.models.Unit;
import sw.db.game.ActionSpace;
import sw.db.game.CardLocation;
import sw.db.game.Game;
import sw.db.game.Player;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public abstract class PlayableCardTest extends BaseTest implements BasePlayableCard {

    public abstract int getId();

    public abstract void assertAfterPlay();

    public abstract Player getPlayer();
    @Getter
    protected PlayableCard card;

    @BeforeEach
    public void setUp() {
        game = new Game();
        game.initialize();
        Player player = getPlayer();
        Faction faction = player.getFaction();
        card = (PlayableCard) game.getCardMap().get(getId());
        // set force to neutral
        game.getEmpire().addForce(3);
        if (faction == Faction.rebellion) {
            game.applyAction(ActionSpace.PassTurn.getMinRange());
        }
        if (card.getLocation() == CardLocation.GalaxyDeck || card.getLocation() == CardLocation.OuterRimPilots) {
            card.buyToHand(player);
        } else if (card.getLocation() == CardLocation.GalaxyRow) {
            card.buyToHand(player);
            // keep 6 cards in row
            game.getGalaxyDeck().get(0).moveToGalaxyRow();
        } else if (card.getLocation() == CardLocation.getDeck(faction)) {
            card.moveToHand();
        }
    }

    protected void prePlaySetup() {
        // can be overridden for extra setup options
    }

    @Test
    public void testPlay() {
        prePlaySetup();
        Player player = getPlayer();
        Faction faction = player.getFaction();
        game.applyAction(getId());
        if (card instanceof CapitalShip) {
            assertThat(card.getLocation(), equalTo(CardLocation.getShipsInPlay(faction)));
            assertThat(card.getCardList(), equalTo(player.getShipsInPlay()));
            assertThat(player.getShipsInPlay(), contains(card));
        } else if (card instanceof Unit) {
            assertThat(card.getLocation(), equalTo(CardLocation.getUnitsInPlay(faction)));
            assertThat(card.getCardList(), equalTo(player.getUnitsInPlay()));
            assertThat(player.getUnitsInPlay(), contains(card));
        }

        assertAfterPlay();
    }


    protected void assertGameState(Player player, int attack, int resource) {
        assertThat(player.getResources(), equalTo(resource));
        assertThat(player.getAvailableAttack(), equalTo(attack));
    }

    protected void assertNoForceChange() {
        assertThat(game.getForceBalance().getPosition(), equalTo(3));
    }

    protected void assertForceIncreasedBy(Faction faction, int amount) {
        int expectedValue = faction == Faction.empire ? 3 - amount : 3 + amount;
        if (expectedValue < 0) {
            expectedValue = 0;
        }
        if (expectedValue > 6) {
            expectedValue = 6;
        }
        assertThat(game.getForceBalance().getPosition(), equalTo(expectedValue));
    }
}
