package br.com.alura.carteira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.alura.carteira.dto.TransacaoDTO;
import br.com.alura.carteira.dto.TransacaoFormDTO;
import br.com.alura.carteira.modelo.Transacao;

@Service
public class TransacaoService {
	
	private List<Transacao> transacoes = new ArrayList<>();
	ModelMapper modelMapper = new ModelMapper();
	
	public List<TransacaoDTO> listar(){
		return transacoes.stream().map(t -> modelMapper.map(t, TransacaoDTO.class)).collect(Collectors.toList());
	}

	public void cadastrar(TransacaoFormDTO dto) {
		
		Transacao transacao = modelMapper.map(dto, Transacao.class);
		transacoes.add(transacao);
	}
}
