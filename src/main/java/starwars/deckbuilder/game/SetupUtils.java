package starwars.deckbuilder.game;

import starwars.deckbuilder.cards.common.models.Card;
import starwars.deckbuilder.cards.empire.base.*;
import starwars.deckbuilder.cards.empire.unit.*;
import starwars.deckbuilder.cards.neutral.unit.*;
import starwars.deckbuilder.cards.rebellion.base.*;
import starwars.deckbuilder.cards.rebellion.unit.*;
import starwars.deckbuilder.cards.empire.ship.GozantiCruiser;
import starwars.deckbuilder.cards.empire.ship.ImperialCarrier;
import starwars.deckbuilder.cards.empire.ship.StarDestroyer;
import starwars.deckbuilder.cards.empire.unit.starter.ImperialShuttle;
import starwars.deckbuilder.cards.empire.unit.starter.Inquisitor;
import starwars.deckbuilder.cards.empire.unit.starter.Stormtrooper;
import starwars.deckbuilder.cards.neutral.ship.BlockadeRunner;
import starwars.deckbuilder.cards.neutral.ship.CrocCruiser;
import starwars.deckbuilder.cards.neutral.ship.NebulonBFrigate;
import starwars.deckbuilder.cards.rebellion.ship.HammerheadCorvette;
import starwars.deckbuilder.cards.rebellion.ship.MonCalamariCruiser;
import starwars.deckbuilder.cards.rebellion.ship.RebelTransport;
import starwars.deckbuilder.cards.rebellion.unit.starter.AllianceShuttle;
import starwars.deckbuilder.cards.rebellion.unit.starter.RebelTrooper;
import starwars.deckbuilder.cards.rebellion.unit.starter.TempleGuardian;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class SetupUtils {
    public static Map<Integer, Card> setup(Game game) {
        Map<Integer, Card> cardMap = new ConcurrentHashMap<>();
        AtomicInteger id = new AtomicInteger(0);
        buildImperialDeck(id, cardMap, game);
        buildImperialCards(id, cardMap, game);
        buildRebelDeck(id, cardMap, game);
        buildRebelCards(id, cardMap, game);
        buildOuterRimPilots(id, cardMap, game);
        buildNeutralCards(id, cardMap, game);
        buildImperialBases(id, cardMap, game);
        buildRebelBases(id, cardMap, game);
        Collections.shuffle(game.getGalaxyDeck());
        return cardMap;
    }
    private static void buildImperialDeck(AtomicInteger currentCardId, Map<Integer, Card> cardMap, Game game) {
        // Add Shuttles
        buildCards(7, cardMap, () -> new ImperialShuttle(currentCardId.getAndIncrement(), game));
        // Add troopers
        buildCards(2, cardMap, () -> new Stormtrooper(currentCardId.getAndIncrement(), game));
        // Add inquisitor
        buildCards(1, cardMap, () -> new Inquisitor(currentCardId.getAndIncrement(), game));
        Collections.shuffle(game.getEmpire().getDeck());
    }

    private static void buildImperialBases(AtomicInteger currentCardId, Map<Integer, Card> cardMap, Game game) {
        buildCards(1, cardMap, () -> new Lothal(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new DeathStar(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Mustafar(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Corellia(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Kessel(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Kafrene(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Rodia(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new OrdMantell(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Endor(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Coruscant(currentCardId.getAndIncrement(), game));
    }

    private static void buildRebelBases(AtomicInteger currentCardId, Map<Integer, Card> cardMap, Game game) {
        buildCards(1, cardMap, () -> new Dantooine(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Hoth(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new MonCala(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Dagobah(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Sullust(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Bespin(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Jedha(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Alderaan(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Tatooine(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Yavin4(currentCardId.getAndIncrement(), game));
    }

    private static void buildRebelDeck(AtomicInteger currentCardId, Map<Integer, Card> cardMap, Game game) {
        // Add Shuttles
        buildCards(7, cardMap, () -> new AllianceShuttle(currentCardId.getAndIncrement(), game));
        // Add troopers
        buildCards(2, cardMap, () -> new RebelTrooper(currentCardId.getAndIncrement(), game));
        // Add guardian
        buildCards(1, cardMap, () -> new TempleGuardian(currentCardId.getAndIncrement(), game));
        Collections.shuffle(game.getRebel().getDeck());
    }

    private static void buildOuterRimPilots(AtomicInteger currentCardId, Map<Integer, Card> cardMap, Game game) {
        buildCards(10, cardMap, () -> new OuterRimPilot(currentCardId.getAndIncrement(), game));
    }

    private static void buildImperialCards(AtomicInteger currentCardId, Map<Integer, Card> cardMap, Game game) {
        buildCards(3, cardMap, () -> new TieFighter(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new DeathTrooper(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new TieBomber(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new ScoutTrooper(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new TieInterceptor(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new AtSt(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new LandingCraft(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new AtAt(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new AdmiralPiett(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new GeneralVeers(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new MoffJerjerrod(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new BobaFett(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new DirectorKrennic(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new GrandMoffTarkin(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new DarthVader(currentCardId.getAndIncrement(), game));
        buildCards(3, cardMap, () -> new GozantiCruiser(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new ImperialCarrier(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new StarDestroyer(currentCardId.getAndIncrement(), game));
    }

    private static void buildRebelCards(AtomicInteger currentCardId, Map<Integer, Card> cardMap, Game game) {
        buildCards(2, cardMap, () -> new YWing(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new Snowspeeder(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new DurosSpy(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new RebelCommando(currentCardId.getAndIncrement(), game));
        buildCards(3, cardMap, () -> new XWing(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new UWing(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new BWing(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new BazeMalbus(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new ChirrutImwe(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new JynErso(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Chewbacca(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new HanSolo(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new CassianAndor(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new PrincessLeia(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new MillenniumFalcon(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new LukeSkywalker(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new RebelTransport(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new HammerheadCorvette(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new MonCalamariCruiser(currentCardId.getAndIncrement(), game));
    }

    private static void buildNeutralCards(AtomicInteger currentCardId, Map<Integer, Card> cardMap, Game game) {
        buildCards(2, cardMap, () -> new Z95Headhunter(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new JawaScavenger(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new RodianGunslinger(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new KelDorMystic(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new TwilekSmuggler(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new FangFighter(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new Hwk290(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new QuarrenMercenary(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Lobot(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Bossk(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Dengar(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new Ig88(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new LandoCalrissian(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new JabbasSailBarge(currentCardId.getAndIncrement(), game));
        buildCards(1, cardMap, () -> new JabbaTheHutt(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new CrocCruiser(currentCardId.getAndIncrement(), game));
        buildCards(3, cardMap, () -> new BlockadeRunner(currentCardId.getAndIncrement(), game));
        buildCards(2, cardMap, () -> new NebulonBFrigate(currentCardId.getAndIncrement(), game));
    }

    private static void buildCards(int number, Map<Integer, Card> cardMap, Supplier<Card> cardSupplier) {
        for (int i = 0; i < number; i++) {
            Card card = cardSupplier.get();
            cardMap.put(card.getId(), card);
        }
    }
}
