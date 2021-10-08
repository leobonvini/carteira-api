package br.com.alura.carteira.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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
	public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody @Valid UsuarioFormDTO dto,
			UriComponentsBuilder uriBuilder) {
		UsuarioDTO usuarioDTO = service.cadastrar(dto);
		
		URI uri = uriBuilder
				.path("/transacoes/{id}")
				.buildAndExpand(usuarioDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(usuarioDTO);
		
	}
	
}
