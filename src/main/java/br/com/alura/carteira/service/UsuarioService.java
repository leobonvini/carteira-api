package br.com.alura.carteira.service;

import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.alura.carteira.dto.UsuarioDTO;
import br.com.alura.carteira.dto.UsuarioFormDTO;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	private ModelMapper modelMapper = new ModelMapper();
	
	public Page<UsuarioDTO> listar(Pageable paginacao) {
		Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
		return usuarios.map(t -> modelMapper.map(t, UsuarioDTO.class));

	}
	public void cadastrar(UsuarioFormDTO dto) {
		
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		
		String senha = new Random().nextInt(999999) + "";
		usuario.setSenha(senha);
		
		usuarioRepository.save(usuario);
		
	}
}
