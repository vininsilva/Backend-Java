package br.com.banco.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "NOME")
	private String nome;

	@Column(name = "PLANO")
	private boolean planoExclusive;
	
	@Column(name = "SALDO")
	private BigDecimal saldo;
	
	@Column(name = "CONTA")
	private String conta;
	
	@Column(name = "DATA_DE_NASCIMENTO")
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

	public Cliente(String nome, boolean planoExclusive, BigDecimal saldo, String conta, Date dataDeNascimento) {
		super();
		this.nome = nome;
		this.planoExclusive = planoExclusive;
		this.saldo = saldo;
		this.conta = conta;
		this.dataDeNascimento = dataDeNascimento;
	}
	
	public Cliente() {
		
	}

}
