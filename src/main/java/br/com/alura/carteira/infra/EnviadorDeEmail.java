package br.com.alura.carteira.infra;

import org.springframework.scheduling.annotation.Async;

public interface EnviadorDeEmail {

	void enviarEmail(String destinario, String assunto, String mensagem);

}