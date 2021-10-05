package br.com.alura.carteira.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.carteira.dto.UsuarioDTO;
import br.com.alura.carteira.dto.UsuarioFormDTO;
import br.com.alura.carteira.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {


	@Autowired
	private UsuarioService service;

	@GetMapping
	public Page<UsuarioDTO> listar(@PageableDefault(size = 10) Pageable paginacao) {
		return service.listar(paginacao);
		
	}

	@PostMapping
	public void cadastrar(@RequestBody @Valid UsuarioFormDTO dto) {
		service.cadastrar(dto);
	}
	
}
