package br.com.banco.util;

import java.util.Random;

public class GeradorDeConta {
	
	public String geradorDeConta() {
		
		int gerador = new Random().nextInt(10000);
	    String numeroGerado = String.format("%04d", gerador);

	    return numeroGerado;
	}
	
    

}
