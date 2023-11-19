package it.unimi.di.sweng.blackjack;

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
public class MazziereTest {
    @Mock
    MultiMazzo mockedMazzo;
    @InjectMocks
    Mazziere SUT;

    @Nested
    class InjectedTests {

        @BeforeEach
        void init() {
            Mockito.when(mockedMazzo.draw())
                    .thenAnswer(AdditionalAnswers.returnsElementsOf(
                            TestCardUtils.fromStringList("0S 4C QD 9S")));
            SUT.carteIniziali();
        }

        @Test
        void getCardsTest() {
            assertThat(SUT.getCards())
                    .toIterable()
                    .containsExactlyInAnyOrder(
                        TestCardUtils.toCard("0S"), TestCardUtils.toCard("4C"));
        }

        @Test
        void hitCard() {
            assertThat(SUT.hit()).isEqualTo(TestCardUtils.toCard("QD"));
        }

        @Test
        void getCartaScopertaTest() {
            assertThat(SUT.getCartaScoperta()).isEqualTo(TestCardUtils.toCard("0S"));
        }

    }

    @Nested
    class PartiallyInjectedTests {
        @ParameterizedTest
        @CsvSource({
                "26,7C 6C 3D QC 4C KS 5S 8C 7D",
                "18,8C KC 6D QC 4C",
                "17,7D KC 6D QC 4C",
                "23,3D AD 9D QC 4C",
        })
        void giocaTest(int puntiRisultato, String carte) {
            Mockito.when(mockedMazzo.draw())
                    .thenAnswer(AdditionalAnswers.returnsElementsOf(
                            TestCardUtils.fromStringList(carte)));

            SUT.carteIniziali();
            SUT.gioca();
            assertThat(SUT.getPunti()).isEqualTo(puntiRisultato);
        }
    }

    @Test
    void getNameTest() {
        assertThat(SUT.getName()).isEqualTo("Mazziere");
    }

}
