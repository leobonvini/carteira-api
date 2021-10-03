package br.com.alura.carteira.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.carteira.dto.TransacaoDTO;
import br.com.alura.carteira.dto.TransacaoFormDTO;
import br.com.alura.carteira.service.TransacaoService;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

	
	@Autowired
	private TransacaoService service;

	@GetMapping
	public List<TransacaoDTO> listar() {
		return service.listar();

	}

	@PostMapping
	public void cadastrar(@RequestBody @Valid TransacaoFormDTO dto) {
		service.cadastrar(dto);

	}
}
