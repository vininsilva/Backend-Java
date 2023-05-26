package br.com.banco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.banco.entity.Cliente;
import br.com.banco.repository.BancoRepository;
import br.com.banco.repository.TransacaoRepository;
import br.com.banco.service.impl.BancoServiceImpl;
import br.com.banco.util.CalcularTaxa;

@SpringBootTest
class BancoApplicationTests {

	@Mock
	private BancoRepository repository;

	@Mock
	private TransacaoRepository transacaoRepository;

	@InjectMocks
	private BancoServiceImpl bancoService;

	@BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saqueClientePlanoExclusivo() {

        int clienteId = 1;
        BigDecimal valor = new BigDecimal("100.00");
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setSaldo(new BigDecimal("500.00"));
        cliente.setPlanoExclusive(true);

        when(repository.findById(clienteId)).thenReturn(Optional.of(cliente));


        Cliente resultado = bancoService.sacarSaldo(clienteId, valor);


        assertNotNull(resultado);
        assertEquals(cliente.getSaldo().subtract(valor), resultado.getSaldo());
        verify(repository, times(1)).save(cliente);
    }

    @Test
    void SaqueClientePlanoNaoExclusivo() {

        int clienteId = 1;
        BigDecimal valor = new BigDecimal("100.00");
        BigDecimal taxa = new BigDecimal("1.00");
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setSaldo(new BigDecimal("500.00"));
        cliente.setPlanoExclusive(false);

        when(repository.findById(clienteId)).thenReturn(Optional.of(cliente));

        CalcularTaxa calcularTaxaMock = mock(CalcularTaxa.class);
        when(calcularTaxaMock.calculaTaxa(valor, cliente.getSaldo())).thenReturn(taxa);


        Cliente resultado = bancoService.sacarSaldo(clienteId, valor);


        assertNotNull(resultado);
        assertEquals(cliente.getSaldo().subtract(valor).subtract(taxa), resultado.getSaldo());
        verify(repository, times(1)).save(cliente);
    }

    @Test
    void saqueSaldoInsuficiente() {

        int clienteId = 1;
        BigDecimal valor = new BigDecimal("600.00");
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setSaldo(new BigDecimal("500.00"));

        when(repository.findById(clienteId)).thenReturn(Optional.of(cliente));

        assertThrows(IllegalArgumentException.class, () -> bancoService.sacarSaldo(clienteId, valor));
        verify(repository, never()).save(cliente);
    }

    @Test
    void saqueClienteNaoEncontrado() {

        int clienteId = 1;
        BigDecimal valor = new BigDecimal("100.00");
        Cliente cliente = new Cliente();
        when(repository.findById(clienteId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> bancoService.sacarSaldo(clienteId, valor));
        verify(repository, never()).save(cliente);
    }

}
