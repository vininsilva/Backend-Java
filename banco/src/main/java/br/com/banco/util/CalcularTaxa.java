package br.com.banco.util;

import java.math.BigDecimal;

public class CalcularTaxa {
	
	public BigDecimal calculaTaxa(BigDecimal valor, BigDecimal saldo) {
		
		if (valor.compareTo(new BigDecimal("100.0")) <= 0) {
		    return BigDecimal.ZERO;
		} else if (valor.compareTo(new BigDecimal("300.0")) <= 0) {
		    return saldo.multiply(new BigDecimal("0.004"));
		} else {
		    return saldo.multiply(new BigDecimal("0.01"));
		}

	}
	
}