package br.com.banco.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.banco.entity.Cliente;
import br.com.banco.entity.Transacao;
import br.com.banco.entity.dto.ClienteDTO;
import br.com.banco.entity.dto.TransacaoDTO;
import br.com.banco.repository.BancoRepository;
import br.com.banco.repository.TransacaoRepository;
import br.com.banco.service.BancoService;
import br.com.banco.util.CalcularTaxa;
import br.com.banco.util.GeradorDeConta;

@Service
public class BancoServiceImpl implements BancoService {

	@Autowired
	private BancoRepository repository;

	@Autowired
	private TransacaoRepository transacaoRepository;

	public List<ClienteDTO> clientes() {

		try {
			List<Cliente> listaClientes = repository.findAll();
			List<ClienteDTO> listaClientesDTO = new ArrayList<>();

			for (Cliente cliente : listaClientes) {
				ClienteDTO clienteDTO = new ClienteDTO();
				clienteDTO.setId(cliente.getId());
				clienteDTO.setNome(cliente.getNome());
				clienteDTO.setPlanoExclusive(cliente.isPlanoExclusive());
				clienteDTO.setConta(cliente.getConta());
				clienteDTO.setSaldo(cliente.getSaldo());
				clienteDTO.setDataDeNascimento(cliente.getDataDeNascimento());
				listaClientesDTO.add(clienteDTO);
			}
			return listaClientesDTO;

		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao buscar os clientes.", e);
		}
	}

	public List<TransacaoDTO> historicoTranscacao() {

		try {
			List<Transacao> historico = transacaoRepository.findAll();
			List<TransacaoDTO> historicoDTO = new ArrayList<>();

			for (Transacao listHistorico : historico) {
				TransacaoDTO trasacaoDTO = new TransacaoDTO();
				trasacaoDTO.setIdTransacao(listHistorico.getIdTransacao());
				trasacaoDTO.setCliente(listHistorico.getCliente());
				trasacaoDTO.setTipoTransacao(listHistorico.getTipoTransacao());
				trasacaoDTO.setData(listHistorico.getData());
				trasacaoDTO.setValor(listHistorico.getValor());
				historicoDTO.add(trasacaoDTO);
			}
			return historicoDTO;

		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao buscar o histórico.", e);
		}
	}

	public Cliente cadastrarCliente(ClienteDTO cliente) {

		try {
			GeradorDeConta gerador = new GeradorDeConta();

			Cliente novoCliente = new Cliente(cliente.getNome(), 
					cliente.isPlanoExclusive(),
					cliente.getSaldo(),
					gerador.geradorDeConta(),
					cliente.getDataDeNascimento());
			return repository.save(novoCliente);

		} catch (Exception e) {
			throw new RuntimeException("Erro ao cadastrar o cliente: " + e.getMessage(), e);
		}
	}

	public Cliente depositaSaldo(int clienteId, BigDecimal valor) {
		try {

			Cliente cliente = repository.findById(clienteId).get();

			if (cliente == null) {
				throw new IllegalArgumentException("Cliente não encontrado.");
			}

			BigDecimal saldoAtual = cliente.getSaldo();

			if (valor.compareTo(BigDecimal.ZERO) < 0) {
				throw new IllegalArgumentException("Valor inválido para depósito.");
			}

			BigDecimal novoSaldo = saldoAtual.add(valor);
			cliente.setSaldo(novoSaldo);
			repository.save(cliente);

			Transacao transacaoSaque = new Transacao();
			transacaoSaque.setCliente(cliente);
			transacaoSaque.setTipoTransacao("DEPOSITO");
			transacaoSaque.setValor(valor);
			transacaoRepository.save(transacaoSaque);

			return cliente;

		} catch (Exception e) {
			throw new RuntimeException("Erro ao depositar saldo: " + e.getMessage(), e);
		}
	}

	public Cliente sacarSaldo(int clienteId, BigDecimal valor) {
		try {

			Cliente cliente = repository.findById(clienteId).get();

			if (cliente == null) {
				throw new IllegalArgumentException("Cliente não encontrado.");
			}

			BigDecimal saldoAtual = cliente.getSaldo();

			if (saldoAtual.compareTo(valor) < 0) {
				throw new IllegalArgumentException("Saldo insuficiente para realizar o saque.");
			}

			BigDecimal novoSaldo = saldoAtual.subtract(valor);
			if (cliente.isPlanoExclusive()) {
				cliente.setSaldo(novoSaldo);
				repository.save(cliente);	
			} else {
				CalcularTaxa taxa = new CalcularTaxa();
				cliente.setSaldo(novoSaldo.subtract(taxa.calculaTaxa(valor, novoSaldo)));
				repository.save(cliente);
			}

			Transacao transacaoSaque = new Transacao();
			transacaoSaque.setCliente(cliente);
			transacaoSaque.setTipoTransacao("SAQUE");
			transacaoSaque.setValor(valor);
			transacaoRepository.save(transacaoSaque);

			return cliente;

		} catch (Exception e) {
			throw new RuntimeException("Erro ao sacar saldo: " + e.getMessage(), e);
		}

	}

}

