## Cheatsheet parziale 1

### Indice

- [Testing](#testing)
- [Testing parametrico](#testing-parametrico)
- [Mocking](#mocking)
- [Dependency injection](#dependency-injection)
- [Decoratori `BeforeEach`, `Nested`](#decoratori-beforeeach-nested)
- [Pattern](#pattern)
- Esempi di utilizzo:
	- [MazziereTest.java](./CodiceEsempi/MazziereTest.java) _(parametrized test, mock, dependency injection)_
	- [SfidanteTest.java](./CodiceEsempi/SfidanteTest.java) _(parametrized test, mock, dependency injection, thenAnswer)_
	- [FattoriaTest.java](./CodiceEsempi/FattoriaTest.java) _(doAnswer, spy)_
	- [TestCardUtils.java](./CodiceEsempi/TestCardUtils.java) _(utils per parametrized test)_

## Testing

Le classi di test devono essere nella directory `test`, con stesso package delle classi in `main`.

```java
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

public class PuffoTest {
	@Test
	void coloreTest() {
		// ...
	}

	@Test
	void statoTest() {
		// ...
	}
}
```

Le asserzioni vengono eseguite con i metodi `assertThat*` di `JUnit`.

- controlli su valori / istanze:
```java
assertThat(puffo.getColore())...

.isEqualTo("blu");
.isInstanceOf(String);
.isGreatherThan(4);
.isNull();
.isFalse();
```

- controlli su `Iterable` e `Iterator`:
```java
// in caso sia un Iterator è possibile convertirlo ad Iterable
assertThat(puffo.iterator()).toIterable()...

// controlli su Iterable
.containsExactly(elem1, elem2, elem3);
.containsExactlyInAnyOrder(elem1, elem2, elem3);
.hasSize(3);
```

Esempio : 
```java
void newPokerHandTest(){
	PokerHand ph = new PokerHand(
		List.of(
			Card.get(Rank.ACE, Suit.Clubs),
			Card.get(Rank.Two, Suit.Clubs),
			......
		
		)
	);

	asserThat((Iterable<Card>) ph ).containsExactlyInAnyOrder(
			Card.get(Rank.ACE, Suit.Clubs),
			Card.get(Rank.Two, Suit.Clubs),
			......
	)
}
```

- controlli su `Eccezioni lanciate`:
```java
assertThatThrownBy(() -> puffo.metodoSbagliato())...
// oppure
assertThatException().isThrownBy(() -> puffo.metodoSbagliato())...

.isInstanceOf(IllegalArgumentException.class)
.hasMessage("Errore");
.withMessageContaining("Errore");
```

## Testing parametrico

```java
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@ParameterizedTest
@CsvSource({
        "12,dodici",
        "0,zero",
        "7,sette",
        "8,otto"
})
void parametricoTest(int num, String nome) {
    assertThat(convertitore.convert(num)).isEqualTo(nome);
}
```

Per sfruttare il testing parametrico per test in cui sono richieste classi (spesso ENUM) è necessario sviluppare delle classi di utilità che convertono da String all'ENUM.

```java
public static Card toCard(String cardString) {
    char cRank = cardString.charAt(0);
    char cSuit = cardString.charAt(1);

    Rank rank;
    rank = switch (cRank) {
        case '1', 'A' -> Rank.ACE;
        case '2' -> Rank.TWO;
	    ...
        default -> throw new IllegalArgumentException("invalid rank");
    };

    Suit suit;
    suit = switch (cSuit) {
        case 'C' -> Suit.CLUBS;
	    ...
        default -> throw new IllegalArgumentException("invalid suit");
    };

    return Card.get(rank, suit);
}
```

```java
@ParameterizedTest
@CsvSource({
        "7C", "8C", "KC", "6D"
})
void carteTest(String carta) {
    assertThat(...).isEqualTo(TestCardUtils.toCard(carta));
}
```
## Mocking vs Spy


- <b><u>Spy</u></b> := Permette di creare spy objects a partire da oggetti reali. <b><u> Si ottiene un oggetto che ha le stesse funzioni dell'oggetto originale</u></b> , ma che può essere utilizzato per eseguire il tracciamento delle chiamate ai suoi metodi  . 

   <b><u>Un oggeto spy continuerà a chiamare il metodo reale, se non diversamente specificato</u></b>. 
   
   Quando devo usare uno spy mocckato utilizzo la seguente sintassi, oppure quando devo <b><u>testare un metodo con parametro di ritorno void </u></b>  : 

```java
	doReturn(iterator).when(SUT).getCards();
```

- <b><u>Mock</u></b> := si tratta di un oggetto utilizzato per creare Test Double a partire da una determinata classe o interfaccia.
   L'oggetto creato si presenta con la stessa interfaccia del metodo mockato, ma fornisce <b><u>un implementazione minimale</u></b>. Questo si limiterà a restituire dei valori di default per il tipo di ritorno del metodo, oppure a non fare nulla se il metodo è void. 

<span style=color:red>N.B</span> = Quando mocko un oggetto lo svuoto completamente. Dovrò dunque andare ad esplicitare i metodi dell'oggetto reale che voglio andare ad utilizzare tramite `thenCallRealMethod()`.


## Mocking

Il mocking è la costruzione di oggetti finti (gli oggetti NON da testare), da utilizzare per testare oggetti che comunicano (o che hanno come dipendenza) l'oggetto finto.

```java
import org.mockito.Mockito;

// oggetto finto, dipendenza dell'oggetto da testare
Puffo puffoMockato = Mockito.mock(Puffo.class);

// lancia sempre l'eccezione
Mockito.when(puffoMockato.err()).thenThrow(new IllegalArgumentException());

// restituisce sempre blu
Mockito.when(puffoMockato.colore()).thenReturn("Blu");

// restituisce alla prima chiama blu, poi verde e poi sempre giallo
Mockito.when(puffoMockato.colore()).thenReturn("Blu", "Verde", "Giallo");

// restituire uno alla volta gli elementi di una lista
Mockito.when(mazziereMockato.hit())
        .thenAnswer(AdditionalAnswers.returnsElementsOf(lista));


// simulare la modifica dello stato della classe mockata
// dopo che viene chiamato mazziereMockato.setNome() viene impostato
// come risultato di getNome il primo argomento di setNome()
Mockito.doAnswer((Answer<Void>) invocation -> {
	Object[] args = invocation.getArguments();
	String nome = (String) args[0];
	when(mazziereMockato.getName()).thenReturn(nome);
	return null;
}).when(mazziereMockato).setNome(Mockito.anyString());
```

Il mocking è utile anche per testare metodi default di interfacce.

```java
// interfaccia finta
Interfaccia inter = Mockito.mock(Interfaccia.class);

// altri metodi interfaccia usati nel default da testare
Mockito.when(inter....()).thenReturn(...);

// chiamare metodo di default vero
Mockito.when(inter.metodo()).thenCallRealMethod();

assertThat(inter.metodo()).isEqualTo(...);
```

<u><b>Verify, viene utilizzato per controllare quante volte viene chiamato un certo metodo su un qualunque oggetto spy o mock</u></b>.

```java
verify(mockedclass, howMany).methodname(args)
```

il parametro `howmany`, <b><u>specifica il numero di volte che il metodo associato all'oggetto mockato deve essere chiamato</u></b> durante l'esecuzione del test. Abbiamo diverse opzioni : 
 - `times(n)` = verifica che `methodname()` si stato chiamato `n` volte. 
 - `never` = verifica che `methodname()` non sia mai stato chiamato 
 - `atLeastOnece()` = verifica che `methodName()` sia chiamato almeno una volta. 
 - `atLeast(n)` = verifica che `methodName()`, venga chiamato almeno `n` volte
 - `atMost(n)` = verifica che `methodName()`, venga chiamato al massimo `n` volte 

`inOrder()` := <b><u>Verifica l'ordine delle occorenze delle chiamate ai metodi di un oggetto</u></b> . 
```java
InOrder inO = inOrder(mock1, mock2, ...) inO.verify...
```

```java
// verificare che il metodo met di oggettoFinto sia chiamato tot volte
Mockito.verify(oggettoFinto, Mockito.times(4)).met();
Mockito.verify(oggettoFinto, Mockito.atLeast(2))).met();
```

## Usare iteratore nei test

<b><u>Quando voglio andare ad usare un iteratore in un test mokkandolo</u></b>, mi devo ricordare di usare il seguente codice : 

```java
import static org.mockito.Mockito.when;  
import org.junit.jupiter.api.extension.ExtendWith;  
import org.mockito.junit.jupiter.MockitoExtension;  
import org.mockito.stubbing.Answer;  
import java.util.Iterator;  
import java.util.List;  
  
public class MockUtils {  
    @SafeVarargs  
    public static <T> void whenIterated(Iterable<T> p, T... d) {  
        when(p.iterator()).thenAnswer(
	        (Answer<Iterator<T>>) invocation -> List.of(d).iterator()
	    );  
    }  
  
}
```
Esempio pratico applicato al testing di getPunti in sfidante: 

```java
@Test  
void testGetPunti (){  
    Sfidante SUT = mock(Sfidante.class);  
    when(SUT.getPunti()).thenCallRealMethod();  
    List<Card> mano = List.of(  
            Card.get(Rank.ACE, Suit.DIAMONDS),  
            Card.get(Rank.ACE,Suit.CLUBS),  
            Card.get(Rank.EIGHT,Suit.DIAMONDS)  
    );  
    
    /* 
	   when(SUT.getCards()).thenAnswer(
		(Answer<Iterator<Card>>) invocation -> mano.iterator()
	   );  
    */
    when(SUT.getCards()).thenAnswer(invocation -> mano.iterator());  
    
    assertThat(SUT.getPunti()).isEqualTo(20);  
    assertThat(SUT.getPunti()).isEqualTo(20);  
}
```


## Dependency injection

Per facilità di testing, è necessario sostituire le dipendenze dell'oggetto da testare con oggetti finti.<br>
InjectMocks = <b><u>prova a inniettare quello che voglio in due modi</u></b> : 
 - <b><u>Con il costruttore</u></b>, prende quello più largo, con più prametri. Dove ho definito un parametro li passa quello, altrimenti passa un null. 

 - Posso anche esplicitare attraverso il costrutto new l'oggetto INjectMocks. 
 Lo uso quando non ho un costruttore che soddisfa l'injection che voglio fare. 

Se trova un costruttore, allora la injection dovrebbe andare a buon fine. 
( nel nostro caso falirebbe la compilazione se non setto la strategia esplicitamente).

```java
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class MazziereTest {

    @Mock
    Dipendenza dip; // dipendenza che verrà sostituita nell'oggetto SUT

    @InjectMocks
    Puffo SUT; // oggetto da testare

	...
}
```
esempio : 

```java
public class gicatroe(){

private final List<Card> mano; 
private final Mazziere banco;


public void gioca(){
	//controllo anche di non chiamare la strategia se ho già sballato.
	// se non sono arrivato ancora a 21 chiedo una carta. 
	while(getPunti() < 21 && strategia.chiediCarta()){ 
		vat carta = banco.draw();
		mano.add(carta);
	}
}
}
```

```java
public testGioca{

	@Mock Mazziere banco; 
	@Mock Strategia strat;
	@InjectsMock Sfidante SUT; 

	@test
	void giocaTest(){
		when(strat.chiediCarta()).thenReturn(true,true,false);
		when(banco.draw()).thenReturn(Card.get(Rank.ACE,SUITS.club))
		//ritorna sempre l'asso.
		
		SUT.setStrategia(strat);
		SUT.carteIniziali();
		SUT.gioca
		
		asserThat(SUT.getCard()).toIterable().hasSize(4);
		verify(banco,times(4)).draw(); //controllo che drwa è stato chiamto 4 volte, banco è un oggetto mockato. 
	
	}
}
```

## Decoratori `BeforeEach`, `Nested`

In alcune istruzioni siano comuni a tutti i test, è possibile utilizzare un metodo decorato con `@BeforeEach`.

```java
import org.junit.jupiter.api.BeforeEach;

@BeforeEach
void init() {
	// viene eseguito prima di ogni test
    Mockito.when(dip.metodo()).thenReturn(42);
    SUT.metodoComuneATuttiITest();
}
```

È possibile racchiudere alcuni test di una classe in alcuni gruppi, ad esempio per eseguire un `@BeforeEach`, creando una classe innestata nella classe di test decorata con `@Nested`.

```java
import org.junit.jupiter.api.Nested;

@ExtendWith(MockitoExtension.class)
class PuffoTest {

	@Nested
	class testConInjections {

		@Mock
	    Dipendenza dip;
	    // mettendo @Mock non è necessario chiamare Mockito.mock(Dipendenza.class)

	    @InjectMocks
		Puffo SUT;

		@BeforeEach
		void init() {
			Mockito.when(dip.metodo()).thenReturn(42);
		    SUT.metodoComuneATuttiITest();
		}

		// questi test avranno dip iniettata in SUT
		@Test
		void test1() {...}
		@Test
		void test2() {...}

	}

	@Test
	void testNonIniettato() {
		Puffo puffo = new Puffo();
		assertThat(puffo.colore()).isEqualTo("blu");
	}

}
```

## Pattern

- [Chain of responsability](./Pattern/ChainOfResponsability.md)
- [Flyweight](./Pattern/Flyweight.md)
- [Iterator](./Pattern/Iterator.md)
- [Null Object](./Pattern/NullObject.md)
- [Singleton](./Pattern/Singleton.md)
- [Strategy](./Pattern/Strategy.md)
