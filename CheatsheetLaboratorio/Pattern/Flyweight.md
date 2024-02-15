## FlyWeight

### Obiettivo

Il pattern serve per gestire una **collezione di oggetti immutabili assicurandone l'unicità**. Vogliamo che non esistano _istanze diverse_ a parità di stato.

Se due client devono usare un istanza con lo stesso stato, vorremmo che ciascuno **non usi un istanza duplicata**, ma proprio la stessa. Essendo gli **oggetti immutabili**, questo tipo di condivisione non causa problemi.

### Come

Il **costruttore** viene reso **privato**, tutte le istanze vengo **costruite a priori** _(un'istanza per ogni possibile combinazione di valori degli attributi)_ tramite un costruttore statico, salvandole in una lista privata.

I client richiederanno una certa istanza specificando lo stato attraverso un metodo `get`. A parità di richiesta verranno restituite le stesse **identiche istanze**.

_Il pattern può risultare inefficiente per oggetti con rappresentazioni grandi: alla prima computazione vengono infatti inizializzati **tutti** gli oggetti, perdendo un po’ di tempo e sprecando potenzialmente spazio se non tutte le istanze saranno accedute._

### Esempio _(carte)_

Il codice assicura che ci sia una **singola istanza** per ciascuna combinazione possibile di `Suit` e `Rank`, promuovendo la condivisione di oggetti e riducendo così la memoria utilizzata.

Le carte sono memorizzate in una matrice statica `CARDS` di oggetti `Card`. La matrice è bidimensionale, la dimensione è determinata dalla lunghezza degli ENUM `Suit` e `Rank`.

L'inizializzazione della matrice avviene in un blocco di inizializzazione statica, per ogni combinazione di `Suit` e `Rank` viene creata una carta.

I client possono richiedere le carte tramite il metodo static `get`, che restituisce l'istanza di una  `Card`, dati un certo `suit` e `rank`. Il metodo accede direttamente alla matrice.

```java
public class Card {
    private static final Card[][] CARDS = new Card[Suit.values().length][];
    private final Rank rank;
    private final Suit suit;

    // costruisco la matrici di possibili carte
    // per ogni seme ho un array di valori
    static {
        for(Suit s : Suit.values()){
            CARDS[s.ordinal()] = new Card[Rank.values().length];
            for (Rank r : Rank.values()) {
                CARDS[s.ordinal()][r.ordinal()] = new Card(r,s);
            }
        }
    }

    // costruttore privato
    private Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    // metodo statico get, prende l'istanza con un certo stato
    public static Card get(Rank rank, Suit suit){
        assert rank != null && suit != null;
        return CARDS[suit.ordinal()][rank.ordinal()];
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank +", "+suit;
    }
}
```

```java
Card assoDiFiori = Card.get(Rank.ACE, Suit.CLUBS);
Card setteDiCuori = Card.get(Rank.SEVEN, Suit.HEARTS);
```
