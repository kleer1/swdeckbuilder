package sw.db.cards;

import lombok.Getter;
import sw.db.cards.common.models.Card;
import sw.db.cards.common.models.PlayableCard;
import sw.db.game.CardLocation;
import sw.db.game.Game;
import sw.db.game.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class BaseTest implements HasMoveToInPlay, HasGame{

    protected static final int NEUTRAL_GALAXY_CARD = 90;
    protected static final int EMPIRE_GALAXY_CARD = 10;
    protected static final int REBEL_GALAXY_CARD = 50;
    protected static final int LUKE_ID = 73;

    private static final Set<CardLocation> BUY_LOCATION = Set.of(
            CardLocation.GalaxyRow,
            CardLocation.GalaxyDiscard,
            CardLocation.GalaxyDeck,
            CardLocation.OuterRimPilots);

    @Getter
    protected Game game;

    public List<PlayableCard> moveToInPlay(Class<?> c, Player player) {
        return moveToInPlay(c, player, 1);
    }
    public List<PlayableCard> moveToInPlay(Class<?> c, Player player, int amount) {
        List<PlayableCard> cards = new ArrayList<>();
        for (Card card : game.getCardMap().values()) {
            if (card instanceof PlayableCard playableCard) {
                if (playableCard.getClass() == c) {
                    if (BUY_LOCATION.contains(playableCard.getLocation())) {
                        playableCard.buyToHand(player);
                    }
                    playableCard.moveToInPlay();
                    cards.add(playableCard);
                    if (cards.size() == amount) {
                        return cards;
                    }
                }
            }
        }
        return cards;
    }

    protected void emptyGalaxyRow() {
        ListIterator<PlayableCard> iterator = game.getGalaxyRow().listIterator();
        while (iterator.hasNext()) {
            PlayableCard card = iterator.next();
            iterator.remove();
            card.moveToTopOfGalaxyDeck();
        }
    }

    protected List<PlayableCard> moveToGalaxyRow(Class<?> c) {
        return moveToGalaxyRow(c, 1);
    }

    protected List<PlayableCard> moveToGalaxyRow(Class<?> c, int amount) {
        List<PlayableCard> cards = new ArrayList<>();
        for (Card card : game.getCardMap().values()) {
            if (card instanceof PlayableCard playableCard) {
                if (playableCard.getClass() == c) {
                    playableCard.moveToGalaxyRow();
                    cards.add(playableCard);
                    if (cards.size() == amount) {
                        return cards;
                    }
                }
            }
        }
        return cards;
    }
}
