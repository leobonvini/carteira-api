package br.com.alura.carteira.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.carteira.dto.ItemCarteiraDTO;
import br.com.alura.carteira.repository.TransacaoRepository;

@Service
public class RelatorioService {

	@Autowired
	private TransacaoRepository repository;
	
	public List<ItemCarteiraDTO> relatorioCarteiraDeInvestimentos() {
		return repository.relatorioCarteiraDeInvestimentos();
	}

}
