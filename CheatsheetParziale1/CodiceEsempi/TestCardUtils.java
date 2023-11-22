public class TestCardUtils {

    public static Rank toRank(char cRank) {
        return switch (cRank) {
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
    }

    public static Suit toSuit(char cSuit) {
        return switch (cSuit) {
            case 'C' -> Suit.CLUBS;
            case 'S' -> Suit.SPADES;
            case 'D' -> Suit.DIAMONDS;
            case 'H' -> Suit.HEARTS;
            default -> throw new IllegalArgumentException("invalid suit");
        };
    }

    public static Card toCard(String cardString) {
        Rank rank = toRank(cardString.charAt(0));
        Suit suit = toSuit(cardString.charAt(1));

        return Card.get(rank, suit);
    }

    public static List<Card> toCardList(String cardsString) {
        String[] tokens = cardsString.split(" ");
        List<Card> cards = new ArrayList<>();
        for (String s : tokens)
            cards.add(toCard(s));
        return cards;
    }

}
