package animali;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FattoriaTest {

    @Nested
    class testAnimale {
        @Mock
        Animale cane;
        @InjectMocks
        Fattoria SUT;

        @Test
        void getVerso() {
            when(cane.getVerso()).thenReturn("bau");
            assertThat(SUT.verso()).isEqualTo("bau");
        }

        @Test
        void doVerso() {
            SUT.doVerso();
            verify(cane, times(1)).doVerso();
        }
    }

    @Nested
    class testTrattore {
        @Mock
        Trattore tr;
        @InjectMocks
        Fattoria SUT;

        @Test
        void getMarca() {
            when(tr.getMarca()).thenReturn("fiat");
            assertThat(SUT.getTratt()).isEqualTo("fiat");
        }

        @Test
        void changeMarca() {
            doAnswer((Answer<Void>) invocation -> {
                Object[] args = invocation.getArguments();
                String marca = (String) args[0];
                when(tr.getMarca()).thenReturn(marca);
                return null;
            }).when(tr).setMarca(anyString());

            SUT.setTratt("audi");
            assertThat(SUT.getTratt()).isEqualTo("audi");
        }

    }

    @Nested
    class spyTrattore {

        // dato che è astratta non deve essere un'implementazione completa
        // ma possiamo overridare solo i metodi che ci servono per il test
        abstract class FakeTrattore implements Trattore {

            private String marca;

            @Override
            public void setMarca(String marca) {
                this.marca = marca;
            }

            @Override
            public String getMarca() {
                return marca;
            }
        }

        @Spy
        FakeTrattore tr;

        @InjectMocks
        Fattoria SUT;

        @Test
        void changeMarca() {
            SUT.setTratt("audi");
            assertThat(SUT.getTratt()).isEqualTo("audi");
        }

    }


}
