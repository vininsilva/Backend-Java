package br.com.banco.entity.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ClienteDTO {
	
	private int id;
	
	private String nome;

	private boolean planoExclusive;
	
	private BigDecimal saldo;
	
	private String conta;
	
	private Date dataDeNascimento;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isPlanoExclusive() {
		return planoExclusive;
	}

	public void setPlanoExclusive(boolean planoExclusive) {
		this.planoExclusive = planoExclusive;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	@Override
	public String toString() {
		return "Cliente [nome=" + nome + ", planoExclusive=" + planoExclusive + ", saldo=" + saldo + ", conta=" + conta
				+ ", dataDeNascimento=" + dataDeNascimento + "]";
	}

}
