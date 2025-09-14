import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;

import com.example.DigitalWallet;



import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.Arguments;

import com.example.DigitalWallet;

class EstornoTest {

    static Stream<Arguments> dadosParaEstorno() {
        return Stream.of(
            Arguments.of(200.0, 20.0, 220.0),
            Arguments.of(10.0,  5.5,  15.5),
            Arguments.of(0.0,   0.99, 0.99)
        );
    }

    @ParameterizedTest
    @MethodSource("dadosParaEstorno")
    void deveRealizarEstornoComDadosValidos(double saldoInicial, double valorEstorno, double saldoFinalEsperado) {
        DigitalWallet carteira = new DigitalWallet("Adrielly", saldoInicial);
        carteira.unlock();
        carteira.verify();

        assumeTrue(!carteira.isLocked() && carteira.isVerified());

        carteira.refund(valorEstorno);

        assertEquals(saldoFinalEsperado, carteira.getBalance());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-5.0, 0.0, -100.25})
    void deveLancarExcecaoParaValoresInvalidosDeEstorno(double valorInvalido) {
        DigitalWallet carteira = new DigitalWallet("Adrielly", 50.0);
        carteira.verify();
        carteira.unlock();

        assumeTrue(!carteira.isLocked() && carteira.isVerified());

        assertThrows(IllegalArgumentException.class, () -> {
            carteira.refund(valorInvalido);
        });
    }

    @Test
    void deveLancarExcecaoSeCarteiraNaoVerificadaOuBloqueada() {
        DigitalWallet carteira = new DigitalWallet("Adrielly", 80.0);

        assertThrows(IllegalStateException.class, () -> {
            carteira.refund(25.0);
        });
    }
}
