import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.DigitalWallet;

class SaldoInicialTest {

    @ParameterizedTest
    @ValueSource(doubles = {5.0, 0.0, 100.0})
    void deveAceitarSaldoInicialValido(double saldo) {
        DigitalWallet carteira = new DigitalWallet("Adrielly", saldo);
        assertEquals(saldo, carteira.getBalance());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -50.0, -0.01})
    void deveLancarErroParaSaldoInicialNegativo(double saldoInvalido) {
        assertThrows(IllegalArgumentException.class, () -> {
            new DigitalWallet("Adrielly", saldoInvalido);
        });
    }
}
