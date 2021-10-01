package br.com.alura.carteira.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.carteira.dto.TransacaoDTO;
import br.com.alura.carteira.dto.TransacaoFormDTO;
import br.com.alura.carteira.modelo.Transacao;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

	private List<Transacao> transacoes = new ArrayList<>();
	ModelMapper modelMapper = new ModelMapper();

	@GetMapping
	public List<TransacaoDTO> listar() {

		return transacoes.stream().map(t -> modelMapper.map(t, TransacaoDTO.class)).collect(Collectors.toList());

	}

	@PostMapping
	public void cadastrar(@RequestBody @Valid TransacaoFormDTO dto) {

		Transacao transacao = modelMapper.map(dto, Transacao.class);
		transacoes.add(transacao);

	}
}
