import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.DigitalWallet;

class DepositoTest {

    private DigitalWallet carteiraDigital;

    @BeforeEach
    void configurar() {
        carteiraDigital = new DigitalWallet("Adrielly", 0.0);
    }

    @ParameterizedTest
    @ValueSource(doubles = {15.50, 0.10, 500.00})
    void devePermitirDepositosValidos(double valorDeposito) {
        double saldoAnterior = carteiraDigital.getBalance();
        carteiraDigital.deposit(valorDeposito);

        double saldoEsperado = saldoAnterior + valorDeposito;
        assertEquals(saldoEsperado, carteiraDigital.getBalance());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.0, -250.75})
    void deveGerarExcecaoParaValoresInvalidos(double valorInvalido) {
        assertThrows(IllegalArgumentException.class, () -> {
            carteiraDigital.deposit(valorInvalido);
        });
    }
}
