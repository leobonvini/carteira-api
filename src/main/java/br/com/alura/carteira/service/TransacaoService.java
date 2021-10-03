package br.com.alura.carteira.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.carteira.dto.TransacaoDTO;
import br.com.alura.carteira.dto.TransacaoFormDTO;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.repository.TransacaoRepository;

@Service
public class TransacaoService {
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	ModelMapper modelMapper = new ModelMapper();
	
	public List<TransacaoDTO> listar(){
		List<Transacao> transacoes = transacaoRepository.findAll();
		return transacoes.stream().map(t -> modelMapper.map(t, TransacaoDTO.class)).collect(Collectors.toList());
	}

	public void cadastrar(TransacaoFormDTO dto) {
		
		Transacao transacao = modelMapper.map(dto, Transacao.class);
		transacaoRepository.save(transacao);
	}
}
