import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.DigitalWallet;

public class Pagamento {

    @ParameterizedTest
    @CsvSource({
        "120.0, 20.0, true",
        "40.0, 60.0, false",
        "25.0, 25.0, true"
    })
    void deveExecutarPagamentoComSucessoSeCarteiraValida(double saldoInicial, double valorPagamento, boolean resultadoEsperado) {
        DigitalWallet carteira = new DigitalWallet("Adrielly", saldoInicial);
        carteira.verify();
        carteira.unlock();

        assumeTrue(carteira.isVerified() && !carteira.isLocked());

        boolean resultado = carteira.pay(valorPagamento);
        assertEquals(resultadoEsperado, resultado);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-50.0, 0.0, -0.01})
    void deveLancarErroParaPagamentosInvalidos(double valorInvalido) {
        DigitalWallet carteira = new DigitalWallet("Adrielly", 200.0);
        carteira.verify();
        carteira.unlock();

        assumeTrue(carteira.isVerified() && !carteira.isLocked());

        assertThrows(IllegalArgumentException.class, () -> {
            carteira.pay(valorInvalido);
        });
    }

    @Test
    void naoDevePermitirPagamentoSeCarteiraNaoVerificadaOuBloqueada() {
        DigitalWallet carteira = new DigitalWallet("Adrielly", 150.0);

        assertThrows(IllegalStateException.class, () -> {
            carteira.pay(50.0);
        });
    }
}
