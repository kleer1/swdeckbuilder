package starwars.deckbuilder.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import starwars.deckbuilder.cards.common.models.Faction;

@AllArgsConstructor
@Getter
public enum CardMapping {
    EmpireStartingCards(0, 10),
    EmpireGalaxyCards(10, 40),
    EmpirePlayableCards(0, 40),
    EmpireUnits(0,33),
    EmpireShips(33, 40),
    RebelStartingCards(40, 50),
    RebelGalaxyCards(50, 80),
    RebelPlayableCards(40, 80),
    RebelUnits(40,74),
    RebelShips(74, 80),
    NeutralOuterRimCards(80, 90),
    NeutralGalaxyCards(90, 120),
    NeutralPlayableCards(80, 120),
    NeutralUnits(80, 113),
    NeutralShips(113, 120),
    EmpireBases(120, 130),
    RebelBases(130, 140);

    private final int minRange; // inclusive
    private final int maxRange; // exclusive

    public static CardMapping getPlayableCards(Faction faction) {
        switch (faction) {
            case rebellion -> {
                return RebelPlayableCards;
            }
            case empire -> {
                return EmpirePlayableCards;
            }
            case neutral -> {
                return NeutralPlayableCards;
            }
        }
        return null;
    }

    public static CardMapping getShipCards(Faction faction) {
        switch (faction) {
            case rebellion -> {
                return RebelShips;
            }
            case empire -> {
                return EmpireShips;
            }
            case neutral -> {
                return NeutralShips;
            }
        }
        return null;
    }

    public static CardMapping getBases(Faction faction) {
        switch (faction) {
            case rebellion -> {
                return RebelBases;
            }
            case empire -> {
                return EmpireBases;
            }
        }
        return null;
    }

    public static CardMapping getGalaxyCards(Faction faction) {
        switch (faction) {
            case rebellion -> {
                return RebelGalaxyCards;
            }
            case empire -> {
                return EmpireGalaxyCards;
            }
            case neutral -> {
                return NeutralGalaxyCards;
            }
        }
        return null;
    }
}
