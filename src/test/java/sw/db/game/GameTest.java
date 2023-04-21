package sw.db.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import sw.db.cards.common.models.*;
import sw.db.cards.empire.base.*;
import sw.db.cards.empire.ship.GozantiCruiser;
import sw.db.cards.empire.ship.ImperialCarrier;
import sw.db.cards.empire.ship.StarDestroyer;
import sw.db.cards.empire.unit.*;
import sw.db.cards.empire.unit.starter.ImperialShuttle;
import sw.db.cards.empire.unit.starter.Inquisitor;
import sw.db.cards.empire.unit.starter.Stormtrooper;
import sw.db.cards.neutral.ship.BlockadeRunner;
import sw.db.cards.neutral.ship.CrocCruiser;
import sw.db.cards.neutral.ship.NebulonBFrigate;
import sw.db.cards.neutral.unit.*;
import sw.db.cards.rebellion.base.*;
import sw.db.cards.rebellion.ship.HammerheadCorvette;
import sw.db.cards.rebellion.ship.MonCalamariCruiser;
import sw.db.cards.rebellion.ship.RebelTransport;
import sw.db.cards.rebellion.unit.*;
import sw.db.cards.rebellion.unit.starter.AllianceShuttle;
import sw.db.cards.rebellion.unit.starter.RebelTrooper;
import sw.db.cards.rebellion.unit.starter.TempleGuardian;
import sw.db.matchers.BetweenMatcher;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

class GameTest {

    private Game game = new Game();

    @BeforeEach
    void setUp() {
        game.initialize();
    }

    @Test
    void testInitialize() {
        assertThat(game.getCurrentPlayersTurn(), equalTo(Faction.empire));
        assertThat(game.getReadyPlayers(), hasSize(2));
        assertThat(game.getReadyPlayers(), hasItems(Faction.rebellion, Faction.empire));
        assertThat(game.getForceBalance().getPosition(), equalTo(6));
        assertGalaxyState();
        assertOuterRim();
        assertThat(game.getExiledCards(), hasSize(0));
        assertThat(game.getCardMap().size(), equalTo(140));
        assertThat(game.getStaticEffects(), hasSize(0));
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.empire), equalTo(0));
        assertThat(game.getKnowsTopCardOfDeck().get(Faction.rebellion), equalTo(0));
        assertEmpireStartState();
        assertRebelStartState();
        assertThat(game.getEmpire().getOpponent(), equalTo(game.getRebel()));
        assertThat(game.getRebel().getOpponent(), equalTo(game.getEmpire()));
        assertThat(game.getCurrentPlayersAction(), equalTo(Faction.empire));
        assertThat(game.getCurrentPlayersTurn(), equalTo(Faction.empire));
        assertThat(game.getPendingActions(), hasSize(0));
        assertThat(game.getLastCardPlayed(), is(nullValue()));
        assertThat(game.getLastCardActivated(),is(nullValue()));
        assertThat(game.getReward(), equalTo(0.0));
        assertThat(game.getAttackers(), hasSize(0));
        assertThat(game.getAttackTarget(), is(nullValue()));
        assertThat(game.canSeeOpponentsHand(), is(false));
        assertThat(game.getExileAtEndOfTurn(), hasSize(0));
        assertThat(game.getANewHope1(), is(nullValue()));
        assertThat(game.getGameState(), is(notNullValue()));
    }

    @RepeatedTest(100)
    void testInvalidActionsEmpire() {
        emptyHand(game.getEmpire());
        for (int i : getRandomizedInvalidActions(Set.of(140))) {
            game.applyAction(i);
            assertThat("asserting action " + i, game.getReward(), equalTo(game.INVALID_ACTION_REWARD));
        }
    }

    @RepeatedTest(100)
    void testInvalidActionsRebel() {
        game.getEmpire().addForce(1);
        game.applyAction(ActionSpace.PassTurn.getMinRange());
        emptyHand(game.getRebel());
        for (int i : getRandomizedInvalidActions(Set.of(140))) {
            game.applyAction(i);
            assertThat("asserting action " + i, game.getReward(), equalTo(game.INVALID_ACTION_REWARD));
        }
    }

    private void emptyHand(Player player) {
        ListIterator<PlayableCard> iterator = player.getHand().listIterator();
        while (iterator.hasNext()) {
            PlayableCard card = iterator.next();
            iterator.remove();
            card.moveToDiscard();
        }
    }

    private void assertEmpireStartState() {
        Player player = game.getEmpire();
        assertThat(player.getFaction(), equalTo(Faction.empire));
        assertStartingDeckSizes(player);
        for (PlayableCard card : player.getHand()) {
            assertThat(card.getId(), BetweenMatcher.isBetween(CardMapping.EmpireStartingCards));
            assertThat(card.getLocation(), equalTo(CardLocation.EmpireHand));
            assertThat(card.getOwner(), equalTo(player));
            assertThat(card.getCardList(), equalTo(player.getHand()));
            assertThat(card.getId(), BetweenMatcher.isBetween(CardMapping.EmpirePlayableCards));
        }

        for (PlayableCard card : player.getDeck()) {
            assertThat(card.getId(), BetweenMatcher.isBetween(CardMapping.EmpireStartingCards));
            assertThat(card.getLocation(), equalTo(CardLocation.EmpireDeck));
            assertThat(card.getOwner(), equalTo(player));
            assertThat(card.getCardList(), equalTo(player.getDeck()));
            assertThat(card.getId(), BetweenMatcher.isBetween(CardMapping.EmpirePlayableCards));
        }
        int numShuttle = countByClass(joinLists(player.getHand(), player.getDeck()), ImperialShuttle.class);
        assertThat(numShuttle, equalTo(7));
        int numTrooper = countByClass(joinLists(player.getHand(), player.getDeck()), Stormtrooper.class);
        assertThat(numTrooper, equalTo(2));
        int numInq = countByClass(joinLists(player.getHand(), player.getDeck()), Inquisitor.class);
        assertThat(numInq, equalTo(1));

        Base currentBase = player.getCurrentBase();
        assertThat(currentBase, is(notNullValue()));
        assertThat(currentBase, is(instanceOf(Lothal.class)));
        assertThat(currentBase.getLocation(), equalTo(CardLocation.EmpireCurrentBase));
        assertThat(currentBase.getOwner(), equalTo(player));
        assertThat(currentBase.getCardList(), is(nullValue()));
        assertThat(currentBase.getId(), BetweenMatcher.isBetween(CardMapping.EmpireBases));

        assertThat(player.getAvailableBases(), hasSize(9));
        for (Base base : player.getAvailableBases()) {
            assertThat(base.getLocation(), equalTo(CardLocation.EmpireAvailableBases));
            assertThat(base.getOwner(), equalTo(player));
            assertThat(base.getCardList(), equalTo(player.getAvailableBases()));
            assertThat(base.getId(), BetweenMatcher.isBetween(CardMapping.EmpireBases));
        }
        assertThat(countByClass(player.getAvailableBases(), Corellia.class), equalTo(1));
        assertThat(countByClass(player.getAvailableBases(), Coruscant.class), equalTo(1));
        assertThat(countByClass(player.getAvailableBases(), DeathStar.class), equalTo(1));
        assertThat(countByClass(player.getAvailableBases(), Endor.class), equalTo(1));
        assertThat(countByClass(player.getAvailableBases(), Kafrene.class), equalTo(1));
        assertThat(countByClass(player.getAvailableBases(), Kessel.class), equalTo(1));
        assertThat(countByClass(player.getAvailableBases(), Mustafar.class), equalTo(1));
        assertThat(countByClass(player.getAvailableBases(), OrdMantell.class), equalTo(1));
        assertThat(countByClass(player.getAvailableBases(), Rodia.class), equalTo(1));

        assertThat(player.getResources(), equalTo(0));
    }

    private void assertRebelStartState() {
        Player player = game.getRebel();
        assertThat(player.getFaction(), equalTo(Faction.rebellion));
        assertStartingDeckSizes(player);
        for (PlayableCard card : player.getHand()) {
            assertThat(card.getId(), BetweenMatcher.isBetween(CardMapping.RebelStartingCards));
            assertThat(card.getLocation(), equalTo(CardLocation.RebelHand));
            assertThat(card.getOwner(), equalTo(player));
            assertThat(card.getCardList(), equalTo(player.getHand()));
            assertThat(card.getId(), BetweenMatcher.isBetween(CardMapping.RebelPlayableCards));
        }

        for (PlayableCard card : player.getDeck()) {
            assertThat(card.getId(), BetweenMatcher.isBetween(CardMapping.RebelStartingCards));
            assertThat(card.getLocation(), equalTo(CardLocation.RebelDeck));
            assertThat(card.getOwner(), equalTo(player));
            assertThat(card.getCardList(), equalTo(player.getDeck()));
            assertThat(card.getId(), BetweenMatcher.isBetween(CardMapping.RebelPlayableCards));
        }
        int numShuttle = countByClass(joinLists(player.getHand(), player.getDeck()), AllianceShuttle.class);
        assertThat(numShuttle, equalTo(7));
        int numTrooper = countByClass(joinLists(player.getHand(), player.getDeck()), RebelTrooper.class);
        assertThat(numTrooper, equalTo(2));
        int numGuard = countByClass(joinLists(player.getHand(), player.getDeck()), TempleGuardian.class);
        assertThat(numGuard, equalTo(1));

        Base currentBase = player.getCurrentBase();
        assertThat(currentBase, is(notNullValue()));
        assertThat(currentBase, is(instanceOf(Dantooine.class)));
        assertThat(currentBase.getLocation(), equalTo(CardLocation.RebelCurrentBase));
        assertThat(currentBase.getOwner(), equalTo(player));
        assertThat(currentBase.getCardList(), is(nullValue()));
        assertThat(currentBase.getId(), BetweenMatcher.isBetween(CardMapping.RebelBases));

        assertThat(player.getAvailableBases(), hasSize(9));
        for (Base base : player.getAvailableBases()) {
            assertThat(base.getLocation(), equalTo(CardLocation.RebelAvailableBases));
            assertThat(base.getOwner(), equalTo(player));
            assertThat(base.getCardList(), equalTo(player.getAvailableBases()));
            assertThat(base.getId(), BetweenMatcher.isBetween(CardMapping.RebelBases));
        }
        assertThat(countByClass(player.getAvailableBases(), Alderaan.class), equalTo(1));
        assertThat(countByClass(player.getAvailableBases(), Bespin.class), equalTo(1));
        assertThat(countByClass(player.getAvailableBases(), Dagobah.class), equalTo(1));
        assertThat(countByClass(player.getAvailableBases(), Hoth.class), equalTo(1));
        assertThat(countByClass(player.getAvailableBases(), Jedha.class), equalTo(1));
        assertThat(countByClass(player.getAvailableBases(), MonCala.class), equalTo(1));
        assertThat(countByClass(player.getAvailableBases(), Sullust.class), equalTo(1));
        assertThat(countByClass(player.getAvailableBases(), Tatooine.class), equalTo(1));
        assertThat(countByClass(player.getAvailableBases(), Yavin4.class), equalTo(1));

        assertThat(player.getResources(), equalTo(0));
    }

    private void assertGalaxyState() {
        assertThat(game.getGalaxyDeck(), hasSize(84));
        assertThat(game.getGalaxyRow(), hasSize(6));
        assertThat(game.getGalaxyDiscard(), hasSize(0));
        for (PlayableCard card : game.getGalaxyRow()) {
            assertThat(card.getId(), BetweenMatcher.isBetween(CardMapping.getGalaxyCards(card.getFaction())));
            assertThat(card.getId(), BetweenMatcher.isBetween(CardMapping.getPlayableCards(card.getFaction())));
            if (card instanceof CapitalShip) {
                assertThat(card.getId(), BetweenMatcher.isBetween(CardMapping.getShipCards(card.getFaction())));
            }
            assertThat(card.getLocation(), equalTo(CardLocation.GalaxyRow));
            assertThat(card.getOwner(), is(nullValue()));
            assertThat(card.getCardList(), equalTo(game.getGalaxyRow()));
        }
        for (PlayableCard card : game.getGalaxyDeck()) {
            assertThat(card.getId(), BetweenMatcher.isBetween(CardMapping.getGalaxyCards(card.getFaction())));
            assertThat(card.getId(), BetweenMatcher.isBetween(CardMapping.getPlayableCards(card.getFaction())));
            if (card instanceof CapitalShip) {
                assertThat(card.getId(), BetweenMatcher.isBetween(CardMapping.getShipCards(card.getFaction())));
            }
            assertThat(card.getLocation(), equalTo(CardLocation.GalaxyDeck));
            assertThat(card.getOwner(), is(nullValue()));
            assertThat(card.getCardList(), equalTo(game.getGalaxyDeck()));
        }
        List<PlayableCard> joinedList = joinLists(game.getGalaxyRow(), game.getGalaxyDeck());
        assertThat(countByClass(joinedList, NebulonBFrigate.class), equalTo(2));
        assertThat(countByClass(joinedList, BlockadeRunner.class), equalTo(3));
        assertThat(countByClass(joinedList, CrocCruiser.class), equalTo(2));
        assertThat(countByClass(joinedList, JabbaTheHutt.class), equalTo(1));
        assertThat(countByClass(joinedList, JabbasSailBarge.class), equalTo(1));
        assertThat(countByClass(joinedList, LandoCalrissian.class), equalTo(1));
        assertThat(countByClass(joinedList, Ig88.class), equalTo(1));
        assertThat(countByClass(joinedList, Dengar.class), equalTo(1));
        assertThat(countByClass(joinedList, Bossk.class), equalTo(1));
        assertThat(countByClass(joinedList, Lobot.class), equalTo(1));
        assertThat(countByClass(joinedList, QuarrenMercenary.class), equalTo(2));
        assertThat(countByClass(joinedList, Hwk290.class), equalTo(2));
        assertThat(countByClass(joinedList, FangFighter.class), equalTo(2));
        assertThat(countByClass(joinedList, TwilekSmuggler.class), equalTo(2));
        assertThat(countByClass(joinedList, KelDorMystic.class), equalTo(2));
        assertThat(countByClass(joinedList, RodianGunslinger.class), equalTo(2));
        assertThat(countByClass(joinedList, JawaScavenger.class), equalTo(2));
        assertThat(countByClass(joinedList, Z95Headhunter.class), equalTo(2));
        assertThat(countByClass(joinedList, MonCalamariCruiser.class), equalTo(2));
        assertThat(countByClass(joinedList, HammerheadCorvette.class), equalTo(2));
        assertThat(countByClass(joinedList, RebelTransport.class), equalTo(2));
        assertThat(countByClass(joinedList, LukeSkywalker.class), equalTo(1));
        assertThat(countByClass(joinedList, PrincessLeia.class), equalTo(1));
        assertThat(countByClass(joinedList, CassianAndor.class), equalTo(1));
        assertThat(countByClass(joinedList, HanSolo.class), equalTo(1));
        assertThat(countByClass(joinedList, MillenniumFalcon.class), equalTo(1));
        assertThat(countByClass(joinedList, Chewbacca.class), equalTo(1));
        assertThat(countByClass(joinedList, JynErso.class), equalTo(1));
        assertThat(countByClass(joinedList, ChirrutImwe.class), equalTo(1));
        assertThat(countByClass(joinedList, BazeMalbus.class), equalTo(1));
        assertThat(countByClass(joinedList, BWing.class), equalTo(2));
        assertThat(countByClass(joinedList, UWing.class), equalTo(2));
        assertThat(countByClass(joinedList, XWing.class), equalTo(3));
        assertThat(countByClass(joinedList, RebelCommando.class), equalTo(2));
        assertThat(countByClass(joinedList, DurosSpy.class), equalTo(2));
        assertThat(countByClass(joinedList, Snowspeeder.class), equalTo(2));
        assertThat(countByClass(joinedList, YWing.class), equalTo(2));
        assertThat(countByClass(joinedList, StarDestroyer.class), equalTo(2));
        assertThat(countByClass(joinedList, ImperialCarrier.class), equalTo(2));
        assertThat(countByClass(joinedList, GozantiCruiser.class), equalTo(3));
        assertThat(countByClass(joinedList, DarthVader.class), equalTo(1));
        assertThat(countByClass(joinedList, GrandMoffTarkin.class), equalTo(1));
        assertThat(countByClass(joinedList, DirectorKrennic.class), equalTo(1));
        assertThat(countByClass(joinedList, BobaFett.class), equalTo(1));
        assertThat(countByClass(joinedList, MoffJerjerrod.class), equalTo(1));
        assertThat(countByClass(joinedList, ScoutTrooper.class), equalTo(2));
        assertThat(countByClass(joinedList, TieInterceptor.class), equalTo(2));
        assertThat(countByClass(joinedList, GeneralVeers.class), equalTo(1));
        assertThat(countByClass(joinedList, AdmiralPiett.class), equalTo(1));
        assertThat(countByClass(joinedList, AtAt.class), equalTo(1));
        assertThat(countByClass(joinedList, LandingCraft.class), equalTo(2));
        assertThat(countByClass(joinedList, AtSt.class), equalTo(2));
        assertThat(countByClass(joinedList, TieBomber.class), equalTo(2));
        assertThat(countByClass(joinedList, TieFighter.class), equalTo(3));
        assertThat(countByClass(joinedList, DeathTrooper.class), equalTo(2));
    }

    private void assertOuterRim() {
        assertThat(game.getOuterRimPilots(), hasSize(10));
        for (PlayableCard card : game.getOuterRimPilots()) {
            assertThat(card.getId(), BetweenMatcher.isBetween(CardMapping.NeutralOuterRimCards));
            assertThat(card.getId(), BetweenMatcher.isBetween(CardMapping.NeutralPlayableCards));
            assertThat(card.getLocation(), equalTo(CardLocation.OuterRimPilots));
            assertThat(card.getOwner(), is(nullValue()));
            assertThat(card.getCardList(), equalTo(game.getOuterRimPilots()));
        }
        assertThat(countByClass(game.getOuterRimPilots(), OuterRimPilot.class), equalTo(10));
    }

    private void assertStartingDeckSizes(Player player) {
        assertThat(player.getHand(), hasSize(5));
        assertThat(player.getDeck(), hasSize(5));
        assertThat(player.getDiscard(), hasSize(0));
        assertThat(player.getShipsInPlay(), hasSize(0));
        assertThat(player.getUnitsInPlay(), hasSize(0));
    }

    private int countByClass(List<? extends Card> cardList, Class<?> c) {
        return (int) cardList.stream().filter(x -> x.getClass() == c).count();
    }

    private <T> List<T> joinLists(List<T> list1, List<T> list2) {
        return Stream.concat(list1.stream(), list2.stream()).collect(Collectors.toList());
    }

    private PlayableCard getPlayableCardFromMap(final int id) {
        return (PlayableCard) game.getCardMap().get(id);
    }

    private static List<Integer> getRandomizedInvalidActions(Set<Integer> excludes) {
        List<Integer> list = Stream.iterate(0, i -> i + 1)
                .limit(149)
                .filter(i -> !excludes.contains(i))
                .collect(Collectors.toList());
        Collections.shuffle(list);
        return list;
    }

    @ParameterizedTest
    @MethodSource("invalidPendingActionsEmpire")
    void testInvalidActionWithPendingActionEmpire(Action pendingAction, Set<CardLocation> validLocations, Set<ActionSpace> validActionSpaces) {
        for (int it = 1; it <= 10; it ++) {
            System.out.println("Testing iteration " + it);
            setUp();
            // manually add a discard from hand pending action
            game.getEmpire().addResources(10);
            game.getPendingActions().add(PendingAction.of(pendingAction));
            for (int i : getRandomizedInvalidActions(getExcludesFromCardLocations(game, validLocations, validActionSpaces))) {
                game.applyAction(i);
                assertThat("asserting action " + i, game.getReward(), equalTo(game.INVALID_ACTION_REWARD));
            }
        }

    }

    @ParameterizedTest
    @MethodSource("invalidPendingActionsRebel")
    void testInvalidActionWithPendingActionRebel(Action pendingAction, Set<CardLocation> validLocations, Set<ActionSpace> validActionSpaces) {
        for (int it = 1; it <= 10; it ++) {
            System.out.println("Testing iteration " + it);
            setUp();
            game.getEmpire().addForce(1);
            game.applyAction(ActionSpace.PassTurn.getMinRange());
            game.getRebel().addResources(10);
            // manually add a discard from hand pending action
            game.getPendingActions().add(PendingAction.of(pendingAction));
            for (int i : getRandomizedInvalidActions(getExcludesFromCardLocations(game, validLocations, validActionSpaces))) {
                game.applyAction(i);
                assertThat("asserting action " + i + " with pending action " + pendingAction, game.getReward(), equalTo(game.INVALID_ACTION_REWARD));
            }
        }

    }

    @Test
    void testThatJustAttackingWillEndGame() {
        List<Integer> actionCounts = new ArrayList<>();
        int iterations = 100;
        for (int i = 1; i <= iterations; i++) {
            game.initialize();
            int numActions = 0;
            while (!game.isGameComplete()) {
                Faction faction = game.getCurrentPlayersTurn();
                Base base = game.getCurrentPlayer().getOpponent().getCurrentBase();
                // play ever card in hand
                List<Integer> actions = game.getCurrentPlayer().getHand().stream()
                        .map(Card::getId).collect(Collectors.toList());
                for (int action : actions) {
                    game.applyAction(action);
                    numActions++;
                    if (!game.getPendingActions().isEmpty()) {
                        // always choose attack
                        numActions++;
                        game.applyAction(ActionSpace.ChooseStatAttack.getMinRange());
                    }
                }
                logCardList(game.getCurrentPlayer().getUnitsInPlay(), CardLocation.getUnitsInPlay(game.getCurrentPlayersTurn()));

                if (game.getCurrentPlayer().getAvailableAttack() > 0 && base != null) {
                    numActions++;
                    game.applyAction(base.getId());
                    System.out.println("Attacking " + game.getAttackTarget().getTitle());

                    // select all attackers
                    actions = game.getCurrentPlayer().getUnitsInPlay().stream()
                            .map(Card::getId).collect(Collectors.toList());
                    actions.addAll(game.getCurrentPlayer().getShipsInPlay().stream()
                            .map(Card::getId).collect(Collectors.toList()));
                    for (int action : actions) {
                        numActions++;
                        game.applyAction(action);
                    }
                    logCardList(game.getAttackers(), CardLocation.getUnitsInPlay(game.getCurrentPlayersTurn()));
                    // confirm attack
                    numActions++;
                    game.applyAction(ActionSpace.ConfirmAttackers.getMinRange());
                    base = game.getCurrentPlayer().getOpponent().getCurrentBase();
                    if (base != null) {
                        System.out.println(game.getCurrentPlayer().getOpponent().getFaction() + " base has " +
                                base.getRemainingHealth() + " remaining health");
                    } else {
                        System.out.println(game.getCurrentPlayersTurn() + " destroyed opponents base ");
                    }
                }

                // try to buy cards with attack starting with highest
                actions = game.getGalaxyRow().stream()
                        .filter(c -> c.getAttack() > 0 && Set.of(game.getCurrentPlayersTurn(), Faction.neutral).contains(c.getFaction()))
                        .sorted(Comparator.comparingInt(PlayableCard::getAttack).reversed()).map(Card::getId).collect(Collectors.toList());

                for (int action : actions) {
                    numActions++;
                    game.applyAction(action);
                    if (game.getReward() != -15.0) {
                        System.out.println(game.getCurrentPlayersTurn() + " bought " + game.getCardMap().get(action).getTitle());
                    }
                    if (!game.getPendingActions().isEmpty()) {
                        numActions++;
                        game.applyAction(ActionSpace.DeclineAction.getMinRange());
                    }
                }
                logCardList(game.getCurrentPlayer().getDiscard(), CardLocation.getDiscard(game.getCurrentPlayersTurn()));


                // pass turn after attack
                System.out.println(faction + " passing turn");
                numActions++;
                game.applyAction(ActionSpace.PassTurn.getMinRange());

                // check if new base needs to be selected
                if (!game.getPendingActions().isEmpty()) {
                    List<Base> availableBases = game.getCurrentPlayer().getAvailableBases();
                    Base newBase = availableBases.get(new Random().nextInt(availableBases.size()));
                    numActions++;
                    game.applyAction(newBase.getId());
                    System.out.println(game.getCurrentPlayersTurn() + " choose new base: " + newBase.getTitle());
                    // decline if the base has an ability
                    if (!game.getPendingActions().isEmpty()) {
                        numActions++;
                        game.applyAction(ActionSpace.DeclineAction.getMinRange());
                    }
                    // galactic rule can't be declined
                    if (!game.getPendingActions().isEmpty()) {
                        numActions++;
                        game.applyAction(game.getGalaxyDeck().get(0).getId());
                    }
                }
            }
            actionCounts.add(numActions);
            System.out.println("Game " + i + " complete");
        }
        System.out.println("Average number of actions in game: " + (actionCounts.stream().reduce(0, (a, b) -> a + b) / (double) iterations));
        System.out.println("Max actions: " + actionCounts.stream().max(Integer::compareTo));
    }

    private static void logCardList(List<? extends Card> cards, CardLocation cardLocation) {
        StringBuilder builder = new StringBuilder();
        builder.append("Location: ")
                .append(cardLocation)
                .append(" has [");
        for (Card card : cards) {
            builder.append("{")
                    .append(card.getId())
                    .append(", ")
                    .append(card.getTitle())
                    .append("},");
        }
        builder.append("]");
        System.out.println(builder.toString());
    }

    private static Stream<Arguments> invalidPendingActionsEmpire() {
        return Stream.of(
                Arguments.of(Action.DiscardFromHand, Set.of(CardLocation.EmpireHand), Set.of()),
                Arguments.of(Action.PurchaseCard, Set.of(CardLocation.GalaxyRow), Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.AttackCenterRow, Set.of(CardLocation.GalaxyRow), Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.SelectAttacker, Set.of(CardLocation.EmpireShipInPlay, CardLocation.EmpireUnitInPlay), Set.of()),
                Arguments.of(Action.DiscardCardFromCenter, Set.of(CardLocation.GalaxyRow), Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.ExileCard, Set.of(CardLocation.EmpireHand, CardLocation.EmpireDiscard), Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.ReturnCardToHand, Set.of(CardLocation.EmpireDiscard), Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.ChooseNextBase, Set.of(CardLocation.EmpireAvailableBases), Set.of()),
                Arguments.of(Action.ChooseNextBase, Set.of(CardLocation.GalaxyRow), Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.FireWhenReady, Set.of(CardLocation.GalaxyRow, CardLocation.RebelShipInPlay),
                        Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.GalacticRule, Set.of(CardLocation.GalaxyDeck), Set.of()),
                Arguments.of(Action.ANewHope1, Set.of(CardLocation.GalaxyDiscard), Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.ANewHope2, Set.of(CardLocation.GalaxyRow), Set.of()),
                Arguments.of(Action.DurosDiscard, Set.of(CardLocation.EmpireHand), Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.BWingDiscard, Set.of(CardLocation.EmpireHand), Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.JynErsoTopDeck, Set.of(CardLocation.EmpireHand), Set.of()),
                Arguments.of(Action.LukeDestroyShip, Set.of(CardLocation.EmpireShipInPlay), Set.of()),
                Arguments.of(Action.HammerHeadAway, Set.of(CardLocation.EmpireShipInPlay, CardLocation.GalaxyRow), Set.of()),
                Arguments.of(Action.JabbaExile, Set.of(CardLocation.EmpireHand), Set.of()),
                Arguments.of(Action.ChooseStatBoost, Set.of(), Set.of(ActionSpace.ChooseStatForce,
                        ActionSpace.ChooseStatResource, ActionSpace.ChooseStatAttack, ActionSpace.DeclineAction)),
                Arguments.of(Action.ChooseResourceOrRepair, Set.of(), Set.of(ActionSpace.ChooseRepair, ActionSpace.ChooseResource,
                        ActionSpace.DeclineAction))
        );
    }

    private static Stream<Arguments> invalidPendingActionsRebel() {
        return Stream.of(
                Arguments.of(Action.DiscardFromHand, Set.of(CardLocation.RebelHand), Set.of()),
                Arguments.of(Action.PurchaseCard, Set.of(CardLocation.GalaxyRow), Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.AttackCenterRow, Set.of(CardLocation.GalaxyRow), Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.SelectAttacker, Set.of(CardLocation.RebelUnitInPlay, CardLocation.RebelShipInPlay), Set.of()),
                Arguments.of(Action.DiscardCardFromCenter, Set.of(CardLocation.GalaxyRow), Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.ExileCard, Set.of(CardLocation.RebelHand, CardLocation.RebelDiscard), Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.ReturnCardToHand, Set.of(CardLocation.RebelDiscard), Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.ChooseNextBase, Set.of(CardLocation.RebelAvailableBases), Set.of()),
                Arguments.of(Action.ChooseNextBase, Set.of(CardLocation.GalaxyRow), Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.FireWhenReady, Set.of(CardLocation.GalaxyRow, CardLocation.RebelShipInPlay),
                        Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.GalacticRule, Set.of(CardLocation.GalaxyDeck), Set.of()),
                Arguments.of(Action.ANewHope1, Set.of(CardLocation.GalaxyDiscard), Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.ANewHope2, Set.of(CardLocation.GalaxyRow), Set.of()),
                Arguments.of(Action.DurosDiscard, Set.of(CardLocation.RebelHand), Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.BWingDiscard, Set.of(CardLocation.RebelHand), Set.of(ActionSpace.DeclineAction)),
                Arguments.of(Action.JynErsoTopDeck, Set.of(CardLocation.EmpireHand), Set.of()),
                Arguments.of(Action.LukeDestroyShip, Set.of(CardLocation.EmpireShipInPlay), Set.of()),
                Arguments.of(Action.HammerHeadAway, Set.of(CardLocation.EmpireShipInPlay, CardLocation.GalaxyRow), Set.of()),
                Arguments.of(Action.JabbaExile, Set.of(CardLocation.RebelHand), Set.of()),
                Arguments.of(Action.ChooseStatBoost, Set.of(), Set.of(ActionSpace.ChooseStatForce,
                        ActionSpace.ChooseStatResource, ActionSpace.ChooseStatAttack, ActionSpace.DeclineAction)),
                Arguments.of(Action.ChooseResourceOrRepair, Set.of(), Set.of(ActionSpace.ChooseRepair, ActionSpace.ChooseResource,
                        ActionSpace.DeclineAction))
        );
    }

    private static Set<Integer> getExcludesFromCardLocations(Game game, Set<CardLocation> locations, Set<ActionSpace> spaces) {
        Set<Integer> excludes = new HashSet<>();
        for (Card card : game.getCardMap().values()) {
            if (locations.contains(card.getLocation())) {
                excludes.add(card.getId());
            }
        }
        for (ActionSpace space : spaces) {
            for (int i = space.getMinRange(); i <= space.getMaxRange(); i++) {
                excludes.add(i);
            }
        }
        return excludes;
    }
}