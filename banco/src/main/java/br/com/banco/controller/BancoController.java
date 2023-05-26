package br.com.banco.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.banco.entity.Cliente;
import br.com.banco.entity.dto.ClienteDTO;
import br.com.banco.entity.dto.TransacaoDTO;
import br.com.banco.service.BancoService;

@RestController
@RequestMapping("/cliente")
public class BancoController {

	@Autowired
	public BancoService service;

	//Retornar todos clientes cadastrados
	@RequestMapping(method = RequestMethod.GET)
	public List<ClienteDTO> clientes() {
		return service.clientes();
	}

	//Cadastrar um cliente
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public Cliente cadastraCliente(@RequestBody ClienteDTO cliente) {
		return service.cadastrarCliente(cliente);
	}

	//Sacar um valor que subtrai o saldo do cliente
	@RequestMapping(value = "/sacar", method = RequestMethod.PUT)
	public Cliente sacarSaldo(@RequestParam("id") int clienteId, @RequestParam("saque") BigDecimal valorSaque) {
		return service.sacarSaldo(clienteId, valorSaque);
	}

	//Depositar um valor que aumenta o saldo de um determinado cliente
	@RequestMapping(value = "/depositar", method = RequestMethod.PUT)
	public Cliente atualizaSaldo(@RequestParam("id") int clienteId, @RequestParam("deposito") BigDecimal valorDeposito) {
		return service.depositaSaldo(clienteId, valorDeposito);
	}

	//Consultar o histórico de transações de cada movimentação por Data (Saque e depósito)
	@RequestMapping(value = "/historicoTranscao", method = RequestMethod.GET)
	public List<TransacaoDTO> historicoTranscao() {
		return service.historicoTranscacao();
	}

}