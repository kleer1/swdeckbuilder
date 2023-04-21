package sw.db.game;

import sw.db.cards.common.models.CapitalShip;
import sw.db.cards.common.models.Faction;

public class RewardCalculator {

    private int startingResources;
    private int cardInDeckAndDiscard;
    private int availableAttack;
    private boolean shouldSkip = false;
    private int numAttackers;
    private int forcePosition;
    private int baseHealth;
    private int shipsInPlay;
    private int damageOnShips;
    public RewardCalculator(final Game game) {
        if (game.getCurrentPlayer().getFaction() != game.getCurrentPlayersAction()) {
            shouldSkip = true;
        }
        Player player = game.getCurrentPlayer();
        startingResources = player.getResources();
        cardInDeckAndDiscard = player.getDeck().size() + player.getDiscard().size();
        availableAttack = player.getAvailableAttack();
        numAttackers = game.getAttackers().size();
        forcePosition = game.getForceBalance().getPosition();
        if (player.getOpponent().getCurrentBase() != null) {
            baseHealth = player.getOpponent().getCurrentBase().getRemainingHealth();
        }
        shipsInPlay = player.getOpponent().getShipsInPlay().size();
        damageOnShips = player.getOpponent().getShipsInPlay().stream()
                .map(CapitalShip::getRemainingHealth)
                .reduce(0, (a, b) -> a + b);

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
        int cardInDeckAndDiscardDiff = cardInDeckAndDiscard - player.getDeck().size() + player.getDiscard().size();
        if (cardInDeckAndDiscardDiff > 0) {
            reward += cardInDeckAndDiscardDiff;
        }
        int attackDiff = player.getAvailableAttack() - availableAttack;
        if (attackDiff > 0) {
            reward += attackDiff;
        }
        if (game.getAttackers().size() > numAttackers) {
            reward += 2;
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
                    .reduce(0, (a, b) -> a + b);
            if (shipDamageDiff > 0) {
                reward += shipDamageDiff;
            }
        }
        return reward;
    }
}
