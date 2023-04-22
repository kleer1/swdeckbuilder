package sw.db.game;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import sw.db.cards.common.models.*;
import sw.db.cards.empire.base.Coruscant;
import sw.db.cards.empire.base.DeathStar;
import sw.db.network.GameState;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Slf4j
public class Game {
    public static final double INVALID_ACTION_REWARD = -10.0;

    private static final Set<Action> PUNISH_IF_PASS_ACTIONS = Set.of(
            Action.AttackBase,
            Action.AttackCenterRow,
            Action.AttackNeutralCard,
            Action.PlayCard);

    private Player empire;
    private Player rebel;
    private ForceBalance forceBalance;
    private List<PlayableCard> galaxyDeck;
    private List<PlayableCard> galaxyRow;
    private List<PlayableCard> galaxyDiscard;
    private List<PlayableCard> outerRimPilots;
    private List<PlayableCard> exiledCards;
    private Map<Integer, Card> cardMap;
    private Faction currentPlayersAction = Faction.empire;
    private Faction currentPlayersTurn = Faction.empire;
    private List<StaticEffect> staticEffects;
    private List<PendingAction> pendingActions;
    private GameState gameState;
    private Card lastCardPlayed;
    private Card lastCardActivated;
    private double reward = 0.0;
    private final Set<Faction> readyPlayers = Collections.synchronizedSet(new HashSet<>());
    private Map<Faction, Integer> knowsTopCardOfDeck;
    private List<PlayableCard> attackers = new ArrayList<>();
    private Card attackTarget;
    @Getter(AccessLevel.NONE)
    private boolean canSeeOpponentsHand;
    private List<PlayableCard> exileAtEndOfTurn;
    private PlayableCard aNewHope1;
    private final Object lock = new Object();
    private Set<Integer> availableActions;
    private final AtomicBoolean isGameOver = new AtomicBoolean(true);

    public synchronized void initialize() {
        initialize(Faction.empire);
        initialize(Faction.rebellion);
    }

    public boolean isInProgress() {
        return !isGameOver.get();
    }

    public synchronized void initialize(Faction faction) {
        synchronized (lock) {
            readyPlayers.add(faction);
            if (readyPlayers.size() < 2) {
                return;
            }
        }
        isGameOver.set(false);
        log.info("Initializing game");
        forceBalance = new ForceBalance();
        galaxyDeck = new ArrayList<>();
        galaxyRow = new ArrayList<>();
        galaxyDiscard = new ArrayList<>();
        outerRimPilots = new ArrayList<>();
        exiledCards = new ArrayList<>();
        cardMap = new HashMap<>();
        staticEffects = new ArrayList<>();
        forgetTopCardOfDeck();

        empire = new Player(Faction.empire);
        rebel = new Player(Faction.rebellion);
        empire.setOpponent(rebel);
        rebel.setOpponent(empire);
        empire.setGame(this);
        rebel.setGame(this);
        cardMap = SetupUtils.setup(this);
        for (int i = 0; i < 6; i++) {
            drawGalaxyCard();
        }
        empire.drawCards(5);
        rebel.drawCards(5);

        currentPlayersAction = Faction.empire;
        currentPlayersTurn = Faction.empire;
        pendingActions = new ArrayList<>();
        lastCardPlayed = null;
        lastCardActivated = null;
        reward = 0.0;
        attackers = new ArrayList<>();
        attackTarget = null;
        canSeeOpponentsHand = false;
        exileAtEndOfTurn = new ArrayList<>();
        aNewHope1 = null;
        gameState = buildGameState(currentPlayersAction);
        availableActions = internalGetAvailableAction();
    }

    public synchronized boolean isWaitingOnPlayer() {
        return readyPlayers.size() != 2;
    }

    public Set<Integer> getAvailableActions() {
        if (availableActions.size() > 1 && availableActions.contains(ActionSpace.PassTurn.getMinRange())) {
            return availableActions.stream().filter(i -> i != ActionSpace.PassTurn.getMinRange()).collect(Collectors.toSet());
        }
        return availableActions;
    }

    private synchronized Set<Integer> internalGetAvailableAction() {
        return Stream.iterate(0, i -> i + 1)
                .limit(ActionSpace.SIZE)
                .filter(i -> {
                    ActionSpace actionSpace = ActionSpace.getActionSpaceByIndex(i);
                    Card card = null;
                    if (actionSpace == ActionSpace.CardAction) {
                        card = cardMap.get(i);
                        if (card == null) {
                            return false;
                        }
                    }

                    Player currentPlayer = currentPlayersAction == Faction.empire ? empire : rebel;
                    Action action = determineAction(actionSpace, card, currentPlayer);
                    if (action == null) {
                        return false;
                    }
                    return isValidAction(actionSpace, action, card, currentPlayer);
                }).collect(Collectors.toSet());
    }

    public Player getCurrentPlayer() {
        return currentPlayersTurn == Faction.empire ? empire : rebel;
    }

    public void revealOpponentsHand() {
        canSeeOpponentsHand = true;
    }

    public boolean canSeeOpponentsHand() {
        return canSeeOpponentsHand;
    }

    private void passCurrentAction() {
        currentPlayersAction = currentPlayersAction == Faction.empire ? Faction.rebellion : Faction.empire;
    }

    public boolean isFactionsAction(Faction faction) {
        synchronized (lock) {
            return currentPlayersAction == faction;
        }
    }

    public synchronized GameState getGameState() {
        return buildGameState(currentPlayersAction);
    }

    private GameState buildGameState(Faction faction) {
        State state = new State(faction == Faction.empire ? empire : rebel);
        return new GameState(state.buildInputs());
    }

    public void applyAction(final int actionIndex) {
        synchronized (lock) {
            internalApplyAction(actionIndex);
        }
    }

    private void internalApplyAction(final int actionIndex) {
        reward = 0.0;
        if (isGameComplete()) {
            reward = -100;
            isGameOver.set(true);
            return;
        }
        availableActions = internalGetAvailableAction();
        if (!availableActions.contains(actionIndex)) {
            reward = INVALID_ACTION_REWARD;
            log.warn(currentPlayersAction + " picked invalid action " + actionIndex);
            return;
        }
        RewardCalculator rewardCalculator = new RewardCalculator(this);

        ActionSpace actionSpace = ActionSpace.getActionSpaceByIndex(actionIndex);
        Card card = null;
        if (actionSpace == ActionSpace.CardAction) {
            card = cardMap.get(actionIndex);
        }

        Player currentPlayer = getCurrentPlayer();
        Action action = determineAction(actionSpace, card, currentPlayer);
        PlayableCard playableCard = null;
        Base base = null;
        if (card instanceof PlayableCard) {
            playableCard = (PlayableCard) card;
        } else if (card instanceof Base) {
            base = (Base) card;
        }
        boolean isPendingAction = !pendingActions.isEmpty();
        boolean endedTurn = false;
        log.info("Valid " + action + " -> " + (card != null ? card : actionIndex) + " before state:\n" + currentPlayer);
        switch (Objects.requireNonNull(action)) {
            case PlayCard -> {
                if (staticEffects.contains(StaticEffect.DrawOnFirstNeutralCard)) {
                    assert card != null;
                    if (card.getFaction() == Faction.neutral) {
                        currentPlayer.drawCards(1);
                        staticEffects.removeAll(List.of(StaticEffect.DrawOnFirstNeutralCard));
                    }
                }
                assert playableCard != null;
                playableCard.moveToInPlay();
                if (card instanceof HasOnPlayAction) {
                    pendingActions.addAll(((HasOnPlayAction) card).getActions());
                }
                lastCardPlayed = card;
                reward = 1.0;
            }
            case PurchaseCard -> {
                assert card != null;
                assert playableCard != null;
                if (card.getLocation() == CardLocation.GalaxyRow) {
                    drawGalaxyCard();
                }
                if (staticEffects.contains(StaticEffect.BuyNextToTopOfDeck)) {
                    playableCard.buyToTopOfDeck(currentPlayer);
                } else if (staticEffects.contains(StaticEffect.BuyNextToHand)) {
                    playableCard.buyToHand(currentPlayer);
                } else if (staticEffects.contains(StaticEffect.BuyNextNeutralToHand) && card.getFaction() == Faction.neutral) {
                    playableCard.buyToHand(currentPlayer);
                } else {
                    playableCard.buy(currentPlayer);
                }
                staticEffects.removeAll(List.of(StaticEffect.BuyNextToTopOfDeck, StaticEffect.BuyNextToHand));
                if (playableCard.getFaction() == Faction.neutral) {
                    staticEffects.remove(StaticEffect.BuyNextNeutralToHand);
                }
                if (!staticEffects.contains(StaticEffect.NextFactionPurchaseIsFree) && !staticEffects.contains(StaticEffect.NextFactionOrNeutralPurchaseIsFree)) {
                    currentPlayer.addResources(-playableCard.getCost());
                } else {
                    staticEffects.removeAll(List.of(StaticEffect.NextFactionPurchaseIsFree, StaticEffect.NextFactionOrNeutralPurchaseIsFree));
                }
                if (staticEffects.contains(StaticEffect.ExileNextFactionPurchase) && card.getFaction() == currentPlayersAction) {
                    staticEffects.removeAll(List.of(StaticEffect.ExileNextFactionPurchase));
                    exileAtEndOfTurn.add(playableCard);
                } else if (staticEffects.contains(StaticEffect.ExileNextNeutralPurchase) && card.getFaction() == Faction.neutral) {
                    staticEffects.removeAll(List.of(StaticEffect.ExileNextNeutralPurchase));
                    exileAtEndOfTurn.add(playableCard);
                }
                if (staticEffects.contains(StaticEffect.PurchaseFromDiscard)) {
                    staticEffects.removeAll(List.of(StaticEffect.PurchaseFromDiscard));
                }
                if (playableCard instanceof PurchaseAction) {
                    ((PurchaseAction) playableCard).applyPurchaseAction();
                }
                reward = playableCard.getCost();
            }
            case UseCardAbility -> {
                assert card != null;
                ((HasAbility) card).applyAbility();
                lastCardActivated = card;
                reward = 1.0;
            }
            case AttackCenterRow, AttackBase -> {
                attackTarget = card;
                pendingActions.add(PendingAction.of(Action.SelectAttacker));
                reward = 1.0;
            }
            case SelectAttacker -> {
                assert playableCard != null;
                playableCard.setAttacked();
                attackers.add(playableCard);
                pendingActions.add(PendingAction.of(Action.SelectAttacker));
                reward = 1.0;
            }
            case DiscardFromHand, DurosDiscard, BWingDiscard -> {
                assert playableCard != null;
                playableCard.moveToDiscard();
                reward = 4 - playableCard.getCost();
                if (staticEffects.contains(StaticEffect.Yavin4Effect) && currentPlayersAction == Faction.empire
                        && empire.getCurrentBase() != null) {
                    empire.getCurrentBase().addDamage(2);
                }
            }
            case DiscardCardFromCenter -> {
                assert playableCard != null;
                playableCard.moveToGalaxyDiscard();
                drawGalaxyCard();
                if (card.getFaction() == currentPlayer.getOpponent().getFaction()) {
                    reward = playableCard.getCost();
                }
            }
            case ExileCard, JabbaExile -> {
                assert playableCard != null;
                playableCard.moveToExile();
                if (playableCard.getCost() == 0) {
                    reward = 5.0;
                }
            }
            case ReturnCardToHand -> {
                assert playableCard != null;
                playableCard.moveToHand();
                reward += playableCard.getCost();
            }
            case ChooseNextBase -> {
                assert base != null;
                base.makeCurrentBase();
                if (base instanceof HasOnReveal) {
                    ((HasOnReveal) base).applyOnReveal();
                }
                reward = 1;
            }
            case SwapTopCardOfDeck -> {
                galaxyDeck.get(0).moveToGalaxyRow();
                assert playableCard != null;
                playableCard.moveToTopOfGalaxyDeck();
                revealTopCardOfDeck();
            }
            case FireWhenReady -> {
                assert playableCard != null;
                currentPlayer.addResources(-4);
                if (card.getLocation() == CardLocation.GalaxyRow) {
                    playableCard.moveToGalaxyDiscard();
                    drawGalaxyCard();
                    if (card.getFaction() == currentPlayer.getOpponent().getFaction()) {
                        reward = playableCard.getCost();
                    }
                } else {
                    playableCard.moveToDiscard();
                    reward = playableCard.getCost();
                }
            }
            case GalacticRule -> {
                assert playableCard != null;
                playableCard.moveToGalaxyDiscard();
                knowsTopCardOfDeck.put(Faction.empire, 1);
            }
            case ANewHope1 -> {
                aNewHope1 = playableCard;
                pendingActions.add(PendingAction.of(Action.ANewHope2));
            }
            case ANewHope2 -> {
                assert playableCard != null;
                playableCard.moveToGalaxyDiscard();
                aNewHope1.moveToGalaxyRow();
                aNewHope1 = null;
                rebel.addResources(1);
            }
            case JynErsoTopDeck -> {
                assert playableCard != null;
                playableCard.moveToTopOfDeck();
                revealTopCardOfDeck();
                reward = playableCard.getCost();
            }
            case LukeDestroyShip -> {
                assert playableCard != null;
                playableCard.moveToDiscard();
            }
            case HammerHeadAway -> {
                assert playableCard != null;
                if (playableCard.getLocation() == CardLocation.GalaxyRow) {
                    playableCard.moveToGalaxyDiscard();
                    drawGalaxyCard();
                } else {
                    playableCard.moveToDiscard();
                }
            }
            case PassTurn -> {
                if (availableActions.size() > 1 && !Collections.disjoint(availableActions, PUNISH_IF_PASS_ACTIONS)) {
                    log.warn(currentPlayer.getFaction() + " passed while still able to attack or play cards. " + availableActions);
                    reward = INVALID_ACTION_REWARD;
                }
                endedTurn = true;
            }
            case DeclineAction -> {
                // Do Nothing
            }
            case ChooseStatBoost -> {
                if (lastCardPlayed instanceof HasChooseStats) {
                    ((HasChooseStats) lastCardPlayed).applyChoice(actionSpace.getStats());
                    reward = 1.0;
                }
            }
            case ChooseResourceOrRepair -> {
                if (lastCardActivated instanceof HasChooseResourceOrRepair) {
                    ((HasChooseResourceOrRepair) lastCardActivated).applyChoice(actionSpace.getResourceOrRepair());
                    reward = 1.0;
                }
            }
            case AttackNeutralCard -> pendingActions.add(PendingAction.of(Action.AttackCenterRow));
            case ConfirmAttackers -> {
                if (attackTarget instanceof PlayableCard playableCardAttackTarget) {
                    // Attack center row
                    if (playableCardAttackTarget instanceof IsTargetable) {
                        ((IsTargetable) playableCardAttackTarget).applyReward();
                    } else {
                        currentPlayer.addResources(playableCardAttackTarget.getCost());
                        staticEffects.remove(StaticEffect.CanBountyOneNeutral);
                    }
                    attackers.forEach(a -> {
                        if (a instanceof IsBountyHunter) {
                            ((IsBountyHunter) a).receiveBounty();
                        }
                    });
                    playableCardAttackTarget.moveToGalaxyDiscard();
                    drawGalaxyCard();
                } else if (attackTarget instanceof Base) {
                    // Attack base
                    int totalAttack = attackers.stream().mapToInt(PlayableCard::getAttack).sum();
                    assignDamageToBase(totalAttack, currentPlayer.getOpponent());
                }
                attackTarget = null;
                attackers = new ArrayList<>();
            }
        }
        reward += rewardCalculator.determineReward(this);

        if (isGameComplete()) {
            reward += 100;
            passCurrentAction();
            return;
        }

        log.info("Post " + action + " -> " + (card != null ? card : actionIndex) + " state: reward=" + reward + "\n" + currentPlayer);
        if (endedTurn) {
            endTurn(currentPlayer);
            startTurn(currentPlayer.getOpponent());
        }


        if (isPendingAction) {
            PendingAction pendingAction = pendingActions.remove(0);
            if (action != Action.DeclineAction) {
                pendingAction.executeCallback();
            }
            if (pendingAction.shouldPassAction()) {
                passCurrentAction();
            }
        }

        if (!pendingActions.isEmpty() && pendingActions.get(0).shouldPassAction()) {
            passCurrentAction();
        }
        availableActions = internalGetAvailableAction();
    }

    private Action determineAction(ActionSpace actionSpace, Card card, Player player) {
        switch (actionSpace) {
            case CardAction -> {
                if (card == null) {
                    return null;
                }
                if (!pendingActions.isEmpty()) {
                    Action expectedNextAction = pendingActions.get(0).getAction();
                    if (Action.getPendingActionsByLocation(card.getLocation()).contains(expectedNextAction)) {
                        return expectedNextAction;
                    }
                } else {
                    Set<Action> possibleActions = Action.getDefaultActionsByLocation(card.getLocation());
                    if (possibleActions.size() == 1) {
                        return possibleActions.iterator().next();
                    }
                    // Currently only dupe is for galaxy row. Decide attack or buy by faction.
                    if (card.getLocation() == CardLocation.GalaxyRow) {
                        if (card.getFaction() == player.getFaction() || card.getFaction() == Faction.neutral) {
                            return Action.PurchaseCard;
                        } else {
                            return Action.AttackCenterRow;
                        }
                    }
                    // found another dupe for DeathStar
                    if (card instanceof Base) {
                        return card.getFaction() == currentPlayersAction ? Action.UseCardAbility : Action.AttackBase;
                    }
                }
            }
            case PassTurn -> {
                return Action.PassTurn;
            }
            case DeclineAction -> {
                return Action.DeclineAction;
            }
            case ChooseStatAttack, ChooseStatResource, ChooseStatForce -> {
                return Action.ChooseStatBoost;
            }
            case ChooseResource, ChooseRepair -> {
                return Action.ChooseResourceOrRepair;
            }
            case AttackNeutralCard -> {
                return Action.AttackNeutralCard;
            }
            case ConfirmAttackers -> {
                return Action.ConfirmAttackers;
            }
        }
        return null;
    }

    private boolean isValidAction(ActionSpace actionSpace,Action action, Card card, Player currentPlayer) {
        if (!pendingActions.isEmpty()) {
            Action pendingAction = pendingActions.get(0).getAction();
            boolean actionMatchesPending = action == pendingAction;
            boolean pendingActionIsDeclinable = pendingAction.isDeclinable() && action == Action.DeclineAction;
            boolean confirmingAttackers = pendingAction == Action.SelectAttacker && action == Action.ConfirmAttackers;
            if (!actionMatchesPending && ! pendingActionIsDeclinable && ! confirmingAttackers) {
                return false;
            }
        }
        switch (action) {
            case PlayCard -> {
                return canPlayCard(card, currentPlayer);
            }
            case PurchaseCard -> {
                return canPurchaseCard(card, currentPlayer);
            }
            case UseCardAbility -> {
                return canUseCardAbility(card, currentPlayer);
            }
            case AttackCenterRow -> {
                // temporarily set target for cards that care about it
                attackTarget = card;
                boolean canAttack = canAttackCardInCenter(card, currentPlayer);
                attackTarget = null;
                return canAttack;
            }
            case AttackBase -> {
                return currentPlayer.getAvailableAttack() > 0 &&
                        (currentPlayer.getOpponent().getCurrentBase() != null ||
                                !currentPlayer.getOpponent().getShipsInPlay().isEmpty());
            }
            case SelectAttacker -> {
                if (!(card instanceof PlayableCard playableCard)) {
                    return false;
                }
                if (!playableCard.canAttack()) {
                    return false;
                }
                return card.getLocation() == CardLocation.getUnitsInPlay(currentPlayer.getFaction()) ||
                        card.getLocation() == CardLocation.getShipsInPlay(currentPlayer.getFaction());
            }
            case DiscardFromHand, DurosDiscard, BWingDiscard -> {
                return canDiscardFromHand(card, currentPlayer);
            }
            case DiscardCardFromCenter -> {
                return canDiscardFromCenter(card);
            }
            case ExileCard -> {
                return canExileCard(card, currentPlayer);
            }
            case ReturnCardToHand -> {
                return canReturnCardToHand(card, currentPlayer);
            }
            case ChooseNextBase -> {
                return canChooseNewBase(card, currentPlayer);
            }
            case SwapTopCardOfDeck -> {
                return canSwapTopCardOfDeck(card);
            }
            case FireWhenReady -> {
                return canFireWhenReady(card, currentPlayer);
            }
            case GalacticRule -> {
                return canGalacticRule(card, currentPlayer);
            }
            case ANewHope1 -> {
                return !pendingActions.isEmpty() && pendingActions.get(0).getAction() == Action.ANewHope1 &&
                        card.getLocation() == CardLocation.GalaxyDiscard && card instanceof PlayableCard &&
                        card.getFaction() == Faction.rebellion;
            }
            case ANewHope2 -> {
                return !pendingActions.isEmpty() && pendingActions.get(0).getAction() == Action.ANewHope2 &&
                        aNewHope1 != null && card.getLocation() == CardLocation.GalaxyRow && card instanceof PlayableCard;
            }
            case JynErsoTopDeck -> {
                return !pendingActions.isEmpty() && pendingActions.get(0).getAction() == Action.JynErsoTopDeck &&
                        currentPlayersAction == Faction.rebellion && card.getLocation() == CardLocation.EmpireHand && card instanceof PlayableCard;
            }
            case LukeDestroyShip -> {
                return !pendingActions.isEmpty() && pendingActions.get(0).getAction() == Action.LukeDestroyShip &&
                        currentPlayersAction == Faction.rebellion && card.getLocation() == CardLocation.EmpireShipInPlay && card instanceof CapitalShip;
            }
            case HammerHeadAway -> {
                return canHammerHeadAway(card, currentPlayer);
            }
            case JabbaExile -> {
                return canExileCardFromHand(card, currentPlayer);
            }
            case PassTurn -> {
                return currentPlayer.getFaction() == currentPlayersTurn;
            }
            case DeclineAction -> {
                return !pendingActions.isEmpty() && pendingActions.get(0).getAction().isDeclinable();
            }
            case ChooseStatBoost -> {
                return !pendingActions.isEmpty() && pendingActions.get(0).getAction() == Action.ChooseStatBoost &&
                        actionSpace.getStats() != null;
            }
            case ChooseResourceOrRepair -> {
                return canChooseResourceOrRepair(actionSpace, currentPlayer);
            }
            case AttackNeutralCard -> {
                if (!pendingActions.isEmpty() || !staticEffects.contains(StaticEffect.CanBountyOneNeutral)) {
                    return false;
                }
                return galaxyRow.stream().anyMatch(pc -> pc instanceof Unit && pc.getFaction() == Faction.neutral &&
                        pc.getCost() <= currentPlayer.getAvailableAttack());
            }
            case ConfirmAttackers -> {
                return canConfirmAttackers();
            }

        }
        return false;
    }

    private boolean canPlayCard(Card card, Player player) {
        return card.getLocation() == CardLocation.getHand(player.getFaction());
    }

    private boolean canPurchaseCard(Card card, Player player) {
        if (card.getFaction() != player.getFaction() && card.getFaction() != Faction.neutral) {
            return false;
        }

        if (staticEffects.contains(StaticEffect.NextFactionPurchaseIsFree) && card.getFaction() != player.getFaction()) {
            return false;
        }
        if (staticEffects.contains(StaticEffect.NextFactionOrNeutralPurchaseIsFree) &&
                card.getFaction() != currentPlayersAction && card.getFaction() != Faction.neutral) {
            return false;
        }
        if (!(card instanceof PlayableCard playableCard)) {
            return false;
        }

        if (!staticEffects.contains(StaticEffect.NextFactionPurchaseIsFree) &&
                !staticEffects.contains(StaticEffect.NextFactionOrNeutralPurchaseIsFree)
                && player.getResources() < playableCard.getCost()) {
            return false;
        }

        if (staticEffects.contains(StaticEffect.PurchaseFromDiscard)) {
            return card.getLocation() == CardLocation.GalaxyDiscard;
        }
        return card.getLocation() == CardLocation.GalaxyRow || card.getLocation() == CardLocation.OuterRimPilots;
    }

    private boolean canUseCardAbility(Card card, Player player) {
        if (card.getLocation() != CardLocation.getShipsInPlay(player.getFaction()) &&
                card.getLocation() != CardLocation.getUnitsInPlay(player.getFaction()) &&
                card.getLocation() != CardLocation.getCurrentBase(player.getFaction())) {
            return false;
        }
        if (!(card instanceof HasAbility)) {
            return false;
        }
        return ((HasAbility) card).abilityActive();
    }

    private boolean canAttackCardInCenter(Card card, Player player) {
        if (card.getLocation() != CardLocation.GalaxyRow) {
            return false;
        }
        if (player.getFaction() == card.getFaction()) {
            return false;
        }
        if (card instanceof IsTargetable && ((IsTargetable) card).getTargetValue() > player.getAvailableAttack()) {
            return false;
        }
        if (card.getFaction() == Faction.neutral && ((PlayableCard) card).getCost() > player.getAvailableAttack()) {
            return false;
        }
        return card instanceof IsTargetable || (staticEffects.contains(StaticEffect.CanBountyOneNeutral) && card.getFaction() == Faction.neutral);
    }

    private boolean canDiscardFromHand(Card card, Player player) {
        Set<Action> discardActions = Set.of(Action.BWingDiscard, Action.DiscardFromHand, Action.DurosDiscard);
        return !pendingActions.isEmpty() && discardActions.contains(pendingActions.get(0).getAction()) &&
                card.getLocation() == CardLocation.getHand(player.getFaction());
    }

    private boolean canDiscardFromCenter(Card card) {
        return !pendingActions.isEmpty() && pendingActions.get(0).getAction() == Action.DiscardCardFromCenter &&
                card.getLocation() == CardLocation.GalaxyRow;
    }

    private boolean canExileCard(Card card, Player player) {
        return canExileCard(card, Set.of(CardLocation.getHand(player.getFaction()), CardLocation.getDiscard(player.getFaction())));
    }

    private boolean canExileCardFromHand(Card card, Player player) {
        return canExileCard(card, Set.of(CardLocation.getHand(player.getFaction())));
    }

    private boolean canExileCard(Card card, Set<CardLocation> cardLocations) {
        if (pendingActions.isEmpty() || (pendingActions.get(0).getAction() != Action.ExileCard) &&
                pendingActions.get(0).getAction() != Action.JabbaExile) {
            return false;
        }
        if (!(card instanceof PlayableCard)) {
            return false;
        }
        return cardLocations.contains(card.getLocation());
    }

    private boolean canChooseNewBase(Card card, Player player) {
        return !pendingActions.isEmpty() && pendingActions.get(0).getAction() == Action.ChooseNextBase && card instanceof Base &&
                player.getCurrentBase() == null && card.getLocation() == CardLocation.getAvailableBases(player.getFaction());
    }

    private boolean canChooseResourceOrRepair(ActionSpace actionSpace, Player player) {
        if (pendingActions.isEmpty() || pendingActions.get(0).getAction() != Action.ChooseResourceOrRepair) {
            return false;
        }
        ResourceOrRepair choice = actionSpace.getResourceOrRepair();
        if (choice == null) {
            return false;
        }
        return choice != ResourceOrRepair.Repair || player.getCurrentBase().getCurrentDamage() != 0;
    }

    private boolean canReturnCardToHand(Card card, Player player) {
        if (pendingActions.isEmpty() || pendingActions.get(0).getAction() != Action.ReturnCardToHand) {
            return false;
        }
        if (card.getLocation() != CardLocation.getDiscard(player.getFaction())) {
            return false;
        }
        if (!(lastCardActivated instanceof HasReturnToHandAbility source)) {
            return false;
        }
        if (!(card instanceof PlayableCard playableCard)) {
            return false;
        }
        return source.isValidTarget(playableCard);
    }

    private boolean canSwapTopCardOfDeck(Card card) {
        if (pendingActions.isEmpty() || pendingActions.get(0).getAction() != Action.SwapTopCardOfDeck) {
            return false;
        }
        if (card.getLocation() != CardLocation.GalaxyRow) {
            return false;
        }
        return !galaxyDeck.isEmpty();
    }

    private boolean canFireWhenReady(Card card, Player player) {
        if (player.getFaction() != Faction.empire || !(player.getCurrentBase() instanceof DeathStar) || player.getResources() < 4) {
            return false;
        }
        if (!(card instanceof CapitalShip)) {
            return false;
        }

        return card.getLocation() == CardLocation.GalaxyRow || card.getLocation() == CardLocation.getShipsInPlay(Faction.rebellion);
    }

    private boolean canGalacticRule(Card card, Player player) {
        if (player.getFaction() != Faction.empire || !(player.getCurrentBase() instanceof Coruscant)) {
            return false;
        }

        if (galaxyDeck.size() < 2) {
            return false;
        }

        if (!(card instanceof PlayableCard playableCard)) {
            return false;
        }
        return  playableCard.getLocation() == CardLocation.GalaxyDeck &&
                (playableCard.equals(galaxyDeck.get(0)) || playableCard.equals(galaxyDeck.get(1)));
    }

    private boolean canHammerHeadAway(Card card, Player player) {
        if (pendingActions.isEmpty() || pendingActions.get(0).getAction() != Action.HammerHeadAway) {
            return false;
        }
        if (player.getFaction() != Faction.rebellion || card.getFaction() != Faction.empire || !(card instanceof CapitalShip)) {
            return false;
        }
        return card.getLocation() == CardLocation.GalaxyRow || card.getLocation() == CardLocation.EmpireShipInPlay;
    }

    private boolean canConfirmAttackers() {
        if (pendingActions.isEmpty() || pendingActions.get(0).getAction() != Action.SelectAttacker) {
            return false;
        }
        if (attackTarget == null || attackers.isEmpty()) {
            return false;
        }
        int totalAttack = attackers.stream().mapToInt(PlayableCard::getAttack).sum();
        if (attackTarget instanceof PlayableCard) {
            if (attackTarget instanceof IsTargetable) {
                return totalAttack >= ((IsTargetable) attackTarget).getTargetValue();
            }
            if (attackTarget.getFaction() == Faction.neutral) {
                return totalAttack >= ((PlayableCard) attackTarget).getCost();
            }
        }
        return true;
    }

    public void drawGalaxyCard() {
        if (galaxyDeck.isEmpty()) {
            galaxyDeck = galaxyDiscard;
            galaxyDiscard = new ArrayList<>();
            Collections.shuffle(galaxyDeck);
            galaxyDeck.forEach(c -> {
                c.setLocation(CardLocation.GalaxyDeck);
                c.setCardList(galaxyDeck);
            });
        }
        PlayableCard card = galaxyDeck.remove(0);
        galaxyRow.add(card);
        card.setLocation(CardLocation.GalaxyRow);
        card.setCardList(galaxyRow);
        for (Map.Entry<Faction, Integer> entry : knowsTopCardOfDeck.entrySet()) {
            if (entry.getValue() > 0) {
                entry.setValue(entry.getValue() - 1);
            }
        }
    }

    public void assignDamageToBase(int damageDealt, Player player) {
        int remainingDamage = damageDealt;
        if (!player.getShipsInPlay().isEmpty()) {
            player.getShipsInPlay().sort((o1, o2) -> Integer.compare(o2.getRemainingHealth(), o1.getRemainingHealth()));
            Iterator<CapitalShip> iterator = player.getShipsInPlay().iterator();
            while (iterator.hasNext()) {
                CapitalShip ship = iterator.next();
                if (ship.getRemainingHealth() <= remainingDamage) {
                    remainingDamage -= ship.getRemainingHealth();
                    iterator.remove();
                    ship.moveToDiscard();
                }
            }
        }
        if (!player.getShipsInPlay().isEmpty()) {
            player.getShipsInPlay().get(0).addDamage(remainingDamage);
        } else if (player.getCurrentBase() != null) {
            player.getCurrentBase().addDamage(remainingDamage);
        }
    }

    private void endTurn(Player player) {
        for (PlayableCard card : exileAtEndOfTurn) {
            if (card.getLocation() != CardLocation.Exile) {
                card.moveToExile();
            }
        }
        exileAtEndOfTurn = new ArrayList<>();
        player.discardUnits();
        player.discardHand();
        player.drawCards(5);
        player.setResources(0);
        staticEffects = new ArrayList<>();
        currentPlayersTurn = currentPlayersTurn == Faction.empire ? Faction.rebellion : Faction.empire;
        currentPlayersAction = currentPlayersTurn;
        pendingActions = new ArrayList<>(); // clear it just in case
        canSeeOpponentsHand = false;
        lastCardActivated = null;
    }

    private void startTurn(Player player) {
        // The only thing that need to happen at the start of turn is queueing up any start of turn pending actions.
        if (player.getCurrentBase() == null) {
            // Start with choosing a new base
            pendingActions.add(PendingAction.of(Action.ChooseNextBase));
        } else {
            // Check for start of turn base abilities
            if (player.getCurrentBase() instanceof HasAtStartOfTurn) {
                ((HasAtStartOfTurn) player.getCurrentBase()).applyAtStartOfTurn();
            }
        }

        // Add capital ship resources
        player.getShipsInPlay().forEach(s ->
        {
            player.addResources(s.getResources());
            if (s instanceof HasAtStartOfTurn) {
                ((HasAtStartOfTurn) s).applyAtStartOfTurn();
            }
        });
        if (player.doesPlayerHaveFullForce()) {
            player.addResources(1);
        }
    }

    public boolean isGameComplete() {
        boolean isComplete = empire.getDestroyedBases().size() >= 4 || rebel.getDestroyedBases().size() >= 4;
        if (isComplete) {
            isGameOver.set(true);
        }
        return isComplete;
    }

    public void forgetTopCardOfDeck() {
        knowsTopCardOfDeck = new HashMap<>(Map.of(Faction.empire, 0, Faction.rebellion, 0));
    }

    public void revealTopCardOfDeck() {
        for (Map.Entry<Faction, Integer> entry : knowsTopCardOfDeck.entrySet()) {
            if (entry.getValue() < 1) {
                entry.setValue(1);
            }
        }
    }

    public void lookAtTopCardOfDeck(Faction faction) {
        if (knowsTopCardOfDeck.get(faction) < 1) {
            knowsTopCardOfDeck.put(faction, 1);
        }
    }
}
