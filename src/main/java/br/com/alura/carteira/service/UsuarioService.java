package br.com.alura.carteira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.alura.carteira.dto.UsuarioDTO;
import br.com.alura.carteira.dto.UsuarioFormDTO;
import br.com.alura.carteira.modelo.Usuario;

@Service
public class UsuarioService {

	private List<Usuario> usuarios = new ArrayList<>();
	private ModelMapper modelMapper = new ModelMapper();
	
	public List<UsuarioDTO> listar() {
		return usuarios.stream().map(t -> modelMapper.map(t, UsuarioDTO.class)).collect(Collectors.toList());

	}
	public void cadastrar(UsuarioFormDTO dto) {
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		
		String senha = new Random().nextInt(999999) + "";
		usuario.setSenha(senha);
		
		usuarios.add(usuario);
		
	}
}
