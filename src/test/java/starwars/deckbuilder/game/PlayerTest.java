package starwars.deckbuilder.game;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import starwars.deckbuilder.cards.common.models.CapitalShip;
import starwars.deckbuilder.cards.common.models.Faction;
import starwars.deckbuilder.cards.common.models.PlayableCard;
import starwars.deckbuilder.cards.common.models.Unit;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class PlayerTest {

    @Mock
    private Game game;

    @Mock
    private ForceBalance forceBalance;

    @Test
    void isForceWithPlayer() {
        Player player = buildImperialPlayer();
        when(game.getForceBalance()).thenReturn(forceBalance);
        when(forceBalance.darkSideHasTheForce()).thenReturn(true, false);
        when(forceBalance.lightSideHasTheForce()).thenReturn(false, true);

        assertTrue(player.isForceWithPlayer());
        assertFalse(player.isForceWithPlayer());

        verify(forceBalance, times(2)).darkSideHasTheForce();
        verify(forceBalance, never()).lightSideHasTheForce();

        player = buildRebelPlayer();

        assertFalse(player.isForceWithPlayer());
        assertTrue(player.isForceWithPlayer());
        verify(forceBalance, times(2)).darkSideHasTheForce();
        verify(forceBalance, times(2)).lightSideHasTheForce();
    }

    @Test
    void addResources() {
        Player player = buildImperialPlayer();
        assertThat(player.getResources(), equalTo(0));

        player.addResources(5);
        assertThat(player.getResources(), equalTo(5));

        player.addResources(-6);
        assertThat(player.getResources(), equalTo(0));
    }

    @Test
    void drawCards() {
        Player player = buildPlayer(Faction.empire, 1, 0, 0, 0, 0);
        assertThat(player.getDeck(), hasSize(1));

        player.drawCards(1);
        assertAllSizes(player, 0, 0, 1, 0, 0);
        PlayableCard card = player.getHand().get(0);
        verify(card, times(1)).moveToHand();

        // test deck shuffle
        player = buildPlayer(Faction.empire, 0, 1, 0, 0, 0);
        player.drawCards(1);
        assertAllSizes(player, 0,0,1,0,0);
        card = player.getHand().get(0);
        verify(card, times(1)).moveToHand();

        // test no exception when more than deck
        player = buildPlayer(Faction.empire, 2, 2, 0, 0, 0);
        player.drawCards(5);
        assertAllSizes(player, 0, 0, 4, 0, 0);
        for (PlayableCard c : player.getHand()) {
            verify(c, times(1)).moveToHand();
        }
    }

    @Test
    void discardUnits() {
        Player player = buildPlayer(Faction.empire, 0, 0, 0, 1, 0);
        player.discardUnits();
        assertAllSizes(player,0 , 1, 0,0,0);
        PlayableCard card = player.getDiscard().get(0);
        verify(card, times(1)).moveToDiscard();

        player = buildPlayer(Faction.empire, 2, 2, 2, 2, 0);
        player.discardUnits();
        assertAllSizes(player, 2, 4, 2, 0, 0);
    }

    @Test
    void discardHand() {
        Player player = buildPlayer(Faction.empire, 0, 0, 2, 0, 0);
        player.discardHand();
        assertAllSizes(player,0, 2, 0, 0, 0);
        for (PlayableCard card : player.getDiscard()) {
            verify(card, times(1)).moveToDiscard();
        }


        player = buildPlayer(Faction.empire, 2, 2, 2, 2, 2);
        player.discardHand();
        assertAllSizes(player, 2, 4, 0, 2, 2);
    }

    @Test
    void getAvailableAttack() {
        Player player = buildPlayer(Faction.empire, 0, 0, 0, 3, 3);
        // attack == 0 + 1 + 2 + 0 + 1 + 2 = 6
        assertThat(player.getAvailableAttack(), equalTo(6));

        // Mark unit 2 as has attacked
        when(player.getUnitsInPlay().get(2).canAttack()).thenReturn(false);
        // attack == 0 + 1 + 0 + 1 + 2 = 4
        assertThat(player.getAvailableAttack(), equalTo(4));

        // Mark ship 1 as has attacked
        when(player.getShipsInPlay().get(1).canAttack()).thenReturn(false);
        // attack == 0 + 1 + 0 + 2 = 3
        assertThat(player.getAvailableAttack(), equalTo(3));

        // Mark unit 1 as has attacked
        when(player.getUnitsInPlay().get(1).canAttack()).thenReturn(false);
        // attack == 0 + 0 + 2 = 2
        assertThat(player.getAvailableAttack(), equalTo(2));

        // Mark ship 0 as has attacked
        when(player.getShipsInPlay().get(0).canAttack()).thenReturn(false);
        // attack == 0 + 2 = 2
        assertThat(player.getAvailableAttack(), equalTo(2));
    }

    private Player buildImperialPlayer() {
        return buildPlayer(Faction.empire, 0, 0, 0, 0, 0);
    }

    private Player buildRebelPlayer() {
        return buildPlayer(Faction.rebellion, 0, 0, 0, 0, 0);
    }
    private Player buildPlayer(Faction faction, int deckSize, int discardSize, int handSize, int unitsInPlay, int shipsInPlay) {
        Player player = new Player(faction);
        player.setFaction(faction);
        player.setGame(game);
        player.setDeck(buildCardList(deckSize, player));
        player.setDiscard(buildCardList(discardSize, player));
        player.setHand(buildCardList(handSize, player));
        player.setUnitsInPlay(buildUnitList(unitsInPlay, player));
        player.setShipsInPlay(buildShipList(shipsInPlay));
        return player;
    }

    private List<Unit> buildUnitList(int amount, Player player) {
        List<Unit> units = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            Unit unit = mock(Unit.class);
            lenient().doAnswer((a) -> {
                units.remove(unit);
                player.getDiscard().add(unit);
                return null;
            }).when(unit).moveToDiscard();
            lenient().when(unit.getAttack()).thenReturn(i);
            lenient().when(unit.canAttack()).thenReturn(true);
            units.add(unit);
        }
        return units;
    }

    private List<CapitalShip> buildShipList(int amount) {
        List<CapitalShip> ships = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            CapitalShip ship = mock(CapitalShip.class);
            lenient().when(ship.getAttack()).thenReturn(i);
            lenient().when(ship.canAttack()).thenReturn(true);
            ships.add(ship);
        }
        return ships;
    }

    private List<PlayableCard> buildCardList(int amount, Player player) {
        List<PlayableCard> cards = new ArrayList<>();
        for (int i = 0; i < amount; i ++) {
            PlayableCard card = mock(PlayableCard.class);
            lenient().doAnswer((a) -> {
                cards.remove(card);
                player.getHand().add(card);
                return null;
            }).when(card).moveToHand();
            lenient().doAnswer((a) -> {
                cards.remove(card);
                player.getDiscard().add(card);
                return null;
            }).when(card).moveToDiscard();
            cards.add(card);
        }
        return cards;
    }

    private void assertAllSizes(Player player, int deckSize, int discardSize, int handSize, int unitsInPlay, int shipsInPlay) {
        assertThat(player.getDeck(), hasSize(deckSize));
        assertThat(player.getDiscard(), hasSize(discardSize));
        assertThat(player.getHand(), hasSize(handSize));
        assertThat(player.getUnitsInPlay(), hasSize(unitsInPlay));
        assertThat(player.getShipsInPlay(), hasSize(shipsInPlay));
    }
}