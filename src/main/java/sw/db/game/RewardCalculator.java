package sw.db.game;

import sw.db.cards.common.models.CapitalShip;
import sw.db.cards.common.models.Faction;

public class RewardCalculator {

    private final int startingResources;
    private final int cardInDeckAndDiscard;
    private final int availableAttack;
    private boolean shouldSkip = false;
    private final int forcePosition;
    private int baseHealth;
    private final int shipsInPlay;
    private final int damageOnShips;
    public RewardCalculator(final Game game) {
        if (game.getCurrentPlayer().getFaction() != game.getCurrentPlayersAction()) {
            shouldSkip = true;
        }
        Player player = game.getCurrentPlayer();
        startingResources = player.getResources();
        cardInDeckAndDiscard = player.getDeck().size() + player.getDiscard().size();
        availableAttack = player.getAvailableAttack();
        forcePosition = game.getForceBalance().getPosition();
        if (player.getOpponent().getCurrentBase() != null) {
            baseHealth = player.getOpponent().getCurrentBase().getRemainingHealth();
        }
        shipsInPlay = player.getOpponent().getShipsInPlay().size();
        damageOnShips = player.getOpponent().getShipsInPlay().stream()
                .map(CapitalShip::getRemainingHealth)
                .reduce(0, Integer::sum);

    }

    public double determineReward(Game game) {
        if (shouldSkip) {
            return 0.0;
        }
        double reward = 0.0;
        Player player = game.getCurrentPlayersAction() == Faction.empire ? game.getEmpire() : game.getRebel();
        int resourceDiff = player.getResources() - startingResources;
        if (resourceDiff > 0) {
            reward += resourceDiff;
        }
        int cardInDeckAndDiscardDiff = cardInDeckAndDiscard - (player.getDeck().size() + player.getDiscard().size());
        if (cardInDeckAndDiscardDiff > 0) {
            reward += cardInDeckAndDiscardDiff * 2;
        }
        int attackDiff = player.getAvailableAttack() - availableAttack;
        if (attackDiff > 0) {
            reward += attackDiff;
        }
        int newForcePos = game.getForceBalance().getPosition();
        int forceDiff = player.getFaction() == Faction.empire ? forcePosition - newForcePos : newForcePos - forcePosition;
        if (forceDiff > 0) {
            reward += forceDiff;
            if (player.doesPlayerHaveFullForce()) {
                reward += 4;
            }
        }
        if (player.getOpponent().getCurrentBase() == null) {
            reward += 50;
            reward += baseHealth * 3;
        } else {
            int healthDiff = baseHealth - player.getOpponent().getCurrentBase().getRemainingHealth();
            if (healthDiff > 0) {
                reward += healthDiff * 3;
            }
        }
        int shipsDiff = shipsInPlay - player.getOpponent().getShipsInPlay().size();
        if (shipsDiff > 0) {
            reward += shipsDiff * 2;
        } else {
            int shipDamageDiff = damageOnShips - player.getOpponent().getShipsInPlay().stream()
                    .map(CapitalShip::getRemainingHealth)
                    .reduce(0, Integer::sum);
            if (shipDamageDiff > 0) {
                reward += shipDamageDiff;
            }
        }
        if (!game.getPendingActions().isEmpty()) {
            PendingAction pendingAction = game.getPendingActions().get(0);
            switch (pendingAction.getAction()) {
                case ReturnCardToHand -> reward += 2.0;
                case DiscardFromHand, DurosDiscard, BWingDiscard -> {
                    if (pendingAction.shouldPassAction()) reward += 2.0;
                }
                case PurchaseCard -> {
                    if (game.getStaticEffects().contains(StaticEffect.NextFactionPurchaseIsFree) ||
                            game.getStaticEffects().contains(StaticEffect.NextFactionOrNeutralPurchaseIsFree)) {
                        reward += 5.0;
                    }
                }
            }
        }
        return reward;
    }
}
