## Cheatsheet parziale 1

### Indice

- [Testing](#testing)
- [Testing parametrico](#testing-parametrico)
- [Mocking](#mocking)
- [Dependency injection](#dependency-injection)
- [Decoratori `BeforeEach`, `Nested`](#decoratori-beforeeach-nested)

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

- controlli su eccezioni lanciate:
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

_Classe completa: [TestCardUtils.java](./TestCardUtils.java)_

```java
@ParameterizedTest
@CsvSource({
        "7C", "8C", "KC", "6D"
})
void carteTest(String carta) {
    assertThat(...).isEqualTo(TestCardUtils.toCard(carta));
}
```

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

È possibile verificare anche proprietà sui metodi finti.

```java
// verificare che il metodo met di oggettoFinto sia chiamato tot volte
Mockito.verify(oggettoFinto, Mockito.times(4)).met();
Mockito.verify(oggettoFinto, Mockito.atLeast(2))).met();
```

## Dependency injection

Per facilità di testing, è necessario sostituire le dipendenze dell'oggetto da testare con oggetti finti.

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

Esempi completi di utilizzo: [MazziereTest.java](./MazziereTest.java), [SfidanteTest.java](./SfidanteTest.java)
## Pattern

Work in progress...
