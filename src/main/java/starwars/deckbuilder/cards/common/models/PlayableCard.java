package starwars.deckbuilder.cards.common.models;

import lombok.AccessLevel;
import lombok.Getter;
import starwars.deckbuilder.game.CardLocation;
import starwars.deckbuilder.game.Game;
import starwars.deckbuilder.game.Player;

import java.util.List;

@Getter
public class PlayableCard extends Card {
    protected final int cost;
    protected final int attack;
    protected final int resources;
    protected final int force;
    protected final List<Trait> traits;
    @Getter(AccessLevel.NONE)
    protected boolean canAttack = true;

    protected PlayableCard(int id, Faction faction, String title, boolean isUnique, CardLocation location,
                        List<? extends Card> cardList, Game game, Player owner, int cost, int attack, int resources,
                        int force, List<Trait> traits) {
        super(id, faction, title, isUnique, location, cardList, game, owner);
        this.cost = cost;
        this.attack = attack;
        this.resources = resources;
        this.force = force;
        this.traits = traits;
    }

    public boolean canAttack() {
        return getAttack() > 0 && canAttack;
    }

    public void setAttacked() {
        canAttack = false;
    }

    public void buy(Player newOwner) {
        owner = newOwner;
        moveToDiscard();
    }

    public void buyToTopOfDeck(Player newOwner) {
        owner = newOwner;
        moveToTopOfDeck();
    }

    public void buyToHand(Player newOwner) {
        owner = newOwner;
        moveToHand();
    }

    public void moveToDiscard() {
        if (owner == null) {
            throw new IllegalArgumentException("Can not move a card into play with no owner");
        }
        cardList.remove(this);
        owner.getDiscard().add(this);
        cardList = owner.getDiscard();
        canAttack = false;
        location = CardLocation.getDiscard(owner.getFaction());
        abilityUsed = false;
    }

    public void moveToTopOfDeck() {
        if (owner == null) {
            throw new IllegalArgumentException("Can not move a card into play with no owner");
        }
        cardList.remove(this);
        owner.getDeck().add(0, this);
        cardList = owner.getDeck();
        location = CardLocation.getDeck(owner.getFaction());
    }

    public void moveToHand() {
        if (owner == null) {
            throw new IllegalArgumentException("Can not move a card into play with no owner");
        }
        cardList.remove(this);
        owner.getHand().add(this);
        cardList = owner.getHand();
        location = CardLocation.getHand(owner.getFaction());
    }

    public void moveToExile() {
        if (owner == null) {
            throw new IllegalArgumentException("Can not move a card into play with no owner");
        }
        cardList.remove(this);
        owner.getGame().getExiledCards().add(this);
        owner = null;
        location = CardLocation.Exile;
        cardList = game.getExiledCards();
    }

    public void moveToInPlay() {
        canAttack = true;
        if (owner == null) {
            throw new IllegalArgumentException("Can not move a card into play with no owner");
        }
        cardList.remove(this);
        owner.addResources(getResources());
        if (owner.getFaction() == Faction.empire) {
            owner.getGame().getForceBalance().darkSideGainForce(getForce());
        } else {
            owner.getGame().getForceBalance().lightSideGainForce(getForce());
        }
    }

    public void moveToGalaxyDiscard() {
        canAttack = false;
        owner = null;
        cardList.remove(this);
        location = CardLocation.GalaxyDiscard;
        game.getGalaxyDiscard().add(this);
        cardList = game.getGalaxyDiscard();
    }

    public void moveToGalaxyRow() {
        canAttack = false;
        owner = null;
        cardList.remove(this);
        location = CardLocation.GalaxyRow;
        game.getGalaxyRow().add(this);
        cardList = game.getGalaxyRow();
    }

    public void moveToTopOfGalaxyDeck() {
        canAttack = false;
        owner = null;
        cardList.remove(this);
        location = CardLocation.GalaxyDeck;
        game.getGalaxyDeck().add(0, this);
        cardList = game.getGalaxyDeck();
    }

    protected boolean isInPlay() {
        return owner!= null &&
                (location == CardLocation.getUnitsInPlay(owner.getFaction()) ||
                        location == CardLocation.getShipsInPlay(owner.getFaction()));
    }

    @Override
    public boolean abilityActive() {
        return super.abilityActive() && isInPlay();
    }

    @Override
    public String toString() {
        return "PlayableCard{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", owner=" + (owner != null ? owner.getFaction() : "none") +
                ", canAttack=" + canAttack +
                '}';
    }
}
