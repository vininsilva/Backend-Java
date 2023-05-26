package br.com.banco.service;

import java.math.BigDecimal;
import java.util.List;

import br.com.banco.entity.Cliente;
import br.com.banco.entity.dto.ClienteDTO;
import br.com.banco.entity.dto.TransacaoDTO;

public interface BancoService {
	
	public List<ClienteDTO> clientes();

	public Cliente depositaSaldo(int clienteId, BigDecimal valorSaque);
	
	public Cliente cadastrarCliente(ClienteDTO cliente);

	public List<TransacaoDTO> historicoTranscacao();

	public Cliente sacarSaldo(int clienteId, BigDecimal valorSaque);
}
