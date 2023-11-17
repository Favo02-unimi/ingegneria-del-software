package it.unimi.di.sweng.blackjack;

import it.unimi.di.sweng.blackjack.strategie.BetweenThirteenAndSixteenAndMoreThanSevenStrategy;
import it.unimi.di.sweng.blackjack.strategie.LessThanElevenStrategy;
import it.unimi.di.sweng.blackjack.strategie.Strategia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SfidanteTest {

    @Mock
    Mazziere mazziereMockato;

    @InjectMocks
    Sfidante SUT;

    @Nested
    class InjectedTests {

        @BeforeEach
        void init() {
            Mockito.when(mazziereMockato.hit())
                    .thenAnswer(AdditionalAnswers.returnsElementsOf(
                            TestCardUtils.fromStringList("6C 3D QC 4C KS 5S 8C 7D")));

            SUT = new Sfidante("Giocatore", mazziereMockato);
            SUT.carteIniziali();
        }

        @Test
        void getCardsTest() {
            assertThat(SUT.getCards())
                    .toIterable()
                    .containsExactlyInAnyOrder(
                            TestCardUtils.toCard("6C"), TestCardUtils.toCard("3D"));
        }

    }

    @Nested
    class PartiallyInjectedTests {
        @ParameterizedTest
        @CsvSource({
                "19,7C,6C 3D QC 4C KS 5S 8C 7D",
                "26,8C,KC 6D QC 4C",
                "16,7D,KC 6D QC 4C",
                "20,3D,AD 9D QC 4C",
        })
        void giocaTest(int puntiRisultato, String cartaMazziere, String carte) {
            Mockito.when(mazziereMockato.getCartaScoperta())
                    .thenReturn(TestCardUtils.toCard(cartaMazziere));
            Mockito.when(mazziereMockato.hit())
                    .thenAnswer(AdditionalAnswers.returnsElementsOf(
                            TestCardUtils.fromStringList(carte)));
            SUT.setStrategia(
                    new LessThanElevenStrategy(
                            new BetweenThirteenAndSixteenAndMoreThanSevenStrategy(
                                    Strategia.STAI)));

            SUT.gioca();
            assertThat(SUT.getPunti()).isEqualTo(puntiRisultato);
        }
    }

    @Test
    void getNameTest() {
        SUT = new Sfidante("Giocatore", mazziereMockato);
        assertThat(SUT.getName()).isEqualTo("Giocatore");
    }

}
