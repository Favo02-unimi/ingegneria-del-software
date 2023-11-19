### FlyWeight

<span style=color:yellow>Obbiettivo</span> :=il pattern serve per <b><u>gestire una collezione di oggetti immutabili assicurandone l'unicità</u></b>. Vogliamo che <b><u>non esistano istanze diverse a parità di stato</u></b>.
Se due client devono usare un istanza con lo stesso stato, vorremmo che ciascuno non usi un istanza duplicata, ma proprio la stessa. Essendo gli oggetti immutabili, questo tipo di condivisione non causa problemi. 

<span style=color:cyan>Come</span> := il costruttore viene reso privato, tutte <b><u>le istanze vengo costruite a priori (un istanza per ogni possibile combinazione di valori degli attributi) tramite un costruttore statico</u></n>, salvandole in una lista privata. 
i client richiederanno una certa istanza specificando lo stato attraverso un metodo <span style=color:yellow>get</span> . A parità di richiesta verranno restituite le stesse identiche istanze. 

<span style=color:red>Singleton vs FlyWeight</span> :
A differenza del pattern Singleton è difficile definire a priori quante istanze ci sono. il pattern può risultare un po’ inefficiente per oggetti con rappresentazioni grandi: alla prima computazione vengono infatti inizializzati _tutti_ gli oggetti, perdendo un po’ di tempo e sprecando potenzialmente spazio se non tutte le istanze saranno accedute.

<span style=color:cyan;font-size:30px>Esempio Creazione Carte</span>

Il codice assicura che ci sia una singola istanza per ciascuna combinazione possibile di `Suit` e `Rank`, promuovendo la condivisione di oggetti e riducendo così la memoria utilizzata.

- <span style=color:orange>Matrice statica</span> `CARDS`  di oggetti `Card`. La matrice è bidimensionale , la dimensione è determinata dalla lunghezza degli ENUM `Suit` e `Rank`.

- <span style=color:green>Blocco di inizializzazione statica</span> : 
   il blocco viene utilizzato per popolare la <span style=color:orange>Matrice</span> `CARDS` con tutte le possibili combinazioni di  `Suit` e `Rank`.
   Per ogni combinazione di `Suit` e `Rank`, viene creata un istanza 
   `Card`. 

- <span style=color:yellow>Metodo statico get </span> 
   - Restituisce l'istanza di una  `Card`, dati un certo  `suit` e  `rank` 
   - Il metodo accede direttamente alla <span style=color:orange>matrice</span>, utilizzando gli ordinali di  `Suit` e  `Rank`  .
   
``` java
public class Card {  
    private static final Card[][] CARDS = new Card[Suit.values().length][];  
    private final Rank rank;  
    private final Suit suit;  
  
  
    //costruisco la matrici di possibili carte  
    //Per ogni seme ho un array di valori    static {  
       for(Suit s : Suit.values()){  
            CARDS[s.ordinal()] = new Card[Rank.values().length];  
            for (Rank r : Rank.values()){  
                CARDS[s.ordinal()][r.ordinal()] = new Card(r,s);  
            }  
       }  
    }  
  
    //costruttore lo metto privato  
    private Card(Rank rank, Suit suit) {  
        this.rank = rank;  
        this.suit = suit;  
    }  
  
    //metodo statico get, prende l'istanza con un certo stato  
    public static Card get(Rank rank, Suit suit){  
        assert rank!= null && suit != null;  
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

<span style=font-size:28px>Esempio di utilizzo</span> : 

``` java
public class Main {  
    public static void main(String[] args) {  
      var c1 = Card.get(Rank.ACE,Suit.CLUBS);  
      var c2 = Card.get(Rank.ACE,Suit.CLUBS);  
      System.out.println("C1:= "+ c1);  
      System.out.println(c1==c2); //sono la stessa istanza  
    }  
}
```