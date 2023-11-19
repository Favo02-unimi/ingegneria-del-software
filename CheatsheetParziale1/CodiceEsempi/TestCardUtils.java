package it.unimi.di.sweng.blackjack;

import ca.mcgill.cs.stg.solitaire.cards.Card;
import ca.mcgill.cs.stg.solitaire.cards.Rank;
import ca.mcgill.cs.stg.solitaire.cards.Suit;

import java.util.ArrayList;
import java.util.List;

public class TestCardUtils {

    public static List<Card> fromStringList(String cardsString) {
        String[] tokens = cardsString.split(" ");
        List<Card> cards = new ArrayList<>();
        for (String s : tokens)
            cards.add(toCard(s));
        return cards;
    }

    public static Card toCard(String cardString) {
        char cRank = cardString.charAt(0);
        char cSuit = cardString.charAt(1);

        Rank rank;
        rank = switch (cRank) {
            case '1', 'A' -> Rank.ACE;
            case '2' -> Rank.TWO;
            case '3' -> Rank.THREE;
            case '4' -> Rank.FOUR;
            case '5' -> Rank.FIVE;
            case '6' -> Rank.SIX;
            case '7' -> Rank.SEVEN;
            case '8' -> Rank.EIGHT;
            case '9' -> Rank.NINE;
            case '0', 'T' -> Rank.TEN;
            case 'J' -> Rank.JACK;
            case 'Q' -> Rank.QUEEN;
            case 'K' -> Rank.KING;
            default -> throw new IllegalArgumentException("invalid rank");
        };

        Suit suit;
        suit = switch (cSuit) {
            case 'C' -> Suit.CLUBS;
            case 'S' -> Suit.SPADES;
            case 'D' -> Suit.DIAMONDS;
            case 'H' -> Suit.HEARTS;
            default -> throw new IllegalArgumentException("invalid suit");
        };

        return Card.get(rank, suit);
    }


}
