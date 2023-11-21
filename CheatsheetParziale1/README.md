## Cheatsheet parziale 1

### Indice

- [Testing](#testing)
	- Assert su tipi semplici
	- Assert su iterable
	- Assert su eccezioni
- [Testing parametrico](#testing-parametrico)
	- Parametrizzare ENUM
- [Mocking](#mocking)
	- `when().thenReturn()`
	- `when().thenAnswer()`: per iteratori
	- `doReturn().when()`: per metodi `void`
	- `thenCallRealMethod()`: per interfacce
	- `verify`: numero chiamate
	- `inOrder`: ordine chiamate
	- `ArgumentCaptor`: parametri chiamate
- [Spy](#spy)
	- `spy()`
- [Dependency injection](#dependency-injection)
	- `@InjectMocks`
	- `@Mock`
- [Decoratori utili](#decoratori-utili)
	- `@BeforeEach`
	- `@Nested`
	- `@MockitoSettings(strictness = Strictness.LENIENT)`
- [Pattern](#pattern)
	- Chain of responsibility
	- Flyweight
	- Iterator
	- Null Object
	- Singleton
	- Strategy
- _Esempi di utilizzo_:
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
.containsExactly(list.toArray(new Object[0]));
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

@ParameterizedTest
@CsvSource({
	"3C 3S AC 5B 7S 2C",
	"7S RC",
	"AD AB 3B"
    })
    void iterableTest(String cards) {
        List<Card> cardList = TestCardUtils.fromStringList(cards);
        ...
        assertThat((Iterable<Card>) SUT).containsExactly(cardList.toArray(new Card[0]));
    }
```

## Mocking

Il mocking è la costruzione di **oggetti finti** (NON da testare), da utilizzare per testare oggetti che comunicano (o che hanno come dipendenza) l'oggetto finto.

Gli oggetti mockati sono **"svuotati"**, non hanno nessun comportamento o stato se non esplicitamente specificato con `when`.

```java
import static org.mockito.Mockito.*;

// oggetto finto, dipendenza dell'oggetto da testare
Puffo puffoMockato = mock(Puffo.class);

// lancia sempre l'eccezione
when(puffoMockato.err()).thenThrow(new IllegalArgumentException());

// restituisce sempre blu
when(puffoMockato.colore()).thenReturn("Blu");

// restituisce alla prima chiama blu, poi verde e poi sempre giallo
when(puffoMockato.colore()).thenReturn("Blu", "Verde", "Giallo");

// restituire uno alla volta gli elementi di una lista
when(mazziereMockato.hit()).thenAnswer(AdditionalAnswers.returnsElementsOf(lista));
```

Un **caso particolare** in cui è quando deve essere restituito un oggetto che deve essere _"resettato"_ ad ogni asserzione (come un **iteratore**). Un semplice `thenReturn(iterator)` funziona, ma tutte le asserzioni utilizzeranno lo stesso iteratore, facendo funzionare solo la prima.

```java
when(mockedClass.iterator()).theReturn(List.of(...).iterator());

// funziona
assertThat(SUT.metodoCheUsaIterator()).isEqualTo(20);
// non funziona, usa lo stesso iteratore, che è "finito"
assertThat(SUT.metodoCheUsaIterator()).isEqualTo(20);
```

Utilizzare una **risposta personalizzata** (`answer`) risolve questo problema:

```java
when(mockedClass.iterator()).thenAnswer(
	invocation -> List.of(...).iterator()
);

assertThat(SUT.metodoCheUsaIterator()).isEqualTo(20);
assertThat(SUT.metodoCheUsaIterator()).isEqualTo(20);
```

Oltre alla sintassi `when().thenReturn()` o `when().thenAnswer()` è possibile utilizzare anche `doReturn().when()` o `doAnswer.when()`.

Con quest'ultima sintassi è possibile simulare anche il comportamento di metodi `void`, eseguendo **codice arbitrario** (ed avendo a disposizione gli **argomenti della chiamata**, `invocation.getArguments()`).

```java
// setNome() è void

// simulare la modifica dello stato della classe mockata
// dopo che viene chiamato mazziereMockato.setNome() viene impostato
// come risultato di getNome il primo argomento di setNome()
doAnswer(invocation -> {
	Object[] args = invocation.getArguments();
	String nome = (String) args[0];
	when(mazziereMockato.getName()).thenReturn(nome);
	return null;
}).when(mazziereMockato).setNome(anyString());
```

Il mocking è utile anche per testare metodi **default di interfacce** o classi sono **parzialmente implementate**, utilizzando solo alcuni metodi reali (`thenCallRealMethod`).

```java
// interfaccia finta
Interfaccia inter = mock(Interfaccia.class);

// altri metodi interfaccia usati nel default da testare
when(inter....()).thenReturn(...);

// chiamare metodo di default vero
when(inter.metodoVero()).thenCallRealMethod();

assertThat(inter.metodoVero()).isEqualTo(...);
```

È possibile utilizzare i mock per controllare le **invocazioni dei metodi** di un oggetto mockato, attraverso `verify`:

```java
verify(mockedclass, howMany).methodname(args);
```

Il parametro `howmany` specifica il **numero di volte** che il metodo dell'oggetto mockato deve essere **chiamato** durante l'esecuzione del test. È possibile utilizzare:
 - `times(n)` = verifica che `methodname()` si stato chiamato `n` volte.
 - `never()` = verifica che `methodname()` non sia mai stato chiamato
 - `atLeastOnece()` = verifica che `methodName()` sia chiamato almeno una volta.
 - `atLeast(n)` = verifica che `methodName()`, venga chiamato almeno `n` volte
 - `atMost(n)` = verifica che `methodName()`, venga chiamato al massimo `n` volte

```java
// verificare che il metodo met di oggettoFinto sia chiamato tot volte
Mockito.verify(oggettoFinto, Mockito.times(4)).met();
Mockito.verify(oggettoFinto, Mockito.atLeast(2))).met();
```

È possibile verificare **l'ordine delle chiamate**, attraverso `inOrder`:

```java
// creare un oggetto inOrder con parametro tutti i mock che useremo
InOrder inOrd = inOrder(mock1, mock2, ...);

// queste chiamate dovranno avvenire in questo ordine
inOrder.verify(mock1).buongiorno();
inOrder.verify(mock1).saluta();
inOrder.verify(mock2).ciao();
```

È possibile verificare i **parametri con cui avvengono le chiamate**, attraverso `ArgumentCaptor`:

```java
ArgumentCaptor<Puffo> arg = ArgumentCaptor.forClass(Puffo.class); verify(mock).doSomething(arg.capture());

assertThat(arg.getValue().getColore()).isEqualTo("blu");
```

## Spy

Lo **spy** è una tecnica simile al _mocking_, ma che al posto di utilizzare oggetti completamente _finti_ e _"svuotati"_, permette di creare _spy objects_ a partire da oggetti reali, ovvero **un oggetto che ha le stesse funzioni dell'oggetto originale**.

Questo oggetto può essere utilizzato (esattamente come per il mocking) per eseguire il **tracciamento delle chiamate** (`verify`, ...) ai suoi metodi ed eventualmente **mockare** (`when`...) alcuni comportamenti.

Un oggetto spy continuerà a chiamare i **metodi reali**, se non **diversamente specificato**.

```java
Puffo puffo = spy(new Puffo());

puffo.saluta(); // viene chiamato il metodo vero

when(puffo.getColore()).thenReturn("blu"); // viene mockato solo questo metodo

// sono utilizzabili tutte le tecniche utilizzabili con oggetti mockati
verify(...);
assertThat(...);
```

## Dependency injection

Per facilità di testing, spesso è necessario sostituire le **dipendenze** dell'oggetto da testare con **oggetti finti**.

Mockito mette a disposizione il decoratore `@InjectMocks`, che appunto **inietta** delle dipendenze dentro l'oggetto, in due modi:
 - attraverso il **costruttore**, prende quello più _"largo"_, con più parametri. Se è presente un `@Mock` che rispetta l'argomento viene passato quello, altrimenti `null`
 - quando non esiste un costruttore Mockito sfrutta le **reflections** (i campi da iniettare **non devono** essere `final`), iniettando le dipendenze

Per utilizzare `@InjectMocks` la classe deve avere il **decoratore** `@ExtendWith(MockitoExtension.class)`.

```java
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class MazziereTest {

    @Mock
    Dipendenza dip; // dipendenza che verrà iniettata nell'oggetto SUT

    @InjectMocks
    Puffo SUT; // oggetto da testare

	...
}
```

## Decoratori utili

In alcune istruzioni siano **comuni a tutti i test**, è possibile utilizzare un metodo decorato con `@BeforeEach`.

```java
import org.junit.jupiter.api.BeforeEach;

@BeforeEach
void init() {
	// viene eseguito prima di ogni test
    Mockito.when(dip.metodo()).thenReturn(42);
    SUT.metodoComuneATuttiITest();
}
```

È possibile **racchiudere** alcuni test di una classe in alcuni **gruppi**, ad esempio per eseguire un `@BeforeEach`, creando una **classe innestata** nella classe di test decorata con `@Nested`.

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

Viene sollevata un eccezione in caso vengano dichiarati delle STUB (`when(...)`) **non necessarie**, che non vengono mai chiamate. Sono possibili dei casi in cui _non si è certi_ di quale oggetto verrà chiamato, quindi alcuni metodi potrebbero **non essere** chiamati. Per non alzare questa eccezione è possibile utilizzare `@MockitoSettings(strictness = Strictness.LENIENT)`.

```java
@MockitoSettings(strictness = Strictness.LENIENT)
class Test {
	void gameWinnerTest(...) {
	    when(gioc1.getName()).thenReturn("g1");
	    when(gioc2.getName()).thenReturn("g2");

		...

		// verrà chiamato getName solo su uno tra g1 e g2
		// quindi verrebbe alzata l'eccezione UnnecessaryStubbingException
	    assertThat(getWinner().getName()).isEqualTo(...);
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
