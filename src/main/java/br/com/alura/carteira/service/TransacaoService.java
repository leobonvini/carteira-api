package br.com.alura.carteira.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.carteira.dto.TransacaoDTO;
import br.com.alura.carteira.dto.TransacaoFormDTO;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.TransacaoRepository;
import br.com.alura.carteira.repository.UsuarioRepository;
import lombok.Getter;
import lombok.Setter;

@Service
public class TransacaoService {
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	ModelMapper modelMapper = new ModelMapper();
	
	public Page<TransacaoDTO> listar(Pageable paginacao){
		Page<Transacao> transacoes = transacaoRepository.findAll(paginacao);
		return transacoes.map(t -> modelMapper.map(t, TransacaoDTO.class));
	}

	@Transactional
	public TransacaoDTO cadastrar(TransacaoFormDTO dto) {
		Long idUsuario = dto.getUsuarioId();
		Usuario usuario = usuarioRepository.getById(idUsuario);
		
		Transacao transacao = modelMapper.map(dto, Transacao.class);
		transacao.setId(null);
		transacao.setUsuario(usuario);
		
		transacaoRepository.save(transacao);
		return modelMapper.map(transacao, TransacaoDTO.class);
	}
}
