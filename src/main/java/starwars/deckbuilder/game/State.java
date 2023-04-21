package starwars.deckbuilder.game;

import starwars.deckbuilder.cards.common.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class State {

    public static int SIZE = 1000;
    /*
     * Game state
     */
    private List<Double> cardsInHand = buildList(80);
    private List<Double> cardsInPlay = buildList(80);
    private List<Double> selectedToAttack = buildList(80);
    private List<Double> cardsInDiscard = buildList(80);
    private List<Double> cardsInDeck = buildList(80);
    private List<Double> opponentsHand = buildList(80);
    private List<Double> opponentsDiscard = buildList(80);
    private List<Double> opponentsDeckOrHand = buildList(80);
    private List<Double> shipsInPlay = buildList(14);
    private List<Double> opponentShipsInPlay = buildList(14);
    private List<Double> topOfGalaxyDeck = buildList(90);
    private List<Double> galaxyRow = buildList(90);
    private List<Double> galaxyDiscard = buildList(90);
    private List<Double> currentBase = buildList(10);
    private List<Double> availableBases = buildList(10);
    private List<Double> forceBalance = buildList(7);
    private double playerBaseDamage;
    private double oppBaseDamage;
    private double victoryCondition;
    private double lossCondition;
    private double resources;


    /*
     * Available next actions
     */
    private double playCard;
    private double purchaseCard;
    private double useCardAbility;
    private double attackCenterRow;
    private double attackBase;
    private double selectAttacker;
    private double discardFromHand;
    private double discardCardFromCenter;
    private double exileCard;
    private double returnCardToHand;
    private double chooseNextBase;
    private double swapTopCardOfDeck;
    private double fireWhenReady;
    private double galacticRule;
    private double aNewHope1;
    private double aNewHope2;
    private double durosDiscard;
    private double bWingDiscard;
    private double jynErsoTopDeck;
    private double lukeDestoryShip;
    private double hammerHead;
    private double jabbaExile;


    private double passTurn;
    private double declineAction;
    private double chooseStats;
    private double chooseResourceOrRepair;
    private double attackNeutralCard;
    private double confirmAttackers;
    private double nextBuyFromDiscard;
    private double mustBuyNext;

    public State(Player player) {
        Faction playerFaction = player.getFaction();
        Player opponent = player.getOpponent();
        Faction opponentFaction = opponent.getFaction();
        Game game = player.getGame();
        Map<Integer, Card> cardMap = game.getCardMap();
        boolean noPendingActions = game.getPendingActions().isEmpty();

        fillCardsByLocation(cardMap, CardLocation.getHand(player.getFaction()),
                List.of(CardMapping.getPlayableCards(player.getFaction()), CardMapping.NeutralPlayableCards), cardsInHand);
        fillCardsByLocation(cardMap, Set.of(CardLocation.getUnitsInPlay(playerFaction), CardLocation.getShipsInPlay(playerFaction)),
                List.of(CardMapping.getPlayableCards(playerFaction), CardMapping.NeutralPlayableCards), cardsInPlay,
                (card -> {
                    boolean canAttack = false;
                    boolean activeAbility = false;
                    if (card instanceof PlayableCard) {
                        canAttack = ((PlayableCard) card).canAttack();
                    }
                    if (card instanceof HasAbility) {
                        activeAbility = ((HasAbility) card).abilityActive();
                    }
                    return canAttack && activeAbility ? 1.0 :
                            canAttack ? 0.75 :
                            activeAbility ? 0.5 : 0.25;
                }));
        fillCardsByLocation(cardMap, Set.of(CardLocation.getUnitsInPlay(playerFaction), CardLocation.getShipsInPlay(playerFaction)),
                List.of(CardMapping.getPlayableCards(playerFaction), CardMapping.NeutralPlayableCards), selectedToAttack,
                (card -> {
                    if (card instanceof PlayableCard) {
                        PlayableCard playableCard = (PlayableCard) card;
                        return game.getAttackers().contains(playableCard) ? 1.0 : 0.0;
                    }
                    return 0.0;
                }));
        fillCardsByLocation(cardMap, CardLocation.getDiscard(playerFaction),
                List.of(CardMapping.getPlayableCards(playerFaction), CardMapping.NeutralPlayableCards), cardsInDiscard);
        fillCardsByLocation(cardMap, CardLocation.getDeck(playerFaction),
                List.of(CardMapping.getPlayableCards(playerFaction), CardMapping.NeutralPlayableCards), cardsInDeck);
        if (game.canSeeOpponentsHand()) {
            fillCardsByLocation(cardMap, CardLocation.getHand(opponentFaction),
                    List.of(CardMapping.getPlayableCards(opponentFaction), CardMapping.NeutralPlayableCards), opponentsHand);
        }
        fillCardsByLocation(cardMap, CardLocation.getDiscard(opponentFaction),
                List.of(CardMapping.getPlayableCards(opponentFaction), CardMapping.NeutralPlayableCards), opponentsDiscard);
        fillCardsByLocation(cardMap, Set.of(CardLocation.getHand(opponentFaction),CardLocation.getDeck(opponentFaction)),
                List.of(CardMapping.getPlayableCards(opponentFaction), CardMapping.NeutralPlayableCards), opponentsDeckOrHand);
        fillCardsByLocation(cardMap, CardLocation.getShipsInPlay(playerFaction),
                List.of(CardMapping.getShipCards(playerFaction), CardMapping.NeutralShips), shipsInPlay, (card) -> {
                    if (card instanceof CapitalShip) {
                        return ((CapitalShip) card).getFlattenedHealth();
                    }
                    return 0.0;
        });
        fillCardsByLocation(cardMap, CardLocation.getShipsInPlay(opponentFaction),
                List.of(CardMapping.getShipCards(opponentFaction), CardMapping.NeutralShips), opponentShipsInPlay, (card) -> {
                    if (card instanceof CapitalShip) {
                        return ((CapitalShip) card).getFlattenedHealth();
                    }
                    return 0.0;
        });
        int numCardsKnown = game.getKnowsTopCardOfDeck().get(playerFaction);
        if (numCardsKnown > 0) {
            List<PlayableCard> topCards = game.getGalaxyDeck().subList(0, numCardsKnown);
            fillCardsByLocation(cardMap, CardLocation.GalaxyDeck, List.of(CardMapping.EmpireGalaxyCards,
                            CardMapping.RebelGalaxyCards, CardMapping.NeutralGalaxyCards), topOfGalaxyDeck,
                    (card -> {
                        if (card instanceof PlayableCard && topCards.contains((PlayableCard) card)) {
                            return 1.0;
                        }
                        return 0.0;
                    }));
        }
        fillCardsByLocation(cardMap, CardLocation.GalaxyRow, List.of(CardMapping.EmpireGalaxyCards,
                CardMapping.RebelGalaxyCards, CardMapping.NeutralGalaxyCards), galaxyRow);
        fillCardsByLocation(cardMap, CardLocation.GalaxyDiscard, List.of(CardMapping.EmpireGalaxyCards,
                CardMapping.RebelGalaxyCards, CardMapping.NeutralGalaxyCards), galaxyDiscard);
        fillCardsByLocation(cardMap, CardLocation.getCurrentBase(playerFaction),
                List.of(CardMapping.getBases(playerFaction)), currentBase);
        fillCardsByLocation(cardMap, CardLocation.getAvailableBases(playerFaction),
                List.of(CardMapping.getBases(playerFaction)), availableBases);
        forceBalance.set(game.getForceBalance().getPosition(), 1.0);


        playerBaseDamage = flattenBaseDamage(player.getCurrentBase());
        oppBaseDamage = flattenBaseDamage(opponent.getCurrentBase(), true);
        victoryCondition = flattenVictoryCondition(player);
        lossCondition = flattenVictoryCondition(opponent, true);
        resources = flattenResources(player.getResources());

        playCard = noPendingActions && !player.getHand().isEmpty() ? 1.0 : 0.0;
        purchaseCard = noPendingActions ? 1.0 : game.getPendingActions().get(0).getAction() == Action.PurchaseCard ? 1.0 : 0.0;
        useCardAbility = noPendingActions ? 1.0 : 0.0;
        attackCenterRow = noPendingActions ? 1.0 : game.getPendingActions().get(0).getAction() == Action.AttackCenterRow ? 1.0 : 0.0;
        attackBase = noPendingActions ? 1.0 : 0.0;
        selectAttacker = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.SelectAttacker
                && player.getAvailableAttack() > 0 ? 1.0 : 0.0;
        discardFromHand = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.DiscardFromHand ? 1.0 : 0.0;
        discardCardFromCenter = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.DiscardCardFromCenter ? 1.0 : 0.0;
        exileCard = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.ExileCard ? 1.0 : 0.0;
        returnCardToHand = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.ReturnCardToHand ? 1.0 : 0.0;
        chooseNextBase = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.ChooseNextBase ? 1.0 : 0.0;
        swapTopCardOfDeck = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.SwapTopCardOfDeck ? 1.0 : 0.0;
        fireWhenReady = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.FireWhenReady ? 1.0 : 0.0;
        galacticRule = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.GalacticRule ? 1.0 : 0.0;
        aNewHope1 = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.ANewHope1 ? 1.0 : 0.0;
        aNewHope2 = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.ANewHope2 ? 1.0 : 0.0;
        durosDiscard = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.DurosDiscard ? 1.0 : 0.0;
        bWingDiscard = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.BWingDiscard ? 1.0 : 0.0;
        jynErsoTopDeck = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.JynErsoTopDeck ? 1.0 : 0.0;
        lukeDestoryShip = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.LukeDestroyShip ? 1.0 : 0.0;
        hammerHead = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.HammerHeadAway ? 1.0 : 0.0;
        jabbaExile = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.JabbaExile ? 1.0 : 0.0;

        passTurn = noPendingActions ? 1.0 : 0.0;
        declineAction = !noPendingActions && game.getPendingActions().get(0).getAction().isDeclinable() ? 1.0 : 0.0;
        chooseStats = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.ChooseStatBoost ? 1.0 : 0.0;
        chooseResourceOrRepair = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.ChooseResourceOrRepair ? 1.0 : 0.0;
        attackNeutralCard = noPendingActions && game.getStaticEffects().contains(StaticEffect.CanBountyOneNeutral) &&
                game.getGalaxyRow().stream().anyMatch(pc -> pc instanceof Unit && pc.getFaction() == Faction.neutral)? 1.0 : 0.0;
        confirmAttackers = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.SelectAttacker &&
                !game.getAttackers().isEmpty() && game.getAttackTarget() != null ? 1.0 : 0.0;
        nextBuyFromDiscard = purchaseCard == 1.0 && game.getStaticEffects().contains(StaticEffect.PurchaseFromDiscard) ? 1.0 : 0.0;
        mustBuyNext = !noPendingActions && game.getPendingActions().get(0).getAction() == Action.PurchaseCard ?
                game.getStaticEffects().contains(StaticEffect.NextFactionOrNeutralPurchaseIsFree) ? 0.5 :
                        game.getStaticEffects().contains(StaticEffect.NextFactionPurchaseIsFree) ? 1.0 : 0.0 : 0.0;

    }

    public double[] buildInputs() {
        List<Double> combinedList = new ArrayList<>();
        combinedList.addAll(cardsInHand);
        combinedList.addAll(cardsInPlay);
        combinedList.addAll(selectedToAttack);
        combinedList.addAll(cardsInDiscard);
        combinedList.addAll(cardsInDeck);
        combinedList.addAll(opponentsHand);
        combinedList.addAll(opponentsDiscard);
        combinedList.addAll(opponentsDeckOrHand);
        combinedList.addAll(shipsInPlay);
        combinedList.addAll(opponentShipsInPlay);
        combinedList.addAll(topOfGalaxyDeck);
        combinedList.addAll(galaxyRow);
        combinedList.addAll(galaxyDiscard);
        combinedList.addAll(currentBase);
        combinedList.addAll(availableBases);
        combinedList.addAll(forceBalance);
        combinedList.add(playerBaseDamage);
        combinedList.add(oppBaseDamage);
        combinedList.add(victoryCondition);
        combinedList.add(lossCondition);
        combinedList.add(resources);
        combinedList.add(playCard);
        combinedList.add(purchaseCard);
        combinedList.add(useCardAbility);
        combinedList.add(attackCenterRow);
        combinedList.add(attackBase);
        combinedList.add(selectAttacker);
        combinedList.add(discardFromHand);
        combinedList.add(discardCardFromCenter);
        combinedList.add(exileCard);
        combinedList.add(returnCardToHand);
        combinedList.add(chooseNextBase);
        combinedList.add(swapTopCardOfDeck);
        combinedList.add(fireWhenReady);
        combinedList.add(galacticRule);
        combinedList.add(aNewHope1);
        combinedList.add(aNewHope2);
        combinedList.add(durosDiscard);
        combinedList.add(bWingDiscard);
        combinedList.add(jynErsoTopDeck);
        combinedList.add(lukeDestoryShip);
        combinedList.add(hammerHead);
        combinedList.add(jabbaExile);
        combinedList.add(passTurn);
        combinedList.add(declineAction);
        combinedList.add(chooseStats);
        combinedList.add(chooseResourceOrRepair);
        combinedList.add(attackNeutralCard);
        combinedList.add(confirmAttackers);
        combinedList.add(nextBuyFromDiscard);
        combinedList.add(mustBuyNext);
        return combinedList.stream().mapToDouble(Double::doubleValue).toArray();
    }

    private void fillCardsByLocation(Map<Integer, Card> cardMap, CardLocation location, List<CardMapping> mappings, List<Double> list) {
        fillCardsByLocation(cardMap, Set.of(location), mappings, list);
    }

    private void fillCardsByLocation(Map<Integer, Card> cardMap, CardLocation location, List<CardMapping> mappings, List<Double> list, Function<Card, Double> valueFunc) {
        fillCardsByLocation(cardMap, Set.of(location), mappings, list, valueFunc);
    }

    private void fillCardsByLocation(Map<Integer, Card> cardMap, Set<CardLocation> locations, List<CardMapping> mappings, List<Double> list) {
        fillCardsByLocation(cardMap, locations, mappings, list, (card) -> 1.0);
    }
    private void fillCardsByLocation(Map<Integer, Card> cardMap, Set<CardLocation> locations, List<CardMapping> mappings, List<Double> list, Function<Card, Double> valueFunc) {
        AtomicInteger index = new AtomicInteger();
        for (CardMapping mapping : mappings) {
            fillList(index, mapping.getMinRange(), mapping.getMaxRange(), locations, cardMap, list, valueFunc);
        }
    }

    private void fillList(AtomicInteger index, int start, int end, Set<CardLocation> locations, Map<Integer, Card> cardMap, List<Double> list, Function<Card, Double> valueFunc) {
        for (int i = start; i < end; i++) {
            Card card = cardMap.get(i);
            int currentIndex = index.getAndIncrement();
            if (locations.contains(card.getLocation())) {
                list.set(currentIndex, valueFunc.apply(card));
            }
        }
    }

    private double flattenBaseDamage(Base base) {
        return flattenBaseDamage(base, false);
    }
    private double flattenBaseDamage(Base base, boolean invert) {
        if (base == null) {
            return invert ? 1.0 : 0.0;
        }
        double flat = base.getRemainingHealth() / base.getHitPoints();
        return invert ? 1.0 - flat : flat;
    }

    private double flattenVictoryCondition(Player player) {
        return flattenVictoryCondition(player, false);
    }
    private double flattenVictoryCondition(Player player, boolean invert) {
        double flat = player.getDestroyedBases().size() / 4;
        return invert ? 1.0 - flat : flat;
    }

    private double flattenResources(int resources) {
        if (resources > 30) {
            return 1.0;
        }
        return 1.0 - ((30 - resources) / 30);
    }

    private static List<Double> buildList(int size) {
        return buildList(size, 0.0);
    }

    private static List<Double> buildList(int size, double value) {
        List<Double> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(value);
        }
        return list;
    }
}
