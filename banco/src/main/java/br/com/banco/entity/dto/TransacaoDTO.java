package br.com.banco.entity.dto;

import java.math.BigDecimal;
import java.util.Date;

import br.com.banco.entity.Cliente;

public class TransacaoDTO {
	
	private int idTransacao;
	
	private Cliente cliente;
	
	private String tipoTransacao;
	
	private BigDecimal valor;
	
	private Date data;

	public int getIdTransacao() {
		return idTransacao;
	}

	public void setIdTransacao(int idTransacao) {
		this.idTransacao = idTransacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(String tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "TransacaoDTO [idTransacao=" + idTransacao + ", cliente=" + cliente + ", tipoTransacao=" + tipoTransacao
				+ ", valor=" + valor + ", data=" + data + "]";
	}

}
