package starwars.deckbuilder.cards.common.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import starwars.deckbuilder.game.*;

import java.util.List;

@Getter
public class Card {
    protected final int id;
    protected final Faction faction;
    protected final String title;
    protected final boolean isUnique;
    @Setter
    protected Player owner;
    protected final Game game;
    @Setter
    protected CardLocation location;
    @Setter
    List<? extends Card> cardList;
    @Getter(AccessLevel.NONE)
    protected boolean abilityUsed;

    protected Card(int id, Faction faction, String title, boolean isUnique,  CardLocation location,
                   List<? extends Card> cardList, Game game, Player owner) {
        this.id = id;
        this.faction = faction;
        this.title = title;
        this.isUnique = isUnique;
        this.game = game;
        this.location = location;
        this.cardList = cardList;
        if (cardList != null) {
            ((List<Card>) cardList).add(this);
        }
        this.owner = owner;
        abilityUsed = false;
    }

    protected boolean abilityActive() {
        return !abilityUsed && this instanceof HasAbility;
    }

    protected void applyAbility() {
        abilityUsed = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        return new EqualsBuilder().append(id, card.id).isEquals();
    }

    private int maxNumExileAbility(Player player) {
        if (player == null) {
            return 0;
        }
        return player.getHand().size() + player.getDiscard().size();
    }

    protected void addExilePendingAction(Player player, int depth) {
        depth = Math.min(depth, maxNumExileAbility(player));
        if (depth < 1) {
            return;
        }
        game.getPendingActions().add(exileActionRec(depth));
    }

    private PendingAction exileActionRec(int depth) {
        if (depth == 1) {
            return PendingAction.of(Action.ExileCard);
        }
        return PendingAction.of(Action.ExileCard, () -> game.getPendingActions().add(exileActionRec(depth - 1)));
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", owner=" + (owner != null ? owner.getFaction() : "none") +
                '}';
    }
}
