package starwars.deckbuilder.game;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import starwars.deckbuilder.cards.common.models.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

@Getter
public class Player {
    @Setter
    private Faction faction;
    @Setter(AccessLevel.PACKAGE)
    private List<PlayableCard> hand = new ArrayList<>();
    @Setter(AccessLevel.PACKAGE)
    private List<PlayableCard> deck = new ArrayList<>();
    @Setter(AccessLevel.PACKAGE)
    private List<PlayableCard> discard = new ArrayList<>();
    @Setter
    private Base currentBase;
    @Setter(AccessLevel.PACKAGE)
    private List<CapitalShip> shipsInPlay = new ArrayList<>();
    @Setter(AccessLevel.PACKAGE)
    private List<Unit> unitsInPlay = new ArrayList<>();
    @Setter(AccessLevel.PACKAGE)
    private List<Base> availableBases = new ArrayList<>();
    @Setter(AccessLevel.PACKAGE)
    private List<Base> destroyedBases = new ArrayList<>();
    @Setter(AccessLevel.PACKAGE)
    private int resources;
    @Setter
    private Player opponent;
    @Setter
    private Game game;

    public Player(Faction faction) {
        this.faction = faction;
    }

    public boolean isForceWithPlayer() {
        if (faction == Faction.empire) {
            return game.getForceBalance().darkSideHasTheForce();
        } else {
            return game.getForceBalance().lightSideHasTheForce();
        }
    }

    public boolean doesPlayerHaveFullForce() {
        if (faction == Faction.empire) {
            return game.getForceBalance().darkSideFull();
        } else {
            return game.getForceBalance().lightSideFull();
        }
    }

    public void addResources(int amount) {
        resources += amount;
        if (resources < 0) {
            resources = 0;
        }
    }

    public void drawCards(int amount) {
        if (amount > deck.size() + discard.size()) {
            amount = deck.size() + discard.size();
        }

        if (amount < 1) {
            return;
        }

        for (int i = 0; i < amount; i++) {
            if (deck.isEmpty()) {
                deck = discard;
                discard = new ArrayList<>();
                Collections.shuffle(deck);
            }
            deck.get(0).moveToHand();
        }

    }

    public void discardUnits() {
        ListIterator<Unit> iterator = unitsInPlay.listIterator();
        while (iterator.hasNext()) {
            Unit unit = iterator.next();
            iterator.remove();
            unit.moveToDiscard();
        }
    }

    public void discardHand() {
        ListIterator<PlayableCard> iterator = hand.listIterator();
        while (iterator.hasNext()) {
            PlayableCard card = iterator.next();
            iterator.remove();
            card.moveToDiscard();
        }
    }

    public int getAvailableAttack() {
        int attack = 0;
        for (Unit card : unitsInPlay) {
            if (card.canAttack()) attack += card.getAttack();
        }
        for (CapitalShip card : shipsInPlay) {
            if (card.canAttack()) attack += card.getAttack();
        }
        return attack;
    }

    public void addForce(int amount) {
        if (faction == Faction.empire) {
            game.getForceBalance().darkSideGainForce(amount);
        } else {
            game.getForceBalance().lightSideGainForce(amount);
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "faction=" + faction +
                "\n\thand=" + hand +
                "\n\tcurrentBase=" + currentBase +
                "\n\tshipsInPlay=" + shipsInPlay +
                "\n\tunitsInPlay=" + unitsInPlay +
                "\n\tdestroyedBases=" + destroyedBases.size() +
                ", resources=" + resources +
                "\n\tgalaxyRow=" + game.getGalaxyRow() +
                "\n}";
    }
}
